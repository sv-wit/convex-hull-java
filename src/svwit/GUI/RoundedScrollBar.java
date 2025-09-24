package svwit.GUI;

import javax.swing.JScrollBar;

/**
 * Diese Klasse repräsentiert eine Scrollleiste mit abgerundeten Kanten.
 * <p>
 * Die Klasse erweitert die Klasse {@code JScrollBar} und nutzt deren Funktionalität.
 * Hier wird lediglich das Aussehen der Scrollleiste angepasst, damit diese in das Gesamtbild der GUI passt.
 * 
 */
public class RoundedScrollBar extends JScrollBar{

	/** Serialisierungs-ID */
	private static final long serialVersionUID = -3280363281644488984L;

	/**
	 * Der Konstruktor.
	 * <p>
	 * Hier wird der Hintergrund der Scrollleiste durchsichtig gemacht,
	 * damit die abgerundeten Kanten auch als solche dargestellt werden.
	 * Anschließend wird ein eigenes UI gesetzt, welches die Darstellung der Scrollleiste übernimmt.
	 */
	public RoundedScrollBar() {
		this.setOpaque(false);
		this.setUI(new RoundedScrollBarUI());
	}
}
