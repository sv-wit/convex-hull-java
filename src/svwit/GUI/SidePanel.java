package svwit.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import svwit.changes.SidePanelChanges;

/**
 * Diese Klasse repäsentiert die Seitenleiste mit allen enthaltenen Elementen.
 * <p>
 * Die Klasse enthält die Methode {@code init}, welche alle Benötigten Elemente und deren Funktionalität erzeugt.
 * 
 */
public class SidePanel extends JPanel{

	/** Serialisierungs-ID */
	private static final long serialVersionUID = -798047066425032807L;

	/** Die Instanz des {@code GUIManager}, der für den Aufbau der GUI zuständig ist */
	private GUIManager guiManager;
	/** Die Instanz des {@code SidePanelChanges}, welcher auftretende Änderungen auf der Punktmenge verwaltet */
	private SidePanelChanges sidePanelChanges;
	
	/** Der Auswahlkasten zum anzeigen/verbergen der Koordinaten der Punkte */
	private JCheckBox showCoordinates;
	/** Der Auswahlkasten zum anzeigen/verbergen der konvexen Hülle */
	private JCheckBox showConvexHull;
	/** Der Auswahlkasten zum anzeigen/verbergen des Durchmessers */
	private JCheckBox showDiameter;
	/** Der Auswahlkasten zum anzeigen/verbergen des größten enthaltenen Vierecks der konvexen Hülle */
	private JCheckBox showQuadrangle;
	/** Der Auswahlkasten zum anzeigen/verbergen des größten enthaltenen Dreiecks der konvexen Hülle */
	private JCheckBox showTriangle;
	/** Der Auswahlkasten zum anzeigen/verbergen der Animation */
	private JCheckBox showAnimation;
	
	/** Die Undo-Schaltfläche */
	private JButton undoButton;
	/** Die Redo-Schaltfläche */
	private JButton redoButton;
	/** Die Schaltfläche zum starten der Animation */
	private JButton playAnimation;
	/** Die Schaltfläche zum pausieren der Animation */
	private JButton pauseAnimation;
	
	/** Der Schieberegler zum festlegen der Geschwindigkeit der Animation */
	private JSlider animationSpeed;
	
	/**
	 * Der Konstruktor.
	 * <p>
	 * Hier wird der übergebene {@code GUIManager}, der für den Aufbau der GUI zuständig ist, in die zugehörige Klassenvariable gespeichert.
	 * Zusätzlich wird eine neue Instanz des {@code SidePanelChanges} erzeugt, welche die auftretenden Änderungen verwaltet.
	 * 
	 * @param guiManager Instanz des {@code GUIManager}, der für den Aufbau der GUI zuständig ist
	 */
	public SidePanel(GUIManager guiManager) {
		this.guiManager = guiManager;
		this.sidePanelChanges = new SidePanelChanges(guiManager.getCalculatorManager(), guiManager.getChangeManager());

		init();
	}
		
