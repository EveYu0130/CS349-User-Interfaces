import javax.swing.*;
import java.awt.*;

public class TPoint {

    private Point point;
    private double time;

    public TPoint(Point p) {
        point = p;
        time = System.currentTimeMillis();
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
