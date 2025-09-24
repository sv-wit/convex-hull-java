package svwit.util;


/**
 * Diese Klasse repräsentiert einen Punkt auf einer Ebene.
 * <p>
 * Der Punkt enthällt eine X- und eine Y- Koordinate.
 * Zusätzlich implementiert diese Klasse das Interface {@code Comparable},
 * wodurch es möglich ist, Instanzen dieser Klasse miteinander zu vergleichen.
 * 
 */
public class Point implements Comparable<Point> {
	
	/** X-Koordinate des Punktes */
	private int x;
	/** Y-Koordinate des Punktes */
	private int y;
	
	/**
	 * Der Konstruktor der Klasse.
	 * <p>
	 * Speichert die übergebenen X- und Y- Koordinaten in die zugehörigen Klassenvariablen.
	 * @param x X-Koordinate des Punktes
	 * @param y Y-Koordinate des Punktes
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gibt die X-Koordinate des Punkts zurück.
	 * @return X-Koordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gibt die Y-Koordinate des Punkts zurück.
	 * @return Y-Koordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Gibt die X- und Y-Koordinaten des Punkts als Integer-Array zurück,
	 * wobei der Index 0 die X-Koordinate und der Index 1 die Y-Koordinate ist.
	 * @return int[] X- und Y-Koordinaten des Punkts als Integer-Array
	 */
	public int[] getPosition() {
		return new int[] {x, y};
	}
	
	/**
	 * Setzt die X-Koordinate des Punkts auf den übergebenen Wert.
	 * @param x X-Koordinate
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Setzt die Y-Koordinate des Punkts auf den übergebenen Wert.
	 * @param y Y-Koordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Setzt die X- und Y-Koordinaten des Punkts auf die übergebenen Werte.
	 * @param x X-Koordinate
	 * @param y Y-Koordinate
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Vergleicht die Koordinaten des Punkts mit den Koordinaten eines anderen Punkts.
	 * @param other der Punkt, mit dem verglichen werden soll.
	 * @return {@code -1}, wenn die eigenen Koordinaten lexikographisch kleiner ist,
	 * 			{@code 0}, wenn die eigenen Koordinaten identisch und
	 * 			{@code 1}, wenn die eigenen Koordinaten lexikographisch größer sind,
	 * 			als die des übergebenen Punktes.
	 */
	@Override
	public int compareTo(Point other) {
		if(this.x < other.x) {
			return -1;
		} else if(this.x == other.x && this.y < other.y) {
			return -1;
		} else if(this.x == other.x && this.y == other.y) {
			return 0;
		} else if(this.x == other.x && this.y > other.y) {
			return 1;
		} else {
			return 1;
		}
	}
	
	
	/**
	 * Vergleicht den eigenen Punkt auf gleichheit mit einem anderen Punkt.
	 * @param other der Punkt, mit dem verglichen werden soll.
	 * @return 		{@code true} wenn die Punkte identisch sind,
	 * 				{@code false} ansonsten.
	 */
	@Override
	public boolean equals(Object other) {
		if(other == null) {
			return false;
		}
		if(this == other) {
			return true;
		}
		if(getClass() != other.getClass()) {
			return false;
		}
		Point point = (Point) other;
		return x == point.getX() && y == point.getY();
	}
	
}
