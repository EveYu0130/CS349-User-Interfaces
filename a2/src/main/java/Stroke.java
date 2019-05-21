import java.util.ArrayList;
import java.awt.Point;
import java.awt.Color;

public class Stroke {
    private ArrayList<TPoint> tpoints;
    private int thickness;
    private Color color;
    private double drawTime;
    private double startTime;

    public Stroke() {
        tpoints = new ArrayList<>();
        thickness = 1;
        color = Color.BLACK;
        drawTime = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
    }

    public void add(TPoint tp) {
        tpoints.add(tp);
        drawTime = tp.getTime() - startTime;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public int getThickness() {
        return thickness;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<TPoint> getPoints() {
        return tpoints;
    }

    public double getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(double drawTime) {
        this.drawTime = drawTime;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }
}
