package svwit.GUI;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Diese Klasse repräsentiert die Menüleiste mit allen enthaltenen Menüpunkten.
 * <p>
 * Die Klasse erweitert die Klasse {@code JMenuBar} und enthält eine Methode {@code init},
 * welche alle benötigten Menüpunkte und deren Funktionalität erzeugt.
 * 
 */
public class MenuBar extends JMenuBar{

	/** Serialisierungs-ID */
	private static final long serialVersionUID = 5254553566948712429L;
	
	/** Die Instanz des {@code GUIManager}, der für den Aufbau der GUI zuständig ist */
	private GUIManager guiManager;
	
	/** Der Pfad der zuletzt geöffneten oder gespeicherten Datei */
	private String prevPath = null;
	
	/** Der {@code JFileChooser}, welcher den Dateiauswahldialog zum speichern und laden einer Datei repräsentiert */
	private JFileChooser fileChooser = new JFileChooser("../ProPra-SS22-Basis/data");
	
	/** Der Menüpunkt zum speichern einer Datei */
	private JMenuItem menuItemSave;
	/** Der Menüpunkt für die Option zum Verschieben von Punkten mit gedrückter Umschalt-Taste */
	private JCheckBoxMenuItem menuItemShitftMove;
	/** Der Menüpunkt zum anzeigen der Mauskoordinaten und dem Zoom */
	private JCheckBoxMenuItem menuItemShowMouseAndZoom;
	/** Der Menüpunkt zum anzeigen der Koordinaten vom nächstgelegensten Punkt */
	private JCheckBoxMenuItem menuItemShowClosestPointCoordinates;
	
	/**
	 * Der Konstruktor.
	 * <p>
	 * Der übergebene {@code GUIManager} wird in der zugehörigen Klassenvariable gespeichert.
	 * Zusätzlich werden die Hintergrund- und Vordergrundfarbe der Menüleiste festgelegt.
	 * Zuletzt wird die Methode {@code init} aufgerufen, die die benötigten Menüs und Menüpunkte erzeugt.
	 * 
	 * @param guiManager Instanz des {@code GUIManager}, der für den Aufbau der GUI zuständig ist
	 */
	public MenuBar(GUIManager guiManager) {
		this.guiManager = guiManager;
		setBackground(ColorPallet.COLOR_MENU_BACKGROUND);
		setForeground(ColorPallet.COLOR_MENU_FOREGROUND);
		
		init();
	}
	
