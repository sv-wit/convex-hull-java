package svwit.calculator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import svwit.util.ConvexHull;
import svwit.util.Diameter;
import svwit.util.Point;
import svwit.util.PointsCollection;
import svwit.util.Quadrangle;
import svwit.util.Triangle;


/**
 * Diese Klasse berechnet die Konvexe Hülle, sowie den Durchmesser und das größte enthaltene Viereck und Dreieck von dieser.
 * <p>
 * Die verwendeten Algorithmen stammen aus der Aufgabenstellung der Programmieraufgabe. 
 * 
 */
public class Calculator {
	
	/** Gibt die Richtung an, in der die Konturen bereinigt werden sollten -> von links nach rechts */
	private static final int DIRECTION_LEFT = 0;
	/** Gibt die Richtung an, in der die Konturen bereinigt werden sollten -> von rechts nach links */
	private static final int DIRECTION_RIGHT = 1;

	/** Die Punktmenge, die verarbeitet werden soll */
	private PointsCollection pointsCollection;
	
	/** Die konvexe Hülle */
	private ConvexHull convexHull;
	/** Der Durchmesser der konvexen Hülle */
	private Diameter diameter;
	/** Das größte enthaltene Viereck der konvexen Hülle */
	private Quadrangle quadrangle;
	/** Das größte enthaltene Dreieck der konvexen Hülle */
	private Triangle triangle;
	
	/** Der Lexikographisch kleinste Punkt */
	private Point minP;
	/** Der Lexikographisch größte Punkt  */
	private Point maxP;
	
	/**
	 * Gibt die konvexe Hülle zurück.
	 * 
	 * @return konvexe Hülle
	 */
	public ConvexHull getConvexHull() {
		return convexHull;
	}
	
	/**
	 * Gibt den Durchmesser der konvexen Hülle zurück.
	 * 
	 * @return Durchmesser der konvexen Hülle
	 */
	public Diameter getDiameter() {
		return diameter;
	}
	
	/**
	 * Gibt das größte enthaltene Viereck der konvexen Hülle zurück.
	 * 
	 * @return größtes enthaltene Viereck der konvexen Hülle
	 */
	public Quadrangle getQuadrangle() {
		return quadrangle;
	}
	
	
	/**
	 * Gibt das größte enthaltene Dreieck der konvexen Hülle zurück.
	 * 
	 * @return größtes enthaltene Dreieck der konvexen Hülle
	 */
	public Triangle getTriangle() {
		return triangle;
	}
	
	/**
	 * Nimmt eine Punktmenge entgegen und startet die Berechnung der konvexen Hülle,
	 * sowie dem Durchmesser, größtes enthaltenes Viereck und Dreieck der konvexen Hülle
	 * aus der Punktmenge.
	 * <p>
	 * Die Berechnung der konvexen Hülle geschieht in der Methode {@code calculateConvexHull}.
	 * Die Berechnung des Durchmesser und des Vierecks geschieht in der Methode {@code calculateDiameterAndQuadrangle}.
	 * <p>
	 * Die konvexe Hülle wird in der Klassenvariable {@code convexHull} gespeichert. <br>
	 * Der Durchmesser wird in der Klassenvariable {@code diameter} gespeichert. <br>
	 * Das größte enthaltene Viereck wird in der Klassenvariable {@code quadrangle}  gespeichert. <br>
	 * Das größte enthaltene Dreieck wird in der Klassenvariable {@code triangle} gespeichert.
	 * 
	 * @param pointsCollection Die Punktmenge, aus der berechnet werden soll.
	 */
	public void update(PointsCollection pointsCollection) {
		this.pointsCollection = pointsCollection;
		
		calculateConvexHull();
		calculateDiameterAndQuadrangle();
		calculateTriangle();
	}
	
