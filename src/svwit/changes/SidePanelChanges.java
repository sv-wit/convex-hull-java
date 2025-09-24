package svwit.changes;

import java.util.ArrayList;
import java.util.Random;

import svwit.calculator.CalculatorManager;
import svwit.util.Point;

/**
 * Diese Klasse dient als Steuereinheit für Änderungen, die von einer Instanz eines {@code SidePanel} aufgerufen werden können.
 * <p>
 * Zu diesen Änderungen gehören das Hinzufügen von zufällig generierten Punkten.
 * 
 */
public class SidePanelChanges {
	
	/** Die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der die Änderungen ausgeführt werden sollen */
	private CalculatorManager calculatorManager;
	/** Die Instanz des {@code ChangeManager}, der die Änderungen ausführt */
	private ChangeManager changeManager;
	
	/**
	 * Der Konstruktor.
	 * <p>
	 * Hier wird die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der die Änderungen ausgeführt werden soll, übergeben.
	 * Zusätzlich wird die Instanz des {@code ChangeManager} übergeben, der die Änderungen letztendlich ausführt.
	 * 
	 * @param calculatorManager Instanz des {@code CalculatorManager}, der die Punktmenge hällt
	 * @param changeManager Instanz des {@code ChangeManager}, der die Änderungen ausführt und wirksam macht
	 */
	public SidePanelChanges(CalculatorManager calculatorManager, ChangeManager changeManager) {
		this.calculatorManager = calculatorManager;
		this.changeManager = changeManager;
	}
	
	/**
	 * Erstellt eine neue Änderung, die das hinzufügen von zufälligen Punkten auf einer Punktmenge veranlasst.
	 * Die Anzahl der zufälligen Punkte wird dieser Methode als Parameter übergeben.
	 * Zusätzlich werden die oberen und unteren Grenzen der X- und Y-Koordinaten übergeben,
	 * welche die zufällig generierten Punkte nicht über- beziehungsweise unterschreiten.
	 * 
	 * @param amount Anzahl an zufällig generierten Punkten
	 * @param minX Minimale X-Koodrinate der Punkte
	 * @param minY Minimale Y-Koodrinate der Punkte
	 * @param maxX Maximale X-Koodrinate der Punkte
	 * @param maxY Maximale Y-Koodrinate der Punkte
	 */
	public void addRandomPoints(int amount, int minX, int minY, int maxX, int maxY) {
		Random r = new Random();
		ArrayList<Point> randomPoints = new ArrayList<>();
		for(int i = 0; i < amount; i++) {
			Point newPoint = new Point(r.nextInt(minX, maxX), r.nextInt(minY, maxY));
			randomPoints.add(newPoint);
		}
		AddRandomPointsChange arpc = new AddRandomPointsChange(calculatorManager, randomPoints);
		changeManager.execute(arpc, ChangeManager.SAVE_CHANGE);
	}
	
}
