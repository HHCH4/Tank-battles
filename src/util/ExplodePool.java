package util;

import Game.Explode;

import java.util.ArrayList;
import java.util.List;

public class ExplodePool {

	public static final int DEFFAULT_POOL_SIZE = 20;
	public static final int POOL_MAX_SIZE = 30;
	// 用于保存所有爆炸效果的容器
	private static List<Explode> pool = new ArrayList<>();

	// 在类加载时候创建多个子弹对象添加到容器中
	static {
		for (int i = 0; i < DEFFAULT_POOL_SIZE; i++) {
			pool.add(new Explode());
		}
	}

	/**
	 * 从池塘中获取爆炸效果对象
	 */
	public static Explode get() {
		Explode explode = null;
		// 池塘被掏空
		if (pool.size() == 0) {
			explode = new Explode();
		} else {// 池塘中还有对象
			explode = pool.remove(0);
		}
		return explode;
	}

	// 归还
	public static void theReturn(Explode explode) {
		// 池塘的爆炸效果个数到达了最大值，那就不再归还
		if (pool.size() == POOL_MAX_SIZE) {
			return;
		}
		pool.add(explode);
	}
}
