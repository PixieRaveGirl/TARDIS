/*
 * Copyright (C) 2013 eccentric_nz
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
package me.eccentric_nz.TARDIS.enumeration;

/**
 *
 * @author eccentric_nz
 */
public enum MESSAGE {

    NO_PERMS_MESSAGE("You do not have permission to do that!"),
    NOT_OWNER("You are not the Timelord or Companion for this TARDIS!"),
    NO_TARDIS("You have not created a TARDIS yet!"),
    TIMELORD_OFFLINE("The Timelord who owns this TARDIS is offline!"),
    TIMELORD_NOT_IN("The Timelord who owns this TARDIS is not inside!");
    String text;

    public String getText() {
        return text;
    }

    private MESSAGE(String text) {
        this.text = text;
    }
}