	/**
	 * Berechnet die konvexe Hülle.
	 * <p>
	 * Die konvexe Hülle wird aus der, in der Klassenvariable {@code pointsCollection} gespeicherten, Punktmenge berechnet.
	 * Es werden die vier Konturen, unten links, unten rechts, oben rechts und oben links mithilfe der Methode {@code calculateContour} berechnet.
	 * Anschließend werden die vier Konturen mithilfe der Methode {@code clearContourWithIterator} einzeln bereinigt.
	 * Zuletzt werden die vier Konturen zusammengefügt und doppelte Punkte an den Stellen des zusammenfügens entfernt.
	 * <p>
	 * Sollte die gespeicherte Punktmenge leer sein, wird diese Methode abgebrochen. 
	 * Für eine Punktmenge, die aus zwei oder weniger Punkten besteht, wird die konvexe Hülle aus genau diesen Punkten bestehen.
	 */
	private void calculateConvexHull() {
		convexHull = new ConvexHull();
		
		if(pointsCollection.isEmpty()) {
			return;
		}
		
		convexHull.setMostLeftPoint(pointsCollection.first());
		convexHull.setMostRightPoint(pointsCollection.last());
		
		if(pointsCollection.size() <= 2) {
			convexHull.setMostTopPoint(pointsCollection.first());
			convexHull.setMostBottomPoint(pointsCollection.first());
			for(Point p : pointsCollection) {
				convexHull.add(p);
				if(p.getY() > convexHull.getMostTopPoint().getY()) {
					convexHull.setMostTopPoint(p);
				}
				if(p.getY() < convexHull.getMostBottomPoint().getY()) {
					convexHull.setMostBottomPoint(p);
				}
			}
			return;
		}

		minP = pointsCollection.first();
		maxP = pointsCollection.last();
		
		// Konturen Berechnen	
		// - Linke Konturen

		ArrayList<Point> contourBottomLeft = new ArrayList<Point>();
		ArrayList<Point> contourTopLeft = new ArrayList<Point>();		
		calculateContour(pointsCollection, contourBottomLeft, contourTopLeft, DIRECTION_LEFT);
		
		// - Rechte Konturen
		
		ArrayList<Point> contourBottomRight = new ArrayList<Point>();
		ArrayList<Point> contourTopRight = new ArrayList<Point>();
		calculateContour(pointsCollection, contourBottomRight, contourTopRight, DIRECTION_RIGHT);

		// Konturen Bereinigen
		
		clearContourWithIterator(contourBottomLeft, (a, b, c) -> getDeterminant(a, b, c) >= 0);
		clearContourWithIterator(contourBottomRight, (a, b, c) -> getDeterminant(a, b, c) <= 0);
		clearContourWithIterator(contourTopRight, (a, b, c) -> getDeterminant(a, b, c) >= 0);
		clearContourWithIterator(contourTopLeft, (a, b, c) -> getDeterminant(a, b, c) <= 0);

		// Konturen zusammenfügen

		for(int i = 0; i < contourBottomLeft.size(); i++) {
			convexHull.add(contourBottomLeft.get(i));
		}
		if(convexHull.get(convexHull.size() - 1).equals(contourBottomRight.get(contourBottomRight.size() - 1))) {
			convexHull.remove(convexHull.size() - 1);
		}
		
		for(int i = contourBottomRight.size() - 1; i >= 0; i--) {
			convexHull.add(contourBottomRight.get(i));
		}
		if(convexHull.get(convexHull.size() - 1).equals(contourTopRight.get(0))) {
			convexHull.remove(convexHull.size() - 1);
		}
		
		for(int i = 0; i < contourTopRight.size(); i++) {
			convexHull.add(contourTopRight.get(i));
		}
		if(convexHull.get(convexHull.size() - 1).equals(contourTopLeft.get(contourTopLeft.size() - 1))) {
			convexHull.remove(convexHull.size() - 1);
		}

		for(int i = contourTopLeft.size() - 1; i >= 0; i--) {
			convexHull.add(contourTopLeft.get(i));
		}
		if(convexHull.get(convexHull.size() - 1).equals(contourBottomLeft.get(0))) {
			convexHull.remove(convexHull.size() - 1);
		}
	}
	
