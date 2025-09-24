package svwit.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import svwit.calculator.CalculatorManager;
import svwit.changes.ChangeManager;

/**
 * Diese Klasse erzeugt das Hauptfenster der GUI und verwaltet die enthaltenen Elemente.
 * <p>
 * Zu den Enthaltenen Elementen der GUI gehören die Menüleiste, die Seitenleiste und die Zeichenfläche.
 * 
 */
public class GUIManager {
	
	/** Die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der Änderungen durch die GUI vorgenommen werden sollen */
	private CalculatorManager calculatorManager;
	/** Die Instanz des {@code ChangeManager}, der Änderungen auf der Punktmenge durch die GUI ausführt und wirksam macht */
	private ChangeManager changeManager;
	
	/** Die Instanz des {@code DrawingPanel}, welches als Zeichenfläche fungiert */
	private DrawingPanel drawingPanel;
	/** Die Instanz des {@code SidePanel}, welches als Seitenleiste fungiert */
	private SidePanel sidePanel;
	
	/**
	 * Der Konstruktor.
	 * <p>
	 * Hier wird die Instanz des {@code CalculatorManager}, der die Punktmenge hällt, auf der die Änderungen ausgeführt werden soll, übergeben.
	 * Zusätzlich wird eine neue Instanz eines {@code ChangeManager} erzeugt und in der zugehörigen Klassenvariable gespeichert.
	 * 
	 * @param calculatorManager Instanz des {@code CalculatorManager}, der die Punktmenge hällt
	 */
	public GUIManager(CalculatorManager calculatorManager) {
		this.calculatorManager = calculatorManager;
		changeManager = new ChangeManager();
	}

	/**
	 * Gibt die Instanz des verwendeten {@code CalculatorManager} zurück.
	 * 
	 * @return Instanz des verwendeten {@code CalculatorManager}
	 */
	public CalculatorManager getCalculatorManager() {
		return calculatorManager;
	}
	
	/**
	 * Gibt die Instanz des verwendeten {@code ChangeManager} zurück.
	 * 
	 * @return Instanz des verwendeten {@code ChangeManager}
	 */
	public ChangeManager getChangeManager() {
		return changeManager;
	}

	/**
	 * Gibt die Instanz des verwendeten {@code DrawingPanel} zurück.
	 * 
	 * @return Instanz des verwendeten {@code DrawingPanel}
	 */
	public DrawingPanel getDrawingPanel() {
		return drawingPanel;
	}
	
	/**
	 * Gibt die Instanz des verwendeten {@code SidePanel} zurück.
	 * 
	 * @return Instanz des verwendeten {@code SidePanel}
	 */
	public SidePanel getSidePanel() {
		return sidePanel;
	}

	/**
	 * Erzeugt die GUI.
	 * <p>
	 * Zunächst wird ein Hauptfenster generiert, welches initial eine Weite von 60% der Bildschirmweite und
	 * eine Höhe von 60% der Bildschirmhöhe besitzt.
	 * Anschließend werden die Menüleiste, die Seitenleiste und die Zeichenfläche erzeugt.
	 * Zuletzt werden diese Elemente dem Hauptfenster hinzugefügt und das Hauptfenster sichtbar gemacht.
	 */
	public void createGUI() {
		// Ermitteln der Größe des Bildschirms
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		// Erstellen des Hauptfensters
		JFrame mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize((int) (dimension.getWidth() * 0.6), (int) (dimension.getHeight() * 0.6));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setTitle("convex-hull-java sv-wit");
		mainFrame.setLayout(new BorderLayout());
		ImageIcon icon = new ImageIcon("./data/images/calculatorSmall.png");
		mainFrame.setIconImage(icon.getImage());
		
		// Erstellen der Menüleiste
		MenuBar menuBar = new MenuBar(this);
		menuBar.setBorderPainted(false);
		
		// Erstellen der Seitenleiste
		sidePanel = new SidePanel(this);
		JScrollPane scrollPane = new JScrollPane(sidePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension((int)(scrollPane.getPreferredSize().getWidth() + scrollPane.getVerticalScrollBar().getPreferredSize().getWidth()), (int)scrollPane.getPreferredSize().getHeight()));
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBar(new RoundedScrollBar());
		scrollPane.setBackground(ColorPallet.COLOR_BACKGROUND_1);
		
		// Erstellen des Zeichenfläche
		drawingPanel = new DrawingPanel(this);

		// Hinzufügen der Komponente zum Hauptfenster
		mainFrame.setJMenuBar(menuBar);
		mainFrame.add(scrollPane, BorderLayout.WEST);
		mainFrame.add(drawingPanel, BorderLayout.CENTER);
		
		// Hauptfenster sichtbar machen
		mainFrame.setVisible(true);
	}
	
}
