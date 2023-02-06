package Game;

import map.GameMap;
import map.MapTile;
import tank.EnemyTank;
import tank.MyTank;
import tank.Tank;
import util.MyUtil;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static util.Constant.*;

/**
 * 游戏主窗口类 游戏的展示内容都在该类中显示
 */
public class GameFrame extends Frame implements Runnable {
	// 第一次使用时加载
	private Image overImg = null;

	// 定义一张和屏幕大小相同图片
	private BufferedImage buffImg = new BufferedImage(Frame_Width, Frame_Height, BufferedImage.TYPE_4BYTE_ABGR);

	// 游戏状态
	private static int gameState;
	// 菜单指向
	private int menuIndex;
	// 标题栏的高度
	public static int titleHeight;

	// 定义坦克对象
	private Tank myTank;
	// 敌人坦克
	private List<Tank> enemies = new ArrayList<>();

	// 定义地图相关的内容
	private GameMap gameMap;

	/**
	 * 对窗口初始化
	 */
	public GameFrame() {
		inifFrame();
		initEventListner();
		// 启动用于刷新画面
		new Thread(this).start();
	}

	/**
	 * 对游戏内容初始化
	 */

	private void initGame() {

		gameState = STATE_MENU;
	}

	/**
	 * 属性进行初始化
	 */
	private void inifFrame() {
		setTitle(GameTitle);
		// 窗口大小
		setSize(Frame_Width, Frame_Height);
		// 窗口大小不可变

		// 设置坐标
		setLocation(Frame_X, Frame_Y);
		// 窗口可见
		setVisible(true);
		// 求标题栏的高度
		titleHeight = getInsets().top;

	}

	/**
	 * 是Frame类方法，继承下来 该方法负责所有绘制的内容 所有需要在屏幕中显示的内容 都要在给方法内调用 该方法不能主动调用
	 * 必须通过调用repaint();去回调该方法
	 *
	 * @param g1
	 */
	@Override
	public void update(Graphics g1) {
		// 得到图片的画笔
		Graphics g = buffImg.getGraphics();
		// 使用图片画笔绘制到图片上
		g.setFont(GAME_FONT);
		switch (gameState) {
		case STATE_MENU:
			drawMenu(g);
			break;
		case STATE_HELP:
			drawHelp(g);
			break;
		case STATE_ABOUT:
			drawAbout(g);
			break;
		case STATE_RUN:
			drawRun(g);
			break;
		case STATE_OVER:
			drawOver(g);
			break;
		}
		// 使用系统画笔，将图片绘制到Frame上
		g1.drawImage(buffImg, 0, 0, null);
	}

	/**
	 * 绘制游戏结束
	 *
	 * @param g
	 */
	private void drawOver(Graphics g) {
		// 保证只加载一次
		if (overImg == null) {
			overImg = MyUtil.createImge("img/over.png");
		}

		int imgW = overImg.getWidth(null);
		int imgH = overImg.getHeight(null);

		g.drawImage(overImg, Frame_Width - imgW >> 1, Frame_Height - imgH >> 1, null);

		// 添加按键的提示信息
		g.setColor(Color.WHITE);
		g.drawString(OVER_STR0, 10, Frame_Height - 30);
		g.drawString(OVER_STR1, Frame_Width - 200, Frame_Height - 30);
	}

	// 游戏运行状态绘制内容
	private void drawRun(Graphics g) {
		// 绘制黑色背景
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Frame_Width, Frame_Width);

		// 绘制地图的碰撞层
		gameMap.drawBK(g);

		drawEnemies(g);

		myTank.draw(g);
		// 绘制地图的遮挡层
		gameMap.drawCover(g);

		drawExplodes(g);

		// 子弹和坦克碰撞的方法
		bulletCollideTank();

