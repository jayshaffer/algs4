import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;
    public FastCollinearPoints(Point[] points){
        if(points == null){
            throw new java.lang.IllegalArgumentException();
        }
        for(Point p : points){
            Arrays.sort(points, p.SLOPE_ORDER);
            ArrayList<Point> current = new ArrayList<Point>();
            for(int i = 0; i < points.length - 1; i++){
                Point q = points[i];
                Point qPlus = points[i + 1];
                if(p == q){
                    continue;
                }
                if(p.slopeTo(q) == p.slopeTo((qPlus))){
                    if(current.size() == 0){
                        current.add(q);
                    }
                    current.add(qPlus);
                }
                else if(current.size() >= 3){
                    Point[] currentBasic = current.toArray();
                    Arrays.sort(current.toArray());
                }
            }
        }
    }

    private void increaseSize(){
        LineSegment[] copy = new LineSegment[segments.length + 1];
        System.arraycopy(segments,0, copy, 0, segments.length);
        this.segments = copy;
    }

    public int numberOfSegments(){
        return segments.length;
    }

    public LineSegment[] segments(){
        return segments;
    }
 }