	/**
	 * Berechnet jeweils die obere und untere Kontur.
	 * <p>
	 * Anhand des Parameters {@code direction} wird festgelegt, in welche Richtung der Algorithmus die Konturen berechnet.
	 * Die Konturen werden in den als Parameter übergebenen Listen gespeichert.
	 * 
	 * @param pointsCollection Punktmenge, aus der die Konturen berechnet werden sollen
	 * @param contourBottom Liste, in die die untere Kontur gespeichert werden soll
	 * @param contourTop Liste, in die die obere Kontur gespeichert werden soll
	 * @param direction Richtung, in der der Algorithmus die Punktmenge durchlaufen soll
	 */
	private void calculateContour(PointsCollection pointsCollection, ArrayList<Point> contourBottom, ArrayList<Point> contourTop, int direction) {
		Iterator<Point> iterator = null;
		if(direction == DIRECTION_LEFT) {
			iterator = pointsCollection.iterator();
		} else if (direction == DIRECTION_RIGHT) {
			iterator = pointsCollection.descendingIterator();
		}
		
		Point minYSoFar;
		Point maxYSoFar;
		Point currentPoint = iterator.next();
		minYSoFar = maxYSoFar = currentPoint;
		
		contourBottom.add(currentPoint);
		contourTop.add(currentPoint);
		
		while(iterator.hasNext()) {
			currentPoint = iterator.next();
			if(currentPoint.getY() < minYSoFar.getY()) {
				contourBottom.add(currentPoint);
				minYSoFar = currentPoint;
			}
			if(currentPoint.getY() > maxYSoFar.getY()) {
				contourTop.add(currentPoint);
				maxYSoFar = currentPoint;
			}
		}
		
		convexHull.setMostBottomPoint(minYSoFar);
		convexHull.setMostTopPoint(maxYSoFar);
	}
	
	/**
	 * Bereinigt die übergebe Kontur.
	 * <p>
	 * Die Bereinigung der Kontur wird mit dem Algorithmus aus der Aufgabenstellung umgesetzt.
	 * Um eine Laufzeit von O(n) zu garantieren, wird ein Zugriff auf die einzelnen Punkte mittels Iterator vorgenommen.
	 * <p>
	 * Der Iterator läuft solange vorwärts, bis ein Punkt gefunden wird, der durch den übergebenen {@code decider} als ungültig erklärt wird.
	 * Daraufhin läuft der Iterator zurück und löscht den ungültigen Punkt aus der Kontur. Anschließend läuft der Iterator wieder vorwärts,
	 * bis der nächste ungültige Punkt gefunden wurde.
	 * <p>
	 * der {@code decider}, der übergeben wird, legt fest, wann ein Punkt als ungültig angesehen wird. Hierfür wird das funktionale Interface
	 * {@code IClearContourDirectionDecider} verwendet.
	 * 
	 * @see IClearContourDirectionDecider
	 * 
	 * @param contour Die Kontur, die bereinigt werden soll
	 * @param decider Der Entscheider, der vorgibt, wann ein Punkt ungültig ist.
	 */
	private void clearContourWithIterator(ArrayList<Point> contour, IClearContourDirectionDecider decider) {
		if(contour.size() < 3) {
			return;
		}
		ListIterator<Point> iterator = contour.listIterator();
		Point pIMinusTwo = null;					// Muss mit null initiiert werden, 
		Point pIMinusOne = null;					// da der Kompiler sont unglücklich ist
		Point pI = null;
		boolean moveForward = true;
		while(iterator.hasNext()) {
			if(moveForward) {
				pIMinusTwo = iterator.next();
				pIMinusOne = iterator.next();
				if(iterator.hasNext())				// Die Abfrage ist wichtig, 
					pI = iterator.next();			// da der Iterator weiter vorne sein kann, 
				else								// und somit die Bedingung der While-Schleife "ignoriert"
					break;
				moveForward = false;
			} else {
				pIMinusTwo = pIMinusOne;
				pIMinusOne = pI;
				pI = iterator.next();
			}
			
			while(decider.decide(pIMinusTwo, pI, pIMinusOne)) {
				moveForward = true;
				while(!iterator.previous().equals(pIMinusOne));
				iterator.remove();
				pIMinusOne = iterator.previous();
				if(iterator.hasPrevious()) {
					pIMinusTwo = iterator.previous();
					iterator.next();
					iterator.next();
					iterator.next();
					moveForward = false;
				} else {
					break;
				}
			} 					
		}
	}
	
