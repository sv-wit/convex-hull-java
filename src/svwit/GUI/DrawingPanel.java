package svwit.GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;
import java.util.SortedSet;

import javax.swing.JPanel;

import svwit.calculator.CalculatorManager;
import svwit.changes.ChangeManager;
import svwit.changes.DrawingPanelChanges;
import svwit.util.ConvexHull;
import svwit.util.Diameter;
import svwit.util.Point;
import svwit.util.PointsCollection;
import svwit.util.Quadrangle;
import svwit.util.Triangle;
import svwit.util.ValueChangeListener;

/**
 * Diese Klasse repräsentiert die Zeichenfläche, auf der die Puntkmenge angezeigt wird.
 * <p>
 * Die Klasse implementiert zusätzlich die Interfaces {@code MouseListener, MouseMotionListener und MouseWheelListener},
 * und kann so eigenständig auf auftretende Mausevents reagieren und diese verarbeiten.
 * 
 */
public class DrawingPanel extends JPanel implements ValueChangeListener, MouseListener, MouseMotionListener, MouseWheelListener {
	
	/** Serialisierungs-ID */
	private static final long serialVersionUID = 8600215577453149184L;
	
	/** Die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der Änderungen durch die GUI vorgenommen werden sollen */
	private CalculatorManager calculatorManager;
	/** Die Instanz des {@code GUIManager}, der für den Aufbau der GUI zuständig ist */
	private GUIManager guiManager;
	/** Die Instanz des {@code DrawingPanelChanges}, welcher auftretende Änderungen auf der Punktmenge verwaltet */
	private DrawingPanelChanges drawingPanelChanges;
	/** Die Instanz des {@code CoordinateTransformer}, welcher für das Umrechnen von Bildschirm- und Modell-Koordinaten zuständig ist */
	private CoordinateTransformer coordinateTransformer;
	/** Die Instanz der {@code AnimatedCalipers}, welche die Animation der Messschieber durchführen */
	private AnimatedCalipers animatedCalipers;
	/** Die Instanz des {@code CoordinateAndZoomPanel}, welcher die Mauskoordinaten und den Zoomfaktor anzeigt */
	private CoordinateAndZoomPanel coordinateAndZoomPanel;
	
	/** Gibt an, ob die Koordinaten der Punkte angezeigt werden sollen */
	private boolean showCoordinatesEnabled = false;
	/** Gibt an, ob nur die Koordinaten des nächstgelegensten Punktes angezeigt werden sollen */
	private boolean showClosestPointCoordinatesEnabled = false;
	/** Gibt an, ob die konvexe Hülle angezeigt werden soll */
	private boolean showConvexHullEnabled = true;
	/** Gibt an, ob der Durchmesser der konvexen Hülle angezeigt werden soll */
	private boolean showDiameterEnabled = true;
	/** Gibt an, ob das größte enthaltene Viereck der konvexen Hülle angezeigt werden soll */
	private boolean showQuadrangleEnabled = true;
	/** Gibt an, ob das größte enthaltene Dreieck der konvexen Hülle angezeigt werden soll */
	private boolean showTriangleEnabled = true;
	/** Gibt an, ob die Animierten Messschieber angezeigt werden sollen */
	private boolean showAnimationEnabled = false;
	/** Gibt an, ob die Mauskoordinaten und der Zoomfaktor angezeigt werden sollen */
	private boolean showMouseCoordinatesAndZoomEnabled = false;
	/** Gibt an, ob aktuell der Sichtbereich verschoben wird */
	private boolean draggedView = false;
	/** Gibt an, ob die Umschalt-Taste zum Verschieben eines Punktes gehalten werden muss */
	private boolean useShiftToMovePoint = true;

