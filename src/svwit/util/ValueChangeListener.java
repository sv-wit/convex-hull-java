package svwit.util;


/**
 * Dieses Interface dient dazu, einer Klasse die Möglichkeit zu bieten, sich an einer Instanz eines {@code CalculatorManager} anzumelden
 * und über Änderungen an der Punktmenge oder neuer Berechnungen informiert zu werden. 
 * 
 */
public interface ValueChangeListener {

	/**
	 * Wird ausgefuehrt, sobald ein Wert verändert wurde.
	 */
	public void onValueChanged();
	
}