	/**
	 * Berechnet den Durchmesser und das größte enthaltene Viereck der konvexen Hülle
	 * <p>
	 * Der Durchmesser und das Viereck werden aus der zuvor berechneten konvexen Hülle berechnet.
	 * Diese ist in der Klassenvariable {@code convexHull} gespeichert.
	 * <p>
	 * Für die Berechnung wird der Algorithmus aus der Aufgabenstellung verwendet. 
	 * In den Fällen, in denen die konvexe Hülle aus zwei oder weniger Punkten besteht,
	 * werden die Konventionen für kleine k aus der Aufgabenstellung verwendet.
	 */
	private void calculateDiameterAndQuadrangle() {
		diameter = new Diameter();
		quadrangle = new Quadrangle();
		
		if(convexHull.size() <= 0) {
			return;
		}
		if(convexHull.size() == 1) {
			diameter.setDiameterPoints(convexHull.get(0));
			quadrangle.setQuandranglePoints(convexHull.get(0));
			return;
		}
		if(convexHull.size() == 2) {
			diameter.setDiameterPoints(convexHull.get(0), convexHull.get(1));
			quadrangle.setQuandranglePoints(convexHull.get(0), convexHull.get(1));
			return;
		}
		
		int indexA = convexHull.indexOf(minP);
		int indexB = convexHull.indexOf(maxP);
		int indexAQuad = indexA;
		int indexBQuad = indexB;
		long determinant;
		long quadrangleArea;
		
		Point pointA = minP;
		Point pointB = maxP;
		Point pointC;
		
		Point pointAQuad = minP;
		Point pointBQuad = maxP;
		
		Point afterA = convexHull.get(++indexA % convexHull.size());
		Point afterB = convexHull.get(++indexB % convexHull.size());
		
		Point afterAQuad = convexHull.get(++indexAQuad % convexHull.size());
		Point afterBQuad = convexHull.get(++indexBQuad % convexHull.size());
		
		diameter.setDiameterPoints(pointA, pointB);
		quadrangle.setQuandranglePoints(pointA, pointAQuad, pointB, pointBQuad);
		quadrangleArea = getDeterminant(pointA, pointAQuad, pointB) + getDeterminant(pointA, pointB, pointBQuad);
		
		while(!(pointA.equals(maxP) && pointB.equals(minP))) {
			pointC = new Point(pointB.getX() + pointA.getX() - afterB.getX(), pointB.getY() + pointA.getY() - afterB.getY());
			determinant = getDeterminant(pointA, afterA, pointC);
			if(determinant > 0) {
				pointA = afterA;
				afterA = convexHull.get(++indexA % convexHull.size());
			} else if(determinant < 0) {
				pointB = afterB;
				afterB = convexHull.get(++indexB % convexHull.size());
			} else {
				if(isLonger(pointA, afterA, pointB, afterB)) {
					if(isLonger(pointA, pointB, diameter.getPointA(), diameter.getPointB())) {
						diameter.setDiameterPoints(pointA, pointB);
					}
					
					while(isHeigher(pointB, pointA, afterAQuad, pointAQuad)) {
						pointAQuad = afterAQuad;
						afterAQuad = convexHull.get(++indexAQuad % convexHull.size());
					}
					while(isHeigher(pointA, pointB, afterBQuad, pointBQuad)) {
						pointBQuad = afterBQuad;
						afterBQuad = convexHull.get(++indexBQuad % convexHull.size());
					}
					if(quadrangleArea < (getDeterminant(pointA, pointAQuad, pointB) + getDeterminant(pointA, pointB, pointBQuad))) {
						quadrangle.setQuandranglePoints(pointA, pointAQuad, pointB, pointBQuad);
						quadrangleArea = getDeterminant(pointA, pointAQuad, pointB) + getDeterminant(pointA, pointB, pointBQuad);
					}
					
					pointB = afterB;
					afterB = convexHull.get(++indexB % convexHull.size());
				} else {
					if(isLonger(pointA, pointB, diameter.getPointA(), diameter.getPointB())) {
						diameter.setDiameterPoints(pointA, pointB);
					}
					
					while(isHeigher(pointB, pointA, afterAQuad, pointAQuad)) {
						pointAQuad = afterAQuad;
						afterAQuad = convexHull.get(++indexAQuad % convexHull.size());
					}
					while(isHeigher(pointA, pointB, afterBQuad, pointBQuad)) {
						pointBQuad = afterBQuad;
						afterBQuad = convexHull.get(++indexBQuad % convexHull.size());
					}
					if(quadrangleArea < (getDeterminant(pointA, pointAQuad, pointB) + getDeterminant(pointA, pointB, pointBQuad))) {
						quadrangle.setQuandranglePoints(pointA, pointAQuad, pointB, pointBQuad);
						quadrangleArea = getDeterminant(pointA, pointAQuad, pointB) + getDeterminant(pointA, pointB, pointBQuad);
					}
					
					pointA = afterA;
					afterA = convexHull.get(++indexA % convexHull.size());
				}
			}
			
			if(isLonger(pointA, pointB, diameter.getPointA(), diameter.getPointB())) {
				diameter.setDiameterPoints(pointA, pointB);
			}	
			
			while(isHeigher(pointB, pointA, afterAQuad, pointAQuad)) {
				pointAQuad = afterAQuad;
				afterAQuad = convexHull.get(++indexAQuad % convexHull.size());
			}
			while(isHeigher(pointA, pointB, afterBQuad, pointBQuad)) {
				pointBQuad = afterBQuad;
				afterBQuad = convexHull.get(++indexBQuad % convexHull.size());
			}
			if(quadrangleArea < (getDeterminant(pointA, pointAQuad, pointB) + getDeterminant(pointA, pointB, pointBQuad))) {
				quadrangle.setQuandranglePoints(pointA, pointAQuad, pointB, pointBQuad);
				quadrangleArea = getDeterminant(pointA, pointAQuad, pointB) + getDeterminant(pointA, pointB, pointBQuad);
			}
		}
	}
	
