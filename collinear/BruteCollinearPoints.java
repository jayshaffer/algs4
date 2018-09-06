import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int count;
    public BruteCollinearPoints(Point[] points){
        if(points == null){throw new java.lang.IllegalArgumentException();}
        this.segments = new LineSegment[0];
        for(int i = 0; i < points.length; i++){
            for(int j = i + 1; j < points.length; j++){
                double ijSlope = points[i].slopeTo(points[j]);
                for(int k = j + 1; k < points.length; k++){
                    double jkSlope = points[j].slopeTo(points[k]);
                    if(jkSlope != ijSlope){
                        break;
                    }
                    for(int l = k + 1; l < points.length; l++){
                        double klSlope = points[k].slopeTo(points[l]);
                        if(klSlope == jkSlope){
                            Point[] temp = new Point[]{points[i], points[j], points[k], points[l]};
                            Arrays.sort(temp);
                            increaseSize();
                            segments[count] = new LineSegment(temp[0], temp[3]);
                            count++;
                            System.out.println("Adding");
                        }
                    }
                }
            }
        }
    }
    public int numberOfSegments(){
        return count;
    }
    public LineSegment[] segments(){
        return segments;
    }
    private void increaseSize(){
        LineSegment[] copy = new LineSegment[segments.length + 1];
        System.arraycopy(segments,0, copy, 0, segments.length);
        this.segments = copy;
    }

    public static void main(String[] args){
        // read the n points from a file
        In in = new In(args[0]);
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
 