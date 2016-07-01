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
package me.eccentric_nz.TARDIS.travel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.TARDISConstants;
import me.eccentric_nz.TARDIS.database.ResultSetDestinations;
import me.eccentric_nz.TARDIS.database.ResultSetHomeLocation;
import me.eccentric_nz.TARDIS.utility.TARDISNumberParsers;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * The "Hollywood" sign was among the Earth cultural items the Threshold stole
 * and moved to the town of Wormwood on the Moon. The moon was later destroyed;
 * the sign likely was also.
 *
 * @author eccentric_nz
 */
public class TARDISSaveSignInventory {

    private final TARDIS plugin;
    private final ItemStack[] terminal;
    private final List<Integer> slots = new LinkedList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 131, 4, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44));
    int id;

    public TARDISSaveSignInventory(TARDIS plugin, int id) {
        this.plugin = plugin;
        this.id = id;
        this.terminal = getItemStack();
    }

    /**
     * Constructs an inventory for the Save Sign GUI.
     *
     * @return an Array of itemStacks (an inventory)
     */
    @SuppressWarnings("deprecation")
    private ItemStack[] getItemStack() {
        HashMap<Integer, ItemStack> dests = new HashMap<Integer, ItemStack>();
        // home stack
        ItemStack his = new ItemStack(TARDISConstants.GUI_IDS.get(0), 1);
        ItemMeta him = his.getItemMeta();
        List<String> hlore = new ArrayList<String>();
        HashMap<String, Object> wherehl = new HashMap<String, Object>();
        wherehl.put("tardis_id", id);
        ResultSetHomeLocation rsh = new ResultSetHomeLocation(plugin, wherehl);
        if (rsh.resultSet()) {
            him.setDisplayName("Home");
            hlore.add(rsh.getWorld().getName());
            hlore.add("" + rsh.getX());
            hlore.add("" + rsh.getY());
            hlore.add("" + rsh.getZ());
            hlore.add(rsh.getDirection().toString());
            if (rsh.isSubmarine()) {
                hlore.add("true");
            }
        } else {
            hlore.add("Not found!");
        }
        him.setLore(hlore);
        his.setItemMeta(him);
        dests.put(0, his);
        // saved destinations
        HashMap<String, Object> did = new HashMap<String, Object>();
        did.put("tardis_id", id);
        ResultSetDestinations rsd = new ResultSetDestinations(plugin, did, true);
        int i = 1;
        ItemStack[] stack = new ItemStack[54];
        if (rsd.resultSet()) {
            ArrayList<HashMap<String, String>> data = rsd.getData();
            // cycle through saves
            for (HashMap<String, String> map : data) {
                if (map.get("type").equals("0")) {
                    if (i < 45) {
                        ItemStack is = new ItemStack(TARDISConstants.GUI_IDS.get(i), 1);
                        ItemMeta im = is.getItemMeta();
                        im.setDisplayName(map.get("dest_name"));
                        List<String> lore = new ArrayList<String>();
                        lore.add(map.get("world"));
                        lore.add(map.get("x"));
                        lore.add(map.get("y"));
                        lore.add(map.get("z"));
                        lore.add(map.get("direction"));
                        lore.add((map.get("submarine").equals("1")) ? "true" : "false");
                        im.setLore(lore);
                        is.setItemMeta(im);
                        int slot;
                        if (!map.get("slot").equals("-1")) {
                            slot = TARDISNumberParsers.parseInt(map.get("slot"));
                        } else {
                            slot = slots.get(0);
                        }
                        dests.put(slot, is);
                        slots.remove(Integer.valueOf(slot));
                        i++;
                    } else {
                        break;
                    }
                }
            }
        }

        for (Integer s = 0; s < 45; s++) {
            if (dests.containsKey(s)) {
                stack[s] = dests.get(s);
            } else {
                stack[s] = null;
            }
        }
        // add button to load TARDIS areas
        ItemStack map = new ItemStack(Material.MAP, 1);
        ItemMeta switchto = map.getItemMeta();
        switchto.setDisplayName("Load TARDIS areas");
        map.setItemMeta(switchto);
        // add button to allow rearranging saves
        ItemStack tool = new ItemStack(Material.YELLOW_FLOWER, 1);
        ItemMeta rearrange = tool.getItemMeta();
        rearrange.setDisplayName("Rearrange saves");
        tool.setItemMeta(rearrange);
        // add button to allow deleting saves
        ItemStack bucket = new ItemStack(Material.BUCKET, 1);
        ItemMeta delete = bucket.getItemMeta();
        delete.setDisplayName("Delete save");
        bucket.setItemMeta(delete);
        for (int m = 45; m < 54; m++) {
            switch (m) {
                case 45:
                    stack[m] = tool;
                    break;
                case 49:
                    stack[m] = map;
                    break;
                case 53:
                    stack[m] = bucket;
                    break;
                default:
                    stack[m] = null;
                    break;
            }
        }
        return stack;
    }

    public ItemStack[] getTerminal() {
        return terminal;
    }
}
