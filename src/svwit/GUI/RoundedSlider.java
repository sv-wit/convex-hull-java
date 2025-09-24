package svwit.GUI;

import javax.swing.JSlider;

/**
 * Diese Klasse repräsentiert einen Schieberegler mit abgerundeten Kanten.
 * <p>
 * Die Klasse erweitert die Klasse {@code JSlider} und nutzt deren Funktionalität.
 * Hier wird lediglich das Aussehen des Schiebereglers angepasst, damit dieser in das Gesamtbild der GUI passt.
 * 
 */
public class RoundedSlider extends JSlider{

	/** Serialisierungs-ID */
	private static final long serialVersionUID = -639257904005184263L;
	
	/**
	 * Der Konstruktor.
	 * <p>
	 * Die übergebenen Werte dienen als Minimal- und Maximalwert, die der Schieberegler annehmen kann.
	 * Zusätzlich wird der Hintergrund des Schiebereglers durchsichtig gemacht,
	 * damit die abgerundeten Kanten auch als solche dargestellt werden.
	 * Zuletzt wird ein eigenes UI gesetzt, welches die Darstellung des Schiebereglers übernimmt.
	 * 
	 * @param min Minimalwert, den der Schieberegler annehmen kann
	 * @param max Maximalwert, den der Schieberegler annehmen kann
	 */
	public RoundedSlider(int min, int max) {
		super(min, max);
		setOpaque(false);
		setUI(new RoundedSliderUI(this));
	}

}
