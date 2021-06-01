/* *****************************************************************************
 *  Name:ELO
 *  Date:2021-06-01
 *  Description:FastCollinearPoint(100/100)
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final LineSegment[] lignSegments;

    public FastCollinearPoints(Point[] points) {
        //When Points is null Array
        if (points == null)
            throw new IllegalArgumentException();

        //When Points[i] is null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }

        Point[] pointsSorted = points.clone();

        Arrays.sort(pointsSorted);
        //Fast에선 걍 Sort하고 이전 것과 현재것이 같은지 파악으로 동일한 점 있는지 파악 Sort에서 nlogn 확인은 n
        for (int i = 0; i < pointsSorted.length - 1; i++) {
            if (pointsSorted[i].compareTo(pointsSorted[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        Point[] points2; //slope기준으로 재정렬된 pointsSorted배열
        int numOfPoints = 0;//3이상이면 선 생성(본임 포함 4개)
        boolean useless = false;
        ArrayList<LineSegment> tmpList = new ArrayList<LineSegment>();

        for (int p = 0; p < pointsSorted.length - 3; p++) {
            points2 = pointsSorted.clone();
            Arrays.sort(points2, points2[p].slopeOrder());
            //Sorted결과는 points2[p]가 가장 맨 앞 그리고 그 후는 points2[p]와의 slope가 작은 것부터 나열됨
            numOfPoints = 0;

            for (int q = 1; q < pointsSorted.length; q++) {//point2의 가장처음은 의미없으므로 제외
                //Point q가 p보다 작으면 의미가 없다. 직선이 만들어졌어도 이미 앞에서 만들어졌었던 것을 의미하므로
                //항상 직선은 copare가 가장 작은것과 큰것의 연결로 생성이 된다.
                if (points2[q].compareTo(points2[0]) < 0) {
                    useless = true;
                    numOfPoints = 0;
                }
                else if (points2[q].compareTo(points2[0]) > 0) { //q가 p보다 클때
                    //q가 p보다 작은 경우와 같은 slope일때 제외
                    if (useless && points2[0].slopeTo(points2[q]) == points2[0]
                            .slopeTo(points2[q - 1])) {
                        continue;
                    }
                    //slope가 이전것과 다를떄(새로운 직선 생성의 가능성이 생길때)
                    else if (points2[0].slopeTo(points2[q]) != points2[0].slopeTo(points2[q - 1])) {
                        useless = false;
                        numOfPoints = 1;
                    }
                    //이전 기울기와 같은 기울기일때
                    else if (points2[0].slopeTo(points2[q]) == points2[0].slopeTo(points2[q - 1])) {
                        numOfPoints += 1;
                    }
                    //q가 마지막이면서 앞의 점들과 직선을 이룰때 || q가 마지막 점이 아니고, slope가 이전것과 다른데, 이전의 것이 이미 직선을 이룰떄
                    if ((q == points.length - 1 && numOfPoints >= 3) || (q != points.length - 1 &&
                            points2[0].slopeTo(points2[q]) != points2[0].slopeTo(points2[q + 1])
                            && numOfPoints >= 3)) {
                        tmpList.add(new LineSegment(points2[0], points2[q]));
                    }
                }
            }
        }
        lignSegments = tmpList.toArray(new LineSegment[tmpList.size()]);
    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return lignSegments.length;
    }       // the number of line segments

    public LineSegment[] segments() {
        return lignSegments.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In("equidistant.txt");
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