	/** Die X-Koordinate, bei welcher das verschieben des Sichtbereichs startet */
	private int originX;
	/** Die Y-Koordinate, bei welcher das verschieben des Sichtbereichs startet */
	private int originY;
	/** Die Verschiebung der X-Koordinate beim start des verschiebens des Sichtbereichs */
	private int initialOffsetX;
	/** Die Verschiebung der Y-Koordinate beim start des verschiebens des Sichtbereichs */
	private int initialOffsetY;
	/** Die maximale Distanz, die ein Punkt zu der Mausposition haben darf, damit er noch als "nächstgelegenster Punkt" gelten kann */
	private int maxDistanceToMouse = 100;

	/** Der Punkt, welcher aktuell verschoben wird */
	private Point draggedPoint;
	/** Die Startposition, die der aktuell zu verschiebende Punkt vor dem start des Verschiebens hatte */
	private Point draggedPointOrigin;
	/** Der Punkt, der der Mausposition am nächsten ist */
	private Point closestPoint;
	
	/** Die Punktmenge, die alle Punkte beinhaltet */
	private PointsCollection allPoints = new PointsCollection();
	/** Die Punktmenge der konvexen Hülle */
	private ConvexHull convexHull = new ConvexHull();
	/** Die Punktmenge des Durchmessers der konvexen Hülle */
	private Diameter diameter = new Diameter();
	/** Die Punktmenge des größten enthaltenen Vierecks der konvexen Hülle */
	private Quadrangle quadrangle = new Quadrangle();
	/** Die Punktmenge des größten enthaltenen Dreiecks der konvexen Hülle */
	private Triangle triangle = new Triangle();

	/**
	 * Der Konstruktor.
	 * <p>
	 * Hier wird der übergebene {@code GUIManager}, der für den Aufbau der GUI zuständig ist, in die zugehörige Klassenvariable gespeichert.
	 * Anschließend werden die benötigten {@code MouseListener} gesetzt, um Mausevents entgegennehmen zu können.
	 * Außerdem meldet sich die Zeichenfläche bei der Instanz des {@code CalculatorManager}, der die Punktmenge hällt, an, 
	 * um über Änderungen an der Punktmenge informiert zu werden.
	 * Zuletzt werden die Benötigten Instanzen von verwendeten Klassen erzeugt und, wenn nötig, der Zeichenfläche hinzugefügt.
	 * 
	 * @param guiManager Instanz des {@code GUIManager}, der für den Aufbau der GUI zuständig ist
	 */
	public DrawingPanel(GUIManager guiManager) {
		super(true); // Setzt das Fenster auf DoubleBuffered, um eine bessere geschwindigkeit beim Zeichnen zu erhalten
		this.guiManager = guiManager;
		calculatorManager = guiManager.getCalculatorManager();
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		setBackground(ColorPallet.COLOR_BACKGROUND_2);

		calculatorManager.addValueChangeListener(this);
		drawingPanelChanges = new DrawingPanelChanges(calculatorManager, guiManager.getChangeManager());
		coordinateTransformer = new CoordinateTransformer();
		animatedCalipers = new AnimatedCalipers(this);
		coordinateAndZoomPanel = new CoordinateAndZoomPanel();
		
		add(coordinateAndZoomPanel);
	}
	
	/**
	 * Aktiviert beziehungsweise deaktiviert das Anzeigen der Mauskoordinaten und dem Zoomfaktor je nach übergebenen Wert.
	 * Anschließend wird die Zeichenfläche neu gezeichnet, um die Änderung sichtbar zu machen
	 * 
	 * @param b {@code true}, wenn dies aktiviert werden soll, {@code false} ansonsten
	 */
	public void setShowMouseCoordinatesAndZoom(boolean b) {
		showMouseCoordinatesAndZoomEnabled = b;
		repaint();
	}
	
	/**
	 * Aktiviert beziehungsweise deaktiviert das Anzeigen der Koordinaten der Punkte je nach übergebenen Wert.
	 * Anschließend wird die Zeichenfläche neu gezeichnet, um die Änderung sichtbar zu machen
	 * 
	 * @param b {@code true}, wenn dies aktiviert werden soll, {@code false} ansonsten
	 */
	public void setShowCoordinatesEnabled(boolean b) {
		showCoordinatesEnabled = b;
		repaint();
	}
	
