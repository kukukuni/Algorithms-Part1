/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] lignSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }

        Arrays.sort(points);

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        Point[] points2;
        double tmpSlope;
        double stdSlope;
        int numOfPoints = 0;
        ArrayList<LineSegment> tmpList = new ArrayList<LineSegment>();

        for (int p = 0; p < points.length - 3; p++) {
            points2 = points.clone();
            Arrays.sort(points2, points2[p].slopeOrder());
            numOfPoints = 0;
            stdSlope = points2[0].slopeTo(points2[1]);
            for (int q = 1; q < points.length; q++) {
                if (q == points.length - 1 && numOfPoints > 2) {
                    tmpList.add(new LineSegment(points[p], points2[q - 1]));
                    break;
                }
                tmpSlope = points2[0].slopeTo(points2[q]);
                if (tmpSlope == stdSlope) {
                    numOfPoints += 1;
                }
                else {
                    if (numOfPoints > 2)
                        tmpList.add(new LineSegment(points2[0], points2[q - 1]));
                    stdSlope = tmpSlope;
                    numOfPoints = 1;
                }
            }
        }
        lignSegments = tmpList.toArray(new LineSegment[tmpList.size()]);
    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return lignSegments.length;
    }       // the number of line segments

    public LineSegment[] segments() {
        return lignSegments;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
