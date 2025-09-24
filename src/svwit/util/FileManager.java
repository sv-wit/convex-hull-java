package svwit.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import svwit.calculator.CalculatorManager;

/**
 * Diese Klasse bietet die nötigen Funktionen, um Punktmengen in eine Datei zu speichern beziehungsweise zu lesen.
 * 
 */
public class FileManager {
	
	/** Der {@code CalculatorManager}, der die Punktmenge verwaltet und die Berechnungen auf dieser startet. */
	private CalculatorManager calculatorManager;
	
	/**
	 * Der Konstruktor.
	 * <p>
	 * Hier wird die übergeben Instanz des {@code CalculatorManager} in die zugehörigen Klassenvariable gespeichert.
	 * 
	 * @param calculatorManager Instanz des {@code CalculatorManager}, der die Punktmenge hällt
	 */
	public FileManager(CalculatorManager calculatorManager) {
		this.calculatorManager = calculatorManager;
	}
	
	/**
	 * Liest eine Punktmenge aus der Datei, die sich an dem übergebenen Pfad befindet.
	 * <p>
	 * Die Punktmenge wird in die Punktmenge gespeichert, die von der Instanz des {@code CalculatorManager} verwaltet wird.
	 * 
	 * @param path Pfad, an dem sich die Datei befindet
	 * @throws IOException Wirft eine Exception, falls die Datei nicht existiert oder gelesen werden kann.
	 */
	public void readPointsFromFile(String path) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		
		int x = 0;
		int y = 0;	
		boolean foundX;
		boolean foundY;
		
		String line;
		String[] words;
		while((line = reader.readLine()) != null) {
			
			foundX = false;
			foundY = false;
			
			words = line.split(" ");
			for(String word : words) {
				try {
					if(word != "") {
						if(!foundX) {
							x = Integer.parseInt(word);
							foundX = true;
						} else if(!foundY) {
							y = Integer.parseInt(word);
							foundY = true;
							break;
						}
					}
				} catch(NumberFormatException e) {
					break;
				}
			}
			if(foundX && foundY) {
				calculatorManager.getAllPoints().add(new Point(x, y));
			}
		}
		reader.close();
	}
	
	/**
	 * Schreibt eine Punktmenge in die Datei, die sich an dem übergebenen Pfad befindet.
	 * <p>
	 * Wenn sich an dem übergebenen Pfad keine Datei befindet, wird eine neue Datei erzeugt.
	 * Die zu speichernden Punkte weder der Punktmenge entnommen, welche von dem {@code CalculatorManager} verwaltet wird.
	 * 
	 * @param path Pfad, an dem sich die Datei befindet
	 * @throws IOException Wirft eine Exception, falls die Datei nicht existiert oder beschrieben werden kann.
	 */
	public void savePointsToFile(String path) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		for(Point dp : calculatorManager.getAllPoints()) {
			writer.write(dp.getX() + " " + dp.getY() + "\n");
		}
		writer.flush();
		writer.close();
	}	
}
