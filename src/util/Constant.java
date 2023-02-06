package util;

import java.awt.*;

/**
 * 游戏中的常量都在该类中维护，便于后期管理
 */

public class Constant {
	/******************* 窗口 ******************/
	public static final String GameTitle = "坦克大战";
	// 长&宽
	public static final int Frame_Width = 800;
	public static final int Frame_Height = 800;

	// 获得当前系统屏幕的宽和高
	public static final int SCREEN_W = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int SCREEN_H = Toolkit.getDefaultToolkit().getScreenSize().height;

	// 位置
	public static final int Frame_X = SCREEN_W - Frame_Width >> 1;
	public static final int Frame_Y = SCREEN_H - Frame_Height >> 1;

	/************** 游戏菜单 ***************/
	public static final int STATE_MENU = 0;
	public static final int STATE_HELP = 1;
	public static final int STATE_ABOUT = 2;
	public static final int STATE_RUN = 3;
	public static final int STATE_OVER = 4;

	public static final String[] MENUS = { "开始游戏", "继续游戏", "游戏帮助", "游戏关于", "退出游戏" };

	public static final String OVER_STR0 = "ESC键退出游戏";
	public static final String OVER_STR1 = "ENTER键回主菜单";

	// 字体
	public static final Font GAME_FONT = new Font("宋体", Font.BOLD, 24);
	public static final Font SMALL_FONT = new Font("黑体", Font.BOLD, 14);

	public static final int REPAINT_INTERVAL = 30;
	// 最大敌人数量
	public static final int ENEMY_MAX_COUNT = 10;
	public static final int ENEMY_BORN_INTERVAL = 5000;// 生成敌人时间5s

	public static final int ENEMY_AI_INITERVAL = 1000;
	public static final double ENEMY_FIRE_PERCENT = 0.05;

}
