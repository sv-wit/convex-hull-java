package svwit.changes;

import svwit.calculator.CalculatorManager;
import svwit.util.Point;

/**
 * Diese Klasse repräsentiert eine Änderung, bei welcher ein Punkt aus einer Punktmenge entfernt wird.
 * 
 */
public class RemovePointChange implements IChange{

	/** Die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der die Änderung ausgeführt werden soll */
	private CalculatorManager calculatorManager;
	/** Der Punkt, der aus der Punktmenge entfernt werden soll */
	private Point point;
	
	/**
	 * Der Konstruktor.
	 * <p>
	 * Hier wird die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der die Änderung ausgeführt werden soll, übergeben.
	 * Zusätzlich wird der Punkt übergeben, der aus der Punktmenge entfernt werden soll.
	 * 
	 * @param calculatorManager Instanz des {@code CalculatorManager}, der die Punktmenge hällt
	 * @param point Der Punkt, der aus der Punktmenge entfernt werden soll 
	 */
	public RemovePointChange(CalculatorManager calculatorManager, Point point) {
		this.calculatorManager = calculatorManager;
		this.point = point;
	}
	
	@Override
	public boolean execute() {
		if(calculatorManager.removePoint(point)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if(calculatorManager.addPoint(point)) {
			return true;
		}
		return false;
	}

}