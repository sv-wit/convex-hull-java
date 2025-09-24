package svwit.changes;

import svwit.calculator.CalculatorManager;
import svwit.util.Point;

/**
 * Diese Klasse repräsentiert eine Änderung, bei welcher ein Punkt einer Punktmenge hinzugefügt wird.
 * 
 */
public class AddPointChange implements IChange{

	/** Die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der die Änderung ausgeführt werden soll */
	private CalculatorManager calculatorManager;
	/** Der Punkt, der der Punktmenge hinzugefügt werden soll */
	private Point point;
	
	/**
	 * Der Konstruktor.
	 * <p>
	 * Hier wird die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der die Änderung ausgeführt werden soll, übergeben.
	 * Zusätzlich werden die X- und Y-Koordinaten des Punktes übergeben, der der Punktmenge hinzugefügt werden soll.
	 * 
	 * @param calculatorManager Instanz des {@code CalculatorManager}, der die Punktmenge hällt
	 * @param x X-Koordinate des Punkts
	 * @param y Y-Koordinate des Punkts
	 */
	public AddPointChange(CalculatorManager calculatorManager, int x, int y) {
		this.calculatorManager = calculatorManager;
		this.point = new Point(x, y);
	}
	
	/**
	 * Der Konstruktor.
	 * <p>
	 * Hier wird die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der die ausgeführt werden soll, übergeben.
	 * Zusätzlich wird der Punkt übergeben, der der Punktmenge hinzugefügt werden soll.
	 * 
	 * @param calculatorManager Instanz des {@code CalculatorManager}, der die Punktmenge hällt
	 * @param point Der Punkt, der der Punktmenge hinzugefügt werden soll 
	 */
	public AddPointChange(CalculatorManager calculatorManager, Point point) {
		this.calculatorManager = calculatorManager;
		this.point = point;
	}
	
	@Override
	public boolean execute() {
		if(calculatorManager.addPoint(point)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if(calculatorManager.removePoint(point)) {
			return true;
		}
		return false;
	}

}
