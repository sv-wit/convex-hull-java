package svwit.util;

import java.util.Iterator;

/**
 * Diese klasse repräsentiert das größte enthaltene Viereck einer konvexen Hülle.
 * <p>
 * Die Klasse speichert die vier Eckpunkte des Vierecks.
 * Da die Klasse das Interface {@code Iterable} implementiert, gibt es auch eine Möglichkeit, 
 * über die gespeicherten Punkte zu iterieren.
 * Zusätztlich bietet die Klasse die Möglichkeit, die Fläche des Vierecks zurückzugeben. 
 * <p>
 * Wenn weniger als vier Punkte verwendet werden soll, werden die entsprechende Konvention 
 * aus der Aufgabenstellung angewendet.
 * 
 */
public class Quadrangle implements Iterable<Point>{

	/** Der erste Punkt des Vierecks */
	private Point pointA;
	/** Der zweite Punkt des Vierecks */
	private Point pointB;
	/** Der dritte Punkt des Vierecks */
	private Point pointC;
	/** Der vierte Punkt des Vierecks */
	private Point pointD;
		
	/**
	 * Gibt die vier Punkte des Vierecks als ein Array von Punkten zurück.
	 * 
	 * @return Array von Punkten
	 */
	public Point[] getQuadranglePoints() {
		return new Point[] {pointA, pointB, pointC, pointD};
	}
	
	/**
	 * Gibt den ersten Punkt des Vierecks zurück.
	 * 
	 * @return Erster Punkt des Vierecks
	 */
	public Point getPointA() {
		return pointA;
	}
	
	/**
	 * Gibt den zweiten Punkt des Vierecks zurück.
	 * 
	 * @return Zweiter Punkt des Vierecks
	 */
	public Point getPointB() {
		return pointB;
	}
	
	/**
	 * Gibt den dritten Punkt des Vierecks zurück.
	 * 
	 * @return Dritter Punkt des Vierecks
	 */
	public Point getPointC() {
		return pointC;
	}
	
	/**
	 * Gibt den vierten Punkt des Vierecks zurück.
	 * 
	 * @return Vierter Punkt des Vierecks
	 */
	public Point getPointD() {
		return pointD;
	}
	
	/**
	 * Setzt die Punkte des Vierecks auf den übergebenen Punkt.
	 * 
	 * @param pointA Der Punkt, der für die vier Punkte des Vierecks verwendet werden soll
	 */
	public void setQuandranglePoints(Point pointA) {
		this.pointA = pointA;
		this.pointB = pointA;
		this.pointC = pointA;
		this.pointD = pointA;	
	}
	
	/**
	 * Setzt die Punkte des Vierecks auf die beiden übergebenen Punkte.
	 * 
	 * @param pointA Der Punkt, der für den ersten und zweiten Punkt des Vierecks verwendet werden soll
	 * @param pointB Der Punkt, der für den dritten und vierten Punkt des Vierecks verwendet werden soll
	 */
	public void setQuandranglePoints(Point pointA, Point pointB) {
		this.pointA = pointA;
		this.pointB = pointA;
		this.pointC = pointB;
		this.pointD = pointB;	
	}
	
	/**
	 * Setzt die Punkte des Vierecks auf die drei übergebenen Punkte.
	 * 
	 * @param pointA Der Punkt, der für den ersten Punkt des Vierecks verwendet werden soll
	 * @param pointB Der Punkt, der für den zweiten Punkt des Vierecks verwendet werden soll
	 * @param pointC Der Punkt, der für den dritten und vierten Punkt des Vierecks verwendet werden soll
	 */
	public void setQuandranglePoints(Point pointA, Point pointB, Point pointC) {
		this.pointA = pointA;
		this.pointB = pointB;
		this.pointC = pointC;
		this.pointD = pointC;	
	}
	
	/**
	 * Setzt die Punkte des Vierecks auf die vier übergebenen Punkte.
	 * 
	 * @param pointA Der Punkt, der für den ersten Punkt des Vierecks verwendet werden soll
	 * @param pointB Der Punkt, der für den zweiten Punkt des Vierecks verwendet werden soll
	 * @param pointC Der Punkt, der für den dritten Punkt des Vierecks verwendet werden soll
	 * @param pointD Der Punkt, der für den vierten Punkt des Vierecks verwendet werden soll
	 */
	public void setQuandranglePoints(Point pointA, Point pointB, Point pointC, Point pointD) {
		this.pointA = pointA;
		this.pointB = pointB;
		this.pointC = pointC;
		this.pointD = pointD;	
	}
	
	/**
	 * Gibt die Fläche des Vierecks zurück.
	 * 
	 * @return Fläche des Vierecks
	 */
	public double getQuadrangleArea() {
		return (getDeterminant(pointA, pointB, pointC) + getDeterminant(pointA, pointC, pointD)) / 2;
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

	@Override
	public Iterator<Point> iterator() {
		Iterator<Point> iterator = new Iterator<>() {

			private int currentIndex = 0;
			
			@Override
			public boolean hasNext() {
				return currentIndex < 4;
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
				} else if(currentIndex == 3) {
					currentIndex++;
					return pointD;
				}
				return null;
			}	
		};
		return iterator;
	}
	
}
