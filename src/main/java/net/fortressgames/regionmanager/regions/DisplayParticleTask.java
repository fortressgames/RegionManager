package net.fortressgames.regionmanager.regions;

import lombok.Setter;
import net.fortressgames.fortressapi.FortressRunnable;
import net.fortressgames.fortressapi.utils.Vector3;
import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplayParticleTask extends FortressRunnable {

	private final Particle particle;

	private final Region region;
	private final List<Vector3> points = new ArrayList<>();

	public DisplayParticleTask(Particle particle, int density, Region region) {
		this.particle = particle;
		this.region = region;

		int play = 0;
		List<Vector3> list = region.getRegionMaths().getAllPoints();
		Collections.shuffle(list);

		for(Vector3 point : list) {

			if(play == density) {
				points.add(point);
				play = 0;
				continue;
			}

			play++;
		}
	}

	@Override
	public void run() {

		points.forEach(vector3 -> region.getWorld().spawnParticle(particle,
				new Location(region.getWorld(), vector3.getBlockX() + 0.5, vector3.getBlockY() + 0.5, vector3.getBlockZ() + 0.5), 1, 0, 0, 0, 0));
	}
}