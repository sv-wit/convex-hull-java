package svwit.GUI;

import java.awt.Rectangle;

/**
 * Diese Klasse dient der Umwandlung von Bildschirmkoordinaten auf Modellkoordinaten und umgekehrt.
 * <p>
 * Die Klasse enthällt jeweils einen Fixpunkt für die Bildschirmkoordinaten und die Modellkoordinaten, 
 * anhand welcher die jeweiligen Koordinaten umgerechnet werden.
 * Außerdem wird hier der Zoomfaktor verwaltet und bei der Umrechnung von Koordinaten angewendet.
 * Zusätzlich gibt es eine Verschiebung der Koordinaten, mit welcher es ermöglicht wird,
 * den aktuellen Bildschirmausschnitt zu verschieben.
 * 
 */
public class CoordinateTransformer {

	/** Die Verschiebung auf der X-Koordinate */
	private int offsetX = 0;
	/** Die Verschiebung auf der Y-Koordinate */
	private int offsetY = 0;
	/** Der Zoomfaktor */
	private double zoom = 1d;
	
	/** Die X-Koordinate des Fixpunkts der Bildschirmkoordinaten */
	private int fixScreenX = 0;
	/** Die Y-Koordinate des Fixpunkts der Bildschirmkoordinaten */
	private int fixScreenY = 0;
	/** Die X-Koordinate des Fixpunkts der Modellkoordinaten */
	private int fixModelX = 0;
	/** Die Y-Koordinate des Fixpunkts der Modellkoordinaten */
	private int fixModelY = 0;
	
	/**
	 * Setzt die Koordinaten-Fixpunkte anhand des übergebenen Rechtecks.
	 * <p>
	 * Das Übergebene Rechteck repräsentiert die Größe der Zeichenfläche.
	 * Diese Methode ist bei einer Änderung der Größe der Zeichenfläche wichtig, 
	 * da sich in diesem Fall die Abstände der Fixpunkte zu dem Rand der Zeichenfläche ändern.
	 * 
	 * @param r Rechteck der Größe der Zeichenfläche
	 */
	public void setScreenHeight(Rectangle r) {
		fixScreenX = r.width / 2;
		fixScreenY = r.height / 2;
		fixModelX = r.width / 2;
		fixModelY = r.height / 2;
	}
	
	/**
	 * Berechnet die Verschiebung auf der X-Koordinate.
	 * <p>
	 * Die Verschiebung wird anhand der Übergebenen Werte neu berechnet.
	 * Hierzu wird die Startposition, sowie die Zielposition der Verschiebung betrachtet.
	 * Zusätzlich wird eine bereits existierende Verschiebung berücksichtigt.
	 * 
	 * @param initialOffsetX Bestehende Verschiebung der X-Koordinate
	 * @param mouseOriginX X-Koordinate der Startposition der Verschiebung
	 * @param mouseCurrentX X-Koodrinate der Zielposition der Verschiebung
	 */
	public void addOffsetX(int initialOffsetX, int mouseOriginX, int mouseCurrentX) {
		this.offsetX = (int) (initialOffsetX - Math.round((mouseOriginX - mouseCurrentX) / zoom));
	}
	
	/**
	 * Gibt die aktuelle Verschiebung der X-Koordinate zurück.
	 * 
	 * @return Aktuelle Verschiebung der X-Koordinate
	 */
	public int getOffsetX() {
		return offsetX;
	}
	
