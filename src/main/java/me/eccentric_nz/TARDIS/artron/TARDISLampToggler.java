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
package me.eccentric_nz.TARDIS.artron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.database.ResultSetLamps;
import me.eccentric_nz.TARDIS.database.ResultSetPlayerPrefs;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author eccentric_nz
 */
public class TARDISLampToggler {

    private final TARDIS plugin;

    public TARDISLampToggler(TARDIS plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    public void flickSwitch(int id, Player player, boolean on) {
        HashMap<String, Object> wherel = new HashMap<String, Object>();
        wherel.put("tardis_id", id);
        ResultSetLamps rsl = new ResultSetLamps(plugin, wherel, true);
        List<Block> lamps = new ArrayList<Block>();
        if (rsl.resultSet()) {
            // get lamp locations
            ArrayList<HashMap<String, String>> data = rsl.getData();
            for (HashMap<String, String> map : data) {
                Location loc = plugin.getUtils().getLocationFromDB(map.get("location"), 0.0F, 0.0F);
                lamps.add(loc.getBlock());
            }
        }
        HashMap<String, Object> wherepp = new HashMap<String, Object>();
        wherepp.put("uuid", player.getUniqueId().toString());
        ResultSetPlayerPrefs rsp = new ResultSetPlayerPrefs(plugin, wherepp);
        boolean use_wool = false;
        if (rsp.resultSet()) {
            use_wool = rsp.isWoolLightsOn();
        }
        for (Block b : lamps) {
            if (on) {
                if (b.getType().equals(Material.REDSTONE_LAMP_ON)) {
                    if (use_wool) {
                        b.setType(Material.WOOL);
                        b.setData((byte) 15);
                    } else {
                        b.setType(Material.SPONGE);
                    }
                }
            } else {
                if (b.getType().equals(Material.SPONGE) || (b.getType().equals(Material.WOOL) && b.getData() == (byte) 15)) {
                    b.setType(Material.REDSTONE_LAMP_ON);
                }
            }
        }
    }
}