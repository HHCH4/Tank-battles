package tank;

import util.MyUtil;

import java.awt.*;

public class MyTank extends Tank {
	// 导入坦克图片
	private static Image[] tankImage;

	static {
		tankImage = new Image[4];
		tankImage[0] = MyUtil.createImge("img/up.png");
		tankImage[1] = MyUtil.createImge("img/D.png");
		tankImage[2] = MyUtil.createImge("img/L.png");
		tankImage[3] = MyUtil.createImge("img/R.png");
	}

	public MyTank(int x, int y, int dir) {
		super(x, y, dir);
	}

	@Override
	public void drawImgTank(Graphics g) {
		g.drawImage(tankImage[getDir()], getX() - RADIUS, getY() - RADIUS, null);
	}
}