	/**
	 * Berechnet das größte enthaltene Dreieck der konvexen Hülle
	 * <p>
	 * Das Dreieck wird aus der zuvor berechneten konvexen Hülle berechnet.
	 * Diese ist in der Klassenvariable {@code convexHull} gespeichert.
	 * <p>
	 * Für die Berechnung wird der Algorithmus aus der Newsgroup von Julius Dittmar vom 10.06.2022 verwendet.
	 * In den Fällen, in denen die konvexe Hülle aus zwei oder weniger Punkten besteht,
	 * werden die Konventionen für kleine k aus der Aufgabenstellung verwendet.
	 */
	private void calculateTriangle() {
		triangle = new Triangle();
		
		if(convexHull.size() <= 0) {
			return;
		}
		if(convexHull.size() == 1) {
			triangle.setTrianglePoints(convexHull.get(0));
			return;
		}
		if(convexHull.size() == 2) {
			triangle.setTrianglePoints(convexHull.get(0), convexHull.get(1));
			return;
		}
		
		int indexA = convexHull.indexOf(minP);
		int indexB = indexA + 1;
		int indexC = indexB + 1;
		
		Point pointA = minP;
		Point pointB = convexHull.get(indexB);
		Point pointC = convexHull.get(indexC);
		
		Point afterA = convexHull.get(++indexA % convexHull.size());
		Point afterB = convexHull.get(++indexB % convexHull.size());
		Point afterC = convexHull.get(++indexC % convexHull.size());
		
		triangle.setTrianglePoints(pointA, pointB, pointC);
		
		boolean changed = false;
		
		if(pointA.equals(pointC)) {
			return;
		}
		
		long triangleArea = getDeterminant(pointA, pointB, pointC);
		long newArea = 0;
		
		do {
			do {
				while(getDeterminant(pointA, pointB, afterC) > getDeterminant(pointA, pointB, pointC)) {
					pointC = afterC;
					afterC = convexHull.get(++indexC % convexHull.size());
				}
				
				newArea = getDeterminant(pointA, pointB, pointC);
				if(newArea > triangleArea) {
					triangleArea = newArea;
					triangle.setTrianglePoints(pointA, pointB, pointC);
				}
				changed = false;
				
				if(getDeterminant(pointA, afterB, pointC) > newArea || getDeterminant(pointA, afterB, afterC) > newArea) {
					pointB = afterB;
					afterB = convexHull.get(++indexB % convexHull.size());
					changed = true;
				}
				
			} while(changed);
			
			pointA = afterA;
			afterA = convexHull.get(++indexA % convexHull.size());
			
		} while(!pointA.equals(minP));
		
	}
	
