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
package me.eccentric_nz.TARDIS.commands;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.database.ResultSetAreas;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 * TabCompleter for /tardistravel
 */
public class TARDISTravelTabComplete extends TARDISCompleter implements TabCompleter {

    TARDIS plugin;
    private final List<String> ROOT_SUBS = new ArrayList<String>();
    private final List<String> firsts = Arrays.asList("home", "biome", "dest", "area", "back", "cave", "village", "cancel", "costs");
    private final List<String> BIOME_SUBS = new ArrayList<String>();
    private final List<String> AREA_SUBS = new ArrayList<String>();

    public TARDISTravelTabComplete(TARDIS plugin) {
        this.plugin = plugin;
        for (Biome bi : org.bukkit.block.Biome.values()) {
            if (!bi.equals(Biome.HELL) && !bi.equals(Biome.SKY) && !bi.equals(Biome.VOID)) {
                BIOME_SUBS.add(bi.toString());
            }
        }
        ROOT_SUBS.addAll(firsts);
        ROOT_SUBS.addAll(plugin.getTardisAPI().getWorlds());
        ResultSetAreas rsa = new ResultSetAreas(plugin, null, false, true);
        if (rsa.resultSet()) {
            AREA_SUBS.addAll(rsa.getNames());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        // Remember that we can return null to default to online player name matching
        String lastArg = args[args.length - 1];
        if (args.length <= 1) {
            List<String> part = partial(args[0], ROOT_SUBS);
            return (part.size() > 0) ? part : null;
        } else if (args.length == 2) {
            String sub = args[0];
            if (sub.equals("area")) {
                return partial(lastArg, AREA_SUBS);
            }
            if (sub.equals("biome")) {
                return partial(lastArg, BIOME_SUBS);
            }
        } else if (args.length == 2) {

        }
        return ImmutableList.of();
    }
}
