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
package me.eccentric_nz.TARDIS.arch;

import java.util.ArrayList;
import java.util.UUID;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.utility.TARDISMessage;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author eccentric_nz
 */
public class TARDISArchCommand {

    private final TARDIS plugin;

    public TARDISArchCommand(TARDIS plugin) {
        this.plugin = plugin;
    }

    public boolean getTime(Player player) {
        UUID uuid = player.getUniqueId();
        if (!plugin.getTrackerKeeper().getJohnSmith().containsKey(uuid)) {
            TARDISMessage.send(player, "ARCH_NOT_VALID");
            return true;
        }
        long time = plugin.getTrackerKeeper().getJohnSmith().get(uuid).getTime();
        long now = System.currentTimeMillis();
        long diff = (time - now);
        if (diff > 0) {
            String sub0 = String.format("%d", (diff / (1000 * 60)) % 60);
            String sub1 = String.format("%d", (diff / 1000) % 60);
            TARDISMessage.send(player, "ARCH_TIME", sub0, sub1);
        } else {
            TARDISMessage.send(player, "ARCH_FREE");
        }
        return true;
    }

    public boolean whois(CommandSender sender, String[] args) {
        for (Player p : new ArrayList<Player>(plugin.getServer().getOnlinePlayers())) {
            if (ChatColor.stripColor(p.getPlayerListName()).equalsIgnoreCase(args[1])) {
                TARDISMessage.send(sender, "ARCH_PLAYER", p.getName());
                return true;
            }
        }
        TARDISMessage.send(sender, "COULD_NOT_FIND_NAME");
        return true;
    }

    public boolean force(CommandSender sender, String[] args) {
        if (args[2].length() < 2) {
            TARDISMessage.send(sender, "TOO_FEW_ARGS");
            return true;
        }
        Player player = plugin.getServer().getPlayer(args[1]);
        if (player == null) {
            TARDISMessage.send(sender, "COULD_NOT_FIND_NAME");
            return true;
        }
        UUID uuid = player.getUniqueId();
        boolean inv = plugin.getConfig().getBoolean("arch.switch_inventory");
        if (!plugin.getTrackerKeeper().getJohnSmith().containsKey(uuid)) {
            final String name = TARDISRandomName.name();
            long time = System.currentTimeMillis() + plugin.getConfig().getLong("arch.min_time") * 60000L;
            TARDISWatchData twd = new TARDISWatchData(name, time);
            plugin.getTrackerKeeper().getJohnSmith().put(uuid, twd);
            if (DisguiseAPI.isDisguised(player)) {
                DisguiseAPI.undisguiseToAll(player);
            }
            player.getWorld().strikeLightningEffect(player.getLocation());
            player.setHealth(player.getMaxHealth() / 10.0d);
            if (inv) {
                new TARDISArchInventory().switchInventories(player, 0);
            }
            PlayerDisguise playerDisguise = new PlayerDisguise(name);
            playerDisguise.setHideHeldItemFromSelf(false);
            playerDisguise.setViewSelfDisguise(false);
            DisguiseAPI.disguiseToAll(player, playerDisguise);
            player.setDisplayName(name);
            player.setPlayerListName(name);
        } else {
            if (DisguiseAPI.isDisguised(player)) {
                DisguiseAPI.undisguiseToAll(player);
            }
            if (inv) {
                new TARDISArchInventory().switchInventories(player, 1);
            }
            player.getWorld().strikeLightningEffect(player.getLocation());
            plugin.getTrackerKeeper().getJohnSmith().remove(uuid);
            player.setPlayerListName(player.getName());
            // remove player from arched table
            new TARDISArchPersister(plugin).removeArch(uuid);
        }
        return true;
    }
}
