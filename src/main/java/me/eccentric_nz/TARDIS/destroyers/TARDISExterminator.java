/*
 * Copyright (C) 2014 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.TARDIS.destroyers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.builders.TARDISInteriorPostioning;
import me.eccentric_nz.TARDIS.builders.TARDISTIPSData;
import me.eccentric_nz.TARDIS.database.QueryFactory;
import me.eccentric_nz.TARDIS.database.ResultSetBlocks;
import me.eccentric_nz.TARDIS.database.ResultSetCurrentLocation;
import me.eccentric_nz.TARDIS.database.ResultSetGravity;
import me.eccentric_nz.TARDIS.database.ResultSetTardis;
import me.eccentric_nz.TARDIS.database.ResultSetTravellers;
import me.eccentric_nz.TARDIS.database.data.ReplacedBlock;
import me.eccentric_nz.TARDIS.database.data.Tardis;
import me.eccentric_nz.TARDIS.enumeration.COMPASS;
import me.eccentric_nz.TARDIS.enumeration.SCHEMATIC;
import me.eccentric_nz.TARDIS.utility.TARDISMessage;
import me.eccentric_nz.TARDIS.utility.TARDISNumberParsers;
import me.eccentric_nz.tardischunkgenerator.TARDISChunkGenerator;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

/**
 * The Daleks were a warlike race who waged war across whole civilisations and
 * races all over the universe. Advance and Attack! Attack and Destroy! Destroy
 * and Rejoice!
 *
 * @author eccentric_nz
 */
public class TARDISExterminator {

    private final TARDIS plugin;

    public TARDISExterminator(TARDIS plugin) {
        this.plugin = plugin;
    }

    public boolean exterminate(int id) {
        HashMap<String, Object> where = new HashMap<String, Object>();
        where.put("tardis_id", id);
        ResultSetTardis rs = new ResultSetTardis(plugin, where, "", false, 2);
        try {
            if (rs.resultSet()) {
                Tardis tardis = rs.getTardis();
                boolean hid = tardis.isHidden();
                String chunkLoc = tardis.getChunk();
                String owner = tardis.getOwner();
                UUID uuid = tardis.getUuid();
                int tips = tardis.getTIPS();
                boolean hasZero = (!tardis.getZero().isEmpty());
                SCHEMATIC schm = tardis.getSchematic();
                HashMap<String, Object> wherecl = new HashMap<String, Object>();
                wherecl.put("tardis_id", id);
                ResultSetCurrentLocation rsc = new ResultSetCurrentLocation(plugin, wherecl);
                if (!rsc.resultSet()) {
                    return false;
                }
                Location bb_loc = new Location(rsc.getWorld(), rsc.getX(), rsc.getY(), rsc.getZ());
                final DestroyData dd = new DestroyData(plugin, uuid.toString());
                dd.setChameleon(false);
                dd.setDirection(rsc.getDirection());
                dd.setLocation(bb_loc);
                dd.setPlayer(plugin.getServer().getOfflinePlayer(uuid));
                dd.setHide(false);
                dd.setOutside(false);
                dd.setSubmarine(rsc.isSubmarine());
                dd.setTardisID(id);
                dd.setBiome(rsc.getBiome());
                if (!hid) {
                    plugin.getPresetDestroyer().destroyPreset(dd);
                }
                cleanHashMaps(id);
                String[] chunkworld = chunkLoc.split(":");
                World cw = plugin.getServer().getWorld(chunkworld[0]);
                int restore = getRestore(cw);
                if (cw == null) {
                    plugin.debug("The server could not find the TARDIS world, has it been deleted?");
                    return false;
                }
                if (!cw.getName().contains("TARDIS_WORLD_")) {
                    plugin.getInteriorDestroyer().destroyInner(schm, id, cw, restore, owner, tips);
                }
                cleanWorlds(cw, owner);
                removeZeroRoom(tips, hasZero);
                cleanDatabase(id);
                return true;
            }
        } catch (Exception e) {
            plugin.getConsole().sendMessage(plugin.getPluginName() + "TARDIS exterminate by id error: " + e);
            return false;
        }
        return true;
    }

