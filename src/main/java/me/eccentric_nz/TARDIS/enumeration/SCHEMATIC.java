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
package me.eccentric_nz.TARDIS.enumeration;

import org.bukkit.Material;

/**
 *
 * @author eccentric_nz
 */
public class SCHEMATIC {

    String seed;
    String permission;
    String description;
    boolean small;
    boolean tall;
    boolean beacon;
    boolean lanterns;
    boolean custom;

    public SCHEMATIC(String seed, String permission, String description, boolean small, boolean tall, boolean beacon, boolean lanterns, boolean custom) {
        this.seed = seed;
        this.permission = permission;
        this.description = description;
        this.small = small;
        this.tall = tall;
        this.beacon = beacon;
        this.lanterns = lanterns;
        this.custom = custom;
    }

    /**
     * Gets the seed block Material.
     *
     * @return the Material.toString().
     */
    public String getSeed() {
        return seed;
    }

    /**
     * Gets the block type of this SCHEMATIC.
     *
     * @return a block type.
     */
    public Material getSeedMaterial() {
        return Material.valueOf(seed);
    }

    /**
     * Gets the block id of this SCHEMATIC.
     *
     * @return a block id.
     */
    @SuppressWarnings("deprecation")
    public int getSeedId() {
        return Material.valueOf(seed).getId();
    }

    /**
     * Gets the SCHEMATIC permission node.
     *
     * @return the Material.toString().
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Gets the SCHEMATIC description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if this SCHEMATIC is 1 chunk wide.
     *
     * @return true if this SCHEMATIC is 1 chunk wide.
     */
    public boolean isSmall() {
        return small;
    }

    /**
     * Checks if this SCHEMATIC is 2 chunks high.
     *
     * @return true if this SCHEMATIC is 2 chunks high.
     */
    public boolean isTall() {
        return tall;
    }

    /**
     * Checks if this SCHEMATIC has a beacon.
     *
     * @return true if this SCHEMATIC has a beacon.
     */
    public boolean hasBeacon() {
        return beacon;
    }

    /**
     * Checks if this SCHEMATIC has a sea lanterns.
     *
     * @return true if this SCHEMATIC has a sea lanterns.
     */
    public boolean hasLanterns() {
        return lanterns;
    }

    /**
     * Checks if this is a custom SCHEMATIC.
     *
     * @return true if this SCHEMATIC is custom.
     */
    public boolean isCustom() {
        return custom;
    }

    /**
     * Checks if players must use the sonic to change the beacon glass colour.
     *
     * @return true or false.
     */
    public boolean mustUseSonic() {
        return this.permission.equals("budget");
    }
}
