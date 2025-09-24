package svwit.changes;

import svwit.calculator.CalculatorManager;
import svwit.util.Point;

/**
 * Diese Klasse dient als Steuereinheit für Änderungen, die von einer Instanz eines {@code DrawingPanel} aufgerufen werden können.
 * <p>
 * Zu diesen Änderungen gehören das Hinzufügen und Entfernen eines Punktes, sowie das Verschieben eines Punkets.
 * 
 */
public class DrawingPanelChanges {

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
	public DrawingPanelChanges(CalculatorManager calculatorManager, ChangeManager changeManager) {
		this.calculatorManager = calculatorManager;
		this.changeManager = changeManager;
	}
	
	/**
	 * Erstellt eine neue Änderung, die das hinzufügen eines Punktes auf eine Punktmenge veranlasst.
	 * Der neue Punkt wird bei erfolgreicher Durchführung der Änderung an den übergebenen X- und Y-Koodrinaten erzeugt.
	 * 
	 * @param x X-Koodrinate des Punktes
	 * @param y Y-Koordinate des Punktes
	 */
	public void addPoint(int x, int y) {
		AddPointChange apc = new AddPointChange(calculatorManager, new Point(x, y));
		changeManager.execute(apc, ChangeManager.SAVE_CHANGE);
	}
	
	/**
	 * Erstellt eine neue Änderung, die das entfernen eines Punktes aus einer Punktmenge veranlasst.
	 * Hierbei wird der übergebene Punkt aus der Punktmenge entfernt, wenn dieser in der Punktmenge enthalten ist.
	 * 
	 * @param p Der zu entfernende Punkt
	 */
	public void removePoint(Point p) {
		RemovePointChange rpc = new RemovePointChange(calculatorManager, p);
		changeManager.execute(rpc, ChangeManager.SAVE_CHANGE);
	}
	
	/**
	 * Erstellt eine neue Änderung, die das Verschieben eines Punktes veranlasst.
	 * Der Punkt wird von der Übergebenen Startposition an die übergebene Zielposition verschoben.
	 * Zusätzlich wird festgelegt, ob die Änderung gespeichert werden soll.
	 * 
	 * @param origin Startposition des zu verschiebenden Punktes
	 * @param destination Zielposition des zu verschiebenden Punktes
	 * @param saveKey Gibt an, ob die Änderung gespeichert werden soll
	 * @return {@code true}, wenn die Änderung erfolgreich ausgeführt wurde, {@code false} ansonsten
	 */
	public boolean movePoint(Point origin, Point destination, int saveKey) {
		MovePointChange mpc = new MovePointChange(calculatorManager, origin, destination);
		changeManager.execute(mpc, saveKey);
		return mpc.isExecuted();
	}
	
}