	/**
	 * Aktiviert beziehungsweise deaktiviert das Anzeigen nur der Koordinaten des nächstgelegensten Punktes je nach übergebenen Wert.
	 * Anschließend wird die Zeichenfläche neu gezeichnet, um die Änderung sichtbar zu machen
	 * 
	 * @param b {@code true}, wenn dies aktiviert werden soll, {@code false} ansonsten
	 */
	public void setShowClosestPointCoordinatesEnabled(boolean b) {
		showClosestPointCoordinatesEnabled = b;
		repaint();
	}
	
	/**
	 * Aktiviert beziehungsweise deaktiviert das Anzeigen der konvexen Hülle je nach übergebenen Wert.
	 * Anschließend wird die Zeichenfläche neu gezeichnet, um die Änderung sichtbar zu machen
	 * 
	 * @param b {@code true}, wenn dies aktiviert werden soll, {@code false} ansonsten
	 */
	public void setShowConvexHullEnabled(boolean b) {
		showConvexHullEnabled = b;
		repaint();
	}
	
	/**
	 * Aktiviert beziehungsweise deaktiviert das Anzeigen des Durchmessers der konvexen Hülle je nach übergebenen Wert.
	 * Anschließend wird die Zeichenfläche neu gezeichnet, um die Änderung sichtbar zu machen
	 * 
	 * @param b {@code true}, wenn dies aktiviert werden soll, {@code false} ansonsten
	 */
	public void setShowDiameterEnabled(boolean b) {
		showDiameterEnabled = b;
		repaint();
	}
	
	/**
	 * Aktiviert beziehungsweise deaktiviert das Anzeigen des größten enthaltenen Vierecks der konvexen Hülle je nach übergebenen Wert.
	 * Anschließend wird die Zeichenfläche neu gezeichnet, um die Änderung sichtbar zu machen
	 * 
	 * @param b {@code true}, wenn dies aktiviert werden soll, {@code false} ansonsten
	 */
	public void setShowQuadrangleEnabled(boolean b) {
		showQuadrangleEnabled = b;
		repaint();
	}
	
	/**
	 * Aktiviert beziehungsweise deaktiviert das Anzeigen des größten enthaltenen Dreiecks der konvexen Hülle je nach übergebenen Wert.
	 * Anschließend wird die Zeichenfläche neu gezeichnet, um die Änderung sichtbar zu machen
	 * 
	 * @param b {@code true}, wenn dies aktiviert werden soll, {@code false} ansonsten
	 */
	public void setShowTriangleEnabled(boolean b) {
		showTriangleEnabled = b;
		repaint();
	}
	
	/**
	 * Aktiviert beziehungsweise deaktiviert das Anzeigen der Animation je nach übergebenen Wert.
	 * Anschließend wird die Zeichenfläche neu gezeichnet, um die Änderung sichtbar zu machen
	 * 
	 * @param b {@code true}, wenn dies aktiviert werden soll, {@code false} ansonsten
	 */
	public void setShowAnimationEnabled(boolean b) {
		showAnimationEnabled = b;
		repaint();
	}
	
	/**
	 * Aktiviert beziehungsweise deaktiviert, dass zum verschieben eines Punktes die Umschalt-Taste gehalten werden muss, je nach übergebenen Wert.
	 * Anschließend wird die Zeichenfläche neu gezeichnet, um die Änderung sichtbar zu machen
	 * 
	 * @param b {@code true}, wenn dies aktiviert werden soll, {@code false} ansonsten
	 */
	public void setUseShiftToMovePointEnabled(boolean b) {
		useShiftToMovePoint = b;
	}
	
	/**
	 * Setzt die Ansicht auf den Initialzustand zurück und stoppt eine noch laufende Animation.
	 */
	public void resetView() {
		coordinateTransformer.reset();
		animatedCalipers.stopAnimation();
	}
	
