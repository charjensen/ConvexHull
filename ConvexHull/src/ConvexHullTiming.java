import java.awt.Point;
import java.util.ArrayList;

public class ConvexHullTiming {

	public static ArrayList<Point> R;

	public static void main(String[] args) {

		System.out.println("brtue force" + "\nn: \ttime");
		for (long n = 10; n <= 50; n += 10) {
			ArrayList<Point> P = initHull(n);
			long numberOfTrials = 600000;
			long startTime = System.currentTimeMillis();

			for (int i = 0; i <= numberOfTrials; i++)
				computeHull(P);

			long endTime = System.currentTimeMillis();

			System.out.println(n + "\t" + (endTime - startTime) / ((double) numberOfTrials));

		}

//		brtue force
//		n: 	time
//		10	0.001705
//		20	0.0114
//		30	0.0489
//		40	0.15244166666666667
//		50	0.37455666666666665

		System.out.println("quick hull" + "\nn: \ttime");
		for (long n = 10; n <= 50; n += 10) {
			ArrayList<Point> P = initHull(n);
			long numberOfTrials = 2000000;
			long startTime = System.currentTimeMillis();

			for (int i = 0; i <= numberOfTrials; i++)
				QuickHullStart(P);

			long endTime = System.currentTimeMillis();

			System.out.println(n + "\t" + (endTime - startTime) / ((double) numberOfTrials));

		}

//		quick hull
//		n: 	time
//		10	4.38E-4
//		20	7.855E-4
//		30	9.745E-4
//		40	0.0014335
//		50	0.0015295

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

	public static ArrayList<Point> initHull(long n) {

		ArrayList<Point> result = new ArrayList<Point>();

		for (int i = 0; i < n; i++)
			result.add(new Point(10 + (int) (Math.random() * 30), 10 + (int) (Math.random() * 30)));

		return result;

	}

}
