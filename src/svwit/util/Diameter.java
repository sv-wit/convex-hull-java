package svwit.util;

import java.util.Iterator;

/**
 * Diese klasse repräsentiert den Durchmesser einer konvexen Hülle.
 * <p>
 * Die Klasse speichert die zwei Punkte, die ,miteinander verbunden, einen Durchmesser darstellen.
 * Da die Klasse das Interface {@code Iterable} implementiert, gibt es auch eine Möglichkeit, 
 * über die gespeicherten Punkte zu iterieren.
 * Zusätztlich bietet die Klasse die Möglichkeit, die Länge des Durchmesser zurückzugeben. 
 * <p>
 * Wenn weniger als zwei Punkte verwendet werden soll, werden die entsprechende Konvention 
 * aus der Aufgabenstellung angewendet.
 * 
 */
public class Diameter implements Iterable<Point>{

	/** Der erste Punkt des Durchmessers */
	private Point pointA;
	/** Der zweite Punkt des Durchmessers */
	private Point pointB;

	/**
	 * Gibt die beiden Punkte des Durchmessers als ein Array von Punkten zurück.
	 * 
	 * @return Array von Punkten
	 */
	public Point[] getDiameterPoints() {
		return new Point[] {pointA, pointB};
	}
	
	/**
	 * Gibt den ersten Punkt des Durchmessers zurück.
	 * 
	 * @return Erster Punkt des Durchmessers
	 */
	public Point getPointA() {
		return pointA;
	}
	
	/**
	 * Gibt den zweiten Punkt des Durchmessers zurück.
	 * 
	 * @return Zweiter Punkt des Durchmessers
	 */
	public Point getPointB() {
		return pointB;
	}
	
	/**
	 * Setzt die Punkte des Durchmessers auf den übergebenen Punkt.
	 * 
	 * @param pointA Der Punkt, der als Durchmesser verwendet werden soll.
	 */
	public void setDiameterPoints(Point pointA) {
		this.pointA = pointA;
		this.pointB = pointA;
	}
	
	/**
	 * Setzt die Punkte des Durchmessers auf die übergebenen Punkte.
	 * 
	 * @param pointA Erster Punkt des Durchmessers
	 * @param pointB Zweiter Punkt des Durchmessers
	 */
	public void setDiameterPoints(Point pointA, Point pointB) {
		this.pointA = pointA;
		this.pointB = pointB;
	}
	
	/**
	 * Gibt die Länge des Durchmessers zurück.
	 * 
	 * @return Länge des Durchmessers
	 */
	public double getDiameterLength() {
		return Math.sqrt(qLength(pointA, pointB));
	}
	
	/**
	 * Gibt die Distanz zwischen zwei Punkten als Quadrat zurück. Die Distanz als Quadrat wird als {@code long} zurückgegeben.
	 * 
	 * @param pointA Der erste Punkt
	 * @param pointB Der zweite Punkt
	 * @return Die Distanz der Punkte als Quadrat
	 */
	private long qLength(Point pointA, Point pointB) {
		long ABx = (long)pointA.getX() - (long)pointB.getX();
		long ABy = (long)pointA.getY() - (long)pointB.getY();
		return ((ABx * ABx) + (ABy * ABy));
	}

	@Override
	public Iterator<Point> iterator() {
		Iterator<Point> iterator = new Iterator<>() {

			private int currentIndex = 0;
			
			@Override
			public boolean hasNext() {
				return currentIndex < 2;
			}

			@Override
			public Point next() {
				if(currentIndex == 0) {
					currentIndex++;
					return pointA;
				} else if(currentIndex == 1) {
					currentIndex++;
					return pointB;
				}
				return null;
			}
			
		};
		return iterator;
	}
	
}
