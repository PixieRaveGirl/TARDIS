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
package me.eccentric_nz.TARDIS.chameleon;

import java.util.Arrays;
import java.util.HashMap;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.advanced.TARDISCircuitChecker;
import me.eccentric_nz.TARDIS.advanced.TARDISCircuitDamager;
import me.eccentric_nz.TARDIS.database.QueryFactory;
import me.eccentric_nz.TARDIS.database.ResultSetTardis;
import me.eccentric_nz.TARDIS.database.ResultSetTravellers;
import me.eccentric_nz.TARDIS.database.data.Tardis;
import me.eccentric_nz.TARDIS.enumeration.DIFFICULTY;
import me.eccentric_nz.TARDIS.enumeration.DISK_CIRCUIT;
import me.eccentric_nz.TARDIS.listeners.TARDISMenuListener;
import me.eccentric_nz.TARDIS.utility.TARDISMessage;
import me.eccentric_nz.TARDIS.utility.TARDISStaticUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author eccentric_nz
 */
public class TARDISPresetListener extends TARDISMenuListener implements Listener {

    private final TARDIS plugin;

    public TARDISPresetListener(TARDIS plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    /**
     * Listens for player clicking inside an inventory. If the inventory is a
     * TARDIS GUI, then the click is processed accordingly.
     *
     * @param event a player clicking an inventory slot
     */
    @EventHandler(ignoreCancelled = true)
    public void onPresetTerminalClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        String name = inv.getTitle();
        if (name.equals("§4More Presets")) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            final Player player = (Player) event.getWhoClicked();
            if (slot >= 0 && slot < 54) {
                ItemStack is = inv.getItem(slot);
                if (is != null) {
                    // get the TARDIS the player is in
                    HashMap<String, Object> wheres = new HashMap<String, Object>();
                    wheres.put("uuid", player.getUniqueId().toString());
                    ResultSetTravellers rst = new ResultSetTravellers(plugin, wheres, false);
                    if (rst.resultSet()) {
                        int id = rst.getTardis_id();
                        HashMap<String, Object> where = new HashMap<String, Object>();
                        where.put("tardis_id", id);
                        ResultSetTardis rs = new ResultSetTardis(plugin, where, "", false, 0);
                        if (rs.resultSet()) {
                            Tardis tardis = rs.getTardis();
                            String last_line = TARDISStaticUtils.getLastLine(tardis.getChameleon());
                            final boolean bool = tardis.isChamele_on();
                            final boolean adapt = tardis.isAdapti_on();
                            String preset = tardis.getPreset().toString();
                            HashMap<String, Object> set = new HashMap<String, Object>();
                            HashMap<String, Object> set_oo = new HashMap<String, Object>();
                            QueryFactory qf = new QueryFactory(plugin);
                            HashMap<String, Object> wherec = new HashMap<String, Object>();
                            wherec.put("tardis_id", id);
                            switch (slot) {
                                case 0:
                                    // toggle chameleon circuit
                                    String onoff;
                                    String engage;
                                    int oo;
                                    if (tardis.isChamele_on()) {
                                        onoff = ChatColor.RED + plugin.getLanguage().getString("SET_OFF");
                                        engage = plugin.getLanguage().getString("SET_ON");
                                        oo = 0;
                                    } else {
                                        onoff = ChatColor.GREEN + plugin.getLanguage().getString("SET_ON");
                                        engage = plugin.getLanguage().getString("SET_OFF");
                                        oo = 1;
                                    }
                                    ItemMeta im = is.getItemMeta();
                                    im.setLore(Arrays.asList(onoff, String.format(plugin.getLanguage().getString("CHAM_CLICK"), engage)));
                                    is.setItemMeta(im);
                                    // set sign text
                                    TARDISStaticUtils.setSign(tardis.getChameleon(), 2, onoff, player);
                                    set_oo.put("chamele_on", oo);
                                    break;
                                case 2:
                                    player.performCommand("tardis rebuild");
                                    close(player);
                                    // damage the circuit if configured
                                    if (plugin.getConfig().getBoolean("circuits.damage") && !plugin.getDifficulty().equals(DIFFICULTY.EASY) && plugin.getConfig().getInt("circuits.uses.chameleon") > 0) {
                                        TARDISCircuitChecker tcc = new TARDISCircuitChecker(plugin, id);
                                        tcc.getCircuits();
                                        // decrement uses
                                        int uses_left = tcc.getChameleonUses();
                                        new TARDISCircuitDamager(plugin, DISK_CIRCUIT.CHAMELEON, uses_left, id, player).damage();
                                    }
                                    break;
                                case 4:
                                    // toggle biome adaption
                                    String biome;
                                    String to_turn;
                                    int ba;
                                    if (tardis.isAdapti_on()) {
                                        biome = ChatColor.RED + plugin.getLanguage().getString("SET_OFF");
                                        to_turn = plugin.getLanguage().getString("SET_ON");
                                        ba = 0;
                                    } else {
                                        biome = ChatColor.GREEN + plugin.getLanguage().getString("SET_ON");
                                        to_turn = plugin.getLanguage().getString("SET_OFF");
                                        ba = 1;
                                    }
                                    ItemMeta bio = is.getItemMeta();
                                    bio.setLore(Arrays.asList(biome, String.format(plugin.getLanguage().getString("CHAM_CLICK"), to_turn)));
                                    is.setItemMeta(bio);
                                    set_oo.put("adapti_on", ba);
                                    break;
                                case 8:
                                    // page three
                                    close(player);
                                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                        @Override
                                        public void run() {
                                            TARDISPageThreeInventory tci = new TARDISPageThreeInventory(plugin, bool, adapt);
                                            ItemStack[] items = tci.getPageThree();
                                            Inventory chaminv = plugin.getServer().createInventory(player, 54, "§4Even More Presets");
                                            chaminv.setContents(items);
                                            player.openInventory(chaminv);
                                        }
                                    }, 2L);
                                    break;
                                case 18:
                                    // custom
                                    if (!last_line.equals("CUSTOM")) {
                                        set.put("chameleon_preset", "CUSTOM");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "CUSTOM", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Server's Custom");
                                    break;
                                case 20:
                                    // Cake
                                    if (!last_line.equals("CAKE")) {
                                        set.put("chameleon_preset", "CAKE");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "CAKE", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Birthday Cake");
                                    break;
                                case 22:
                                    // Gravestone
                                    if (!last_line.equals("GRAVESTONE")) {
                                        set.put("chameleon_preset", "GRAVESTONE");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "GRAVESTONE", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Gravestone");
                                    break;
                                case 24:
                                    // Topsy-turvey
                                    if (!last_line.equals("TOPSYTURVEY")) {
                                        set.put("chameleon_preset", "TOPSYTURVEY");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "TOPSYTURVEY", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Topsy-turvey");
                                    break;
                                case 26:
                                    // Mushroom
                                    if (!last_line.equals("SHROOM")) {
                                        set.put("chameleon_preset", "SHROOM");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "SHROOM", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Mushroom");
                                    break;
                                case 28:
                                    // Rubber Duck
                                    if (!last_line.equals("DUCK")) {
                                        set.put("chameleon_preset", "DUCK");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "DUCK", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Rubber Duck");
                                    break;
                                case 30:
                                    // Mineshaft
                                    if (!last_line.equals("MINESHAFT")) {
                                        set.put("chameleon_preset", "MINESHAFT");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "MINESHAFT", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Mineshaft");
                                    break;
                                case 32:
                                    // Creepy
                                    if (!last_line.equals("CREEPY")) {
                                        set.put("chameleon_preset", "CREEPY");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "CREEPY", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Creepy");
                                    break;
                                case 34:
                                    // Peanut Butter Jar
                                    if (!last_line.equals("PEANUT")) {
                                        set.put("chameleon_preset", "PEANUT");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "PEANUT", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Peanut Butter Jar");
                                    break;
                                case 36:
                                    // Lamp Post
                                    if (!last_line.equals("LAMP")) {
                                        set.put("chameleon_preset", "LAMP");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "LAMP", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Lamp Post");
                                    break;
                                case 38:
                                    // Candy Cane
                                    if (!last_line.equals("CANDY")) {
                                        set.put("chameleon_preset", "CANDY");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "CANDY", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Candy Cane");
                                    break;
                                case 40:
                                    // Toilet
                                    if (!last_line.equals("TOILET")) {
                                        set.put("chameleon_preset", "TOILET");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "TOILET", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Water Closet");
                                    break;
                                case 42:
                                    // Robot
                                    if (!last_line.equals("ROBOT")) {
                                        set.put("chameleon_preset", "ROBOT");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "ROBOT", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Robot");
                                    break;
                                case 44:
                                    // Flaming Torch
                                    if (!last_line.equals("TORCH")) {
                                        set.put("chameleon_preset", "TORCH");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "TORCH", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Flaming Torch");
                                    break;
                                case 46:
                                    // Pine Tree
                                    if (!last_line.equals("PINE")) {
                                        set.put("chameleon_preset", "PINE");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "PINE", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Pine Tree");
                                    break;
                                case 48:
                                    // Steam Punked
                                    if (!last_line.equals("PUNKED")) {
                                        set.put("chameleon_preset", "PUNKED");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "PUNKED", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Steam Punked");
                                    break;
                                case 50:
                                    // Random Fence
                                    if (!last_line.equals("FENCE")) {
                                        set.put("chameleon_preset", "FENCE");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "FENCE", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Random Fence");
                                    break;
                                case 52:
                                    // Nether Portal
                                    if (!last_line.equals("PORTAL")) {
                                        set.put("chameleon_preset", "PORTAL");
                                        TARDISStaticUtils.setSign(tardis.getChameleon(), 3, "PORTAL", player);
                                    }
                                    TARDISMessage.send(player, "CHAM_SET", ChatColor.AQUA + "Nether Portal");
                                    break;
                                default:
                                    close(player);
                            }
                            if (set.size() > 0) {
                                set.put("chameleon_demat", preset);
                                qf.doUpdate("tardis", set, wherec);
                            }
                            if (set_oo.size() > 0) {
                                qf.doUpdate("tardis", set_oo, wherec);
                            }
                        }
                    }
                }
            }
        }
    }
}