	/**
	 * Gibt die Instanz des verwendeten {@code CoordinateTransformer} zurück.
	 * 
	 * @return Instanz des verwendeten {@code CoordinateTransformer}
	 */
	public CoordinateTransformer getCoordinateTransformer() {
		return coordinateTransformer;
	}
	
	/**
	 * Gibt die Instanz der verwendeten {@code AnimatedCalipers} zurück.
	 * 
	 * @return Instanz der verwendeten {@code AnimatedCalipers}
	 */
	public AnimatedCalipers getAnimatedCalipers() {
		return animatedCalipers;
	}
	
	/**
	 * Zentriert die Ansicht so, dass die aktuelle Punktmenge auf der gesamten Zeichenfläche angezeigt werden kann.
	 * Hierfür werden automatisch der Zoomfaktor und die Verschiebung des Sichtbereichs angepasst.
	 */
	public void centerView() {
		if(allPoints.size() <= 0) {
			coordinateTransformer.setZoom(1);
			coordinateTransformer.setOffsetX(0);
			coordinateTransformer.setOffsetY(0);
			return;
		}
		
		double convexHullWidth = convexHull.getMostRightPoint().getX() - convexHull.getMostLeftPoint().getX();
		double convexHullHeight = convexHull.getMostTopPoint().getY() - convexHull.getMostBottomPoint().getY();
		
		double widthScale = (double)(getWidth() - 60) / convexHullWidth;
		double heightScale = (double)(getHeight() - 60) / convexHullHeight;
	
		double prefferedZoom = Math.min(widthScale, heightScale);

		coordinateTransformer.setZoom(prefferedZoom);
		coordinateTransformer.setOffsetX((int)((getWidth() - convexHullWidth) / 2) - convexHull.getMostLeftPoint().getX());
		coordinateTransformer.setOffsetY(-(int)((getHeight() - convexHullHeight) / 2) + convexHull.getMostBottomPoint().getY());
		
		coordinateAndZoomPanel.updateZoom(coordinateTransformer.getZoom());
	}
	
	/**
	 * Zeichnet Linien zwischen den Punkten der übergebenen Punktmenge.
	 * Ein Punkt aus der übergebenen Punktmenge kann maximal zwei gleichfarbige Verbindungslinien besitzen. 
	 * Eine zu dem vorherigen Punkt und eine zu dem nachfolgenden.
	 * 
	 * @param g2d Graphics2D Objekt der Zeichenfläche
	 * @param points Punktmenge, der Verbindungslinien hinzugefügt werden soll
	 * @param color Farbe der Verbindungslinien
	 */
	private void drawLinesBetwenPoints(Graphics2D g2d, Iterable<Point> points, Color color) {
		Color previousColor = g2d.getColor();
		g2d.setColor(color);
		Iterator<Point> iterator = points.iterator();
		Point firstPoint = iterator.next();
		Point currentPoint;
		Point previousPoint = firstPoint;
		while(iterator.hasNext()) {
			currentPoint = iterator.next();
			g2d.drawLine(coordinateTransformer.transformToScreenX(previousPoint.getX()), 
					coordinateTransformer.transformToScreenY(previousPoint.getY()), 
					coordinateTransformer.transformToScreenX(currentPoint.getX()), 
					coordinateTransformer.transformToScreenY(currentPoint.getY()));
			previousPoint = currentPoint;
		}
		g2d.drawLine(coordinateTransformer.transformToScreenX(previousPoint.getX()), 
				coordinateTransformer.transformToScreenY(previousPoint.getY()), 
				coordinateTransformer.transformToScreenX(firstPoint.getX()), 
				coordinateTransformer.transformToScreenY(firstPoint.getY()));
		g2d.setColor(previousColor);
	}
	
