package util;

import map.MapTile;

import java.util.ArrayList;
import java.util.List;

public class MapTilePool {
	public static final int DEFFAULT_POOL_SIZE = 50;
	public static final int POOL_MAX_SIZE = 70;

	private static List<MapTile> pool = new ArrayList<>();

	static {
		for (int i = 0; i < DEFFAULT_POOL_SIZE; i++) {
			pool.add(new MapTile());
		}
	}

	public static MapTile get() {
		MapTile tile = null;

		if (pool.size() == 0) {
			tile = new MapTile();
		} else {
			tile = pool.remove(0);
		}
		return tile;
	}

	public static void theReturn(MapTile tile) {

		if (pool.size() == POOL_MAX_SIZE) {
			return;
		}
		pool.add(tile);
	}
}
