package svwit.GUI;

import java.awt.Cursor;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

/**
 * Diese abstrakte Klasse dient der Erzeugung von GUI-Elementen.
 * <p>
 * Die folgenden GUI-Elemente können mithilfe dieser Klasse erzeugt werden:
 * <br>
 * <ul><li>Menüs</li>
 * <li>Menüpunkte</li>
 * <li>Auswahlkästen für ein Menü</li>
 * <li>Auswahlkästen</li>
 * <li>Abgerundete Schaltflächen</li>
 * <li>Schaltflächen mit Bildern</li>
 * <li>Abgerundete Schieberegler</li></ul>
 * <br>
 * Alle GUI-Elemente werden bei der Erzeugung optisch so angepasst, dass sie zum Gesamtbild der GUI passen.
 * 
 */
public abstract class GUIElementsFactory {

	/**
	 * Erzeugt ein Menü, welches in einer Menüleiste verwendet werden kann.
	 * <p>
	 * Der übergebene Name wird als Anzeigename für das Menü verwendet.
	 * Das erzeugte Menü ist zunächst leer. Es ist also notwendig, die einzelnen Menüpunkte seperat hinzuzufügen.
	 * 
	 * @param menuName Anzeigename des Menüs
	 * @return ein neues, zunächst leeres, Menü
	 */
	public static JMenu createMenu(String menuName) {
		JMenu newMenu = new JMenu(menuName);
		newMenu.setBorderPainted(false);
		newMenu.setFocusPainted(false);
		newMenu.setBackground(ColorPallet.COLOR_MENU_BACKGROUND);
		newMenu.setForeground(ColorPallet.COLOR_MENU_FOREGROUND);
		newMenu.getPopupMenu().setBackground(ColorPallet.COLOR_MENU_ITEM_BACKGROUND);
		newMenu.getPopupMenu().setForeground(ColorPallet.COLOR_MENU_ITEM_FOREGROUND);
		newMenu.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		return newMenu;
	}
	
	/**
	 * Erzeugt einen Menüpunkt, welcher einem bestehenden Menü hinzugefügt werden kann.
	 * <p>
	 * Der übergebene Name wird als Anzeigename für den Menüpunkt verwendet. Zusätzlich muss ein
	 * {@code ActionListener} übergeben werden, in welchem spezifiziert wird, was bei einer Betätigung des
	 * Menüpunktes geschehen soll.
	 * 
	 * @param menuItemName Anzeigename des Menüpunkts
	 * @param actionListener {@code ActionListener}, der spezifiziert, wass bei einer Betätigung des Menüpunkts geschieht
	 * @return ein neuer Menüpunkt
	 */
	public static JMenuItem createMenuItem(String menuItemName, ActionListener actionListener) {
		JMenuItem newMenuItem = new JMenuItem(menuItemName);
		newMenuItem.setBorderPainted(false);
		newMenuItem.setFocusPainted(false);
		newMenuItem.setBackground(ColorPallet.COLOR_MENU_ITEM_BACKGROUND);
		newMenuItem.setForeground(ColorPallet.COLOR_MENU_ITEM_FOREGROUND);
		newMenuItem.addActionListener(actionListener);
		return newMenuItem;
	}
	
	/**
	 * Erzeugt einen Menüpunkt mit einem Auswahlkasten, welcher einem bestehenden Menü hinzugefügt werden kann.
	 * <p>
	 * Der übergebene Name wird als Anzeigename für den Menüpunkt verwendet. 
	 * Der zweite Parameter gibt an, ob der Auswahlkasten initial ausgewählt ist oder nicht.
	 * Zusätzlich muss ein {@code ActionListener} übergeben werden, in welchem spezifiziert wird, was bei einer Betätigung des
	 * Menüpunktes geschehen soll.
	 * 
	 * @param checkBoxName Anzeigename des Menüpunkts
	 * @param checkBoxState Initialzustand des Auswahlkastens
	 * @param actionListener {@code ActionListener}, der spezifiziert, wass bei einer Betätigung des Menüpunkts geschieht
	 * @return ein neuer Menüpunkt mit einem Auswahlkasten
	 */
	public static JCheckBoxMenuItem createMenuCheckBox(String checkBoxName, boolean checkBoxState, ActionListener actionListener) {
		JCheckBoxMenuItem newCheckBox = new JCheckBoxMenuItem(checkBoxName, checkBoxState);
		newCheckBox.setFocusPainted(false);
		newCheckBox.setBorderPainted(false);
		newCheckBox.setBackground(ColorPallet.COLOR_MENU_ITEM_BACKGROUND);
		newCheckBox.setForeground(ColorPallet.COLOR_MENU_ITEM_FOREGROUND);
		newCheckBox.addActionListener(actionListener);
		return newCheckBox;
	}
	
