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
package me.eccentric_nz.TARDIS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import me.eccentric_nz.TARDIS.JSON.JSONObject;
import me.eccentric_nz.TARDIS.arch.TARDISWatchData;
import me.eccentric_nz.TARDIS.builders.BuildData;
import me.eccentric_nz.TARDIS.desktop.TARDISUpgradeData;
import me.eccentric_nz.TARDIS.flight.TARDISRegulatorRunnable;
import me.eccentric_nz.TARDIS.info.TARDISInfoMenu;
import me.eccentric_nz.TARDIS.move.TARDISMoveSession;
import me.eccentric_nz.TARDIS.move.TARDISTeleportLocation;
import me.eccentric_nz.TARDIS.rooms.TARDISSeedData;
import me.eccentric_nz.TARDIS.siegemode.TARDISSiegeArea;
import me.eccentric_nz.TARDIS.utility.TARDISAntiBuild;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 * A central repository used to store various data values required to track what
 * Time lords and TARDIS are doing in-game, and provide easy access to the data
 * in other classes. For example the spectacleWearers List tracks which Time
 * Lords are currently wearing 3d_glasses.
 *
 * @author eccentric_nz
 */
public class TARDISTrackerInstanceKeeper {

    private String immortalityGate = "";
    private final HashMap<Integer, Boolean> malfunction = new HashMap<Integer, Boolean>();
    private final HashMap<Integer, Integer> damage = new HashMap<Integer, Integer>();
    private final HashMap<Integer, Integer> hasDestination = new HashMap<Integer, Integer>();
    private final HashMap<Integer, Integer> destinationVortex = new HashMap<Integer, Integer>();
    private final HashMap<Integer, String> renderer = new HashMap<Integer, String>();
    private final HashMap<Integer, TARDISAntiBuild> antiBuild = new HashMap<Integer, TARDISAntiBuild>();
    private final HashMap<Integer, UUID> rescue = new HashMap<Integer, UUID>();
    private final HashMap<Location, TARDISTeleportLocation> portals = new HashMap<Location, TARDISTeleportLocation>();
    private final HashMap<String, Sign> sign = new HashMap<String, Sign>();
    private final HashMap<String, List<TARDISSiegeArea>> siegeBreedingAreas = new HashMap<String, List<TARDISSiegeArea>>();
    private final HashMap<String, List<TARDISSiegeArea>> siegeGrowthAreas = new HashMap<String, List<TARDISSiegeArea>>();
    private final HashMap<UUID, Block> exterminate = new HashMap<UUID, Block>();
    private final HashMap<UUID, Block> invisibleDoors = new HashMap<UUID, Block>();
    private final HashMap<UUID, Block> lazarus = new HashMap<UUID, Block>();
    private final HashMap<UUID, Double[]> gravity = new HashMap<UUID, Double[]>();
    private final HashMap<UUID, Integer> binder = new HashMap<UUID, Integer>();
    private final HashMap<UUID, Integer> count = new HashMap<UUID, Integer>();
    private final HashMap<UUID, Integer> ejecting = new HashMap<UUID, Integer>();
    private final HashMap<UUID, Integer> junkPlayers = new HashMap<UUID, Integer>();
    private final HashMap<UUID, Integer> siegeCarrying = new HashMap<UUID, Integer>();
    private final HashMap<UUID, JSONObject> pastes = new HashMap<UUID, JSONObject>();
    private final HashMap<UUID, List<Integer>> renderedNPCs = new HashMap<UUID, List<Integer>>();
    private final HashMap<UUID, List<Location>> repeaters = new HashMap<UUID, List<Location>>();
    private final HashMap<UUID, Location> dispersed = new HashMap<UUID, Location>();
    private final HashMap<UUID, Location> sonicGenerators = new HashMap<UUID, Location>();
    private final HashMap<UUID, Location> startLocation = new HashMap<UUID, Location>();
    private final HashMap<UUID, Location> endLocation = new HashMap<UUID, Location>();
    private final HashMap<UUID, Long> setTime = new HashMap<UUID, Long>();
    private final HashMap<UUID, Long> hideCooldown = new HashMap<UUID, Long>();
    private final HashMap<UUID, Long> rebuildCooldown = new HashMap<UUID, Long>();
    private final HashMap<UUID, String> area = new HashMap<UUID, String>();
    private final HashMap<UUID, String> block = new HashMap<UUID, String>();
    private final HashMap<UUID, String> end = new HashMap<UUID, String>();
    private final HashMap<UUID, String> flight = new HashMap<UUID, String>();
    private final HashMap<UUID, String> jettison = new HashMap<UUID, String>();
    private final HashMap<UUID, TARDISWatchData> johnSmith = new HashMap<UUID, TARDISWatchData>();
    private final HashMap<UUID, String> perm = new HashMap<UUID, String>();
    private final HashMap<UUID, String> players = new HashMap<UUID, String>();
    private final HashMap<UUID, String> preset = new HashMap<UUID, String>();
    private final HashMap<UUID, String> secondary = new HashMap<UUID, String>();
    private final HashMap<UUID, String> telepathicPlacements = new HashMap<UUID, String>();
    private final HashMap<UUID, TARDISInfoMenu> infoMenu = new HashMap<UUID, TARDISInfoMenu>();
    private final HashMap<UUID, BuildData> flightData = new HashMap<UUID, BuildData>();
    private final HashMap<UUID, TARDISMoveSession> moveSessions = new HashMap<UUID, TARDISMoveSession>();
    private final HashMap<UUID, TARDISRegulatorRunnable> regulating = new HashMap<UUID, TARDISRegulatorRunnable>();
    private final HashMap<UUID, TARDISSeedData> roomSeed = new HashMap<UUID, TARDISSeedData>();
    private final HashMap<UUID, TARDISUpgradeData> upgrades = new HashMap<UUID, TARDISUpgradeData>();
    private final HashMap<UUID, UUID> chat = new HashMap<UUID, UUID>();
    private final HashMap<UUID, UUID> telepaths = new HashMap<UUID, UUID>();
    private final HashMap<UUID, UUID> telepathicRescue = new HashMap<UUID, UUID>();
    private final List<String> artronFurnaces = new ArrayList<String>();
    private final List<Integer> dematerialising = new ArrayList<Integer>();
    private final List<Integer> dispersedTARDII = new ArrayList<Integer>();
    private final List<Integer> hasNotClickedHandbrake = new ArrayList<Integer>();
    private final List<Integer> hasClickedHandbrake = new ArrayList<Integer>();
    private final List<Integer> hasRandomised = new ArrayList<Integer>();
    private final List<Integer> inSiegeMode = new ArrayList<Integer>();
    private final List<Integer> inVortex = new ArrayList<Integer>();
    private final List<Integer> isSiegeCube = new ArrayList<Integer>();
    private final List<Integer> materialising = new ArrayList<Integer>();
    private final List<Integer> minecart = new ArrayList<Integer>();
    private final List<Integer> submarine = new ArrayList<Integer>();
    private final List<Integer> keyboard = new ArrayList<Integer>();
    private final List<String> reset = new ArrayList<String>();
    private final List<UUID> arrangers = new ArrayList<UUID>();
    private final List<UUID> beaconColouring = new ArrayList<UUID>();
    private final List<UUID> eggs = new ArrayList<UUID>();
    private final List<UUID> farming = new ArrayList<UUID>();
    private final List<UUID> geneticManipulation = new ArrayList<UUID>();
    private final List<UUID> geneticallyModified = new ArrayList<UUID>();
    private final List<UUID> hasTravelled = new ArrayList<UUID>();
    private final List<UUID> howTo = new ArrayList<UUID>();
    private final List<UUID> mover = new ArrayList<UUID>();
    private final List<UUID> recipeView = new ArrayList<UUID>();
    private final List<UUID> sonicDoors = new ArrayList<UUID>();
    private final List<UUID> spectacleWearers = new ArrayList<UUID>();
    private final List<UUID> temporallyLocated = new ArrayList<UUID>();
    private final List<UUID> renderRoomOccupants = new ArrayList<UUID>();
    private final List<UUID> zeroRoomOccupants = new ArrayList<UUID>();

