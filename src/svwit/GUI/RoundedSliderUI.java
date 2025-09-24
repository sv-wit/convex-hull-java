package svwit.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * Diese Klasse dient der Darstellung eines Schiebereglers.
 * <p>
 * In ihr wird spezifiziert, wie ein Schieberegler auszusehen hat und anschließend so auf der GUI gezeichnet.
 * 
 */
public class RoundedSliderUI extends BasicSliderUI {
	
	/**
	 * Der Konstruktor ruft lediglich den Konstruktor der Elternklasse auf und übergibt {@code null}.
	 * Der Konstruktor der Elternklasse erwartet einen {@code JSlider}, ist aber ansonsten leer und
	 * verarbeitet den übergebenen {@code JSlider} in keiner Weise, weswegen das Übegeben von {@code null} 
	 * in Ordnung ist.
	 * 
	 * Sollte sich irgendjemand diesen Quatsch überhaupt durchlesen: Ich wünsche Ihnen einen wunderschönen Tag! :)
	 * 
	 * @param slider Der slider, auf den das UI angewendet werden soll
	 */
	public RoundedSliderUI(JSlider slider) {
		super(null);
	}
	
	@Override
	protected Dimension getThumbSize() {
		return new Dimension(14, 14);
	}
	
	@Override
	public void paintTrack(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(slider.isEnabled()) {
			g2d.setColor(slider.getBackground());
		} else {
			g2d.setColor(Color.LIGHT_GRAY);
		}
		if(slider.getOrientation() == JSlider.VERTICAL) {
			g2d.fillRoundRect(slider.getWidth() / 2 - 4, 2, 6, slider.getHeight(), 2, 2);
		} else {
			g2d.fillRoundRect(2, slider.getHeight() / 2 - 4, slider.getWidth() - 5, 6, 2, 2);
		}
	}
	
	@Override
	public void paintThumb(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(slider.isEnabled()) {
			g2d.setColor(slider.getForeground());
		} else {
			g2d.setColor(Color.GRAY);
		}
		g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
	}
	
	@Override
	public void paintFocus(Graphics g) {
		// Der Focus wird nicht dargestellt!
	}
	
}
