package svwit.GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Diese Klasse dient der Darstellung einer Scrollleiste.
 * <p>
 * In ihr wird spezifiziert, wie eine Scrollleiste auszusehen hat und anschließend so auf der GUI gezeichnet.
 * 
 */
public class RoundedScrollBarUI extends BasicScrollBarUI{
	
	/** Die Größe des Scrollfeldes */
	private final int THUMB_SIZE = 60;
	
	/**
	 * Erzeugt eine Schaltfläche ohne Funktion, welche durch eine Größe von 0x0 nicht angezeigt wird.
	 * Diese Schaltfläche wird anstelle der üblichen Scrollpfeile verwendet. Hintergrund dieser Aktion ist,
	 * dass die Scrollpfeile nicht angezeigt werden sollen.
	 * 
	 * @return Eine Schaltfläche ohne Funktion mit einer Größe von 0x0.
	 */
	protected JButton createZeroButton() {
	    JButton button = new JButton("zero button");
	    Dimension zeroDim = new Dimension(0,0);
	    button.setPreferredSize(zeroDim);
	    button.setMinimumSize(zeroDim);
	    button.setMaximumSize(zeroDim);
	    return button;
	}
	
	@Override
	protected Dimension getMaximumThumbSize() {
		if(scrollbar.getOrientation() == JScrollBar.VERTICAL) {
			return new Dimension(0, THUMB_SIZE);
		} else {
			return new Dimension(THUMB_SIZE, 0);
		}
	}
	
	@Override
	protected JButton createDecreaseButton(int orientation) {
	    return createZeroButton();
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
	    return createZeroButton();
	}
	
	@Override
	 protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(ColorPallet.COLOR_BUTTON_FOREGROUND);
		g2d.fillRoundRect(r.x + 6, r.y + 6, r.width  - 12, r.height - 12, 10, 10);
	}
	
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		g2d.setColor(ColorPallet.COLOR_BUTTON_BACKGROUND);
		g2d.fillRoundRect(r.x + 3, r.y + 3, r.width - 6, r.height - 6, 10, 10);
	}

}