	/**
	 * Erzeugt einen Auswahlkasten mit zugehöriger Beschriftung.
	 * <p>
	 *  Der übergebene Name wird als die Beschriftung des Auswahlkastens verwendet.
	 *  Der zweite Parameter gibt an, ob der Auswahlkasten initial ausgewählt ist oder nicht.
	 * Zusätzlich muss ein {@code ActionListener} übergeben werden, in welchem spezifiziert wird, was bei einer Betätigung des
	 * Auswahlkastens geschehen soll.
	 * 
	 * @param checkBoxName Beschriftung des Auswahlkastens
	 * @param checkBoxState Initialzustand des Auswahlkastens
	 * @param actionListener {@code ActionListener}, der spezifiziert, wass bei einer Betätigung des Auswahlkastens geschieht
	 * @return ein neuer Auswahlkasten
	 */
	public static JCheckBox createCheckBox(String checkBoxName, boolean checkBoxState, ActionListener actionListener) {
		ImageIcon check = new ImageIcon("./data/images/check.png");
		ImageIcon uncheck = new ImageIcon("./data/images/uncheck.png");
		JCheckBox newCheckBox = new JCheckBox(checkBoxName, checkBoxState);
		newCheckBox.setSelectedIcon(check);
		newCheckBox.setIcon(uncheck);
		newCheckBox.setFocusPainted(false);
		newCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		newCheckBox.setBackground(ColorPallet.COLOR_BACKGROUND_1);
		newCheckBox.setForeground(ColorPallet.COLOR_FOREGROUND);
		newCheckBox.addActionListener(actionListener);
		return newCheckBox;
	}
	
	/**
	 * Erzeugt eine abgerundete Schaltfläche.
	 * <p>
	 * Der übergebene Name wird als die Beschriftung der Schaltfläche verwendet.
	 * Zusätzlich muss ein {@code ActionListener} übergeben werden, in welchem spezifiziert wird, was bei einer Betätigung der
	 * Schaltfläche geschehen soll.
	 * 
	 * @param buttonName Beschriftung der Schaltfläche
	 * @param actionListener {@code ActionListener}, der spezifiziert, wass bei einer Betätigung der Schaltfläche geschieht
	 * @return eine neue abgerundete Schaltfläche
	 */
	public static JButton createRoundedButtonWithDetail(String buttonName, ActionListener actionListener) {
		RoundedButtonWithDetail newButton = new RoundedButtonWithDetail(buttonName);
		newButton.setFocusPainted(false);
		newButton.setBorderPainted(false);
		newButton.setContentAreaFilled(false);
		newButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		newButton.setBackground(ColorPallet.COLOR_BUTTON_BACKGROUND);
		newButton.setForeground(ColorPallet.COLOR_BUTTON_FOREGROUND);
		newButton.setDetailBackground(ColorPallet.COLOR_BUTTON_DETAIL_BACKGROUND);
		newButton.setDetailForeground(ColorPallet.COLOR_BUTTON_DETAIL_FOREGROUND);
		newButton.addActionListener(actionListener);
		return newButton;
	}
	
	/**
	 * Erzeugt eine Schaltfläche mit einem Bild.
	 * <p>
	 * Der übergebene Name wird als die Beschriftung der Schaltfläche verwendet. 
	 * Die Beschriftung wird überhalb des Bildes der Schaltfläche angezeigt.
	 * Das anzuzeigende Bild wird der Methode als {@code ImageIcon} übergeben.
	 * Zusätzlich muss angegeben werden, ob die Schaltfläche initial aktiviert oder deaktiviert sein soll.
	 * Zuletzt muss ein {@code ActionListener} übergeben werden, in welchem spezifiziert wird, was bei einer Betätigung der
	 * Schaltfläche geschehen soll.
	 * 
	 * @param buttonName Beschriftung der Schaltfläche
	 * @param buttonIcon Bild, welches auf der Schaltfläche angezeigt werden soll
	 * @param enabled Initialzustand der Schaltfläche (aktiviert oder deaktiviert)
	 * @param actionListener {@code ActionListener}, der spezifiziert, wass bei einer Betätigung der Schaltfläche geschieht
	 * @return eine neue Schaltfläche mit einem Bild
	 */
	public static JButton createButtonWithImage(String buttonName, ImageIcon buttonIcon, boolean enabled, ActionListener actionListener) {
		JButton newImageButton = new JButton(buttonName);
		newImageButton.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		newImageButton.setHorizontalTextPosition(JButton.CENTER);
		newImageButton.setVerticalTextPosition(JButton.NORTH);
		newImageButton.setIcon(buttonIcon);
		newImageButton.setContentAreaFilled(false);
		newImageButton.setBorderPainted(false);
		newImageButton.setFocusPainted(false);
		newImageButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		newImageButton.setBackground(ColorPallet.COLOR_BACKGROUND_1);
		newImageButton.setForeground(ColorPallet.COLOR_BUTTON_FOREGROUND);
		newImageButton.addActionListener(actionListener);
		newImageButton.setEnabled(enabled);
		return newImageButton;
	}
	
	/**
	 * Erzeugt einen Schieberegler.
	 * <p>
	 * Der Minimal- und Maximalwert des Schiebereglers werden der Methode übergeben.
	 * Zuletzt muss ein {@code ActionListener} übergeben werden, in welchem spezifiziert wird, was bei einer Änderung des
	 * Schiebereglers geschehen soll.
	 * 
	 * @param min Minimalwert des Schiebereglers
	 * @param max Maximalwert des Schiebereglers
	 * @param changeListener {@code ActionListener}, der spezifiziert, wass bei einer Änderung des Schiebereglers geschieht
	 * @return ein neuer Schieberegler
	 */
	public static JSlider createRoundedSlider(int min, int max, ChangeListener changeListener) {
		RoundedSlider newSlider = new RoundedSlider(min, max);
		newSlider.setBorder(null);
		newSlider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		newSlider.setBackground(ColorPallet.COLOR_BUTTON_BACKGROUND);
		newSlider.setForeground(ColorPallet.COLOR_BUTTON_FOREGROUND);
		newSlider.addChangeListener(changeListener);
		return newSlider;
	}
}
