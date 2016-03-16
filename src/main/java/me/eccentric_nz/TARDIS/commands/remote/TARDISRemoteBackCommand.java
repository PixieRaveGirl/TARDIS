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
package me.eccentric_nz.TARDIS.commands.remote;

import java.util.HashMap;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.builders.TARDISMaterialisationData;
import me.eccentric_nz.TARDIS.database.QueryFactory;
import me.eccentric_nz.TARDIS.database.ResultSetBackLocation;
import me.eccentric_nz.TARDIS.database.ResultSetCurrentLocation;
import me.eccentric_nz.TARDIS.utility.TARDISMessage;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author eccentric_nz
 */
public class TARDISRemoteBackCommand {

    private final TARDIS plugin;

    public TARDISRemoteBackCommand(TARDIS plugin) {
        this.plugin = plugin;
    }

    public boolean sendBack(CommandSender sender, int id, OfflinePlayer player) {

        // get fast return location
        HashMap<String, Object> wherebl = new HashMap<String, Object>();
        wherebl.put("tardis_id", id);
        ResultSetBackLocation rsb = new ResultSetBackLocation(plugin, wherebl);
        if (!rsb.resultSet()) {
            if (sender instanceof Player) {
                TARDISMessage.send((Player) sender, "PREV_NOT_FOUND");
            }
            return true;
        }
        HashMap<String, Object> set = new HashMap<String, Object>();
        set.put("world", rsb.getWorld().getName());
        set.put("x", rsb.getX());
        set.put("y", rsb.getY());
        set.put("z", rsb.getZ());
        set.put("direction", rsb.getDirection().toString());
        set.put("submarine", (rsb.isSubmarine()) ? 1 : 0);
        // get current police box location
        HashMap<String, Object> wherecl = new HashMap<String, Object>();
        wherecl.put("tardis_id", id);
        ResultSetCurrentLocation rsc = new ResultSetCurrentLocation(plugin, wherecl);
        if (!rsc.resultSet()) {
            if (sender instanceof Player) {
                TARDISMessage.send((Player) sender, "CURRENT_NOT_FOUND");
            }
            return true;
        }
        // set hidden false
        final QueryFactory qf = new QueryFactory(plugin);
        HashMap<String, Object> sett = new HashMap<String, Object>();
        sett.put("hidden", 0);
        HashMap<String, Object> ttid = new HashMap<String, Object>();
        ttid.put("tardis_id", id);
        qf.doUpdate("tardis", sett, ttid);
        HashMap<String, Object> tid = new HashMap<String, Object>();
        tid.put("tardis_id", id);
        qf.doUpdate("current", set, tid);
        plugin.getTrackerKeeper().getInVortex().add(id);
        // destroy the police box
        final TARDISMaterialisationData pdd = new TARDISMaterialisationData(plugin, player.getUniqueId().toString());
        pdd.setChameleon(false);
        pdd.setDirection(rsc.getDirection());
        pdd.setLocation(new Location(rsc.getWorld(), rsc.getX(), rsc.getY(), rsc.getZ()));
        pdd.setDematerialise(false);
        pdd.setPlayer(player);
        pdd.setHide(false);
        pdd.setOutside(true);
        pdd.setSubmarine(rsc.isSubmarine());
        pdd.setTardisID(id);
        pdd.setBiome(rsc.getBiome());
        plugin.getTrackerKeeper().getDematerialising().add(id);
        plugin.getPresetDestroyer().destroyPreset(pdd);
        // rebuild the police box
        final TARDISMaterialisationData pbd = new TARDISMaterialisationData(plugin, player.getUniqueId().toString());
        pbd.setChameleon(false);
        pbd.setDirection(rsb.getDirection());
        pbd.setLocation(new Location(rsb.getWorld(), rsb.getX(), rsb.getY(), rsb.getZ()));
        pbd.setMalfunction(false);
        pbd.setOutside(true);
        pbd.setPlayer(player);
        pbd.setRebuild(false);
        pbd.setSubmarine(rsb.isSubmarine());
        pbd.setTardisID(id);
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                plugin.getPresetBuilder().buildPreset(pbd);
            }
        }, 20L);
        plugin.getTrackerKeeper().getHasDestination().remove(id);
        if (plugin.getTrackerKeeper().getRescue().containsKey(id)) {
            plugin.getTrackerKeeper().getRescue().remove(id);
        }
        return true;
    }
}
