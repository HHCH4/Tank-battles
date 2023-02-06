package util;

import Game.Bullet;

import java.util.ArrayList;
import java.util.List;

/**
 * 子弹对象池类
 */
public class BulletsPool {
	public static final int DEFFAULT_POOL_SIZE = 200;
	public static final int POOL_MAX_SIZE = 300;
	// 用于保存所有子弹的容器
	private static List<Bullet> pool = new ArrayList<>();

	// 在类加载时候创建多个子弹对象添加到容器中
	static {
		for (int i = 0; i < DEFFAULT_POOL_SIZE; i++) {
			pool.add(new Bullet());
		}
	}

	/**
	 * 从池塘中获取子弹对象
	 */
	public static Bullet get() {
		Bullet bullet = null;
		// 池塘被掏空
		if (pool.size() == 0) {
			bullet = new Bullet();
		} else {// 池塘中还有对象
			bullet = pool.remove(0);
		}
		return bullet;
	}

	// 归还
	public static void theReturn(Bullet bullet) {
		// 池塘的子弹个数到达了最大值，那就不在归还
		if (pool.size() == POOL_MAX_SIZE) {
			return;
		}
		pool.add(bullet);
	}
}
