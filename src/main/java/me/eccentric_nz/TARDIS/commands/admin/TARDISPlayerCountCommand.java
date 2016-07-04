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
package me.eccentric_nz.TARDIS.commands.admin;

import java.util.HashMap;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.database.QueryFactory;
import me.eccentric_nz.TARDIS.database.ResultSetCount;
import me.eccentric_nz.TARDIS.utility.TARDISMessage;
import me.eccentric_nz.TARDIS.utility.TARDISNumberParsers;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

/**
 *
 * @author eccentric_nz
 */
public class TARDISPlayerCountCommand {

    private final TARDIS plugin;

    public TARDISPlayerCountCommand(TARDIS plugin) {
        this.plugin = plugin;
    }

    public boolean countPlayers(CommandSender sender, String[] args) {
        int max_count = plugin.getConfig().getInt("creation.count");
        OfflinePlayer player = plugin.getServer().getOfflinePlayer(args[1]);
        if (player == null) {
            TARDISMessage.send(sender, "PLAYER_NOT_VALID");
            return true;
        }
        String uuid = player.getUniqueId().toString();
        HashMap<String, Object> where = new HashMap<String, Object>();
        where.put("uuid", uuid);
        ResultSetCount rsc = new ResultSetCount(plugin, where, false);
        if (rsc.resultSet()) {
            if (args.length == 3) {
                // set count
                int count = TARDISNumberParsers.parseInt(args[2]);
                HashMap<String, Object> setc = new HashMap<String, Object>();
                setc.put("count", count);
                HashMap<String, Object> wherec = new HashMap<String, Object>();
                wherec.put("uuid", uuid);
                QueryFactory qf = new QueryFactory(plugin);
                qf.doUpdate("t_count", setc, wherec);
                TARDISMessage.send(sender, "COUNT_SET", args[1], count, max_count);
            } else {
                // display count
                TARDISMessage.send(sender, "COUNT_IS", args[1], rsc.getCount(), max_count);
            }
        } else {
            TARDISMessage.send(sender, "COUNT_NOT_FOUND");
        }
        return true;
    }
}
