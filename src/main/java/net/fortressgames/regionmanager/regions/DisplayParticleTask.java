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
	@Setter private int density;

	private final Region region;
	private final List<Vector3> points = new ArrayList<>();

	public DisplayParticleTask(Particle particle, int density, Region region) {
		this.particle = particle;
		this.region = region;
		this.density = density;

		int play = 0;
		List<Vector3> list = region.getAllPoints();
		Collections.shuffle(list);

		for(Vector3 point : list) {

			if(play == 30) {
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
				new Location(region.getWorld(), vector3.getBlockX(), vector3.getBlockY(), vector3.getBlockZ()), density, 0, 0, 0, 0));
	}
}