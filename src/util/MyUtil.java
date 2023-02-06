package util;

import java.awt.*;

/**
 * 工具类
 */
public class MyUtil {
	private MyUtil() {
	}

	/**
	 * 得到指定区间的随机数
	 *
	 * @param min 最小区间包含
	 * @param max 最大区间不包含
	 * @return 随机数
	 */
	public static final int getRandomNumber(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	/**
	 * 获得随机颜色
	 *
	 * @return
	 */
	public static final Color getRandomColor() {
		int red = getRandomNumber(0, 256);
		int blue = getRandomNumber(0, 256);
		int green = getRandomNumber(0, 256);
		return new Color(red, blue, green);
	}

	/**
	 * 判断一个点是否在某一个正方形内部，
	 *
	 * @param rectX  正方形的中心的x坐标
	 * @param rectY  正方形的中心点的y坐标
	 * @param radius 正方形的边长的一半
	 * @param pointX 点的x坐标
	 * @param pointY 点的y坐标
	 * @return 如果点在正方形内部，返回true，否则返回false
	 */
	public static final boolean isCollide(int rectX, int rectY, int radius, int pointX, int pointY) {
		// 正方形的中心点和点的x y 轴的距离
		int disX = Math.abs(rectX - pointX);
		int disY = Math.abs(rectY - pointY);
		if (disX < radius && disY < radius)
			return true;
		return false;
	}

	/**
	 * 根据图片资源路径创建加载图片对象
	 *
	 * @param path 图片路径资源的路径
	 * @return
	 */
	public static final Image createImge(String path) {
		return Toolkit.getDefaultToolkit().createImage(path);
	}

	private static final String[] NAMES = { "A", "B", "C", "D", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
			"Q", "R", "S", "T", "U", "V", "W", "S", "Y", "Z" };

	private static final String[] MODIFIY = { "1", "2", "3", "4", "5", "6", "7", "8", "9", };

	/**
	 * 获得随机名称
	 *
	 * @return
	 */
	public static final String getRandoName() {
		return MODIFIY[getRandomNumber(0, MODIFIY.length)] + NAMES[getRandomNumber(0, NAMES.length)];
	}
}