	/**
	 * Berechnet den nächstgelegensten Punkt zu der Mausposition.
	 * 
	 * @param e {@code MouseEvent}, welches diese Funktion aufruft
	 */
	private void calculateClosestPoint(MouseEvent e) {
		if(allPoints.size() <= 0) {
			return;
		}
		Point min = new Point(coordinateTransformer.transformToModelX(e.getX() - maxDistanceToMouse), coordinateTransformer.transformToModelY(e.getY() - maxDistanceToMouse));
		Point max = new Point(coordinateTransformer.transformToModelX(e.getX() + maxDistanceToMouse), coordinateTransformer.transformToModelY(e.getY() + maxDistanceToMouse));
		SortedSet<Point> subCollection = allPoints.subSet(min, max);
		long distance = Long.MAX_VALUE;
		for(Point p : subCollection) {
			long newDistance = qLength(p, e.getX(), e.getY());
			if(newDistance < distance) {
				distance = newDistance;
				closestPoint = p;
			}
		}
		if(distance > Math.pow(maxDistanceToMouse / coordinateTransformer.getZoom(), 2)) {
			closestPoint = null;
		}
	}
	
	/**
	 * Gibt die Distanz zwischen einem Punkt und der Mausposition als Quadrat zurück. Die Distanz als Quadrat wird als {@code long} zurückgegeben.
	 * 
	 * @param pointA Punkt, zu dem die Distanz berechnet werden soll
	 * @param mouseX X-Koordinate der Maus
	 * @param mouseY Y-Koordinate der Maus
	 * @return Die Distanz des Punktes zur Mausposition als Quadrat
	 */
	private long qLength(Point pointA, int mouseX, int mouseY) {
		long ABx = (long)pointA.getX() - (long)coordinateTransformer.transformToModelX(mouseX);
		long ABy = (long)pointA.getY() - (long)coordinateTransformer.transformToModelY(mouseY);
		return ((ABx * ABx) + (ABy * ABy));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		coordinateTransformer.setScreenHeight(this.getBounds());
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(4));
		g2d.setFont(new Font("Dialog", Font.BOLD, 12));
		
		if(allPoints.size() <= 0) {
			if(showMouseCoordinatesAndZoomEnabled) {
				super.paintComponents(g2d);
			}
			return;
		}
		
		if(showConvexHullEnabled) {
			drawLinesBetwenPoints(g2d, convexHull, ColorPallet.COLOR_CONVEX_HULL);
		}
		
		if(showDiameterEnabled) {
			drawLinesBetwenPoints(g2d, diameter, ColorPallet.COLOR_DIAMETER);
		}
		
		if(showQuadrangleEnabled) {
			drawLinesBetwenPoints(g2d, quadrangle, ColorPallet.COLOR_QUADRANGLE);
		}
		
		if(showTriangleEnabled) {
			drawLinesBetwenPoints(g2d, triangle, ColorPallet.COLOR_TRIANGLE);
		}
		
		if(showAnimationEnabled) {
			animatedCalipers.drawAnimation(g2d, ColorPallet.COLOR_ANIMATION);
		}
		
		if(closestPoint != null) {
			g2d.setColor(ColorPallet.COLOR_SELECTED_POINT);
			g2d.fillOval(coordinateTransformer.transformToScreenX(closestPoint.getX()) - 9, coordinateTransformer.transformToScreenY(closestPoint.getY()) - 9, 18, 18);
			if(showCoordinatesEnabled && showClosestPointCoordinatesEnabled) {
				g2d.setColor(ColorPallet.COLOR_POINT);
				String string = "( " + closestPoint.getX() + " , " + closestPoint.getY() + " )";
				int stringWidth = g2d.getFontMetrics().stringWidth(string);
				g2d.drawString(string, coordinateTransformer.transformToScreenX(closestPoint.getX()) - stringWidth / 2, coordinateTransformer.transformToScreenY(closestPoint.getY()) - 8);
			}
		}
		
