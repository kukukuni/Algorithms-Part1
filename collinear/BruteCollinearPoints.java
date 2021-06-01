/* *****************************************************************************
 *  Name:ELO
 *  Date:2021-06-01
 *  Description:BruteCollinearPoints(100/100)
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] lignSegments;

    public BruteCollinearPoints(Point[] points) {
        //When Points is null Array
        if (points == null)
            throw new IllegalArgumentException();

        //When Points[i] is null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }

        //When Points[i] has same points in Array n^2
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }

        Point[] pointsSorted = points.clone();

        Arrays.sort(pointsSorted);
        //값이 몇개 나올지 모르니까 ArrayList로 짠다.
        ArrayList<LineSegment> tmpList = new ArrayList<LineSegment>();

        for (int p = 0; p < pointsSorted.length - 3; p++) {
            for (int q = p + 1; q < pointsSorted.length - 2; q++) {
                for (int r = q + 1; r < pointsSorted.length - 1; r++) {
                    //p-q slope and  q-r slope is same
                    if (pointsSorted[p].slopeTo(pointsSorted[q]) == pointsSorted[q]
                            .slopeTo(pointsSorted[r])) {
                        for (int s = r + 1; s < pointsSorted.length; s++) {
                            //r-s slope and p-q, q-r slope is same
                            if (pointsSorted[q].slopeTo(pointsSorted[r]) == pointsSorted[r]
                                    .slopeTo(pointsSorted[s])) {
                                tmpList.add(new LineSegment(pointsSorted[p], pointsSorted[s]));
                            }
                        }
                    }
                }
            }
        }
        lignSegments = tmpList.toArray(new LineSegment[tmpList.size()]);
    }   // finds all line segments containing 4 points

    public int numberOfSegments() {
        return lignSegments.length;
    }      // the number of line segments

    public LineSegment[] segments() {
        return lignSegments.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("input8.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
