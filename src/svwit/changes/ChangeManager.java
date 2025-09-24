package svwit.changes;

import java.util.Stack;

/**
 * Diese Klasse verwaltet die Änderunge, die auf einer Punktmenge ausgeführt werden.
 * <p>
 * Die Änderungen werden jeweils auf einem Stapel gespeichert, sodass die Möglichkeit besteht,
 * die Änderungen zurückzunehmen oder wiederherzustellen (Undo und Redo).
 * <p>
 * Bei der Ausführung einer Änderung kann angegeben werden, ob diese Änderung auf dem Stapel gespeichert werden soll. 
 * So ist es Möglich, dass bestimmte Änderungen nicht zurücknehmbar gemacht werden. Dies ist beispielsweise bei
 * dem erzeugen einer neuen Zeichenfläche gewünscht.
 * 
 */
public class ChangeManager {
	
	/** Gibt an, dass eine Änderung gespeichert werden soll */
	public static final int SAVE_CHANGE = 1;
	/** Gibt an, dass eine Änderung nicht gespeichert werden soll */
	public static final int DONT_SAVE_CHANGE = 2;

	/** Der Stapel, auf dem die Änderungen zum rückgängig machen gespeichert werden */
	private Stack<IChange> undoStack = new Stack<>();
	/** Der Stapel, auf dem die Änderungen zum wiederherstellen gespeichert werden */
	private Stack<IChange> redoStack = new Stack<>();
	
	/**
	 * Führt die übergebene Änderung aus und speichert diese auf dem Stapel der Änderungen, die rückgängig gemacht werden können, 
	 * wenn die Änderung erfolgreich war.
	 * Leert gleichzeitig den Stapel, der Änderung, die wiederhergestellt werden können.
	 * Zusätzlich kann angegeben werden, ob die übergebene Änderung überhaupt gespeichert werden soll.
	 * 
	 * @param change Die Änderung, die ausgeführt werden soll.
	 * @param key Die Angabe, ob die Änderung gespeichert werden soll oder nicht.
	 */
	public void execute(IChange change, int key) {
		if(change.execute()) {
			if(key == SAVE_CHANGE) {
				undoStack.push(change);
				redoStack.clear();
			}
		}
	}
	
	/**
	 * Macht die Änderung rückgängig, die sich oben auf dem Stapel der Änderungen, die rückgängig gemacht werden können, abgelegt ist.
	 * Anschließend wird diese Änderung auf den Stapel der Änderungen, die wiederhergestellt werden können, abgelegt, 
	 * wenn das Rückgängigmachen erfolgreich war.
	 */
	public void unExecute() {
		if(undoStack.size() > 0) {
			IChange change = undoStack.pop();
			if(change.unExecute()) {
				redoStack.push(change);
			}
		}
	}
	
	/**
	 * Stellt die Änderung wieder her, die sich oben auf dem Stapel der Änderungen, die wiederherstellbar sind, abgelegt ist.
	 * Anschließend wird diese Änderung auf den Stapel der Änderungen, die rückgängig gemacht werden können,
	 *  abgelegt, wenn die Änderung erfolgreich war.
	 */
	public void reExecute() {
		if(redoStack.size() > 0) {
			IChange change = redoStack.pop();
			if(change.execute()) {
				undoStack.push(change);
			}
		}
	}
	
	/**
	 * Leert beide Stapel, sodass keine Änderungen mehr gespeichert sind.
	 */
	public void clearUndoStack() {
		undoStack.clear();
		redoStack.clear();
	}
	
	/**
	 * Gibt wieder, ob der Stapel der Änderungen, die rückgängig gemacht werden können, leer ist oder nicht.
	 * @return {@code true}, wenn der Stapel leet ist, {@code false} ansonsten
	 */
	public boolean isUndoEmpty() {
		return undoStack.size() == 0;
	}
	
	/**
	 * Gibt wieder, ob der Stapel der Änderungen, die wiederherstellbar sind, leer ist oder nicht.
	 * @return {@code true}, wenn der Stapel leet ist, {@code false} ansonsten
	 */
	public boolean isRedoEmpty() {
		return redoStack.size() == 0;
	}
	
}