	/**
	 * Setzt die aktuelle Verschiebung der X-Koordinate auf den übergebenen Wert.
	 * 
	 * @param offsetX Wert, der als Verschiebung der X-Koordinate gesetzt werden soll
	 */
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}
	
	/**
	 * Berechnet die Verschiebung auf der Y-Koordinate.
	 * <p>
	 * Die Verschiebung wird anhand der Übergebenen Werte neu berechnet.
	 * Hierzu wird die Startposition, sowie die Zielposition der Verschiebung betrachtet.
	 * Zusätzlich wird eine bereits existierende Verschiebung berücksichtigt.
	 * 
	 * @param initialOffsetY Bestehende Verschiebung der Y-Koordinate
	 * @param mouseOriginY Y-Koordinate der Startposition der Verschiebung
	 * @param mouseCurrentY Y-Koodrinate der Zielposition der Verschiebung
	 */
	public void addOffsetY(int initialOffsetY, int mouseOriginY, int mouseCurrentY) {
		this.offsetY = (int) (initialOffsetY - Math.round((mouseOriginY - mouseCurrentY) / zoom));
	}
	
	/**
	 * Gibt die aktuelle Verschiebung der Y-Koordinate zurück.
	 * 
	 * @return Aktuelle Verschiebung der Y-Koordinate
	 */
	public int getOffsetY() {
		return offsetY;
	}
	
	/**
	 * Setzt die aktuelle Verschiebung der Y-Koordinate auf den übergebenen Wert.
	 * 
	 * @param offsetY Wert, der als Verschiebung der Y-Koordinate gesetzt werden soll
	 */
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * Erhöht den Zoomfaktor um den Wert 1.2.
	 * Der Zoomfaktor kann ein Maximum von 25 annehmen.
	 */
	public void increaseZoom() {
		double tempZoom = zoom * 1.2;
		if(tempZoom > 25d) {
			tempZoom = 25d;
		}
		zoom = tempZoom;
	}
	
	/**
	 * Verringert den Zoomfaktor um den Wert 1.2.
	 * Der Zoomfaktor kann ein Minimum von 0.000001 annehmen.
	 */
	public void decreaseZoom() {
		double tempZoom = zoom / 1.2;
		if(tempZoom < 0.000001) {
			tempZoom = 0.000001;
		}
		zoom = tempZoom;
	}
	
	/**
	 * Gibt den aktuellen Zoomfaktor zurück.
	 * 
	 * @return Aktueller Zoomfaktor
	 */
	public double getZoom() {
		return zoom;
	}
	
	/**
	 * Setzt den aktuellen Zoomfaktor auf den übergebenen Wert.
	 * Der Zoomfaktor kann durch diese Methode nicht kleiner als 1 gesetzt werden.
	 * 
	 * @param d Wert, der als aktueller Zoomfaktor verwendet werden soll
	 */
	public void setZoom(double d) {
		if(d > 1d) {
			zoom = 1d;
			return;
		}
		zoom = d;
	}
	
	/**
	 * Wandelt die übergebene X-Koordinate in eine Bildschirmkoordinate um.
	 * 
	 * @param x X-Koordinate, die umgewandelt werden soll
	 * @return Umgewandelte X-Koordinate
	 */
	public int transformToScreenX(int x) {
		return (int)(fixScreenX + zoom * (x - fixModelX + offsetX));
	}
	
	/**
	 * Wandelt die übergebene Y-Koordinate in eine Bildschirmkoordinate um.
	 * 
	 * @param y Y-Koordinate, die umgewandelt werden soll
	 * @return Umgewandelte Y-Koordinate
	 */
	public int transformToScreenY(int y) {
		return (int)(fixScreenY - zoom * (y - fixModelY - offsetY));
	}
	
	/**
	 * Wandelt die übergebene X-Koordinate in eine Modellkoordinate um.
	 * 
	 * @param x X-Koordinate, die umgewandelt werden soll
	 * @return Umgewandelte X-Koordinate
	 */
	public int transformToModelX(int x) {
		return (int)(fixModelX + 1 / zoom * (x - fixScreenX) - offsetX);
	}
	
	/**
	 * Wandelt die übergebene Y-Koordinate in eine Modellkoordinate um.
	 * 
	 * @param y Y-Koordinate, die umgewandelt werden soll
	 * @return Umgewandelte Y-Koordinate
	 */
	public int transformToModelY(int y) {
		return (int)(fixModelY - 1 / zoom * (y - fixScreenY) + offsetY);
	}
	
	/**
	 * Setzt den Zoomfaktor und die Verschiebung auf den Ursprungszustand zurück.
	 */
	public void reset() {
		this.zoom = 1d;
		this.offsetX = 0;
		this.offsetY = 0;
	}
	
}
