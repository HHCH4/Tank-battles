package tank;

import Game.GameFrame;
import util.Constant;
import util.EnemyTanksPool;
import util.MyUtil;

import java.awt.*;

public class EnemyTank extends Tank {
	public static final int TYPE_GREEN = 0;
	public static final int TYPE_BULE = 1;
	private int type = TYPE_GREEN;

	// 导入坦克图片
	private static Image[] greenImg;
	private static Image[] buleImg;

	// 记录5秒开始的时间
	private long aiTime;

	static {
		greenImg = new Image[4];
		greenImg[0] = MyUtil.createImge("img/up1.png");
		greenImg[1] = MyUtil.createImge("img/D1.png");
		greenImg[2] = MyUtil.createImge("img/L1.png");
		greenImg[3] = MyUtil.createImge("img/R1.png");

		buleImg = new Image[4];
		buleImg[0] = MyUtil.createImge("img/up.png");
		buleImg[1] = MyUtil.createImge("img/D.png");
		buleImg[2] = MyUtil.createImge("img/L.png");
		buleImg[3] = MyUtil.createImge("img/R.png");
	}

	private EnemyTank(int x, int y, int dir) {
		super(x, y, dir);
		// 敌人一旦创建就开始计时
		aiTime = System.currentTimeMillis();
		type = MyUtil.getRandomNumber(0, TYPE_BULE + 1);
	}

	public EnemyTank() {
		aiTime = System.currentTimeMillis();
		type = MyUtil.getRandomNumber(0, TYPE_BULE + 1);
	}

	// 产生敌人坦克
	public static Tank createEnemy() {
		int x = MyUtil.getRandomNumber(0, 2) == 0 ? RADIUS : Constant.Frame_Width - RADIUS;
		int y = GameFrame.titleHeight + RADIUS;
		int dir = DIR_DOWN;
//        Tank enemy = new EnemyTank(x,y,dir);
		Tank enemy = EnemyTanksPool.get();
		enemy.setX(x);
		enemy.setY(y);
		enemy.setDir(dir);
		enemy.setEnemy(true);
		enemy.setState(STATE_MOVE);
		enemy.setHp(Tank.DEFAULT_HP);
		enemy.setState(STATE_MOVE);

		return enemy;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void drawImgTank(Graphics g) {
		ai();
		g.drawImage(type == TYPE_GREEN ? greenImg[getDir()] : buleImg[getDir()], getX() - RADIUS, getY() - RADIUS,
				null);
	}

	/**
	 * 敌人ai
	 */
	private void ai() {
		if (System.currentTimeMillis() - aiTime > Constant.ENEMY_AI_INITERVAL) {
			// 间隔五秒随机一个状态
			setDir(MyUtil.getRandomNumber(DIR_UP, DIR_RIGHT + 1));
			setState(MyUtil.getRandomNumber(0, 2) == 0 ? STATE_STAND : STATE_MOVE);
			aiTime = System.currentTimeMillis();
		} // 比较小的概率去开火
		if (Math.random() < Constant.ENEMY_FIRE_PERCENT) {
			fire();
		}
	}

}
