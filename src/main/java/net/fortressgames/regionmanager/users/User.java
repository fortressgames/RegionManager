package net.fortressgames.regionmanager.users;

import lombok.Getter;
import lombok.Setter;
import net.fortressgames.fortressapi.players.CustomPlayer;
import net.fortressgames.fortressapi.utils.Vector3;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.regions.RegionModule;
import net.fortressgames.regionmanager.tasks.CombatTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class User extends CustomPlayer {

	@Getter private final List<Vector3> points = new ArrayList<>();
	@Getter private final List<Region> regions = new ArrayList<>();

	@Setter @Getter private CombatTask combatTask;
	@Setter @Getter private Location lastLocation;

	public User(Player player) {
		super(player);

		lastLocation = player.getLocation();
		regions.add(RegionModule.getInstance().getGlobal());
	}

	public Region getHighestRegion() {
		Region out = RegionModule.getInstance().getGlobal();

		for(Region region : regions) {

			if(region.getPri() > out.getPri()) {
				out = region;
			}
		}

		return out;
	}

	public List<Region> getRegionsInOrder() {
		HashMap<Region, Integer> map = new HashMap<>();
		regions.forEach(region -> map.put(region, region.getPri()));

		Collections.sort(new ArrayList<>(map.values()));

		List<Region> list = new ArrayList<>();
		map.forEach((region, integer) -> list.add(region));

		return list;
	}
}