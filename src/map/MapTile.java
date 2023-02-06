package map;

import util.BulletsPool;
import util.MyUtil;

import java.awt.*;

import Game.Bullet;
import java.util.List;

/**
 * 地图元素
 */
public class MapTile {
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_HOUSE = 1;
	public static final int TYPE_COVER = 2;
	public static final int TYPE_HARD = 3;

	public static int tileW = 40;
	public static int radius = tileW >> 1;
	private int type = TYPE_NORMAL;

	private static Image[] tileImg;
	static {
		tileImg = new Image[4];
		tileImg[TYPE_NORMAL] = MyUtil.createImge("img/word.png");
		tileImg[TYPE_HOUSE] = MyUtil.createImge("img/house.png");
		tileImg[TYPE_COVER] = MyUtil.createImge("img/cover.png");
		tileImg[TYPE_HARD] = MyUtil.createImge("img/hard.png");

		if (tileW <= 0) {
			tileW = tileImg[TYPE_NORMAL].getWidth(null);
		}
	}

	private int x, y;

	private boolean visible = true;

	public MapTile() {
	}

	public MapTile(int x, int y) {
		this.x = x;
		this.y = y;
		if (tileW <= 0) {
			tileW = tileImg[TYPE_NORMAL].getWidth(null);
		}
	}

	public void draw(Graphics g) {
		if (!visible) {
			return;
		}
		if (tileW <= 0) {
			tileW = tileImg[TYPE_NORMAL].getWidth(null);
		}
		g.drawImage(tileImg[type], x, y, null);

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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 子弹是否和地图块有碰撞
	 * 
	 * @param bullet
	 * @return
	 */
//    public boolean isCollideBullet(List<Bullet> bullets) {
//    	for(Bullet bullet : bullets) {
//    		int bulletX = bullet.getX ();
//    		int bulletY = bullet.getY ();
//    		return MyUtil.isCollide(x+radius, y+radius, radius, bulletX, bulletY);
//    	}
//    	return false; 
//    }

	public boolean isCollideBullet(List<Bullet> bullets) {
		if (!visible || type == TYPE_COVER)
			return false;
		for (Bullet bullet : bullets) {
			int bulletX = bullet.getX();
			int bulletY = bullet.getY();
			boolean collide = MyUtil.isCollide(x + radius, y + radius, radius, bulletX, bulletY);
			if (collide) {
				// 子弹的销毁 TODO
				bullet.setVisible(false);
				BulletsPool.theReturn(bullet);
				return true;
			}

		}
		return false;
	}

	// 判断当前的地图块是否是老巢
	public boolean isHouse() {
		return type == TYPE_HOUSE;
	}

	@Override
	public String toString() {
		return "MapTile [x=" + x + ", y=" + y + ", visible=" + visible + "]";
	}

}
