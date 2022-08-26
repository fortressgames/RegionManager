package net.fortressgames.regionmanager.users;

import lombok.Getter;
import net.fortressgames.fortressapi.utils.Vector3;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.regions.RegionModule;

import java.util.ArrayList;
import java.util.List;

public class User {

	@Getter private final List<Vector3> points = new ArrayList<>();
	@Getter private final List<Region> regions = new ArrayList<>();

	public User() {
		regions.add(RegionModule.getInstance().getGlobal());
	}
}