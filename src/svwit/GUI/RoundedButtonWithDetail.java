package svwit.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

/**
 * Diese Klasse repräsentiert eine Schaltfläche mit abgerundeten Kanten und Detail.
 * <p>
 * Das Detail wird als andersfarbiger Kreis mit dem Symbol "+" am linken Ende der Schaltfläche dargestellt.
 * Die Klasse erweitert die Klasse {@code JButton} und nutzt deren Funktionalität.
 * Hier wird lediglich das Aussehen der Schaltfläche angepasst, damit diese in das Gesamtbild der GUI passt.
 * 
 */
public class RoundedButtonWithDetail extends JButton{

	/** Serialisierungs-ID */
	private static final long serialVersionUID = 7920913454393853557L;
	
	/** Farbe des Hintergrunds des Details. Initialisiert mit {@code LIGHT_GRAY} */
	private Color detailBackground = Color.LIGHT_GRAY;
	/** Farbe des Vordergrunds des Details. Initialisiert mit {@code BLACK} */
	private Color detailForeground = Color.BLACK;

	/**
	 * Der Konstruktor.
	 * <p>
	 * Der übergebene Name wird als die Beschriftung der Schaltfläche verwendet. 
	 * Anschließend wird der Hintergrund der Schaltfläche durchsichtig gemacht,
	 * damit die abgerundeten Kanten auch als solche dargestellt werden.
	 * 
	 * @param buttonName Beschriftung der Schaltfläche
	 */
	public RoundedButtonWithDetail(String buttonName) {
		super(buttonName);
		setOpaque(false);
	}
	
	/**
	 * Setzt die Hintergrundfarbe des Details auf die übergebene Farbe.
	 * 
	 * @param detailBackground Hintergrundfarbe des Details
	 */
	public void setDetailBackground(Color detailBackground) {
		this.detailBackground = detailBackground;
	}
	
	/**
	 * Gibt die Hintergrundfarbe des Details zurück.
	 * 
	 * @return Hintergrundfarbe des Details
	 */
	public Color getDetailBackground() {
		return detailBackground;
	}
	
	/**
	 * Setzt die Vordergrundfarbe des Details auf die übergebene Farbe.
	 * 
	 * @param detailForeground Vordergrundfarbe des Details
	 */
	public void setDetailForeground(Color detailForeground) {
		this.detailForeground = detailForeground;
	}
	
	/**
	 * Gibt die Vordergrundfarbe des Details zurück.
	 * 
	 * @return Vordergrundfarbe des Details
	 */
	public Color getDetailForeground() {
		return detailForeground;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(getModel().isArmed()) {
			g2d.setColor(getDetailBackground());
		} else {
			g.setColor(getBackground());
		}
		g2d.fillRoundRect(getSize().height / 2 - 7, 0, getSize().width - 1 - getSize().height / 2, getSize().height - 1, 15, 20);
		
		g2d.setColor(getDetailBackground());
		g2d.fillOval(0, 0, getSize().height - 1, getSize().height - 1);
		
		g2d.setColor(getDetailForeground());
		Font temp = g.getFont();
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g2d.drawString("+", 6, 20);
		g2d.setFont(temp);
		super.paintComponent(g);
	}
	
}
