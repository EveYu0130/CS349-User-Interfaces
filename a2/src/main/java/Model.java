
import java.awt.*;
import java.util.*;

public class Model {
    /** The observers that are watching this model for changes. */
    private ArrayList<IView> views = new ArrayList<IView>();

    private ArrayList<Stroke> strokes = new ArrayList<>();
    private int ticks = 0;
    private int thickness = 1;
    private Color selectedColor = Color.BLACK;
    private double totalTime = 0;
    private double elapsedTime = 0;

    private boolean isPlaying = false;

    public ArrayList<Stroke> getStrokes() {
        return strokes;
    }

    public int getTicks() {
        return ticks;
    }

    public void updateTicks() {
        int size = strokes.size();
        if (size > 0) {
            ticks = Integer.MAX_VALUE / size;
        }
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
        notifyObservers();
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
        notifyObservers();
    }

    public void addStroke(TPoint tp, Color color, int thickness) {
        Stroke stroke = new Stroke();
        stroke.add(tp);
        stroke.setColor(color);
        stroke.setThickness(thickness);
        strokes.add(stroke);
        updateTicks();
        notifyObservers();
    }

    public void updateStroke(TPoint tp) {
        Stroke st = strokes.get(strokes.size()-1);
        double prevTime = st.getDrawTime();
        st.add(tp);
        totalTime = totalTime - prevTime + st.getDrawTime();
        elapsedTime = totalTime;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(double elapsedTime) {
        this.elapsedTime = elapsedTime;
        notifyObservers();
    }

    public void setElapsedTime(int tickValue) {
        if (totalTime > 0) {
            elapsedTime = 0;
            tickValue -= ticks;
            int idx = 0;
            while (tickValue > 0) {
                elapsedTime += strokes.get(idx).getDrawTime();
                tickValue -= ticks;
                idx++;
            }
            if (idx < strokes.size()) {
                elapsedTime += Math.round(((tickValue + ticks) * 1.0 / ticks) * strokes.get(idx).getDrawTime());
            }
            notifyObservers();
        }
    }

    public int getTickValue() {
        if (totalTime > 0) {
            double time = elapsedTime;
            double t;
            int value = 0;
            for (int i = 0; i < strokes.size(); i++) {
                t = strokes.get(i).getDrawTime();
                time -= t;
                if (time > 0) {
                    value += ticks;

                }
                else {
                    value += Math.round(((time+t)/t)*ticks);
                    break;
                }
            }
            return value;
        } else {
            return Integer.MAX_VALUE;
        }
    }



    /**
     * Add an observer to be notified when this model changes.
     */
    public void addView(IView view) {

        views.add(view);
        view.updateView();
    }

    /**
     * Remove an observer from this model.
     */
    public void removeView(IView view) {

        views.remove(view);
        view.updateView();
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (IView view : this.views) {
            view.updateView();
        }
    }
}
