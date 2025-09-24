package svwit.calculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import svwit.util.ConvexHull;
import svwit.util.Diameter;
import svwit.util.FileManager;
import svwit.util.Point;
import svwit.util.PointsCollection;
import svwit.util.Quadrangle;
import svwit.util.Triangle;
import svwit.util.ValueChangeListener;

/**
 * Diese Klasse dient als Schnittstelle zwischen der GUI, und der Berechnungslogik.
 * <p>
 * Hier wird die Punktmenge, sowie alle Methoden auf der Punktmenge verwaltet. 
 * Dazu gehören das Hinzufügen und Entfernen von einem oder mehreren Punkten,
 * sowie das Laden und Speichern der Punktmenge aus/in eine Datei.
 * <p>
 * Zusätzlich wird eine Instanz der Klasse {@code Calculator} verwaltet, in welcher die konvexe Hülle,
 * sowie der Durchmesser und das größte Enthaltene Viereck und Dreieck der konvexen Hülle berechnet werden.
 * Die ergebnisse dieser Berechnungen können über diese Klasse abgefragt werden.
 * 
 */
public class CalculatorManager {
	
	/** Der {@code FileManager} zum Laden und Speichern von Punktmengen */
	private FileManager fileManager;
	/** Eine Liste aller angemeldeter Listener, die über Änderungen an der
	 *  Punktmenge und den Ergebnissen der Berechnungen informiert werden sollen. */
	private List<ValueChangeListener> listeners;
	/** Der {@code Calculator} zur Berechnung der konvexen Hülle, sowie dem Durchmesser und
	 * dem größten enthaltenen Viereck und Dreieck der konvexen Hülle */
	private Calculator calc;
	
	/** Die Punktmenge, die alle Punkte enthällt */
	private PointsCollection allPoints;
	
	/**
	 * Der Konstruktor.
	 * <p>
	 * Hier wird jeweils eine neue Instanz des {@code Calculator}, {@code FileManager}, Der Punktmenge, 
	 * die alle Punkte enthällt und eine Liste der angemeldeten Listener erzeugt.
	 */
	public CalculatorManager() {
		calc = new Calculator();
		fileManager = new FileManager(this);
		listeners = new ArrayList<>();
		allPoints = new PointsCollection();
	}
	
	/**
	 * Gibt die Punktmenge, die alle Punkte enthällt, zurück.
	 * @return Punktmenge mit allen Punkten
	 */
	public PointsCollection getAllPoints() {
		return allPoints;
	}
	
	/**
	 * Gibt die konvexe Hülle zurück.
	 * 
	 * @return konvexe Hülle
	 */
	public ConvexHull getconvexHull() {
		return calc.getConvexHull();
	}
	
	/**
	 * Gibt den Durchmesser der konvexen Hülle zurück.
	 * 
	 * @return Durchmesser der konvexen Hülle
	 */
	public Diameter getDiameter() {
		return calc.getDiameter();
	}
	
	/**
	 * Gibt das größte enthaltene Viereck der konvexen Hülle zurück.
	 * 
	 * @return größtes enthaltene Viereck der konvexen Hülle
	 */
	public Quadrangle getQuadrangle() {
		return calc.getQuadrangle();
	}
	
	/**
	 * Gibt das größte enthaltene Dreieck der konvexen Hülle zurück.
	 * 
	 * @return größtes enthaltene Dreieck der konvexen Hülle
	 */
	public Triangle getTriangle() {
		return calc.getTriangle();
	}
	
	/**
	 * Fügt einen neuen Punkt der aktuellen Punktmenge hinzu.
	 * 
	 * @param x	Die X-Koordinate des neuen Punkts.
	 * @param y	Die Y-Koordinate des neuen Punkts.
	 */
	public void addPoint(int x, int y) {
		Point newPoint = new Point(x, y);
		allPoints.add(newPoint);
		update();
	}
	
	/**
	 * Fügt einen neuen Punkt der aktuellen Punktmenge hinzu.
	 * 
	 * @param p Der Punkt, der hinzugefügt werden soll.
	 * @return {@code true}, wenn der Punkt hinzugefügt wurde, {@code false} ansonsten.
	 */
	public boolean addPoint(Point p) {
		if(allPoints.add(p)) {
			update();
			return true;
		}
		return false;
	}
	
