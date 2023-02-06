package util;

import tank.EnemyTank;
import tank.Tank;

import java.util.ArrayList;
import java.util.List;

/**
 * 敌人坦克对象池
 */
public class EnemyTanksPool {
	public static final int DEFFAULT_POOL_SIZE = 20;
	public static final int POOL_MAX_SIZE = 20;
	private static List<Tank> pool = new ArrayList<>();

	static {
		for (int i = 0; i < DEFFAULT_POOL_SIZE; i++) {
			pool.add(new EnemyTank());
		}
	}

	public static Tank get() {
		Tank tank = null;
		// 池塘被掏空
		if (pool.size() == 0) {
			tank = new EnemyTank() {
			};
		} else {
			tank = pool.remove(0);
		}
		return tank;
	}

	// 归还
	public static void theReturn(Tank tank) {
		// 池塘的子弹个数到达了最大值，那就不在归还
		if (pool.size() == POOL_MAX_SIZE) {
			return;
		}
		pool.add(tank);
	}
}
