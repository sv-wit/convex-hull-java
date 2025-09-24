package svwit.GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

import javax.swing.JPanel;

/**
 * Diese Klasse repräsentiert ein Fenster, in welchem die aktuelle Mausposition und der Zoomfaktor angezeigt werden.
 * 
 */
public class CoordinateAndZoomPanel extends JPanel{
	
	/** Serialisierungs-ID */
	private static final long serialVersionUID = 6579505307043408953L;
	
	/** Die X-Koordinate der Maus */
	private int mouseX = 0;
	/** Die Y-Koordinate der Maus */
	private int mouseY = 0;
	/** Der Zoomfaktor */
	private double zoom = 1d;
	
	/**
	 * Der Konstruktor.
	 * <p>
	 * Hier wird die Größe des Fensters auf 300x40 Festgelegt
	 */
	CoordinateAndZoomPanel(){
		setPreferredSize(new Dimension(300, 40));
	}
	
	/**
	 * Aktuallisiert die Mauskoordinaten anhand der Übergebenen X- und Y-Koordinaten.
	 * Anschließend wird das Fenster neu gezeichnet, damit die aktuellen Informationen angezeigt werden.
	 * 
	 * @param x X-Koordinate der Maus
	 * @param y Y-Koodrinate der Maus
	 */
	public void updateMouseCoordinates(int x, int y) {
		mouseX = x;
		mouseY = y;
		repaint();
	}
	
	/**
	 * Aktuallisiert den Zoomfaktor anhand des Übergebenen Zoomfaktors.
	 * Anschließend wird das Fenster neu gezeichnet, damit die aktuellen Informationen angezeigt werden.
	 * 
	 * @param z Zoomfaktor
	 */
	public void updateZoom(double z) {
		zoom = z;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(new Font("Dialog", Font.BOLD, 15));
		
		g2d.setColor(ColorPallet.COLOR_BACKGROUND_1);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		g2d.setColor(ColorPallet.COLOR_FOREGROUND);
		String mouseString = "Mausposition: (" + mouseX + " , " + mouseY + ")";
		Rectangle2D mouseStringBounds = g.getFontMetrics().getStringBounds(mouseString, g2d);
		g2d.drawString(mouseString, (int)(getWidth() / 2 - mouseStringBounds.getWidth() / 2), (int)(getHeight() / 2 - mouseStringBounds.getHeight() / 2 + 5));
		
		DecimalFormat format = new DecimalFormat("###.####");
		String zoomString = "Zoomfaktor: " + format.format(zoom * 100) + "%";
		Rectangle2D zoomStringBounds = g.getFontMetrics().getStringBounds(zoomString, g2d);
		g2d.drawString(zoomString, (int)(getWidth() / 2 - zoomStringBounds.getWidth() / 2), (int)(getHeight() - zoomStringBounds.getHeight() / 2 + 5));
	}
	
}
