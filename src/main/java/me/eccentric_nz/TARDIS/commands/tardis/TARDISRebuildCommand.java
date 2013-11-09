/*
 * Copyright (C) 2013 eccentric_nz
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
package me.eccentric_nz.TARDIS.commands.tardis;

import java.util.HashMap;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.TARDISConstants;
import me.eccentric_nz.TARDIS.database.QueryFactory;
import me.eccentric_nz.TARDIS.database.ResultSetCurrentLocation;
import me.eccentric_nz.TARDIS.database.ResultSetTardis;
import me.eccentric_nz.TARDIS.database.ResultSetTravellers;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author eccentric_nz
 */
public class TARDISRebuildCommand {

    private final TARDIS plugin;

    public TARDISRebuildCommand(TARDIS plugin) {
        this.plugin = plugin;
    }

    public boolean rebuildPreset(Player player, String[] args) {
        if (player.hasPermission("tardis.rebuild")) {
            World w;
            int id;
            boolean cham = false;
            HashMap<String, Object> where = new HashMap<String, Object>();
            where.put("owner", player.getName());
            ResultSetTardis rs = new ResultSetTardis(plugin, where, "", false);
            if (!rs.resultSet()) {
                player.sendMessage(plugin.pluginName + TARDISConstants.NO_TARDIS);
                return false;
            }
            id = rs.getTardis_id();
            HashMap<String, Object> wherein = new HashMap<String, Object>();
            wherein.put("player", player.getName());
            ResultSetTravellers rst = new ResultSetTravellers(plugin, wherein, false);
            if (rst.resultSet() && args[0].equalsIgnoreCase("rebuild") && plugin.tardisHasDestination.containsKey(id)) {
                player.sendMessage(plugin.pluginName + "You cannot rebuild the TARDIS right now! Try travelling first.");
                return true;
            }
            int level = rs.getArtron_level();
            if (plugin.tardisMaterialising.contains(id) || plugin.tardisDematerialising.contains(id)) {
                player.sendMessage(plugin.pluginName + "You cannot do that while the TARDIS is materialising!");
                return true;
            }
            if (plugin.getConfig().getBoolean("chameleon")) {
                cham = rs.isChamele_on();
            }
            HashMap<String, Object> wherecl = new HashMap<String, Object>();
            wherecl.put("tardis_id", rs.getTardis_id());
            ResultSetCurrentLocation rsc = new ResultSetCurrentLocation(plugin, wherecl);
            if (!rsc.resultSet()) {
                player.sendMessage(plugin.pluginName + "Could not get the TARDIS location!");
                return true;
            }
            Location l = new Location(rsc.getWorld(), rsc.getX(), rsc.getY(), rsc.getZ());
            HashMap<String, Object> wheret = new HashMap<String, Object>();
            wheret.put("tardis_id", id);
            QueryFactory qf = new QueryFactory(plugin);
            int rebuild = plugin.getArtronConfig().getInt("random");
            if (level < rebuild) {
                player.sendMessage(plugin.pluginName + ChatColor.RED + "The TARDIS does not have enough Artron Energy to rebuild!");
                return false;
            }
            plugin.buildPB.buildPoliceBox(id, l, rsc.getDirection(), cham, player, true, false);
            player.sendMessage(plugin.pluginName + "The TARDIS Police Box was rebuilt!");
            qf.alterEnergyLevel("tardis", -rebuild, wheret, player);
            // set hidden to false
            if (rs.isHidden()) {
                HashMap<String, Object> whereh = new HashMap<String, Object>();
                whereh.put("tardis_id", id);
                HashMap<String, Object> seth = new HashMap<String, Object>();
                seth.put("hidden", 0);
                qf.doUpdate("tardis", seth, whereh);
            }
            return true;
        } else {
            player.sendMessage(plugin.pluginName + TARDISConstants.NO_PERMS_MESSAGE);
            return false;
        }
    }
}