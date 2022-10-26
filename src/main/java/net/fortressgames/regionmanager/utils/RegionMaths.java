package net.fortressgames.regionmanager.utils;

import lombok.Getter;
import net.fortressgames.fortressapi.utils.Vector2;
import net.fortressgames.fortressapi.utils.Vector3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegionMaths {

	@Getter private final List<Vector2> points;
	@Getter private final int maxY;
	@Getter private final int minY;

	private Vector2 min;
	private Vector2 max;

	public RegionMaths(List<Vector2> points, int maxY, int minY) {
		this.points = points;
		this.maxY = maxY;
		this.minY = minY;

		if(points.isEmpty()) return;

		setMinMaxPoints(points);
	}

	public List<Vector3> getAllPoints() {
		List<Vector3> out = new ArrayList<>();

		List<Integer> vectorX = new ArrayList<>();
		List<Integer> vectorZ = new ArrayList<>();

		points.forEach(vector2 -> {
			vectorX.add(vector2.getBlockX());
			vectorZ.add(vector2.getBlockZ());
		});
		int maxX = Collections.max(vectorX);
		int minX = Collections.min(vectorX);
		int maxZ = Collections.max(vectorZ);
		int minZ = Collections.min(vectorZ);

		for(int y = minY; y < maxY; y++) {

			for(int x = minX; x <= maxX; x++) {
				for(int z = minZ; z <= maxZ; z++) {

					Vector3 vector3 = new Vector3(x, y, z);

					if(inside(vector3)) {
						out.add(vector3);
					}
				}
			}
		}

		return out;
	}

	public boolean inside(Vector3 position) {

		int targetX = position.getBlockX(); // Width
		int targetY = position.getBlockY(); // Height
		int targetZ = position.getBlockZ(); // Depth

		if(targetY < minY || targetY > maxY) {
			return false;
		}

		//Quick and dirty check.
		if(targetX < min.getBlockX() || targetX > max.getBlockX() || targetZ < min.getBlockZ() || targetZ > max.getBlockZ()) {
			return false;
		}

		boolean inside = false;
		int npoints = points.size();
		int xNew, zNew;
		int xOld, zOld;
		int x1, z1;
		int x2, z2;
		long crossproduct;
		int i;

		xOld = points.get(npoints - 1).getBlockX();
		zOld = points.get(npoints - 1).getBlockZ();

		for(i = 0; i < npoints; i++) {
			xNew = points.get(i).getBlockX();
			zNew = points.get(i).getBlockZ();
			//Check for corner
			if(xNew == targetX && zNew == targetZ) {
				return true;
			}
			if(xNew > xOld) {
				x1 = xOld;
				x2 = xNew;
				z1 = zOld;
				z2 = zNew;
			} else {
				x1 = xNew;
				x2 = xOld;
				z1 = zNew;
				z2 = zOld;
			}
			if(x1 <= targetX && targetX <= x2) {
				crossproduct = ((long) targetZ - (long) z1) * (long) (x2 - x1)
						- ((long) z2 - (long) z1) * (long) (targetX - x1);
				if(crossproduct == 0) {
					if((z1 <= targetZ) == (targetZ <= z2)) return true; // on edge
				} else if(crossproduct < 0 && (x1 != targetX)) {
					inside = !inside;
				}
			}
			xOld = xNew;
			zOld = zNew;
		}

		return inside;
	}

	private void setMinMaxPoints(List<Vector2> points) {
		int minX = points.get(0).getBlockX();
		int minZ = points.get(0).getBlockZ();
		int maxX = minX;
		int maxZ = minZ;

		for(Vector2 v : points) {
			int x = v.getBlockX();
			int z = v.getBlockZ();

			if(x < minX) minX = x;
			if(z < minZ) minZ = z;

			if(x > maxX) maxX = x;
			if(z > maxZ) maxZ = z;
		}

		min = new Vector2(minX, minZ);
		max = new Vector2(maxX, maxZ);
	}
}