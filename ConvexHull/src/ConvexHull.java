import java.awt.Point;
import java.util.ArrayList;
import edu.du.dudraw.*;

public class ConvexHull {

	public static ArrayList<Point> P = initHull();
	public static ArrayList<Point> R;
	public static String[] args1 = {"hi"};

	public static void main(String[] args) {

		// dudraw b4
		DUDraw.setCanvasSize(500, 500);
		DUDraw.setScale(0, 50);
		//DUDraw.enableDoubleBuffering();

		if (args.length > 0 && args[0].equals("hi")) {
			DUDraw.text(25, 45, ":DDDDDDDDDDD");
			//DUDraw.show();
			DUDraw.pause(250);
			DUDraw.clear();
		}

		DUDraw.text(25, 45, "Initial Points");
		initDrawPoints(P);

		DUDraw.pause(5000);

		// dudraw after
		DUDraw.clear();
		DUDraw.setPenColor(DUDraw.BLACK);
		DUDraw.text(25, 45, "Brute Force");
		drawPoints(P);
		R = computeHull(P);
		drawHull(R);

		DUDraw.pause(5000);

		DUDraw.clear();
		DUDraw.setPenColor(DUDraw.BLACK);
		DUDraw.text(25, 45, "Quick Hull");
		R.clear();
		drawPoints(P);
		R = QuickHullStart(P);
		drawHull(R);

		DUDraw.pause(5000);

		main(args1);

	}

	public static ArrayList<Point> QuickHullStart(ArrayList<Point> P) {

		ArrayList<Point> UH = new ArrayList<Point>();
		ArrayList<Point> LH = new ArrayList<Point>();

		Point a = findMinX(P);
		Point b = findMaxX(P);

		for (Point p : P) {
			if (!p.equals(a) && !p.equals(b)) {

				if (leftTurn(a, b, p) && !UH.contains(p))
					UH.add(p);
				else if (leftTurn(b, a, p) && !LH.contains(p))
					LH.add(p);

			}
		}

		ArrayList<Point> S = new ArrayList<Point>();

		QuickHull(UH, a, b, S);
		QuickHull(LH, b, a, S);

		S.add(a);
		S.add(b);

		return S;

	}

	public static void QuickHull(ArrayList<Point> S, Point a, Point b, ArrayList<Point> R) {

		Point c = furthestFromLine(S, a, b);

		if (c != null)
			R.add(c);

		if (S.size() < 2 || c == null)
			return;

		ArrayList<Point> left = new ArrayList<Point>();
		ArrayList<Point> right = new ArrayList<Point>();

		for (Point p : S) {

			if (leftTurn(a, c, p) && !left.contains(p))
				left.add(p);
			else if (!(leftTurn(b, c, p)) && !right.contains(p))
				right.add(p);

		}

		QuickHull(left, a, c, R);
		QuickHull(right, c, b, R);

	}

	public static Point furthestFromLine(ArrayList<Point> P, Point a, Point b) {

		Point f = null;
		double maxDist = 0;

		for (Point p : P) {
			if (!p.equals(a) && !p.equals(b)) {
				double currentDist = valueBasedOnLineDistance(a, b, p);
				if (maxDist < currentDist) {
					f = p;
					maxDist = currentDist;
				}
			}
		}

		return f;

	}

	public static double valueBasedOnLineDistance(Point a, Point b, Point p) {

		Point v1 = new Point(b.x - a.x, b.y - a.y);
		Point v2 = new Point(p.x - a.x, p.y - a.y);

		return Math.abs((v1.x * v2.y) - (v1.y * v2.x));

	}

	public static ArrayList<Point> computeHull(ArrayList<Point> L) {

		ArrayList<Point> CH = new ArrayList<Point>();

		for (Point i : L) {
			for (Point j : L) {
				int leftTurnCount = 0;
				if (!(j.equals(i))) {
					for (Point k : L) {
						if (!((k.equals(j) && k.equals(i)))) {
							if (leftTurn(i, j, k))
								leftTurnCount++;
						}
					}
					if ((leftTurnCount == 0) || (leftTurnCount == (L.size() - 2))) {
						if (!CH.contains(i))
							CH.add(i);
						else if (!CH.contains(j))
							CH.add(j);
					}
				}
			}
		}

		return CH;

	}