		// 子弹和所有地图块的碰撞
		bulletAndTankCollideMapTile();

	}

	// 绘制所有敌人的坦克
	private void drawEnemies(Graphics g) {
		for (int i = 0; i < enemies.size(); i++) {
			Tank enemy = enemies.get(i);
			if (enemy.isDie()) {
				enemies.remove(i);
				i--;
				continue;
			}

			enemy.draw(g);
		}
	}

	private void drawAbout(Graphics g) {

	}

	private void drawHelp(Graphics g) {

	}

	/**
	 * 绘制菜单状态下的内容
	 *
	 * @param g 画笔对象
	 */

	private void drawMenu(Graphics g) {
		// 绘制黑色背景
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, Frame_Width, Frame_Width);

		final int STR_WIDTH = 66;
		final int DIS = 40;// 行间距
		int x = Frame_X - STR_WIDTH >> 1;
		int y = Frame_Y;

		g.setColor(Color.WHITE);
		for (int i = 0; i < MENUS.length; i++) {
			if (i == menuIndex) {// 选中的菜单项颜色设置为红色
				g.setColor(Color.red);
			} else {// 其他的为白色
				g.setColor(Color.WHITE);
			}
			g.drawString(MENUS[i], x, y + DIS * i);
		}

	}

	/**
	 * 初始化事件监听
	 */
	private void initEventListner() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// 按键监听事件
		addKeyListener(new KeyAdapter() {
			// 键盘按下的时候被回调的方法
			@Override
			public void keyPressed(KeyEvent e) {
				// 获得被按下键的键值
				int keyCode = e.getKeyCode();

				// 不同的游戏状态，给出不同的处理方法
				switch (gameState) {
				case STATE_MENU:
					KeyPreesedEvenMenu(keyCode);
					break;
				case STATE_HELP:
					KeyPreesedEvenHelp(keyCode);
					break;
				case STATE_ABOUT:
					KeyPreesedEvenAbout(keyCode);
					break;
				case STATE_RUN:
					KeyPreesedEvenRun(keyCode);
					break;
				case STATE_OVER:
					KeyPreesedEvenOver(keyCode);
					break;
				}
			}

			// 键盘松开的时候回调的内容
			@Override
			public void keyReleased(KeyEvent e) {
				// 获得被按下键的键值
				int keyCode = e.getKeyCode();

				// 不同的游戏状态，给出不同的处理方法
				if (gameState == STATE_RUN) {
					KeyReleasedEvenRun(keyCode);
				}
			}
		});
	}

	// 按键松开时，游戏中的处理方法
	private void KeyReleasedEvenRun(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:

		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:

		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:

		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			myTank.setState(Tank.STATE_STAND);
			break;
		}
	}

	/**
	 * 游戏结束的按键处理
	 *
	 * @param keyCode
	 */
	private void KeyPreesedEvenOver(int keyCode) {
		// 结束游戏
		if (keyCode == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		} else if (keyCode == KeyEvent.VK_ENTER) {
			setGameState(STATE_MENU);
			// 游戏需要进行重置
			resetGame();
		}
	}

	// 重置游戏状态
	private void resetGame() {
		menuIndex = 0;
		// 先让自己坦克的子弹还回对象池
		myTank.bulletsReturn();
		// 销毁自己的坦克
		myTank = null;
		// 清空敌人相关资源
		for (Tank enemy : enemies) {
			enemy.bulletsReturn();
		}
		enemies.clear();
		// 清空地图资源
		gameMap = null;
	}

	// 游戏运行中的按键处理方法
	private void KeyPreesedEvenRun(int keyCode) {
		// 游戏中按键处理
		switch (keyCode) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			myTank.setDir(Tank.DIR_UP);
			myTank.setState(Tank.STATE_MOVE);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			myTank.setDir(Tank.DIR_DOWN);
			myTank.setState(Tank.STATE_MOVE);
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			myTank.setDir(Tank.DIR_LIFE);
			myTank.setState(Tank.STATE_MOVE);
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			myTank.setDir(Tank.DIR_RIGHT);
			myTank.setState(Tank.STATE_MOVE);
			break;
		case KeyEvent.VK_J:
		case KeyEvent.VK_SPACE:
			myTank.fire();
			break;
		}
	}

	private void KeyPreesedEvenAbout(int keyCode) {

	}

	private void KeyPreesedEvenHelp(int keyCode) {

	}

	// 菜单状态下的按箭头处理
	private void KeyPreesedEvenMenu(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			if (--menuIndex < 0) {
				menuIndex = MENUS.length - 1;
			}

			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			if (++menuIndex > MENUS.length - 1) {
				menuIndex = 0;
			}

			break;
		case KeyEvent.VK_ENTER:
			newGame();
			break;
		}
	}

	/**
	 * 开始新游戏
	 */

	private void newGame() {
		gameState = STATE_RUN;

		// 创建坦克对象，敌人坦克对象
		myTank = new MyTank(Frame_Width / 3, Frame_Height - Tank.RADIUS, Tank.DIR_UP);

		gameMap = new GameMap();

		// 使用单独线程用于控制生产敌人的坦克
		new Thread() {
			@Override
			public void run() {
				while (true) {
					if (enemies.size() < ENEMY_MAX_COUNT) {
						Tank enemy = EnemyTank.createEnemy();
						enemies.add(enemy);
					}
					try {
						Thread.sleep(ENEMY_BORN_INTERVAL);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// 只有游戏在run的状态下才创建敌人坦克
					if (gameState != STATE_RUN) {
						break;
					}
				}
			}
		}.start();
	}

	// 双缓冲
	@Override
	public void run() {
		while (true) {
			repaint();
			try {
				Thread.sleep(REPAINT_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void bulletCollideTank() {
		// 我的坦克子弹和敌人坦克碰撞
		for (Tank enemy : enemies) {
			enemy.collidBullets(myTank.getBullets());
		}
		// 敌人的坦克子弹和我的坦克碰撞
		for (Tank enemy : enemies) {
			myTank.collidBullets(enemy.getBullets());
		}

	}

	// 子弹和地图块的碰撞
	private void bulletAndTankCollideMapTile() {
		// 己方坦克子弹与地图块碰撞
		myTank.bulletsCollideMapTiles(gameMap.getTiles());

		for (Tank enemy : enemies) {
			enemy.bulletsCollideMapTiles(gameMap.getTiles());
		}
		// 己方坦克和地图的碰撞
		if (myTank.isCollideTile(gameMap.getTiles())) {
			myTank.back();
		}
		// 敌人坦克和地图的碰撞
		for (Tank enemy : enemies) {
			if (enemy.isCollideTile(gameMap.getTiles())) {
				enemy.back();
			}
		}

		// 清理所有被销毁的地图块
		gameMap.clearDestoryTile();
	}

	// 所有的坦克上的爆炸效果
	private void drawExplodes(Graphics g) {
		for (Tank enemy : enemies) {
			enemy.drawExplodes(g);
		}
		myTank.drawExplodes(g);
	}

	// 获得游戏状态和修改游戏状态
	public static void setGameState(int gameState) {
		GameFrame.gameState = gameState;
	}

	public static int getGameState() {
		return gameState;
	}
}