package svwit.util;

import java.util.TreeSet;

/**
 * Diese Klasse repräsentiert eine Punktmenge.
 * <p>
 * Die Klasse erweitert ein {@code TreeSet}, wodurch beliebig Punkt hinzugefügt und entfernt werden könne.
 * Das Hinzufügen und Entfernen eines Punktes geschieht in O(n) Zeit. Die Punkte werden in sortierter Reihenfolge gespeichert,
 * wodurch eine externe sortierung nicht notwendig ist.
 * <p>
 * Diese Klasse besitzt aktuell keine eigenen Methoden. Da sich dies aber ändern kann, wurde sich dazu entschieden,
 * diese Klasse als eigenständiges Objekt umzusetzen.
 * 
 */
public class PointsCollection extends TreeSet<Point>{

	/** Serialisierungs-ID */
	private static final long serialVersionUID = 7553144033791692651L;
	
}