	/**
	 * Gibt die Determinante aus den drei übergebenen Punkten zurück. Die Determinante wird als {@code long} zurückgegeben.
	 * 
	 * @param pointA Der erste Punkt
	 * @param pointB Der zweite Punkt
	 * @param pointC Der dritte Punkt
	 * @return Die Determinante
	 */
	private long getDeterminant(Point pointA, Point pointB, Point pointC) {
		long determinant = 0;	
		determinant += (long)pointA.getX() * ((long)pointB.getY() - (long)pointC.getY());
		determinant += (long)pointB.getX() * ((long)pointC.getY() - (long)pointA.getY());
		determinant += (long)pointC.getX() * ((long)pointA.getY() - (long)pointB.getY());	
		return determinant;
	}
	
	/**
	 * Gibt zurück, ob die Strecke zwischen einem Punkt und seinem nachfolgenen Punkt länger ist,
	 * als die Strecke zwischen einem zweiten Punkt und dessen nachfolgenden Punkt. 
	 * 
	 * @param pointA Der erste Punkt
	 * @param afterA Der Punkt nach dem ersten Punkt
	 * @param pointB Der zweite Punkt
	 * @param afterB Der Punkt nach dem zweiten Punkt
	 * @return {@code true}, wenn die Strecke des ersten Punktes zu seinem Nachfolger länger ist, ansonsten {@code false}
	 */
	private boolean isLonger(Point pointA, Point afterA, Point pointB, Point afterB) {
		return qLength(pointA, afterA) > qLength(pointB, afterB);
	}
	
	/**
	 * Gibt zurück, ob es einen nachfolgenden Punkt eines Punktes gibt, der höher liegt, als dieser.
	 * <p>
	 * Hierfür werden die Punkte A und B als eine Gerade angesehen. Der Punkt C ist der Punkt, der auf den Punkt D folgt.
	 * Zusätzlich wird ein Punkt E konstruiert, mit dessen Hilfe eine parallele Gerade zu der Gerade aus Punkt A und B erzeugt werden kann.
	 * 
	 * @param pointA Der erste Punkt der initialen Geraden
	 * @param pointB Der zweite Punkt der initialen Geraden
	 * @param pointC Der auf D folgende Punkt
	 * @param pointD Der Punkt, der mit seinem Nachfolger verglichen werden soll
	 * @return {@code true}, wenn der Punkt C höher liegt, als der Punkt D, ansonsten {@code false}
	 */
	private boolean isHeigher(Point pointA, Point pointB, Point pointC, Point pointD) {
		Point pointE = new Point(pointA.getX() + pointD.getX() - pointB.getX(), pointA.getY() + pointD.getY() - pointB.getY());
		long det = getDeterminant(pointE, pointD, pointC);
		return det > 0;
	}
	
	/**
	 * Gibt die Distanz zwischen zwei Punkten als Quadrat zurück. Die Distanz als Quadrat wird als {@code long} zurückgegeben.
	 * 
	 * @param pointA Der erste Punkt
	 * @param pointB Der zweite Punkt
	 * @return Die Distanz der Punkte als Quadrat
	 */
	private long qLength(Point pointA, Point pointB) {
		long ABx = (long)pointA.getX() - (long)pointB.getX();
		long ABy = (long)pointA.getY() - (long)pointB.getY();
		return ((ABx * ABx) + (ABy * ABy));
	}
}
