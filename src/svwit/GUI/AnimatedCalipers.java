package svwit.GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.Timer;

import svwit.util.ConvexHull;
import svwit.util.Point;

/**
 * Diese Klasse dient der Animation der rotierenden Messchieber.
 * <p>
 * Hier wird die Position der beiden Messschieber berechnet. Gleichzeitig wird die Position kontinuierlich inkrementiert,
 * solange die Animation aktiv und laufend ist.
 * Zusätzlich werden die Messschieber gezeichnet. 
 * 
 */
public class AnimatedCalipers {
	
	/** Die Instanz des {@code DrawingPanel}, welches als Zeichenfläche fungiert */
	private DrawingPanel drawingPanel;
	/** Die konvexe Hülle */
	private ConvexHull convexHull;
	/** Die Instanz des {@code CoordinateTransformer}, welcher die Umrechnung der Bildschirm- und Modellkoordinaten übernimmt */
	private CoordinateTransformer coordinateTransformer;
	/** Der {@code Timer}, welcher die Neuberechnung der Messschieber in einem bestimmten Zeitintervall veranlasst */
	private Timer timer;
	
	/** Der Winkel, in welchem sich die Messschieber befinden */
	private double angle;
	/** Die Rotationsgeschwindigkeit der Messschieber */
	private float rotationSpeed = 0.025f;
	
	/** Der Punkt, der als Ankerpunkt für den ersten Messschieber dient */
	private Point pointA;
	/** Der Punkt, der als Ankerpunkt für den zweiten Messschieber dient */
	private Point pointB;
	
	/** Der Punkt, der auf den Ankerpunkt für den ersten Messschieber folgt */
	private Point pointAfterA;
	/** Der Punkt, der auf den Ankerpunkt für den zweiten Messschieber folgt */
	private Point pointAfterB;
	
	/** Der erste Eckpunkt des ersten Messschiebers */
	private Point pointACaliperA = new Point(0, 0);
	/** Der zweite Eckpunkt des ersten Messschiebers */
	private Point pointBCaliperA = new Point(0, 0);
	
	/** Der erste Eckpunkt des zweiten Messschiebers */
	private Point pointACaliperB = new Point(0, 0);
	/** Der zweite Eckpunkt des zweiten Messschiebers */
	private Point pointBCaliperB = new Point(0, 0);
	
	/** Der Iterator des ersten Messschiebers, welcher durch die Punkte der konvexen Hülle iteriert */
	private Iterator<Point> iteratorA;
	/** Der Iterator des zweiten Messschiebers, welcher durch die Punkte der konvexen Hülle iteriert */
	private Iterator<Point> iteratorB;
	
