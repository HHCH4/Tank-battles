package tank;

import Game.Bullet;
import Game.Explode;
import Game.GameFrame;
import map.MapTile;
import util.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 坦克类
 */

public abstract class Tank {
	// 四个方向
	public static final int DIR_UP = 0;
	public static final int DIR_DOWN = 1;
	public static final int DIR_LIFE = 2;
	public static final int DIR_RIGHT = 3;

	// 半径
	public static final int RADIUS = 20;
	// 默认速度 每30ms跑4px
	public static final int DEFAULT_SPEED = 4;
	// 坦克状态
	public static final int STATE_STAND = 0;
	public static final int STATE_MOVE = 1;
	public static final int STATE_DIE = 2;
	// 初始生命
	public static final int DEFAULT_HP = 10000;

	private int x, y;

	private int hp = DEFAULT_HP;// 血量
	private String name;
	private int atk;// 攻击力
	// 坦克的伤害最值
	public static final int ATK_MAX = 2500;
	public static final int ATK_MIN = 2500;

	private int speed = DEFAULT_SPEED;// 速度
	private int dir;// 方向
	private int state = STATE_STAND;// 状态
	private Color color;
	private boolean isEnemy = false;

	private BloodBar bar = new BloodBar();

	// 炮弹
	private List<Bullet> bullets = new ArrayList();

	// 保存当前坦克上所有的爆炸效果
	private List<Explode> explodes = new ArrayList<>();

	public Tank(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		initTank();
	}

	public Tank() {
		initTank();
	}

	private void initTank() {
		color = MyUtil.getRandomColor();
		name = MyUtil.getRandoName();
		atk = MyUtil.getRandomNumber(ATK_MIN, ATK_MAX);
	}

	/**
	 * 绘制坦克
	 *
	 * @param g
	 */
	public void draw(Graphics g) {
		logic();

//        drawTank (g);
		drawImgTank(g);
		drawBullets(g);
		drawName(g);
		bar.draw(g);
	}

	private void drawName(Graphics g) {
		g.setColor(color);
		g.setFont(Constant.SMALL_FONT);
		g.drawString(name, x - RADIUS, y - 35);
	}

	/**
	 * 使用图片绘制坦克
	 *
	 * @param g
	 */
	public abstract void drawImgTank(Graphics g);

	/**
	 * 使用系统方式绘制坦克
	 *
	 * @param g
	 */
//	private void drawTank(Graphics g) {
//		g.setColor(color);
//		// 绘制坦克的圆
//		g.fillOval(x - RADIUS, y - RADIUS, RADIUS << 1, RADIUS << 1);
//
//		// 炮管
//		int endX = x;
//		int endY = y;
//
//		switch (dir) {
//		case DIR_UP:
//			endY = y - RADIUS * 2;
//			g.drawLine(x - 1, y, endX - 1, endY);
//			g.drawLine(x + 1, y, endX + 1, endY);
//			break;
//		case DIR_DOWN:
//			endY = y + RADIUS * 2;
//			g.drawLine(x - 1, y, endX - 1, endY);
//			g.drawLine(x + 1, y, endX + 1, endY);
//			break;
//		case DIR_LIFE:
//			endX = x - 2 * RADIUS;
//			g.drawLine(x, y - 1, endX, endY - 1);
//			g.drawLine(x, y + 1, endX, endY + 1);
//			break;
//		case DIR_RIGHT:
//			endX = x + 2 * RADIUS;
//			g.drawLine(x, y - 1, endX, endY - 1);
//			g.drawLine(x, y + 1, endX, endY + 1);
//			break;
//		}
//		g.drawLine(x, y, endX, endY);
//	}

	// 坦克的逻辑处理
	private void logic() {
		switch (state) {
		case STATE_STAND:
			break;
		case STATE_MOVE:
			move();
			break;
		case STATE_DIE:
			break;
		}
	}

	private int oldX = -1, oldY = -1;