	public static boolean leftTurn(Point a, Point b, Point i) {

		Point vab = new Point(b.x - a.x, b.y - a.y);
		Point vbi = new Point(i.x - b.x, i.y - b.y);
		return ((vab.x * vbi.y) - (vab.y * vbi.x)) > 0;

	}

	public static ArrayList<Point> initHull() {

		ArrayList<Point> result = new ArrayList<Point>();

		for (int i = 0; i < 50; i++) {
			Point p = new Point(10 + (int) (Math.random() * 35), 10 + (int) (Math.random() * 35));
			result.add(p);
			result.add(new Point(9, 25));
			result.add(new Point(46, 25));
		}

		return result;

	}

	public static void initDrawPoints(ArrayList<Point> P) {

		for (Point p : P) {
			DUDraw.setPenColor(DUDraw.BLACK);
			DUDraw.filledCircle(p.x, p.y, 0.1);
			DUDraw.setPenColor(DUDraw.GREEN);
			DUDraw.text(p.x, p.y + 0.7, p.x + ", " + p.y);
		}

		//DUDraw.show();

	}

	public static void drawPoints(ArrayList<Point> P) {

		for (Point p : P) {
			DUDraw.setPenColor(DUDraw.BLACK);
			DUDraw.filledCircle(p.x, p.y, 0.1);
		}

		//DUDraw.show();

	}

	public static void drawLines(ArrayList<Point> P) {

		for (int i = 0; i < P.size() - 1; i++) {
			int j = i + 1;
			DUDraw.setPenColor(DUDraw.BLACK);
			DUDraw.line(P.get(i).x, P.get(i).y, P.get(j).x, P.get(j).y);
			//DUDraw.pause(100);
		}

	}

	public static void drawHull(ArrayList<Point> P) {

		ArrayList<Point> UH = new ArrayList<Point>();
		ArrayList<Point> LH = new ArrayList<Point>();

		Point left = findMinX(P);
		Point right = findMaxX(P);

		for (Point p : P) {
			if (!p.equals(left) && !p.equals(right)) {

				if (leftTurn(left, right, p) && !UH.contains(p))
					UH.add(p);
				else if (!leftTurn(left, right, p) && !LH.contains(p))
					LH.add(p);

			}
		}

		if (slope(left, right) > 0) {
			LH.add(left);
			UH.add(right);
		} else {
			UH.add(left);
			LH.add(right);
		}

		insertionSortX(UH);
		insertionSortX(LH);

		DUDraw.line(UH.get(0).x, UH.get(0).y, LH.get(0).x, LH.get(0).y);
		DUDraw.pause(100);
		DUDraw.line(UH.get(UH.size()-1).x, UH.get(UH.size()-1).y, LH.get(LH.size()-1).x, LH.get(LH.size()-1).y);
		DUDraw.pause(100);
		drawLines(UH);
		drawLines(LH);

		//DUDraw.show();

	}

	public static Point findMinX(ArrayList<Point> P) {

		Point least = P.get(0);

		for (Point p : P)
			if (least.x > p.x)
				least = p;

		return least;

	}

	public static Point findMaxX(ArrayList<Point> P) {

		Point most = P.get(0);

		for (Point p : P)
			if (most.x < p.x)
				most = p;

		return most;

	}

	public static int slope(Point a, Point b) {

		return (b.y - a.y) / (b.x - a.x);

	}

	public static void insertionSortX(ArrayList<Point> P) {
		// i got this from the book
		for (int j = 1; j < P.size(); j++) {

			Point key = P.get(j);
			int i = j - 1;

			while (i >= 0 && (P.get(i).x > key.x)) {
				P.set(i + 1, P.get(i));
				i = i - 1;
			}

			P.set(i + 1, key);

		}

	}

}