	/**
	 * Der Konstruktor.
	 * <p>
	 * Hier wird die Zeichenfläche übergeben, auf welcher die Animation dargestellt werden soll.
	 * Zusätzlich wird die Instanz des {@code CoordinateTransformer} in der zugehörigen Klassenvariable gespeichert,
	 * damit die Messschieber auch an der richtigen Position auf dem Bildschirm gezeichnet werden können.
	 * Zuletzt wird der {@code Timer} initialisiert. Der {@code Timer} aktuallisiert die Messschieber ungefähr 30 mal pro Sekunde,
	 * sodass eine Flüssige Bewegung dargestellt werden sollte.
	 * 
	 * @param drawingPanel Zeichenfläche, auf der die Animation gezeichnet werden soll
	 */
	public AnimatedCalipers(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		coordinateTransformer = drawingPanel.getCoordinateTransformer();

		// ~30 FPS
		timer = new Timer(33, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doAnimation();
			}
		});
	}
	
	/**
	 * Setzt die Startparameter der Animation.
	 * <p>
	 * Die Animation wird auf der übergebenen konvexen Hülle angewendet.
	 * 
	 * @param convexHull Konvexe Hülle, auf die die Animation angewendet werden soll
	 */
	public void setAnimation(ConvexHull convexHull) {
		this.convexHull = convexHull;
		
		angle = Math.PI / 2;
		if(convexHull.size() <= 0) {
			timer.stop();
		}
		if(convexHull.size() > 0) {
			pointA = convexHull.getMostLeftPoint();
			pointB = convexHull.getMostRightPoint();
			iteratorA = convexHull.loopingIterator(convexHull.indexOf(pointA));
			iteratorB = convexHull.loopingIterator(convexHull.indexOf(pointB));
			pointAfterA = iteratorA.next();
			pointAfterB = iteratorB.next();
			recalculateCalipers();
		}
	}
	
	/**
	 * Setzt die Rotationsgeschwindigkeit der Messschieber.
	 * 
	 * @param speed Rotationsgeschwindigkeit der Messschieber
	 */
	public void setSpeed(int speed) {
		float tempSpeed = (float)speed;
		rotationSpeed = tempSpeed / 2000;
	}
	
	/**
	 * Startet die Animation
	 */
	public void startAnimation() {
		timer.start();
	}
	
	/**
	 * Stoppt die Animation
	 */
	public void stopAnimation() {
		timer.stop();
	}
	
	/**
	 * Zeichnet die Messschieber, sowie die Verbindungslinie zwischen den Messschiebern.
	 * <p>
	 * Die Messschieber werden durch das übergebene {@code Graphics2D} Objekt der Zeichenfläche gezeichnet.
	 * Die Messschieber werden in der übergebenen Farbe dargestellt.
	 * 
	 * @param g2d Graphics2D Objekt der Zeichenfläche
	 * @param color Farbe, in der die Messschieber dargestellt werden sollen
	 */
	public void drawAnimation(Graphics2D g2d, Color color) {
		Color previousColor = g2d.getColor();
		g2d.setColor(color);
		
		g2d.fillOval(coordinateTransformer.transformToScreenX(pointA.getX()) - 7,
				coordinateTransformer.transformToScreenY(pointA.getY()) - 7, 14, 14);
		
		g2d.drawLine(coordinateTransformer.transformToScreenX(pointACaliperA.getX()),
				coordinateTransformer.transformToScreenY(pointACaliperA.getY()),
				coordinateTransformer.transformToScreenX(pointBCaliperA.getX()),
				coordinateTransformer.transformToScreenY(pointBCaliperA.getY()));
		
		g2d.fillOval(coordinateTransformer.transformToScreenX(pointB.getX()) - 8,
				coordinateTransformer.transformToScreenY(pointB.getY()) - 8, 16, 16);
		
		g2d.drawLine(coordinateTransformer.transformToScreenX(pointACaliperB.getX()),
				coordinateTransformer.transformToScreenY(pointACaliperB.getY()),
				coordinateTransformer.transformToScreenX(pointBCaliperB.getX()),
				coordinateTransformer.transformToScreenY(pointBCaliperB.getY()));
		
		Stroke previousStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(coordinateTransformer.transformToScreenX(pointA.getX()),
				coordinateTransformer.transformToScreenY(pointA.getY()),
				coordinateTransformer.transformToScreenX(pointB.getX()),
				coordinateTransformer.transformToScreenY(pointB.getY()));
		
		g2d.setStroke(previousStroke);
		g2d.setColor(previousColor);
	}
	
	/**
	 * Führt einen Schritt der Animation durch.
	 * <p>
	 * Zuerst wird eine neuberechnung der Position der Messschieber veranlasst.
	 * Anschließend wird geschaut, ob einer der Messschieber an die Position des nächsten Punktes versetzt werden muss.
	 */
	private void doAnimation() {
		
		recalculateCalipers();

		while(getDeterminant(pointA, pointAfterA, pointBCaliperA) >= 0 && convexHull.size() > 2) {
			pointA = pointAfterA;
			pointAfterA = iteratorA.next();
			recalculateCaliperA();
		}
		while(getDeterminant(pointB, pointAfterB, pointACaliperB) >= 0 && convexHull.size() > 2) {
			pointB = pointAfterB;
			pointAfterB = iteratorB.next();
			recalculateCaliperB();
		}
		
		if(angle < 2 * Math.PI) {
			angle += rotationSpeed;
		} else {
			angle = 0;
		}

		drawingPanel.repaint();
	}
	
	/**
	 * Veranlasst eine Neuberechnung der Position der beiden Messschieber.
	 */
	private void recalculateCalipers() {
		recalculateCaliperA();
		recalculateCaliperB();
	}
	
	/**
	 * Berechnet die Position des ersten Messschiebers neu.
	 */
	private void recalculateCaliperA() {
		pointACaliperA.setX((int)(pointA.getX() + Math.cos(angle) * calculateLength()));
		pointACaliperA.setY((int)(pointA.getY() + Math.sin(angle) * calculateLength()));
		pointBCaliperA.setX((int)(pointA.getX() + Math.cos(angle + Math.PI) * calculateLength()));
		pointBCaliperA.setY((int)(pointA.getY() + Math.sin(angle + Math.PI) * calculateLength()));
	}
	
	/**
	 * Berechnet die Position des zweiten Messschiebers neu.
	 */
	private void recalculateCaliperB() {
		pointACaliperB.setX((int)(pointB.getX() + Math.cos(angle) * calculateLength()));
		pointACaliperB.setY((int)(pointB.getY() + Math.sin(angle) * calculateLength()));
		pointBCaliperB.setX((int)(pointB.getX() + Math.cos(angle + Math.PI) * calculateLength()));
		pointBCaliperB.setY((int)(pointB.getY() + Math.sin(angle + Math.PI) * calculateLength()));
	}
	
	/**
	 * Gibt die Determinante aus den drei übergebenen Punkten zurück. Die Determinante wird als {@code long} zurückgegeben.
	 * 
	 * @param pointA Der erste Punkt
	 * @param pointB Der zweite Punkt
	 * @param pointC Der dritte Punkt
	 * @return Die Determinante
	 */
	private long getDeterminant(Point pointA, Point pointB, Point pointC) {
		long determinant = 0;	
		determinant += (long)pointA.getX() * ((long)pointB.getY() - (long)pointC.getY());
		determinant += (long)pointB.getX() * ((long)pointC.getY() - (long)pointA.getY());
		determinant += (long)pointC.getX() * ((long)pointA.getY() - (long)pointB.getY());	
		return determinant;
	}
	
	/**
	 * Berechnet die Länge, die ein Messschieber annehmen soll und gibt diese zurück.
	 * <p>
	 * Hier wird geschaut, dass die Endpunkte des Messschiebers möglichst außerhalb des Sichtbereichs liegen,
	 * um die Illusion zu wahren, dass die Messschieber bis zum Rand der Zeichenfläche reichen.
	 * Dieses Vorgehen funktioniert nicht, wenn der Sichtbereich durch den Benutzer verschoben wird.
	 * 
	 * @return Länge, die die Messschieber annehmen sollen.
	 */
	private int calculateLength() {
		return (int)(Math.max(drawingPanel.getWidth(), drawingPanel.getHeight()) / coordinateTransformer.getZoom());
	}
	
}
