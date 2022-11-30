import java.util.Comparator;

public class Point  {
    public float x;
    public float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static float euclideanDistance(Point p1, Point p2){
        return (float) (Math.pow((p1.x-p2.x),2)+Math.pow((p1.y-p2.y),2));
    }



}
