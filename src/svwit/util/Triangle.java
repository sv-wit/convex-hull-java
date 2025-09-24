package svwit.util;

import java.util.Iterator;

/**
 * Diese klasse repräsentiert das größte enthaltene Dreieck einer konvexen Hülle.
 * <p>
 * Die Klasse speichert die drei Eckpunkte des Dreiecks.
 * Da die Klasse das Interface {@code Iterable} implementiert, gibt es auch eine Möglichkeit, 
 * über die gespeicherten Punkte zu iterieren.
 * Zusätztlich bietet die Klasse die Möglichkeit, die Fläche des Dreiecks zurückzugeben. 
 * <p>
 * Wenn weniger als drei Punkte verwendet werden soll, werden die entsprechende Konvention 
 * aus der Aufgabenstellung angewendet.
 * 
 */
public class Triangle implements Iterable<Point>{

	/** Der erste Punkt des Vierecks */
	private Point pointA;
	/** Der zweite Punkt des Vierecks */
	private Point pointB;
	/** Der dritte Punkt des Vierecks */
	private Point pointC;
	
	/**
	 * Gibt die drei Punkte des Dreiecks als ein Array von Punkten zurück.
	 * 
	 * @return Array von Punkten
	 */
	public Point[] getTrianglePoints() {
		return new Point[] {pointA, pointB, pointC};
	}
	
	/**
	 * Gibt den ersten Punkt des Dreiecks zurück.
	 * 
	 * @return Erster Punkt des Vierecks
	 */
	public Point getPointA() {
		return pointA;
	}
	
	/**
	 * Gibt den zweiten Punkt des Dreiecks zurück.
	 * 
	 * @return Erster Punkt des Vierecks
	 */
	public Point getPointB() {
		return pointB;
	}
	
	/**
	 * Gibt den dritten Punkt des Dreiecks zurück.
	 * 
	 * @return Erster Punkt des Vierecks
	 */
	public Point getPointC() {
		return pointC;
	}
	
	/**
	 * Setzt die Punkte des Dreiecks auf den übergebenen Punkt.
	 * 
	 * @param pointA Der Punkt, der für die drei Punkte des Dreiecks verwendet werden soll
	 */
	public void setTrianglePoints(Point pointA) {
		this.pointA = pointA;
		this.pointB = pointA;
		this.pointC = pointA;
	}
	
	/**
	 * Setzt die Punkte des Dreiecks auf die beiden übergebenen Punkte.
	 * 
	 * @param pointA Der Punkt, der für den ersten und zweiten Punkt des Dreiecks verwendet werden soll
	 * @param pointB Der Punkt, der für den dritten Punkt des Dreiecks verwendet werden soll
	 */
	public void setTrianglePoints(Point pointA, Point pointB) {
		this.pointA = pointA;
		this.pointB = pointA;
		this.pointC = pointB;
	}
	
	
	/**
	 * Setzt die Punkte des Dreiecks auf die drei übergebenen Punkte.
	 * 
	 * @param pointA Der Punkt, der für den ersten Punkt des Dreiecks verwendet werden soll
	 * @param pointB Der Punkt, der für den zweiten Punkt des Dreiecks verwendet werden soll
	 * @param pointC Der Punkt, der für den dritten Punkt des Dreiecks verwendet werden soll
	 */
	public void setTrianglePoints(Point pointA, Point pointB, Point pointC) {
		this.pointA = pointA;
		this.pointB = pointB;
		this.pointC = pointC;
	}
	
	/**
	 * Gibt die Fläche des Dreiecks zurück.
	 * 
	 * @return Fläche des Dreiecks
	 */
	public double getTriangleArea() {
		return getDeterminant(pointA, pointB, pointC) / 2;
	}
	
	/**
	 * Gibt die Determinante aus den drei übergebenen Punkten zurück. Die Determinante wird als {@code long} zurückgegeben.
	 * 
	 * @param pointA Der erste Punkt
	 * @param pointB Der zweite Punkt
	 * @param pointC Der dritte Punkt
	 * @return Die Determinante
	 */
	// TODO wird das gebraucht? Wenn nein, weg damit!
	private long getDeterminant(Point pointA, Point pointB, Point pointC) {
		long determinant = 0;	
		determinant += (long)pointA.getX() * ((long)pointB.getY() - (long)pointC.getY());
		determinant += (long)pointB.getX() * ((long)pointC.getY() - (long)pointA.getY());
		determinant += (long)pointC.getX() * ((long)pointA.getY() - (long)pointB.getY());	
		return determinant;
	}
	
	@Override
	public Iterator<Point> iterator() {
		Iterator<Point> iterator = new Iterator<>() {

			private int currentIndex = 0;
			
			@Override
			public boolean hasNext() {
				return currentIndex < 3;
			}

			@Override
			public Point next() {
				if(currentIndex == 0) {
					currentIndex++;
					return pointA;
				} else if(currentIndex == 1) {
					currentIndex++;
					return pointB;
				} else if(currentIndex == 2) {
					currentIndex++;
					return pointC;
				}
				return null;
			}	
		};
		return iterator;
	}
	
}
