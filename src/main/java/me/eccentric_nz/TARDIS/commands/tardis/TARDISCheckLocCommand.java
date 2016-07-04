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
package me.eccentric_nz.TARDIS.commands.tardis;

import java.util.HashMap;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.database.ResultSetCurrentLocation;
import me.eccentric_nz.TARDIS.database.ResultSetTardisID;
import me.eccentric_nz.TARDIS.enumeration.COMPASS;
import me.eccentric_nz.TARDIS.travel.TARDISTimeTravel;
import me.eccentric_nz.TARDIS.utility.TARDISMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 *
 * @author eccentric_nz
 */
public class TARDISCheckLocCommand {

    private final TARDIS plugin;

    public TARDISCheckLocCommand(TARDIS plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    public boolean doACheckLocation(Player player, String[] args) {
        final Location eyeLocation = player.getTargetBlock(plugin.getGeneralKeeper().getTransparent(), 50).getLocation();
        Material m = player.getTargetBlock(plugin.getGeneralKeeper().getTransparent(), 50).getType();
        if (m != Material.SNOW) {
            int yplusone = eyeLocation.getBlockY();
            eyeLocation.setY(yplusone + 1);
        }
        // check they are a timelord
        ResultSetTardisID rs = new ResultSetTardisID(plugin);
        if (!rs.fromUUID(player.getUniqueId().toString())) {
            TARDISMessage.send(player, "NOT_A_TIMELORD");
            return true;
        }
        HashMap<String, Object> wherecl = new HashMap<String, Object>();
        wherecl.put("tardis_id", rs.getTardis_id());
        ResultSetCurrentLocation rsc = new ResultSetCurrentLocation(plugin, wherecl);
        if (!rsc.resultSet()) {
            TARDISMessage.send(player, "DIRECTION_NOT_FOUND");
            return true;
        }
        COMPASS d = rsc.getDirection();
        TARDISTimeTravel tt = new TARDISTimeTravel(plugin);
        tt.testSafeLocation(eyeLocation, d);
        return true;
    }
}
