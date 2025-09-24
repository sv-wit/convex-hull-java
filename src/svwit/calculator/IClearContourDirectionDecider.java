package svwit.calculator;

import svwit.util.Point;

/**
 * Dieses funktionale Interface dient der Beschreibung eines Entscheiders für die Bereinigung einer Kontur.
 * <p>
 * Die hier angegebene Methode wird in der Klasse {@code Calculator} verwendet, um zu entscheiden,
 * ob bei der Bereinigung einer Kontur ein Punkt gültig ist oder nicht.
 * 
 * @see Calculator
 */
public interface IClearContourDirectionDecider {
	
	/**
	 * Diese Methode dient der Entscheidung, ob bei der Bereinigung einer Kontur ein Punkt als gültig oder ungültig erklärt wird.
	 * <p>
	 * Hierfür wird geschaut, ob der Dritte Punkt links oder rechts von der Geraden durch die ersten beiden Punkte liegt.
	 * 
	 * @param a Der erste Punkt
	 * @param b Der zweite Punkt
	 * @param c Der dritte Punkt
	 * @return {@code true}, wenn die angegebene Bedingung zutrifft, {@code false} ansonsten
	 */
	boolean decide(Point a, Point b, Point c);
}
