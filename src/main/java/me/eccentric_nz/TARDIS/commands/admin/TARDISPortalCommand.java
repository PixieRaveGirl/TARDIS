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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.database.TARDISDatabaseConnection;
import me.eccentric_nz.TARDIS.utility.TARDISMessage;
import org.bukkit.command.CommandSender;

/**
 *
 * @author eccentric_nz
 */
public class TARDISPortalCommand {

    private final TARDIS plugin;
    private final TARDISDatabaseConnection service = TARDISDatabaseConnection.getInstance();
    private final Connection connection = service.getConnection();
    private Statement statement = null;
    private final String prefix;

    public TARDISPortalCommand(TARDIS plugin) {
        this.plugin = plugin;
        this.prefix = this.plugin.getPrefix();
    }

    public boolean clearAll(CommandSender sender) {
        // clear all portals on the server
        plugin.getTrackerKeeper().getPortals().clear();
        // stop tracking players
        plugin.getTrackerKeeper().getMover().clear();
        // clear the portals table
        try {
            statement = connection.createStatement();
            String query = "DELETE FROM " + prefix + "portals";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            plugin.debug("Error deleting from portals table! " + e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                plugin.debug("Error closing portals table! " + e.getMessage());
            }
        }
        TARDISMessage.send(sender, "PURGE_PORTAL");
        return true;
    }
}
