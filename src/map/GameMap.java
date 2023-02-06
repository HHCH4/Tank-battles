package map;

import Game.GameFrame;
import tank.Tank;
import util.Constant;
import util.MapTilePool;
import java.awt.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 游戏地图类
 */
public class GameMap {
	public static final int MAP_X = Tank.RADIUS * 3;
	public static final int MAP_Y = Tank.RADIUS * 3 + GameFrame.titleHeight;
	public static final int MAP_WIDTH = Constant.Frame_Width - Tank.RADIUS * 6;
	public static final int MAP_HEIGHT = Constant.Frame_Height - Tank.RADIUS * 6 - GameFrame.titleHeight;

	// 地图元素块的容器
	private List<MapTile> tiles = new ArrayList<>();

	// 大本营
	private TankHouse house;

	public GameMap() {
		initMap(1);
	}

	/**
	 * 初始化地图,level第几关
	 */
	private void initMap(int level) {
		try {
			loadLevel(level);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 随机添加地图元素块，添加到容器中
//		final int COUNT = 30;
//		for (int i = 0; i < COUNT; i++) {
//			MapTile tile = MapTilePool.get();
//			int x = MyUtil.getRandomNumber(MAP_X, MAP_X + MAP_WIDTH - MapTile.tileW);
//			int y = MyUtil.getRandomNumber(MAP_Y, MAP_Y + MAP_HEIGHT - MapTile.tileW);
//			// 新生成的随机块和已经存在的块有重叠部分，那就重新生成
//			if (isCollide(tiles, x, y)) {
//				i--;
//				continue;
//			}
//			tile.setX(x);
//			tile.setY(y);
//			tiles.add(tile);
//		}

		// 三行的一个地图
//		addRow(MAP_X, MAP_Y, MAP_X + MAP_WIDTH, MapTile.TYPE_NORMAL, 0);
//		addRow(MAP_X, MAP_Y + MapTile.tileW * 2, MAP_X + MAP_WIDTH, MapTile.TYPE_COVER, 0);
//		addRow(MAP_X, MAP_Y + MapTile.tileW * 4, MAP_X + MAP_WIDTH, MapTile.TYPE_HARD, MapTile.tileW + 6);

		// 初始化大本营
		house = new TankHouse();
		addHouse();
	}

	/**
	 * 加载关卡信息
	 * 使用文本添加关卡
	 * @param level
	 */
	private void loadLevel(int level) throws Exception {
		Properties prop = new Properties();
		prop.load(new FileInputStream("level/lv_" + level + ".txt"));
		// 将所有的地图信息都记载进来
		int enemyCount = Integer.parseInt(prop.getProperty("enemyCount"));
		String methodName = prop.getProperty("method");
		int invokeCount = Integer.parseInt(prop.getProperty("invokeCount"));
		// 把实参都读取到数组里面来
		String[] params = new String[invokeCount];
		for (int i = 1; i <= invokeCount; i++) {
			params[i - 1] = prop.getProperty("param" + i);
		}
		// 使用读取到的参数，调用对应的方法
//		TODO
		invokeMethod(methodName, params);
	}

	// 根据方法的名字和参数调用对应的方法
	private void invokeMethod(String name, String[] params) {
		for (String param : params) {
			// 获得每一行的方法的参数，解析
			String[] split = param.split(",");
			// 使用一个int数组保存解析后的内容
			int[] arr = new int[split.length];
			for (int i = 0; i < split.length; i++) {
				arr[i] = Integer.parseInt(split[i]);
			}
			// 块之间的间隔是地图块的倍数
			final int DIS = MapTile.tileW;

			// 解析最后一个double值
//			int dis = (int) (Double.parseDouble(split[i]) * DIS);
			switch (name) {
			case "addRow":
				addRow(MAP_X + arr[0] * DIS, MAP_Y + arr[1] * DIS,
						MAP_X + MAP_WIDTH - arr[2] * DIS, 
						arr[3], DIS+6);
				break;
			case "addCol":
//				addCol(MAP_X + arr[0] * DIS, MAP_Y + arr[1] * DIS, 
//						MAP_Y + MAP_HEIGHT - arr[2] * DIS, 
//						arr[3], DIS);
				break;
			case "addRect":
//				addRect(MAP_X + arr[0] * DIS, MAP_Y + arr[1] * DIS, 
//						MAP_X + MAP_WIDTH - arr[2] * DIS,
//						MAP_Y + MAP_HEIGHT - arr[3] * DIS, 
//						arr[4], DIS);
				break;
			}
		}
	}

	// 将老巢的元素块添加到地图的容器中来
	private void addHouse() {
		tiles.addAll(house.getTiles());
	}

	/**
	 * 某一个点确定的地图块是否和tiles集合中所有的块有重叠的部分
	 *
	 * @param tiles
	 * @param x
	 * @param y
	 * @return 有重叠返回true 否则false
	 */
	private boolean isCollide(List<MapTile> tiles, int x, int y) {
		for (MapTile tile : tiles) {
			int tileX = tile.getX();
			int tileY = tile.getY();

			if (Math.abs(tileX - x) < MapTile.tileW && (tileY - y) < MapTile.tileW) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 只对没有遮挡效果的块进行绘制
	 * 
	 * @param g
	 */
	public void drawBK(Graphics g) {
		for (MapTile tile : tiles) {
			if (tile.getType() != MapTile.TYPE_COVER)
				tile.draw(g);
		}
	}

	/**
	 * 只绘制有遮挡效果的块
	 * 
	 * @param g
	 */
	public void drawCover(Graphics g) {
		for (MapTile tile : tiles) {
			if (tile.getType() == MapTile.TYPE_COVER)
				tile.draw(g);
		}
	}

	public List<MapTile> getTiles() {
		return tiles;
	}

	/**
	 * 对所有不可见的地图块从容器中移除
	 */
	public void clearDestoryTile() {
		for (int i = 0; i < tiles.size(); i++) {
			MapTile tile = tiles.get(i);
			if (!tile.isVisible()) {
				tiles.remove(i);
			}
		}
	}

	/**
	 * 往地图块容器中添加一行指定的地图块容器
	 * 
	 * @param startX 添加地图块的起始的x坐标
	 * @param startY 添加地图块的起始的y坐标
	 * @param endX   结束的x坐标
	 * @param type   地图块的类型
	 * @param DIS    地图块的间隔 如果是块的宽度，这是连续的
	 */
	public void addRow(int startX, int startY, int endX, int type, final int DIS) {
		int count = (endX - startX) / (MapTile.tileW + DIS);
		for (int i = 0; i < count; i++) {
			MapTile tile = MapTilePool.get();
			tile.setType(type);
			tile.setX(startX + i * (MapTile.tileW + DIS));
			tile.setY(startY);
			tiles.add(tile);
		}
	}

	/**
	 * 往地图块容器中添加一行指定的地图块容器
	 * 
	 * @param startX 添加地图块的起始的x坐标
	 * @param startY 添加地图块的起始的y坐标
	 * @param endY   结束的y坐标
	 * @param type   地图块的类型
	 * @param DIS    地图块的间隔 如果是块的宽度，这是连续的
	 */
	public void addCol(int startX, int startY, int endY, int type, final int DIS) {
		int count = (endY - startY) / (MapTile.tileW + DIS);
		for (int i = 0; i < count; i++) {
			MapTile tile = MapTilePool.get();
			tile.setType(type);
			tile.setX(startX);
			tile.setY(startY + i * (MapTile.tileW + DIS));
			tiles.add(tile);
		}
	}

	/**
	 * 对指定的矩形区域添加元素块
	 * 
	 * @param startX 添加地图块的起始的x坐标
	 * @param startY 添加地图块的起始的y坐标
	 * @param endX   结束的x坐标
	 * @param endY   结束的y坐标
	 * @param type   地图块的类型
	 * @param DIS    地图块的间隔 如果是块的宽度，这是连续的
	 */
	public void addRect(int startX, int startY, int endX, int endY, int type, final int DIS) {
//		int cols = (endX - startX) / (MapTile.tileW + DIS);
//		for (int i = 0; i < cols; i++) {
//			addRow(startX+i*(MapTile.tileW+DIS), startY, endX, type, DIS);
//		}

		int rows = (endY - startY) / (MapTile.tileW + DIS);
		for (int i = 0; i < rows; i++) {
			addCol(startX, startY + i * (MapTile.tileW + DIS), endX, type, DIS);
		}

	}

}
