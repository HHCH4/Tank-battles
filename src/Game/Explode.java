package Game;

import util.MyUtil;

import java.awt.*;
import java.text.RuleBasedCollator;

public class Explode {
	// 爆炸帧个数
	public static final int EXPLODE_FRAME_COUNT = 4;
	// 导入资源
	public static Image[] img;

	static {
		img = new Image[EXPLODE_FRAME_COUNT];
		for (int i = 0; i < img.length; i++) {
			img[i] = MyUtil.createImge("img/boom" + i + ".png");
		}
	}

	// 爆炸效果的属性
	private int x, y;
	// 当前播放的帧的下标[0-3]
	private int index;
	//
	private boolean visable = true;

	public Explode() {
		index = 0;
	}

	public Explode(int x, int y) {
		this.x = x;
		this.y = y;
		index = 0;

	}

	public void draw(Graphics g) {
		if (!visable) {
			return;
		}
		g.drawImage(img[index], x, y, null);
		index++;
		// 播放为最后一帧设置为不可见
		if (index >= EXPLODE_FRAME_COUNT) {
			visable = false;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isVisable() {
		return visable;
	}

	public void setVisable(boolean visable) {
		this.visable = visable;
	}

	@Override
	public String toString() {
		return "Explode{" + "x=" + x + ", y=" + y + ", index=" + index + ", visable=" + visable + '}';
	}
}
