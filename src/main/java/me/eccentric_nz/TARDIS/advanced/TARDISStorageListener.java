/*
 * Copyright (C) 2016 eccentric_nz
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
package me.eccentric_nz.TARDIS.advanced;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.database.QueryFactory;
import me.eccentric_nz.TARDIS.database.ResultSetDiskStorage;
import me.eccentric_nz.TARDIS.enumeration.DISK_CIRCUIT;
import me.eccentric_nz.TARDIS.enumeration.STORAGE;
import me.eccentric_nz.TARDIS.listeners.TARDISMenuListener;
import me.eccentric_nz.TARDIS.utility.TARDISMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Banshee Circuits were components of TARDISes, emergency defence mechanisms
 * used as a last resort when all other systems had failed. They allowed a
 * TARDIS to use whatever resources were available to ensure the survival of the
 * ship and its crew.
 *
 * @author eccentric_nz
 */
public class TARDISStorageListener extends TARDISMenuListener implements Listener {

    private final TARDIS plugin;
    List<String> inv_titles = new ArrayList<String>();
    private final List<Material> onlythese = new ArrayList<Material>();

    public TARDISStorageListener(TARDIS plugin) {
        super(plugin);
        this.plugin = plugin;
        for (STORAGE s : STORAGE.values()) {
            this.inv_titles.add(s.getTitle());
        }
        for (DISK_CIRCUIT dc : DISK_CIRCUIT.values()) {
            if (!onlythese.contains(dc.getMaterial())) {
                onlythese.add(dc.getMaterial());
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onDiskStorageClose(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();
        String title = inv.getTitle();
        if (inv_titles.contains(title)) {
            // which inventory screen is it?
            String[] split = title.split(" ");
            String tmp = split[0].toUpperCase(Locale.ENGLISH);
            if (split.length > 2) {
                tmp = tmp + "_" + split[2];
            }
            STORAGE store = STORAGE.valueOf(tmp);
            saveCurrentStorage(inv, store.getTable(), (Player) event.getPlayer());
        } else if (!title.equals("§4TARDIS Console")) {
            /**
             * Fix incorrect Bukkit behaviour
             *
             * https://bukkit.atlassian.net/browse/BUKKIT-2788
             */
            int isze = (inv.getType().equals(InventoryType.ANVIL)) ? 2 : inv.getSize();
            // scan the inventory for area disks and spit them out
            for (int i = 0; i < isze; i++) {
                ItemStack stack = inv.getItem(i);
                if (stack != null && stack.getType().equals(Material.RECORD_3) && stack.hasItemMeta()) {
                    ItemMeta ims = stack.getItemMeta();
                    if (ims.hasDisplayName() && ims.getDisplayName().equals("Area Storage Disk")) {
                        Player p = (Player) event.getPlayer();
                        Location loc = p.getLocation();
                        loc.getWorld().dropItemNaturally(loc, stack);
                        inv.setItem(i, new ItemStack(Material.AIR));
                        TARDISMessage.send(p, "ADV_NO_STORE");
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onDiskStorageInteract(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        String title = inv.getTitle();
        if (!inv_titles.contains(title)) {
            return;
        }
        int slot = event.getRawSlot();
        if ((slot >= 0 && slot < 27) || event.isShiftClick()) {
            event.setCancelled(true);
        }
        final Player player = (Player) event.getWhoClicked();
        // get the storage record
        HashMap<String, Object> where = new HashMap<String, Object>();
        where.put("uuid", player.getUniqueId().toString());
        ResultSetDiskStorage rs = new ResultSetDiskStorage(plugin, where);
        if (rs.resultSet()) {
            // which inventory screen is it?
            String[] split = title.split(" ");
            String tmp = split[0].toUpperCase(Locale.ENGLISH);
            if (split.length > 2) {
                tmp = tmp + "_" + split[2];
            }
            STORAGE store = STORAGE.valueOf(tmp);
            if (slot < 6 || slot == 18 || slot == 26) {
                saveCurrentStorage(inv, store.getTable(), player);
            }
            switch (slot) {
                case 0:
                    if (!store.equals(STORAGE.SAVE_1)) {
                        // switch to saves
                        loadInventory(rs.getSavesOne(), player, STORAGE.SAVE_1);
                    }
                    break;
                case 1:
                    if (!store.equals(STORAGE.AREA)) {
                        // switch to areas
                        loadInventory(rs.getAreas(), player, STORAGE.AREA);
                    }
                    break;
                case 2:
                    if (!store.equals(STORAGE.PLAYER)) {
                        // switch to players
                        loadInventory(rs.getPlayers(), player, STORAGE.PLAYER);
                    }
                    break;
                case 3:
                    if (!store.equals(STORAGE.BIOME_1)) {
                        // switch to biomes
                        loadInventory(rs.getBiomesOne(), player, STORAGE.BIOME_1);
                    }
                    break;
                case 4:
                    if (!store.equals(STORAGE.PRESET_1)) {
                        // switch to presets
                        loadInventory(rs.getPresetsOne(), player, STORAGE.PRESET_1);
                    }
                    break;
                case 5:
                    if (!store.equals(STORAGE.CIRCUIT)) {
                        // switch to circuits
                        loadInventory(rs.getCircuits(), player, STORAGE.CIRCUIT);
                    }
                    break;
            }
            switch (store) {
                case BIOME_1:
                    switch (slot) {
                        case 26:
                            // switch to biome 2
                            loadInventory(rs.getBiomesTwo(), player, STORAGE.BIOME_2);
                            break;
                        default:
                            break;
                    }
                    break;
                case BIOME_2:
                    switch (slot) {
                        case 18:
                            // switch to biome 1
                            loadInventory(rs.getBiomesOne(), player, STORAGE.BIOME_1);
                            break;
                        default:
                            break;
                    }
                    break;
                case PRESET_1:
                    switch (slot) {
                        case 26:
                            // switch to preset 2
                            loadInventory(rs.getPresetsTwo(), player, STORAGE.PRESET_2);
                            break;
                        default:
                            break;
                    }
                    break;
                case PRESET_2:
                    switch (slot) {
                        case 18:
                            // switch to preset 1
                            loadInventory(rs.getPresetsOne(), player, STORAGE.PRESET_1);
                            break;
                        default:
                            break;
                    }
                    break;
                case SAVE_1:
                    switch (slot) {
                        case 26:
                            // switch to save 2
                            loadInventory(rs.getSavesTwo(), player, STORAGE.SAVE_2);
                            break;
                        default:
                            break;
                    }
                    break;
                case SAVE_2:
                    switch (slot) {
                        case 18:
                            // switch to save 1
                            loadInventory(rs.getSavesOne(), player, STORAGE.SAVE_1);
                            break;
                        default:
                            break;
                    }
                    break;
                default: // no extra pages
                    break;
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDropAreaDisk(PlayerDropItemEvent event) {
        ItemStack stack = event.getItemDrop().getItemStack();
        if (stack != null && stack.getType().equals(Material.RECORD_3) && stack.hasItemMeta()) {
            ItemMeta ims = stack.getItemMeta();
            if (ims.hasDisplayName() && ims.getDisplayName().equals("Area Storage Disk")) {
                event.setCancelled(true);
                Player p = event.getPlayer();
                TARDISMessage.send(p, "ADV_NO_DROP");
            }
        }
    }

    private void saveCurrentStorage(Inventory inv, String column, Player p) {
        // loop through inventory contents and remove any items that are not disks or circuits
        for (int i = 27; i < 54; i++) {
            ItemStack is = inv.getItem(i);
            if (is != null) {
                if (!onlythese.contains(is.getType())) {
                    p.getLocation().getWorld().dropItemNaturally(p.getLocation(), is);
                    inv.setItem(i, new ItemStack(Material.AIR));
                }
            }
        }
        String serialized = TARDISSerializeInventory.itemStacksToString(inv.getContents());
        HashMap<String, Object> set = new HashMap<String, Object>();
        set.put(column, serialized);
        HashMap<String, Object> where = new HashMap<String, Object>();
        where.put("uuid", p.getUniqueId().toString());
        new QueryFactory(plugin).doUpdate("storage", set, where);
    }

    private void loadInventory(final String serialized, final Player p, final STORAGE s) {
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                ItemStack[] stack = null;
                try {
                    if (!serialized.isEmpty()) {
                        if (s.equals(STORAGE.AREA)) {
                            stack = TARDISSerializeInventory.itemStacksFromString(new TARDISAreaDisks(plugin).checkDisksForNewAreas(p));
                        } else {
                            stack = TARDISSerializeInventory.itemStacksFromString(serialized);
                        }
                    } else if (s.equals(STORAGE.AREA)) {
                        stack = new TARDISAreaDisks(plugin).makeDisks(p);
                    } else {
                        stack = TARDISSerializeInventory.itemStacksFromString(s.getEmpty());
                    }
                } catch (IOException ex) {
                    plugin.debug("Could not get inventory from database! " + ex);
                }
                // close inventory
                p.closeInventory();
                if (stack != null) {
                    // open new inventory
                    Inventory inv = plugin.getServer().createInventory(p, 54, s.getTitle());
                    inv.setContents(stack);
                    p.openInventory(inv);
                }
            }
        }, 1L);
    }
}
