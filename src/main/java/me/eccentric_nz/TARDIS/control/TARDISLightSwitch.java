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
package me.eccentric_nz.TARDIS.control;

import java.util.HashMap;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.artron.TARDISLampToggler;
import me.eccentric_nz.TARDIS.database.QueryFactory;
import org.bukkit.entity.Player;

/**
 *
 * @author eccentric_nz
 */
public class TARDISLightSwitch {

    private final TARDIS plugin;
    private final int id;
    private final boolean lights;
    private final Player player;
    private final boolean lanterns;

    public TARDISLightSwitch(TARDIS plugin, int id, boolean lights, Player player, boolean lanterns) {
        this.plugin = plugin;
        this.id = id;
        this.lights = lights;
        this.player = player;
        this.lanterns = lanterns;
    }

    public void flickSwitch() {
        HashMap<String, Object> wherel = new HashMap<String, Object>();
        wherel.put("tardis_id", id);
        HashMap<String, Object> setl = new HashMap<String, Object>();
        if (lights) {
            new TARDISLampToggler(plugin).flickSwitch(id, player.getUniqueId(), true, lanterns);
            setl.put("lights_on", 0);
        } else {
            new TARDISLampToggler(plugin).flickSwitch(id, player.getUniqueId(), false, lanterns);
            setl.put("lights_on", 1);
        }
        new QueryFactory(plugin).doUpdate("tardis", setl, wherel);
    }
}
