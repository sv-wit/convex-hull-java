package svwit.changes;

/**
 * Dieses Interface beinhaltet die Methoden, die eine Änderung auf einer Punktmenge veranlassen.
 * <p>
 * Eine Änderung kann beispielsweise das Hinzufügen oder Entfernen eines Punktes zu einer Punktmenge sein.
 * Die konkereten Änderungen müssen von den Klassen spezifiziert werden, die dieses Interface implementieren.
 * 
 */
public interface IChange {	
	
	/**
	 * Wird ausgeführt, sobald die Änderung wirksam gemacht werden soll.
	 * 
	 * @return {@code true}, wenn die Änderung erfolgreich war, {@code false} ansonsten
	 */
	public boolean execute();
	
	/**
	 * Wird ausgeführt, sobald die bereits wirksam gemacht Änderung rückgängig gemacht werden soll.
	 * 
	 * @return {@code true}, wenn die Änderung erfolgreich war, {@code false} ansonsten
	 */
	public boolean unExecute();
	
}
