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
package me.eccentric_nz.TARDIS.artron;

import java.util.HashMap;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.database.ResultSetPlayerPrefs;
import me.eccentric_nz.TARDIS.database.ResultSetTardisArtron;
import me.eccentric_nz.TARDIS.utility.TARDISMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 *
 * @author eccentric_nz
 */
public class TARDISArtronIndicator {

    private final TARDIS plugin;
    private final ScoreboardManager manager;
    private final int fc;
    private final Material filter;

    public TARDISArtronIndicator(TARDIS plugin) {
        this.plugin = plugin;
        this.manager = plugin.getServer().getScoreboardManager();
        this.fc = plugin.getArtronConfig().getInt("full_charge");
        this.filter = Material.valueOf(plugin.getRecipesConfig().getString("shaped.Perception Filter.result"));
    }

    public void showArtronLevel(final Player p, int id, int used) {
        // check if they have the perception filter on
        boolean isFiltered = false;
        ItemStack[] armour = p.getInventory().getArmorContents();
        for (ItemStack is : armour) {
            if (is != null && is.getType().equals(filter)) {
                isFiltered = true;
            }
        }
        final Scoreboard currentScoreboard = (p.getScoreboard().getObjective("TARDIS") != null) ? manager.getMainScoreboard() : p.getScoreboard();
        // get Artron level
        ResultSetTardisArtron rs = new ResultSetTardisArtron(plugin);
        if (rs.fromID(id)) {
            int current_level = rs.getArtron_level();
            int percent = Math.round((current_level * 100F) / fc);
            if (!isFiltered) {
                Scoreboard board = manager.getNewScoreboard();
                Objective objective = board.registerNewObjective("TARDIS", "Artron");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName(plugin.getLanguage().getString("ARTRON_DISPLAY"));
                if (used == 0) {
                    Score max = objective.getScore(ChatColor.AQUA + plugin.getLanguage().getString("ARTRON_MAX") + ":");
                    max.setScore(fc);
                    Score timelord = objective.getScore(ChatColor.YELLOW + plugin.getLanguage().getString("ARTRON_TL") + ":");
                    HashMap<String, Object> wherep = new HashMap<String, Object>();
                    wherep.put("uuid", p.getUniqueId().toString());
                    ResultSetPlayerPrefs rsp = new ResultSetPlayerPrefs(plugin, wherep);
                    if (rsp.resultSet()) {
                        timelord.setScore(rsp.getArtronLevel());
                    }
                }
                Score current = objective.getScore(ChatColor.GREEN + plugin.getLanguage().getString("ARTRON_REMAINING") + ":");
                Score percentage = objective.getScore(ChatColor.LIGHT_PURPLE + plugin.getLanguage().getString("ARTRON_PERCENT") + ":");
                if (used > 0) {
                    Score amount_used = objective.getScore(ChatColor.RED + plugin.getLanguage().getString("ARTRON_USED") + ":");
                    amount_used.setScore(used);
                } else if (plugin.getTrackerKeeper().getHasDestination().containsKey(id)) {
                    Score amount_used = objective.getScore(ChatColor.RED + plugin.getLanguage().getString("ARTRON_COST") + ":");
                    amount_used.setScore(plugin.getTrackerKeeper().getHasDestination().get(id));
                }
                current.setScore(current_level);
                percentage.setScore(percent);
                p.setScoreboard(board);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (p.isOnline()) {
                            p.setScoreboard(currentScoreboard);
                        }
                    }
                }, 150L);
            } else if (used > 0) {
                TARDISMessage.send(p, "ENERGY_USED", String.format("%d", used));
            } else {
                TARDISMessage.send(p, "ENERGY_LEVEL", String.format("%d", percent));
            }
        }
    }
}