    public HashMap<Integer, Boolean> getMalfunction() {
        return malfunction;
    }

    public HashMap<Integer, Integer> getDamage() {
        return damage;
    }

    public HashMap<Integer, Integer> getHasDestination() {
        return hasDestination;
    }

    public HashMap<Integer, Integer> getDestinationVortex() {
        return destinationVortex;
    }

    public HashMap<Integer, String> getRenderer() {
        return renderer;
    }

    public HashMap<Integer, UUID> getRescue() {
        return rescue;
    }

    public HashMap<Location, TARDISTeleportLocation> getPortals() {
        return portals;
    }

    public HashMap<Integer, TARDISAntiBuild> getAntiBuild() {
        return antiBuild;
    }

    public HashMap<UUID, Block> getExterminate() {
        return exterminate;
    }

    public HashMap<UUID, Block> getInvisibleDoors() {
        return invisibleDoors;
    }

    public HashMap<UUID, Block> getLazarus() {
        return lazarus;
    }

    public HashMap<UUID, Double[]> getGravity() {
        return gravity;
    }

    public HashMap<UUID, Integer> getBinder() {
        return binder;
    }

    public HashMap<UUID, Integer> getJunkPlayers() {
        return junkPlayers;
    }

    public HashMap<UUID, Long> getSetTime() {
        return setTime;
    }

    public HashMap<UUID, Long> getHideCooldown() {
        return hideCooldown;
    }

    public HashMap<UUID, Long> getRebuildCooldown() {
        return rebuildCooldown;
    }

    public HashMap<String, Sign> getSign() {
        return sign;
    }

    public HashMap<String, List<TARDISSiegeArea>> getSiegeBreedingAreas() {
        return siegeBreedingAreas;
    }

    public HashMap<String, List<TARDISSiegeArea>> getSiegeGrowthAreas() {
        return siegeGrowthAreas;
    }

    public HashMap<UUID, String> getBlock() {
        return block;
    }

    public HashMap<UUID, UUID> getChat() {
        return chat;
    }

