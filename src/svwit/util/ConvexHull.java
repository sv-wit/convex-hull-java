package svwit.util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Diese Klasse repräsentiert eine konvexe Hülle.
 * <p>
 * Die Klasse erweitert eine Liste, sodass sie eine unsortierte Menge von Punkten beinhalten kann.
 * Zusätzlich werden vier weitere, wichtige Punkte als Eigenschaft der konvexen Hülle gespeichert.
 * Diese sind: <br>
 * Der lexikographisch kleinste und größte Punkt, sowie die Punkte mit kleinstem und größtem Y-Koordinaten.
 * 
 */
public class ConvexHull extends ArrayList<Point>{

	/** Serialisierungs-ID */
	private static final long serialVersionUID = 3703572238240496844L;
	
	/** Der lexikographisch kleinste Punkt */
	private Point mostLeftPoint = null;
	/** Der lexikographisch größte Punkt */
	private Point mostRightPoint = null;
	/** Der Punkt mit der größten Y-Koordinate */
	private Point mostTopPoint = null;
	/** Der Punkt mit der kleinsten Y-Koordinate */
	private Point mostBottomPoint = null;
	
	/**
	 * Gibt den lexikographisch kleinsten Punkt zurück.
	 * 
	 * @return lexikographisch kleinster Punkt
	 */
	public Point getMostLeftPoint() {
		return mostLeftPoint;
	}
	
	/**
	 * Setzt den übergebenen Punkt als lexikographisch kleinsten Punkt.
	 * 
	 * @param mostLeftPoint Der Punkt, der als lexikographisch kleinster Punkt gesetzt werden soll
	 */
	public void setMostLeftPoint(Point mostLeftPoint) {
		this.mostLeftPoint = mostLeftPoint;
	}
	
	/**
	 * Gibt den lexikographisch größten Punkt zurück.
	 * 
	 * @return lexikographisch größter Punkt
	 */
	public Point getMostRightPoint() {
		return mostRightPoint;
	}
	
	/**
	 * Setzt den übergebenen Punkt als lexikographisch größten Punkt.
	 * 
	 * @param mostRightPoint Der Punkt, der als lexikographisch größter Punkt gesetzt werden soll
	 */
	public void setMostRightPoint(Point mostRightPoint) {
		this.mostRightPoint = mostRightPoint;
	}
	
	/**
	 * gibt den Punkt mit der größten Y-Koordinate zurück.
	 * 
	 * @return Punkt mit größter Y-Koordinate
	 */
	public Point getMostTopPoint() {
		return mostTopPoint;
	}

	/**
	 * Setzt den übergebenen Punkt als Punkt mit der größten Y-Koordinate.
	 * 
	 * @param mostTopPoint Der Punkt, der als Punkt mit der größten Y-Koordinate gesetzt werden soll.
	 */
	public void setMostTopPoint(Point mostTopPoint) {
		this.mostTopPoint = mostTopPoint;
	}

	/**
	 * gibt den Punkt mit der kleinsten Y-Koordinate zurück.
	 * 
	 * @return Punkt mit kleinster Y-Koordinate
	 */
	public Point getMostBottomPoint() {
		return mostBottomPoint;
	}

	/**
	 * Setzt den übergebenen Punkt als Punkt mit der kleinsten Y-Koordinate.
	 * 
	 * @param mostBottomPoint Der Punkt, der als Punkt mit der kleinsten Y-Koordinate gesetzt werden soll.
	 */
	public void setMostBottomPoint(Point mostBottomPoint) {
		this.mostBottomPoint = mostBottomPoint;
	}
	
	/**
	 * Gibt einen Iterator zurück, der die konvexe Hülle in einer Schleife durchläuft.
	 * <p>
	 * Der Iterator startet an der Position, die als Parameter übergeben wird. 
	 * Wenn der Iterator das Ende der konvexen Hülle erreicht hat, springt er zurück auf den ersten Punkt der konvexen Hülle.
	 * 
	 * @param index Startpunkt des Iterators
	 * @return Iterator, der die konvexe Hülle in einer Schleife durchläuft.
	 */
	public Iterator<Point> loopingIterator(int index) {
		Iterator<Point> loopingIterator = new Iterator<>() {

			private int currentIndex = index;
			
			@Override
			public boolean hasNext() {
				return currentIndex < size();
			}

			@Override
			public Point next() {
				Point p = get(currentIndex);
				if(currentIndex < size() - 1) {
					currentIndex++;
				} else {
					currentIndex = 0;
				}
				return p;
			}
			
		};
		return loopingIterator;
	}
	
}
