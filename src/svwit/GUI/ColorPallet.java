package svwit.GUI;

import java.awt.Color;

/**
 * Diese Klasse hällt alle verwendeten Farben der GUI und ihrer Elemente.
 * <p>
 * Es war angedacht, die Farben aus einer Datei auszulesen, um somit einem Farbwechsel innerhalb des Programms
 * durchführen zu können (z.B. von Darkmode auf Lightmode), hierfür fehlte aber die Zeit.
 * 
 */
public class ColorPallet {

	// Colors UI
	/** Farbe für den Hintergrund der GUI */
	public static final Color COLOR_BACKGROUND_1 = Color.decode("#2D3142");
	/** Farbe für den zweiten Hintergrund der GUI */
	public static final Color COLOR_BACKGROUND_2 = Color.decode("#4F5D75");
	/** Farbe für den Vordergrund der GUI */
	public static final Color COLOR_FOREGROUND = Color.decode("#FFFFFF");
	
	//Colors Buttons
	/** Farbe für den Hintergrund der Schaltflächen */
	public static final Color COLOR_BUTTON_BACKGROUND = Color.decode("#3EA357");
	/** Farbe für den Vordergrund der Schaltflächen */
	public static final Color COLOR_BUTTON_FOREGROUND = Color.decode("#FFFFFF");
	/** Farbe für den Hintergrund der Details der Schaltflächen */
	public static final Color COLOR_BUTTON_DETAIL_BACKGROUND = Color.decode("#67C329");
	/** Farbe für den Vordergrund der Details der Schaltflächen  */
	public static final Color COLOR_BUTTON_DETAIL_FOREGROUND = Color.decode("#FFFFFF");
		
	// Colors Menu
	/** Farbe für den Hintergrund der Menüs */
	public static final Color COLOR_MENU_BACKGROUND = Color.decode("#2D3142");
	/** Farbe für den Vordergrund der Menüs */
	public static final Color COLOR_MENU_FOREGROUND = Color.decode("#FFFFFF");
	/** Farbe für den Hintergrund der Menüpunkte */
	public static final Color COLOR_MENU_ITEM_BACKGROUND = Color.decode("#FFFFFF");
	/** Farbe für den Vordergrund der Menüpunkte */
	public static final Color COLOR_MENU_ITEM_FOREGROUND = Color.decode("#2D3142");
	
	// Colors DrawingPanel
	/** Farbe für die Punkte auf der Zeichenfläche */
	public static final Color COLOR_POINT = Color.decode("#FFFFFF");
	/** Farbe für den nächstgelegensten Punkt auf der Zeichenfläche */
	public static final Color COLOR_SELECTED_POINT = Color.decode("#DC256E");
	/** Farbe für die konvexe Hülle auf der Zeichenfläche */
	public static final Color COLOR_CONVEX_HULL = Color.decode("#FE7F2D");
	/** Farbe für den Durchmesser der konvexen Hülle auf der Zeichenfläche */
	public static final Color COLOR_DIAMETER = Color.decode("#619B8A");
	/** Farbe für das größte enthaltene Viereck der konvexen Hülle auf der Zeichenfläche */
	public static final Color COLOR_QUADRANGLE = Color.decode("#FCCA46");
	/** Farbe für das größte enthaltene Dreieck der konvexen Hülle auf der Zeichenfläche */
	public static final Color COLOR_TRIANGLE = Color.decode("#599FD9");
	/** Farbe für die Animation der konvexen Hülle auf der Zeichenfläche */
	public static final Color COLOR_ANIMATION = Color.decode("#249EA0");
	
}