    public HashMap<UUID, String> getFlight() {
        return flight;
    }

    public HashMap<UUID, Integer> getCount() {
        return count;
    }

    public HashMap<UUID, TARDISRegulatorRunnable> getRegulating() {
        return regulating;
    }

    public HashMap<UUID, BuildData> getFlightData() {
        return flightData;
    }

    public HashMap<UUID, List<Location>> getRepeaters() {
        return repeaters;
    }

    public HashMap<UUID, List<Integer>> getRenderedNPCs() {
        return renderedNPCs;
    }

    public HashMap<UUID, String> getEnd() {
        return end;
    }

    public HashMap<UUID, String> getJettison() {
        return jettison;
    }

    public HashMap<UUID, String> getArea() {
        return area;
    }

    public HashMap<UUID, String> getPerm() {
        return perm;
    }

    public HashMap<UUID, String> getPlayers() {
        return players;
    }

    public HashMap<UUID, String> getPreset() {
        return preset;
    }

    public HashMap<UUID, String> getSecondary() {
        return secondary;
    }

    public HashMap<UUID, String> getTelepathicPlacements() {
        return telepathicPlacements;
    }

    public HashMap<UUID, TARDISInfoMenu> getInfoMenu() {
        return infoMenu;
    }

    public TARDISMoveSession getTARDISMoveSession(Player p) {
        if (this.moveSessions.containsKey(p.getUniqueId())) {
            return this.moveSessions.get(p.getUniqueId());
        }
        TARDISMoveSession session = new TARDISMoveSession(p);
        this.moveSessions.put(p.getUniqueId(), session);
        return session;
    }

    public HashMap<UUID, TARDISSeedData> getRoomSeed() {
        return roomSeed;
    }

    public HashMap<UUID, TARDISUpgradeData> getUpgrades() {
        return upgrades;
    }

    public List<String> getArtronFurnaces() {
        return artronFurnaces;
    }

    public List<Integer> getDematerialising() {
        return dematerialising;
    }

    public HashMap<UUID, Location> getDispersed() {
        return dispersed;
    }

    public List<Integer> getHasNotClickedHandbrake() {
        return hasNotClickedHandbrake;
    }

    public List<Integer> getHasClickedHandbrake() {
        return hasClickedHandbrake;
    }

    public List<Integer> getHasRandomised() {
        return hasRandomised;
    }

    public List<Integer> getInSiegeMode() {
        return inSiegeMode;
    }

    public List<Integer> getIsSiegeCube() {
        return isSiegeCube;
    }

    public List<Integer> getInVortex() {
        return inVortex;
    }

    public List<Integer> getMaterialising() {
        return materialising;
    }

    public List<Integer> getMinecart() {
        return minecart;
    }

    public List<Integer> getSubmarine() {
        return submarine;
    }

    public List<Integer> getDispersedTARDII() {
        return dispersedTARDII;
    }

    public List<Integer> getKeyboard() {
        return keyboard;
    }

    public List<UUID> getArrangers() {
        return arrangers;
    }

    public List<UUID> getBeaconColouring() {
        return beaconColouring;
    }

    public List<UUID> getEggs() {
        return eggs;
    }

    public HashMap<UUID, Integer> getEjecting() {
        return ejecting;
    }

    public List<UUID> getFarming() {
        return farming;
    }

    public List<UUID> getGeneticManipulation() {
        return geneticManipulation;
    }

    public List<UUID> getGeneticallyModified() {
        return geneticallyModified;
    }

    public List<UUID> getHasTravelled() {
        return hasTravelled;
    }

    public List<UUID> getHowTo() {
        return howTo;
    }

    public HashMap<UUID, TARDISWatchData> getJohnSmith() {
        return johnSmith;
    }

    public List<UUID> getMover() {
        return mover;
    }

    public List<UUID> getRecipeView() {
        return recipeView;
    }

    public List<UUID> getSonicDoors() {
        return sonicDoors;
    }

    public List<UUID> getSpectacleWearers() {
        return spectacleWearers;
    }

    public HashMap<UUID, UUID> getTelepaths() {
        return telepaths;
    }

    public HashMap<UUID, UUID> getTelepathicRescue() {
        return telepathicRescue;
    }

    public List<String> getReset() {
        return reset;
    }

    public List<UUID> getTemporallyLocated() {
        return temporallyLocated;
    }

    public List<UUID> getRenderRoomOccupants() {
        return renderRoomOccupants;
    }

    public List<UUID> getZeroRoomOccupants() {
        return zeroRoomOccupants;
    }

    public String getImmortalityGate() {
        return immortalityGate;
    }

    public void setImmortalityGate(String immortalityGate) {
        this.immortalityGate = immortalityGate;
    }

    public HashMap<UUID, JSONObject> getPastes() {
        return pastes;
    }

    public HashMap<UUID, Location> getStartLocation() {
        return startLocation;
    }

    public HashMap<UUID, Location> getSonicGenerators() {
        return sonicGenerators;
    }

    public HashMap<UUID, Location> getEndLocation() {
        return endLocation;
    }

    public HashMap<UUID, Integer> getSiegeCarrying() {
        return siegeCarrying;
    }
}