	/**
	 * Fügt eine Menge an Punkten der aktuellen Punktmenge hinzu.
	 * <p>
	 * Die hinzuzufügenden Punkte werden als 2-Dimensionales Integer-Array mit X- und Y-Koordinaten übergeben.
	 * 
	 * @param newPoints	Ein Array von Arrays mit X- und Y-Koordinaten, die hinzugefügt werden sollen
	 */
	public void addPointsFromArray(int[][] newPoints) {
		for(int[] point : newPoints) {
			Point newPoint = new Point(point[0], point[1]);
			allPoints.add(newPoint);
		}	
		update();
	}
	
	/**
	 * Fügt eine Menge an Punkten der aktuellen Punktmenge hinzu.
	 * <p>
	 * Die hinzuzufügenden Punkte werden als Liste von Punkten übergeben.
	 * 
	 * @param newPoints Eine Liste an Punkten, die hinzugefügt werden sollen
	 */
	public void addPointsFromArrayList(ArrayList<Point> newPoints) {
		allPoints.addAll(newPoints);
		update();
	}

	/**
	 * Entfernt den überbenen Punkt aus der aktuellen Punktmenge.
	 * 
	 * @param p Der Punkt, der entfernt werden soll.
	 * @return {@code true}, wenn das Entfernen erfolgreich war, {@code false} ansonsten
	 */
	public boolean removePoint(Point p) {
		if(allPoints.remove(p)) {
			update();
			return true;
		}
		return false;
	}
	
	/**
	 * Entfernt eine Menge an Punkten aus der aktuellen Punktmenge.
	 * <p>
	 * Die zu entfernenden Punkte werden als Liste an Punkten übergeben.
	 * 
	 * @param removePoints Die Liste der zu entfernenden Punkte
	 */
	public void removePointsFromArrayList(ArrayList<Point> removePoints) {
		allPoints.removeAll(removePoints);
		update();
	}
	
	/**
	 * Entfernt alle Punkte aus der aktuellen Punktmenge.
	 */
	public void clearAllPoints() {
		allPoints.clear();
		update();
	}

	/**
	 * Entfernt alle Punkte aus der aktuellen Punktmenge und fügt anschließend eine Menge an Punkten,
	 * die aus einer Datei gelesen werden, der nun leeren Punktmenge hinzu.
	 * 
	 * @param path			Der Pfad zu der Datei, aus welcher die Punkte gelesen werden sollen.
	 * @throws IOException	Wirft eine Ausnahme zurück, falls die Datei nicht vorhanden ist oder nicht gelesen werden kann.
	 */
	public void addPointsFromFile(String path) throws IOException {
		clearAllPoints();
		fileManager.readPointsFromFile(path);
		update();
	}
	
	/**
	 * Speichert die aktuelle Punktmenge in eine Datei.
	 * @param path			Der Pfad zu der Datei, in welche die Punkte geschrieben werden sollen.
	 * @throws IOException	Wirft eine Ausnahme zurück, falls die Datei nicht erstellt oder beschrieben werden kann.
	 */
	public void savePointsToFile(String path) throws IOException {
		fileManager.savePointsToFile(path);
	}

	/**
	 * Fügt einen Listener der Liste aller Listener hinzu.
	 * @param listener Der Listener, der hinzugefügt werden soll.
	 */
	public void addValueChangeListener(ValueChangeListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Führt eine neuberechnung der konvexen Hülle, sowie dessen Durchmesser und größtes enthaltenes Viereck und Dreieck durch.
	 * Benachrichtigt anschließend alle Listener, die sich angemeldet haben.
	 */
	private void update() {
		calc.update(allPoints);
		notifyValueChangeListeners();
	}
	
	/**
	 * Benachrichtigt alle Listener über Änderungen.
	 */
	private void notifyValueChangeListeners() {
		for(ValueChangeListener listener : listeners) {
			listener.onValueChanged();
		}
	}
		
}