    /**
     * Deletes a TARDIS.
     *
     * @param player running the command.
     * @param block the block that represents the Police Box sign
     * @return true or false depending on whether the TARIS could be deleted
     */
    @SuppressWarnings("deprecation")
    public boolean exterminate(final Player player, Block block) {
        int signx = 0, signz = 0;
        Location sign_loc = block.getLocation();
        HashMap<String, Object> where = new HashMap<String, Object>();
        ResultSetTardis rs;
        if (player.hasPermission("tardis.delete")) {
            Block blockbehind = null;
            byte data = block.getData();
            if (data == 4) {
                blockbehind = block.getRelative(BlockFace.EAST, 2);
            }
            if (data == 5) {
                blockbehind = block.getRelative(BlockFace.WEST, 2);
            }
            if (data == 3) {
                blockbehind = block.getRelative(BlockFace.NORTH, 2);
            }
            if (data == 2) {
                blockbehind = block.getRelative(BlockFace.SOUTH, 2);
            }
            if (blockbehind != null) {
                Block blockDown = blockbehind.getRelative(BlockFace.DOWN, 2);
                Location bd_loc = blockDown.getLocation();
                HashMap<String, Object> wherecl = new HashMap<String, Object>();
                wherecl.put("world", bd_loc.getWorld().getName());
                wherecl.put("x", bd_loc.getBlockX());
                wherecl.put("y", bd_loc.getBlockY());
                wherecl.put("z", bd_loc.getBlockZ());
                ResultSetCurrentLocation rsc = new ResultSetCurrentLocation(plugin, wherecl);
                if (!rsc.resultSet()) {
                    TARDISMessage.send(player, "CURRENT_NOT_FOUND");
                    return false;
                }
                where.put("tardis_id", rsc.getTardis_id());
                rs = new ResultSetTardis(plugin, where, "", false, 2);
            } else {
                TARDISMessage.send(player, "CURRENT_NOT_FOUND");
                return false;
            }
        } else {
            where.put("uuid", player.getUniqueId().toString());
            rs = new ResultSetTardis(plugin, where, "", false, 0);
        }
        if (rs.resultSet()) {
            Tardis tardis = rs.getTardis();
            final int id = tardis.getTardis_id();
            String owner = tardis.getOwner();
            String chunkLoc = tardis.getChunk();
            int tips = tardis.getTIPS();
            boolean hasZero = (!tardis.getZero().isEmpty());
            SCHEMATIC schm = tardis.getSchematic();
            // need to check that a player is not currently in the TARDIS
            if (player.hasPermission("tardis.delete")) {
                HashMap<String, Object> travid = new HashMap<String, Object>();
                travid.put("tardis_id", id);
                ResultSetTravellers rst = new ResultSetTravellers(plugin, travid, false);
                if (rst.resultSet()) {
                    TARDISMessage.send(player, "TARDIS_NO_DELETE");
                    return false;
                }
            }
            // check the sign location
            HashMap<String, Object> wherecl = new HashMap<String, Object>();
            wherecl.put("tardis_id", id);
            ResultSetCurrentLocation rsc = new ResultSetCurrentLocation(plugin, wherecl);
            if (!rsc.resultSet()) {
                TARDISMessage.send(player, "CURRENT_NOT_FOUND");
                return false;
            }
            Location bb_loc = new Location(rsc.getWorld(), rsc.getX(), rsc.getY(), rsc.getZ());
            // get TARDIS direction
            COMPASS d = rsc.getDirection();
            switch (d) {
                case EAST:
                    signx = -2;
                    signz = 0;
                    break;
                case SOUTH:
                    signx = 0;
                    signz = -2;
                    break;
                case WEST:
                    signx = 2;
                    signz = 0;
                    break;
                case NORTH:
                    signx = 0;
                    signz = 2;
                    break;
            }
            int signy = -2;
            // if the sign was on the TARDIS destroy the TARDIS!
            final DestroyData dd = new DestroyData(plugin, player.getUniqueId().toString());
            dd.setChameleon(false);
            dd.setDirection(d);
            dd.setLocation(bb_loc);
            dd.setPlayer(player);
            dd.setHide(true);
            dd.setOutside(false);
            dd.setSubmarine(rsc.isSubmarine());
            dd.setTardisID(id);
            dd.setBiome(rsc.getBiome());
            if (sign_loc.getBlockX() == bb_loc.getBlockX() + signx && sign_loc.getBlockY() + signy == bb_loc.getBlockY() && sign_loc.getBlockZ() == bb_loc.getBlockZ() + signz) {
                if (!tardis.isHidden()) {
                    // remove Police Box
                    plugin.getPresetDestroyer().destroyPreset(dd);
                } else {
                    // restore biome
                    plugin.getUtils().restoreBiome(bb_loc, rsc.getBiome());
                }
                String[] chunkworld = chunkLoc.split(":");
                World cw = plugin.getServer().getWorld(chunkworld[0]);
                if (cw == null) {
                    TARDISMessage.send(player, "WORLD_DELETED");
                    return true;
                }
                int restore = getRestore(cw);
                if (!cw.getName().contains("TARDIS_WORLD_")) {
                    plugin.getInteriorDestroyer().destroyInner(schm, id, cw, restore, owner, tips);
                }
                cleanWorlds(cw, owner);
                removeZeroRoom(tips, hasZero);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        cleanDatabase(id);
                        TARDISMessage.send(player, "TARDIS_EXTERMINATED");
                    }
                }, 40L);
                return false;
            } else {
                // cancel the event because it's not the player's TARDIS
                TARDISMessage.send(player, "NOT_OWNER");
                return false;
            }
        } else {
            TARDISMessage.send(player, "NO_GRIEF");
            return false;
        }
    }

    private int getRestore(World w) {
        World.Environment env = w.getEnvironment();
        if (w.getWorldType() == WorldType.FLAT || w.getName().equals("TARDIS_TimeVortex") || w.getGenerator() instanceof TARDISChunkGenerator) {
            return 0;
        }
        switch (env) {
            case NETHER:
                return 87;
            case THE_END:
                return 121;
            default:
                return 1;
        }
    }

    public void cleanHashMaps(int id) {
        // remove protected blocks from the HashMap
        HashMap<String, Object> whereb = new HashMap<String, Object>();
        whereb.put("tardis_id", id);
        ResultSetBlocks rsb = new ResultSetBlocks(plugin, whereb, true);
        if (rsb.resultSet()) {
            for (ReplacedBlock rp : rsb.getData()) {
                plugin.getGeneralKeeper().getProtectBlockMap().remove(rp.getStrLocation());
            }
        }
        // remove gravity well blocks from the HashMap
        HashMap<String, Object> whereg = new HashMap<String, Object>();
        whereg.put("tardis_id", id);
        ResultSetGravity rsg = new ResultSetGravity(plugin, whereg, true);
        if (rsg.resultSet()) {
            ArrayList<HashMap<String, String>> gdata = rsg.getData();
            for (HashMap<String, String> gmap : gdata) {
                int direction = TARDISNumberParsers.parseInt(gmap.get("direction"));
                switch (direction) {
                    case 1:
                        plugin.getGeneralKeeper().getGravityUpList().remove(gmap.get("location"));
                        break;
                    case 2:
                        plugin.getGeneralKeeper().getGravityNorthList().remove(gmap.get("location"));
                        break;
                    case 3:
                        plugin.getGeneralKeeper().getGravityWestList().remove(gmap.get("location"));
                        break;
                    case 4:
                        plugin.getGeneralKeeper().getGravitySouthList().remove(gmap.get("location"));
                        break;
                    case 5:
                        plugin.getGeneralKeeper().getGravityEastList().remove(gmap.get("location"));
                        break;
                    default:
                        plugin.getGeneralKeeper().getGravityDownList().remove(gmap.get("location"));
                        break;
                }
            }
        }
    }

    public void cleanDatabase(int id) {
        QueryFactory qf = new QueryFactory(plugin);
        List<String> tables = Arrays.asList("ars", "back", "blocks", "chameleon", "chunks", "controls", "current", "destinations", "doors", "gravity_well", "homes", "junk", "lamps", "next", "tardis", "thevoid", "travellers", "vaults");
        // remove record from database tables
        for (String table : tables) {
            HashMap<String, Object> where = new HashMap<String, Object>();
            where.put("tardis_id", id);
            qf.doDelete(table, where);
        }
    }

    private void cleanWorlds(World w, String owner) {
        // remove world guard region protection
        if (plugin.isWorldGuardOnServer() && plugin.getConfig().getBoolean("preferences.use_worldguard")) {
            plugin.getWorldGuardUtils().removeRegion(w, owner);
            plugin.getWorldGuardUtils().removeRoomRegion(w, owner, "renderer");
        }
        // unload and remove the world if it's a TARDIS_WORLD_ world
        if (w.getName().contains("TARDIS_WORLD_")) {
            String name = w.getName();
            List<Player> players = w.getPlayers();
            Location spawn = plugin.getServer().getWorlds().get(0).getSpawnLocation();
            for (Player p : players) {
                TARDISMessage.send(p, "WORLD_RESET");
                p.teleport(spawn);
            }
            if (plugin.isMVOnServer()) {
                plugin.getServer().dispatchCommand(plugin.getConsole(), "mv remove " + name);
            }
            if (plugin.getPM().isPluginEnabled("MultiWorld")) {
                plugin.getServer().dispatchCommand(plugin.getConsole(), "mw unload " + name);
                plugin.getServer().dispatchCommand(plugin.getConsole(), "mw delete " + name);
            }
            if (plugin.getPM().isPluginEnabled("My Worlds")) {
                plugin.getServer().dispatchCommand(plugin.getConsole(), "myworlds unload " + name);
            }
            if (plugin.getPM().isPluginEnabled("WorldBorder")) {
                // wb <world> clear
                plugin.getServer().dispatchCommand(plugin.getConsole(), "wb " + name + " clear");
            }
            plugin.getServer().unloadWorld(w, true);
            File world_folder = new File(plugin.getServer().getWorldContainer() + File.separator + name + File.separator);
            if (!deleteFolder(world_folder)) {
                plugin.debug("Could not delete world <" + name + ">");
            }
        }
    }

    public static boolean deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { //some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else if (!f.delete()) {
                    TARDIS.plugin.debug("Could not delete file");
                }
            }
        }
        folder.delete();
        return true;
    }

    private void removeZeroRoom(int slot, boolean hasZero) {
        if (slot != -1 && plugin.getConfig().getBoolean("allow.zero_room") && hasZero) {
            TARDISInteriorPostioning tips = new TARDISInteriorPostioning(plugin);
            TARDISTIPSData coords = tips.getTIPSData(slot);
            World w = plugin.getServer().getWorld("TARDIS_Zero_Room");
            if (w != null) {
                tips.reclaimChunks(w, coords);
            }
        }
    }
}
