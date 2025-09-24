package svwit.main;

import svwit.GUI.GUIManager;
import svwit.calculator.CalculatorManager;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * Diese Klasse dient als Einstiegspunkt in das Programm.
 * <p>
 * Hier ist die Main-Methode definiert, welche je nach übergebenen Parametern das Programm
 * mit oder ohne einer GUI startet.
 * 
 */
public class ConvexHullMain {

	/**
	 * Startet das Programm. 
	 * <p>
	 * Wenn der Parameter "-t" übergeben wird, wird das Programm ohne eine grafische Benutzeroberfläche gestartet.
	 * In diesem Fall wird der Tester aus der Aufgabenstellung ausgeführt und eine anzahl an Tests durchgeführt.
	 * Es kann spezifiziert werden, welcher Test ausgeführt werden soll, indem der Name des Tests nach dem Testparameter angefügt wird.
	 * Zum Beispiel: "-t random10.test".
	 * <p>
	 * Wenn kein Parameter angegeben wird, wird zunächst ein Plattformübergreifendes Look and Feel geladen und anschließend das
	 * Programm mit einer grafischen Benutzeroberfläche gestartet.
	 * 
	 * @param args zu übergebende Parameter
	 */
	public static void main(String[] args) {
		if (args.length > 0 && "-t".equals(args[0])) {
			// starte den Tester und danach die Ausgabe der Ergebnisse
			
			// IHullCalculator calculator = new CalculatorManagerAdapter();
			// Tester tester = new Tester(args, calculator);
			// System.out.println(tester.test());
			// System.exit(0); //fertig
		} else {
			// Andernfalls starte den normalen Programmablauf mit GUI		
			
			// Plattformübergreifendes Look & Feel verwenden
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
				
			CalculatorManager calculatorManager = new CalculatorManager();

			GUIManager guiManager = new GUIManager(calculatorManager);
			guiManager.createGUI();
			
		}
	}
}