		g2d.setColor(ColorPallet.COLOR_POINT);
		for(Point point : allPoints) {
			g2d.fillOval(coordinateTransformer.transformToScreenX(point.getX()) - 4, coordinateTransformer.transformToScreenY(point.getY()) - 4, 8, 8);
			if(showCoordinatesEnabled && !showClosestPointCoordinatesEnabled) {
				String string = "( " + point.getX() + " , " + point.getY() + " )";
				int stringWidth = g2d.getFontMetrics().stringWidth(string);
				g2d.drawString(string, coordinateTransformer.transformToScreenX(point.getX()) - stringWidth / 2, coordinateTransformer.transformToScreenY(point.getY()) - 8);
			}
		}
		if(showMouseCoordinatesAndZoomEnabled) {
			super.paintComponents(g2d);
		}
	}

	@Override
	public void onValueChanged() {
		allPoints = calculatorManager.getAllPoints();
		convexHull = calculatorManager.getconvexHull();
		diameter = calculatorManager.getDiameter();
		quadrangle = calculatorManager.getQuadrangle();
		triangle = calculatorManager.getTriangle();
		
		animatedCalipers.setAnimation(convexHull);

		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			drawingPanelChanges.addPoint(coordinateTransformer.transformToModelX(e.getX()), coordinateTransformer.transformToModelY(e.getY()));
			guiManager.getSidePanel().updateUndoReduButtons();
		} else if(e.getButton() == MouseEvent.BUTTON3) {
			if(closestPoint != null) {
				drawingPanelChanges.removePoint(closestPoint);
				guiManager.getSidePanel().updateUndoReduButtons();
			}
		}
		calculateClosestPoint(e);
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0 || !useShiftToMovePoint) {
				if(closestPoint != null) {
					draggedPoint = closestPoint;
					draggedPointOrigin = closestPoint;
					return;
				}
			}
			draggedView = true;
			originX = e.getX();
			originY = e.getY();
			initialOffsetX = coordinateTransformer.getOffsetX();
			initialOffsetY = coordinateTransformer.getOffsetY();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(draggedPoint != null) {
			if(e.getX() > 0 &&
					e.getX() < getWidth() &&
					e.getY() > 0 &&
					e.getY() < getHeight()) {
				Point newPoint = new Point(coordinateTransformer.transformToModelX(e.getX()), coordinateTransformer.transformToModelY(e.getY()));
				if(drawingPanelChanges.movePoint(draggedPoint, newPoint, ChangeManager.DONT_SAVE_CHANGE))
					draggedPoint = newPoint;
				closestPoint = draggedPoint;
				repaint();
			}
			return;
		}
		if(draggedView) {
			coordinateTransformer.addOffsetX(initialOffsetX, originX, e.getX()); 
			coordinateTransformer.addOffsetY(initialOffsetY, originY, e.getY()); 
			setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			repaint();
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(draggedPoint != null) {
			drawingPanelChanges.movePoint(draggedPointOrigin, draggedPoint, ChangeManager.SAVE_CHANGE);
			guiManager.getSidePanel().updateUndoReduButtons();
			draggedPoint = null;
		}
		draggedView = false;
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		repaint();
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int direction = e.getWheelRotation();
		if(direction > 0) {
			coordinateTransformer.decreaseZoom();
		} else {
			coordinateTransformer.increaseZoom();
		}
		calculateClosestPoint(e);
		if(showMouseCoordinatesAndZoomEnabled) {
			coordinateAndZoomPanel.updateMouseCoordinates(coordinateTransformer.transformToModelX(e.getX()), coordinateTransformer.transformToModelY(e.getY()));
			coordinateAndZoomPanel.updateZoom(coordinateTransformer.getZoom());
		}
		repaint();
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		Point prevClosestPoint = closestPoint;
		calculateClosestPoint(e);
		if(showMouseCoordinatesAndZoomEnabled) {
			coordinateAndZoomPanel.updateMouseCoordinates(coordinateTransformer.transformToModelX(e.getX()), coordinateTransformer.transformToModelY(e.getY()));
			coordinateAndZoomPanel.updateZoom(coordinateTransformer.getZoom());
		}
		if(closestPoint != prevClosestPoint) {
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {
		closestPoint = null;
		repaint();
	}
	
}
