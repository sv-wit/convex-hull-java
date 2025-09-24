package svwit.changes;

import svwit.calculator.CalculatorManager;
import svwit.util.Point;

/**
 * Diese Klasse repräsentiert eine Änderung, bei welcher ein Punkt verschoben wird.
 * 
 */
public class MovePointChange implements IChange{
	
	/** Die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der die Änderung ausgeführt werden soll */
	private CalculatorManager calculatorManager;
	/** Die Startposition des zu verschiebenden Punktes */
	private Point origin;
	/** Die Zielposition des zu verschiebenden Punktes */
	private Point destination;
	/** Gibt an, ob die Änderung erfolgreich durchgeführt wurde */
	private boolean executed = false;
	
	/**
	 * Der Konstruktor
	 * <p>
	 * Hier wird die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der die Änderung ausgeführt werden soll, übergeben.
	 * Zusätzlich wird die Startposition und die Zielposition des zu verschiebenden Punktes übergeben.
	 * 
	 * @param calculatorManager Instanz des {@code CalculatorManager}, der die Punktmenge hällt
	 * @param origin Startposition des zu verschiebenden Punktes
	 * @param destination Zielposition des zu verschiebenden Punktes
	 */
	public MovePointChange(CalculatorManager calculatorManager, Point origin, Point destination) {
		this.calculatorManager = calculatorManager;
		this.origin = origin;
		this.destination = destination;
	}
	
	/**
	 * Gibt zurück, ob die Änderung erfolgreich ausgeführt wurde.
	 * @return {@code true}, wenn die Änderung erfolgreich durchgeführt wurde, {@code false} ansonsten.
	 */
	public boolean isExecuted() {
		return executed;
	}
	

	@Override
	public boolean execute() {
		if(calculatorManager.addPoint(destination)) {
			calculatorManager.removePoint(origin);
			executed = true;
		}
		return true;
	}

	@Override
	public boolean unExecute() {
		calculatorManager.removePoint(destination);
		calculatorManager.addPoint(origin);
		return true;
	}

}
