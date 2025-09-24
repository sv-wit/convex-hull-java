package svwit.changes;

import java.util.ArrayList;

import svwit.calculator.CalculatorManager;
import svwit.util.Point;

/**
 * Diese Klasse repräsentiert eine Änderung, bei welcher ein Menge von zufälligen Punkten einer Punktmenge hinzugefügt werden.
 * 
 */
public class AddRandomPointsChange implements IChange{

	/** Die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der die Änderung ausgeführt werden soll */
	private CalculatorManager calculatorManager;
	/** Die Menge an zufälligen Punkten, die der Punktmenge hinzugefügt werden sollen */
	private ArrayList<Point> randomPoints;
	
	/**
	 * Der Konstruktor.
	 * <p>
	 * Hier wird die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der die Änderung ausgeführt werden soll, übergeben.
	 * Zusätzlich wird Menge der zufälligen Punkte übergeben, die der Punktmenge hinzugefügt werden sollen.
	 * 
	 * @param calculatorManager Instanz des {@code CalculatorManager}, der die Punktmenge hällt
	 * @param randomPoints Die Menge der zufälligen Punkte, die der Punktmenge hinzugefügt werden sollen
	 */
	public AddRandomPointsChange(CalculatorManager calculatorManager, ArrayList<Point> randomPoints) {
		this.calculatorManager = calculatorManager;
		this.randomPoints = randomPoints;
	}
	
	@Override
	public boolean execute() {
		ArrayList<Point> newRandomPoints = new ArrayList<>();
		for(Point p : randomPoints) {
			if(calculatorManager.addPoint(p)) {
				newRandomPoints.add(p);
			}
		}
		randomPoints = newRandomPoints;
		return true;
	}

	@Override
	public boolean unExecute() {
		calculatorManager.removePointsFromArrayList(randomPoints);
		return true;
	}

}
