package main.application.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

public class Constants {
	public static Font TINY_FONT = new Font(Font.DIALOG, Font.ITALIC, 10);
	public static Font DEFAULT_FONT = new Font(Font.DIALOG, Font.PLAIN, 13);
	public static Font BOLD_FONT = new Font(Font.DIALOG, Font.PLAIN, 20);
	public static BasicStroke stroke1 = new BasicStroke(1);
	public static BasicStroke stroke2 = new BasicStroke(2);
	public static Color DARK_RED = new Color(128, 0, 0);
	public static Color DARK_ORANGE = new Color(255,165,0);
	
	public static int TABLE_SIDE_MARGIN = 50;
	public static int TABLE_TOP_MARGIN = 50;
	public static int DRAWING_PANEL_WIDTH = 1000;
	public static int HEADER_COLUMN_HEIGHT = 30;
	public static int COLUMN_HEIGHT = 20;	
	public static int SYMBOL_WIDTH = 20;	
	public static int CONSTRAINT_MARK_RADIUS = 6;	
	public static int LINE_HOVER_RANGE = 5;
}