	// 坦克移动
	private void move() {
		oldX = x;
		oldY = y;
		switch (dir) {
		case DIR_UP:
			y -= speed;
			if (y < RADIUS + GameFrame.titleHeight) {
				y = RADIUS + GameFrame.titleHeight;
			}
			break;
		case DIR_DOWN:
			if (y > Constant.Frame_Height - RADIUS) {
				y = Constant.Frame_Height - RADIUS;
			}
			y += speed;
			break;
		case DIR_LIFE:
			if (x < RADIUS) {
				x = RADIUS;
			}
			x -= speed;
			break;
		case DIR_RIGHT:
			if (x > Constant.Frame_Height - RADIUS) {
				x = Constant.Frame_Height - RADIUS;
			}
			x += speed;
			break;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public List getBullets() {
		return bullets;
	}

	public void setBullets(List bullets) {
		this.bullets = bullets;
	}

	public boolean isEnemy() {
		return isEnemy;
	}

	public void setEnemy(boolean enemy) {
		isEnemy = enemy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Tank{" + "x=" + x + ", y=" + y + ", hp=" + hp + ", atk=" + atk + ", speed=" + speed + ", dir=" + dir
				+ ", state=" + state + '}';
	}

	private long fireTime;// 上次开火时间
	public static final int FIRE_INTERVAL = 500;// 子弹发射最小间隔

	/**
	 * 发射子弹 创建了子弹对象，子弹对象的属性信息通过坦克的信息获得 然后将创建的子弹添加到坦克管理的容器中。
	 */
	public void fire() {
		// 射速
		if (System.currentTimeMillis() - fireTime > FIRE_INTERVAL) {
			int bulletX = x;
			int bulletY = y;
			switch (dir) {
			case DIR_UP:
				bulletY -= RADIUS;
				break;
			case DIR_DOWN:
				bulletY += RADIUS;
				break;
			case DIR_LIFE:
				bulletX -= RADIUS;
				break;
			case DIR_RIGHT:
				bulletX += RADIUS;
				break;
			}

			Bullet bullet = BulletsPool.get();
			bullet.setX(bulletX);
			bullet.setY(bulletY);
			bullet.setDir(dir);
			bullet.setAtk(atk);
			bullet.setColor(color);
			bullet.setVisible(true);
			bullets.add(bullet);

			// 发射子弹后记录本次发射时间
			fireTime = System.currentTimeMillis();
		}
	}

	/**
	 * 将当前坦克发射的所有子弹绘制出来
	 *
	 * @param g
	 */
	private void drawBullets(Graphics g) {
		for (Bullet bullet : bullets) {
			bullet.draw(g);
		}

		// 遍历所有的子弹，将不可见的子弹移除，并还原回对象池
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			if (!bullet.isVisible()) {
				Bullet remove = bullets.remove(i);
				i--;
				BulletsPool.theReturn(remove);
			}
		}
	}

	/**
	 * 坦克销毁时处理坦克所有的子弹
	 */
	public void bulletsReturn() {
		for (Bullet bullet : bullets) {
			BulletsPool.theReturn(bullet);
		}
		bullets.clear();
	}

	// 坦克和子弹碰撞的方法
	public void collidBullets(List<Bullet> bullets) {
		// 遍历所有子弹，依次和当前的坦克进行碰撞的检测
		for (Bullet bullet : bullets) {
			int bulletX = bullet.getX();
			int bulletY = bullet.getY();
			// 子弹和坦克碰撞
			if (MyUtil.isCollide(x, y, RADIUS, bulletX, bulletY)) {
				// 子弹消失
				bullet.setVisible(false);
				// 坦克受伤
				hurt(bullet);
				// 爆炸效果
				addExplode(x, y);
			}
		}
	}

	private void addExplode(int x, int y) {
		// 爆炸效果
		Explode explode = ExplodePool.get();
		explode.setX(x);
		explode.setY(y);
		explode.setVisable(true);
		explode.setIndex(0);
		explodes.add(explode);
	}

	private void hurt(Bullet bullet) {
		int atk = bullet.getAtk();
//		System.out.println("atk=" + atk);
		hp -= atk;
		if (hp < 1) {
			hp = 0;
			die();
		}
	}

	// 坦克死亡需要处理
	private void die() {
		if (isEnemy) {
			// 敌人坦克被消灭 归还
			EnemyTanksPool.theReturn(this);
		} else {
			delaySecondsToOver(1000);
		}
	}

	/**
	 * 判断当前坦克死亡
	 *
	 * @return
	 */
	public boolean isDie() {
		return hp <= 0;
	}

	/**
	 * 绘制当前坦克上所有的爆炸效果
	 *
	 * @param g
	 */
	public void drawExplodes(Graphics g) {
		for (Explode explode : explodes) {
			explode.draw(g);
		}
		// 将不可见的爆炸效果删除
		for (int i = 0; i < explodes.size(); i++) {
			Explode explode = explodes.get(i);
			if (!explode.isVisable()) {
				Explode remove = explodes.remove(i);
				ExplodePool.theReturn(remove);
				i--;
			}
		}
	}

	// 血条
	class BloodBar {
		public static final int BAR_LENGTH = 50;
		public static final int BAR_HEIGHT = 3;

		public void draw(Graphics g) {
			// 填充底色
			g.setColor(Color.YELLOW);
			g.fillRect(x - RADIUS, y - RADIUS - BAR_HEIGHT * 2, BAR_LENGTH, BAR_HEIGHT);
			// 红色血条
			g.setColor(Color.red);
			g.fillRect(x - RADIUS, y - RADIUS - BAR_HEIGHT * 2, hp * BAR_LENGTH / DEFAULT_HP, BAR_HEIGHT);
			// 蓝色边框
			g.setColor(Color.white);
			g.drawRect(x - RADIUS, y - RADIUS - BAR_HEIGHT * 2, BAR_LENGTH, BAR_HEIGHT);
		}
	}

	// 坦克的子弹和地图所有块的碰撞
	public void bulletsCollideMapTiles(List<MapTile> tiles) {
		for (MapTile tile : tiles) {
			if (tile.isCollideBullet(bullets)) {
				// 添加爆炸效果
				addExplode(tile.getX(), tile.getY());
				// 地图水泥块击毁无处理
				if (tile.getType() == MapTile.TYPE_HARD)
					continue;
				// 设置地图块销毁 TODO
				tile.setVisible(false);
				// 归还对象池
				MapTilePool.theReturn(tile);
				// 大本营被摧毁，游戏结束
				if (tile.isHouse()) {
					delaySecondsToOver(1000);
				}
			}
		}
	}

	/**
	 * 延迟若干毫秒切换到游戏结束
	 * 
	 * @param millisSecond
	 */

	private void delaySecondsToOver(int millisSecond) {

		new Thread() {
			public void run() {
				try {
					Thread.sleep(millisSecond);// 1s后切换画面
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				GameFrame.setGameState(Constant.STATE_OVER);
			};
		}.start();

	}

	/**
	 * 一个地图块和当前坦克碰撞的方法 从tile中提取八个点来判断8个点的任何一个点是否和当前坦克有了碰撞 点的顺序从点的左上角开始
	 * 
	 * @param tile
	 * @return
	 */
	public boolean isCollideTile(List<MapTile> tiles) {
		for (MapTile tile : tiles) {
			if (!tile.isVisible() || tile.getType() == MapTile.TYPE_COVER)
				continue;
			// 点——1 左上
			int tileX = tile.getX();
			int tileY = tile.getY();
			boolean collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
			// 如果碰上直接返回，否则就继续判断下一个点
			if (collide) {
				return true;
			}
			// 点——2 中上
			tileX += MapTile.radius;
			collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
			if (collide) {
				return true;
			}
			// 点——3 右上
			tileX += MapTile.radius;
			collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
			if (collide) {
				return true;
			}
			// 点——4 右中
			tileY += MapTile.radius;
			collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
			if (collide) {
				return true;
			}
			// 点——5 右下
			tileY += MapTile.radius;
			collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
			if (collide) {
				return true;
			}
			// 点——6 下中
			tileX -= MapTile.radius;
			collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
			if (collide) {
				return true;
			}
			// 点——7 下左
			tileX -= MapTile.radius;
			collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
			if (collide) {
				return true;
			}
			// 点——8 左中
			tileY -= MapTile.radius;
			collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
			if (collide) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 坦克回退的方法
	 */
	public void back() {
		x = oldX;
		y = oldY;

	}

}
