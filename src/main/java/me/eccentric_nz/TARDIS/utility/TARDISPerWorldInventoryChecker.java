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
package me.eccentric_nz.TARDIS.utility;

import me.gnat008.perworldinventory.PerWorldInventory;
import me.gnat008.perworldinventory.groups.Group;
import me.gnat008.perworldinventory.groups.GroupManager;
import org.bukkit.Bukkit;

public class TARDISPerWorldInventoryChecker {

    public static boolean checkWorldsCanShare(String from, String to) {
        PerWorldInventory pwi = (PerWorldInventory) Bukkit.getServer().getPluginManager().getPlugin("PerWorldInventory");
        GroupManager gm = pwi.getGroupManager();
        Group gf = gm.getGroupFromWorld(from);
        return (gf.containsWorld(to));
    }
}