	/**
	 * Erzeugt die Bedienelemente der Seitenleiste.
	 * <p>
	 * Zu den Bedienelementen gehören: 
	 * <br>
	 * <ul>
	 * <li>Undo- und Redo-Schaltfläche</li>
	 * <li>Auswahlkästen zum anzeigen/verbergen von Eigenschaften/Elementen</li>
	 * <li>Schaltflächen zum erzeugen von zufälligen Punkten</li>
	 * <li>Bedienelemente für die Animation</li>
	 * </ul>
	 */
	public void init() {
		setLayout(new BorderLayout());
		setBackground(ColorPallet.COLOR_BACKGROUND_1);
		
		//-------------------------------------------
		// Undo- und Redo-Optionen erstellen
		// Und einem Wrapper hinzufügen
		//-------------------------------------------
		
		JPanel undoRedoElementsWrapper = new JPanel();
		undoRedoElementsWrapper.setBackground(getBackground());
		undoRedoElementsWrapper.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		ImageIcon undoIcon = new ImageIcon("./data/images/undo.png");
		undoButton = GUIElementsFactory.createButtonWithImage("Undo", undoIcon, false, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.getChangeManager().unExecute();
				updateUndoReduButtons();
			}
		});
		
		ImageIcon redoIcon = new ImageIcon("./data/images/redo.png");
		redoButton = GUIElementsFactory.createButtonWithImage("Redo", redoIcon, false, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.getChangeManager().reExecute();
				updateUndoReduButtons();
			}
		});
				
		undoRedoElementsWrapper.add(undoButton);
		undoRedoElementsWrapper.add(redoButton);
		
		//-------------------------------------------
		// Anzeigeoptionen erstellen
		// Und einem Wrapper hinzufügen
		//-------------------------------------------
		
		JPanel showElementsWrapper = new JPanel();
		showElementsWrapper.setBackground(getBackground());
		showElementsWrapper.setLayout(new GridLayout(0,1,0,5));
		showElementsWrapper.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		
		showCoordinates = GUIElementsFactory.createCheckBox("Koordinaten anzeigen", false, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(showCoordinates.isSelected()) {
					guiManager.getDrawingPanel().setShowCoordinatesEnabled(true);
				} else {
					guiManager.getDrawingPanel().setShowCoordinatesEnabled(false);
				}
			}
		});
		
		showConvexHull = GUIElementsFactory.createCheckBox("Konvexe Hülle anzeigen", true, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(showConvexHull.isSelected()) {
					guiManager.getDrawingPanel().setShowConvexHullEnabled(true);
				} else {
					guiManager.getDrawingPanel().setShowConvexHullEnabled(false);
				}
			}
		});
		
		showDiameter = GUIElementsFactory.createCheckBox("Durchmesser anzeigen", true, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(showDiameter.isSelected()) {
					guiManager.getDrawingPanel().setShowDiameterEnabled(true);
				} else {
					guiManager.getDrawingPanel().setShowDiameterEnabled(false);
				}			
			}
		});
		
		showQuadrangle = GUIElementsFactory.createCheckBox("Größtes Viereck anzeigen", true, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(showQuadrangle.isSelected()) {
					guiManager.getDrawingPanel().setShowQuadrangleEnabled(true);
				} else {
					guiManager.getDrawingPanel().setShowQuadrangleEnabled(false);
				}
			}
		});
		
		showTriangle = GUIElementsFactory.createCheckBox("Größtes Dreieck anzeigen", true, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(showTriangle.isSelected()) {
					guiManager.getDrawingPanel().setShowTriangleEnabled(true);
				} else {
					guiManager.getDrawingPanel().setShowTriangleEnabled(false);
				}
			}
		});
		
		showElementsWrapper.add(showCoordinates);
		showElementsWrapper.add(showConvexHull);
		showElementsWrapper.add(showDiameter);
		showElementsWrapper.add(showQuadrangle);
		showElementsWrapper.add(showTriangle);
		
		//-------------------------------------------
		// Buttons für zufällige Punkte erstellen
		// Und einem Wrapper hinzufügen
		//-------------------------------------------
		
		JPanel randomElementsWrapper = new JPanel();	
		randomElementsWrapper.setBackground(getBackground());
		randomElementsWrapper.setLayout(new GridLayout(0,1,0,15));
		randomElementsWrapper.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
		
		JLabel labelRandom = new JLabel("Zufällige Punkte erzeugen:", SwingConstants.CENTER);
		labelRandom.setForeground(ColorPallet.COLOR_FOREGROUND);
		
		JButton buttonRandom10 = GUIElementsFactory.createRoundedButtonWithDetail("10", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRandomPoints(10);
				updateUndoReduButtons();
			}
		});
		
		JButton buttonRandom50 = GUIElementsFactory.createRoundedButtonWithDetail("50", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRandomPoints(50);
				updateUndoReduButtons();
			}
		});
		
		JButton buttonRandom100 = GUIElementsFactory.createRoundedButtonWithDetail("100", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRandomPoints(100);
				updateUndoReduButtons();
			}
		});
		
		JButton buttonRandom500 = GUIElementsFactory.createRoundedButtonWithDetail("500", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRandomPoints(500);
				updateUndoReduButtons();
			}
		});
		
		JButton buttonRandom1000 = GUIElementsFactory.createRoundedButtonWithDetail("1000", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRandomPoints(1000);
				updateUndoReduButtons();
			}
		});
		
		randomElementsWrapper.add(labelRandom);
		randomElementsWrapper.add(buttonRandom10);
		randomElementsWrapper.add(buttonRandom50);
		randomElementsWrapper.add(buttonRandom100);
		randomElementsWrapper.add(buttonRandom500);
		randomElementsWrapper.add(buttonRandom1000);
		
		//-------------------------------------------
		// Animations-Optionen erstellen
		// Und einem Wrapper hinzufügen
		//-------------------------------------------
		
		JPanel animationWrapper = new JPanel();
		animationWrapper.setBackground(getBackground());
		animationWrapper.setLayout(new GridLayout(0,1,0,5));
		animationWrapper.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		
		showAnimation = GUIElementsFactory.createCheckBox("Animation anzeigen", false, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(showAnimation.isSelected()) {
					guiManager.getDrawingPanel().setShowAnimationEnabled(true);
					playAnimation.setEnabled(true);
					pauseAnimation.setEnabled(true);
					animationSpeed.setEnabled(true);
				} else {
					guiManager.getDrawingPanel().setShowAnimationEnabled(false);
					guiManager.getDrawingPanel().getAnimatedCalipers().stopAnimation();
					playAnimation.setEnabled(false);
					pauseAnimation.setEnabled(false);
					animationSpeed.setEnabled(false);
				}
				guiManager.getDrawingPanel().repaint();	
			}
		});		
		
		JPanel animationButtonsWrapper = new JPanel();
		animationButtonsWrapper.setBackground(getBackground());
		
		ImageIcon playIcon = new ImageIcon("./data/images/play.png");
		playAnimation = GUIElementsFactory.createButtonWithImage("", playIcon, false, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.getDrawingPanel().getAnimatedCalipers().startAnimation();
			}
		});
		
		ImageIcon pauseIcon = new ImageIcon("./data/images/pause.png");
		pauseAnimation = GUIElementsFactory.createButtonWithImage("", pauseIcon, false, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.getDrawingPanel().getAnimatedCalipers().stopAnimation();
			}
		});
		
		animationButtonsWrapper.add(playAnimation);
		animationButtonsWrapper.add(pauseAnimation);
		
		JLabel labelAnimationSpeed = new JLabel("Geschwindigkeit:", SwingConstants.CENTER);
		labelAnimationSpeed.setForeground(ColorPallet.COLOR_FOREGROUND);
		
		animationSpeed = GUIElementsFactory.createRoundedSlider(0, 100, new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				guiManager.getDrawingPanel().getAnimatedCalipers().setSpeed(animationSpeed.getValue());
			}
		});
		animationSpeed.setEnabled(false);
		
		animationWrapper.add(showAnimation);
		animationWrapper.add(animationButtonsWrapper);
		animationWrapper.add(labelAnimationSpeed);
		animationWrapper.add(animationSpeed);
		
		//-------------------------------------------
		// Die Wrapper dem Panel hinzufügen
		//-------------------------------------------
		
		JPanel optionsWrapper = new JPanel();
		optionsWrapper.setBackground(getBackground());
		optionsWrapper.setLayout(new BoxLayout(optionsWrapper, BoxLayout.Y_AXIS));
		
		optionsWrapper.add(undoRedoElementsWrapper);
		optionsWrapper.add(new JSeparator());
		optionsWrapper.add(showElementsWrapper);
		optionsWrapper.add(new JSeparator());
		optionsWrapper.add(randomElementsWrapper);
		optionsWrapper.add(new JSeparator());
		optionsWrapper.add(animationWrapper);
		optionsWrapper.add(new JSeparator());

		optionsWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		this.add(optionsWrapper, BorderLayout.NORTH);
	}
	
	/**
	 * Aktuallisiert den Zustand der Undo- und Redo-Schaltflächen.
	 * Die Schaltflächen sollten deaktiviert sein, wenn keine Änderungen rückgängig beziehungsweise wiederholbar gemacht werden können.
	 * Andernfalls sollten die Schaltflächen aktiviert sein.
	 */
	public void updateUndoReduButtons() {
		if(guiManager.getChangeManager().isUndoEmpty()) {
			undoButton.setEnabled(false);
		} else {
			undoButton.setEnabled(true);
		}
		
		if(guiManager.getChangeManager().isRedoEmpty()) {
			redoButton.setEnabled(false);
		} else {
			redoButton.setEnabled(true);
		}
	}

	/**
	 * Weist die Instanz des {@code sidePanelChanges} eine festen Anzahl zufälliger Punkte zu generieren.
	 * Diese Methode wird verwendet, um zusätzlich die Ober- beziehungsweise Untergrenzen festzulegen, 
	 * innerhalb welcher zufällige Punkte generiert werden dürfen.
	 * 
	 * @param amount Anzahl der zu erzeugenden zufälligen Punkte
	 */
	private void addRandomPoints(int amount) {
		int minX = guiManager.getDrawingPanel().getCoordinateTransformer().transformToModelX(0 + 20);
		int maxX = guiManager.getDrawingPanel().getCoordinateTransformer().transformToModelX(guiManager.getDrawingPanel().getWidth() - 20);
		int minY = guiManager.getDrawingPanel().getCoordinateTransformer().transformToModelY(guiManager.getDrawingPanel().getHeight() - 20);
		int maxY = guiManager.getDrawingPanel().getCoordinateTransformer().transformToModelY(0 + 20);
		sidePanelChanges.addRandomPoints(amount, minX, minY, maxX, maxY);
	}
	
}