	/**
	 * Erzeugt die Menüs und fügt die zugehörigen Menüpunkte hinzu.
	 * <p>
	 * Das Ergebnis dieser Methode sollte eine Menüleiste mit folgender Struktur sein:
	 * <br>
	 * <ul>
	 * <li>Datei
	 * <ul>
	 * <li>Neu</li>
	 * <li>Öffnen</li>
	 * <li>Speichern</li>
	 * <li>Speichern Unter..</li>
	 * <li>Beenden</li>
	 * </ul>
	 * </li>
	 * <li>Ansicht
	 * <ul>
	 * <li>Ansicht zentrieren</li>
	 * <li>Mauskoordinaten und Zoomfaktor anzeigen</li>
	 * <li>Nur Koordinaten des nächstgelegensten Punkts anzeigen</li>
	 * </ul>
	 * </li>
	 * <li>Optionen
	 * <ul>
	 * <li>Umschalt-Taste zum Verschieben von Punkten verwendent</li>
	 * </ul>
	 * </li>
	 * <li>Hilfe
	 * <ul>
	 * <li>Bedienungsanleitung</li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private void init() {
		
		UIManager.getLookAndFeelDefaults().put("MenuItem.acceleratorForeground", Color.DARK_GRAY);
		fileChooser.setFileFilter(new FileNameExtensionFilter("Points Datei", "points"));
		
		// -------------------------------------------
		// "Datei" Menu
		// -------------------------------------------
		
		JMenu menuData = GUIElementsFactory.createMenu("Datei");

		// menuItem "Datei -> Neu"
		JMenuItem menuItemNew = GUIElementsFactory.createMenuItem("Neu", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.getCalculatorManager().clearAllPoints();
				guiManager.getDrawingPanel().resetView();
				guiManager.getChangeManager().clearUndoStack();
				guiManager.getSidePanel().updateUndoReduButtons();
				prevPath = null;
				menuItemSave.setEnabled(false);
			}
		});
		KeyStroke newKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
		menuItemNew.setAccelerator(newKeyStroke);

		// menuItem "Datei -> Öffnen"
		JMenuItem menuItemOpen = GUIElementsFactory.createMenuItem("Öffnen", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						guiManager.getCalculatorManager().addPointsFromFile(fileChooser.getSelectedFile().getAbsolutePath());
						prevPath = fileChooser.getSelectedFile().getAbsolutePath();
						guiManager.getDrawingPanel().resetView();
						guiManager.getDrawingPanel().centerView();
						menuItemSave.setEnabled(true);
					} catch (IOException e1) {
						System.out.println("Error: Datei konnte nicht geöffnet werden!");
					}
				}
			}
		});
		KeyStroke openKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
		menuItemOpen.setAccelerator(openKeyStroke);

		// menuItem "Datei -> Speichern"
		menuItemSave = GUIElementsFactory.createMenuItem("Speichern", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					guiManager.getCalculatorManager().savePointsToFile(prevPath);
				} catch (IOException e1) {
					System.out.println("Error: Datei konnte nicht gespeichert werden!");
				}
			}
		});
		menuItemSave.setEnabled(false);
		KeyStroke saveKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
		menuItemSave.setAccelerator(saveKeyStroke);

		// menuItem "Datei -> Speichern unter.."
		JMenuItem menuItemSaveAs = GUIElementsFactory.createMenuItem("Speichern unter..", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnValue = fileChooser.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						if(!fileChooser.getSelectedFile().getAbsolutePath().endsWith(".points")) {
							guiManager.getCalculatorManager().savePointsToFile(fileChooser.getSelectedFile().getAbsolutePath() + ".points");
							prevPath = fileChooser.getSelectedFile().getAbsolutePath() + ".points";
						} else {
							guiManager.getCalculatorManager().savePointsToFile(fileChooser.getSelectedFile().getAbsolutePath());
							prevPath = fileChooser.getSelectedFile().getAbsolutePath();
						}
						menuItemSave.setEnabled(true);
					} catch (IOException e1) {
						System.out.println("Error: Datei konnte nicht gespeichert werden!");
					}
				}
			}
		});
		KeyStroke saveAsKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() + InputEvent.SHIFT_DOWN_MASK);
		menuItemSaveAs.setAccelerator(saveAsKeyStroke);

		// menuItem "Datei -> Beenden"
		JMenuItem menuItemClose = GUIElementsFactory.createMenuItem("Beenden", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		// Items zu "Datei"-Menu hinzufügen
		menuData.add(menuItemNew);
		menuData.add(menuItemOpen);
		menuData.addSeparator();
		menuData.add(menuItemSave);
		menuData.add(menuItemSaveAs);
		menuData.addSeparator();
		menuData.add(menuItemClose);

		// -------------------------------------------
		// "Ansicht" Menu
		// -------------------------------------------
		
		JMenu menuView = GUIElementsFactory.createMenu("Ansicht");
		
		// menuItem "Ansicht -> Ansicht zentrieren"
		JMenuItem menuViewCenter = GUIElementsFactory.createMenuItem("Ansicht zentrieren", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.getDrawingPanel().centerView();
				guiManager.getDrawingPanel().repaint();
			}
		});
		KeyStroke centerViewKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
		menuViewCenter.setAccelerator(centerViewKeyStroke);
		
		// menuItem "Ansicht -> Mauskoordinaten und Zoom anzeigen"
		menuItemShowMouseAndZoom = GUIElementsFactory.createMenuCheckBox("Mauskoordinaten und Zoomfaktor anzeigen", false, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(menuItemShowMouseAndZoom.isSelected()) {
					guiManager.getDrawingPanel().setShowMouseCoordinatesAndZoom(true);
				} else {
					guiManager.getDrawingPanel().setShowMouseCoordinatesAndZoom(false);
				}
			}
		});
		
		menuItemShowClosestPointCoordinates = GUIElementsFactory.createMenuCheckBox("Nur Koordinaten des nächstgelegensten Punkts anzeigen", false, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(menuItemShowClosestPointCoordinates.isSelected()) {
					guiManager.getDrawingPanel().setShowClosestPointCoordinatesEnabled(true);
				} else {
					guiManager.getDrawingPanel().setShowClosestPointCoordinatesEnabled(false);
				}
			}
		});
		
		// Items zu "Ansicht"-Menu hinzufügen
		menuView.add(menuViewCenter);
		menuView.addSeparator();
		menuView.add(menuItemShowMouseAndZoom);
		menuView.add(menuItemShowClosestPointCoordinates);
		
		// -------------------------------------------
		// "Optionen" Menu
		// -------------------------------------------
		
		JMenu menuOptions = GUIElementsFactory.createMenu("Optionen");
		
		// menuItem "Optionen -> Shift zum Verschieben"
		menuItemShitftMove = GUIElementsFactory.createMenuCheckBox("Umschalt-Taste zum Verschieben von Punkten verwenden", true, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(menuItemShitftMove.isSelected()) {
					guiManager.getDrawingPanel().setUseShiftToMovePointEnabled(true);
				} else {
					guiManager.getDrawingPanel().setUseShiftToMovePointEnabled(false);
				}
			}
		});
		
		// Items zu "Optionen"-Menu hinzufügen
		menuOptions.add(menuItemShitftMove);
		
		
		// -------------------------------------------
		// "Hilfe" Menu
		// -------------------------------------------
		
		JMenu menuHelp = GUIElementsFactory.createMenu("Hilfe");

		// menuItem "Hilfe -> Bedienungsanleitung"
		JMenuItem menuItemManual = GUIElementsFactory.createMenuItem("Bedienungsanleitung", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File file = new File("data/manual/manual.html");
				try {
					Desktop.getDesktop().browse(file.toURI());
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		});

		// Items zu "Hilfe"-Menu hinzufügen
		menuHelp.add(menuItemManual);

		// -------------------------------------------
		// Komponenten der Menu-Leiste hinzufügen
		// -------------------------------------------
		
		this.add(menuData);
		this.add(menuView);
		this.add(menuOptions);
		this.add(menuHelp);
		this.setPreferredSize(new Dimension(0, 40));
	}
}
