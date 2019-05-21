import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class DrawPanelView extends JPanel implements IView {

    private Model model;
    private double mouseDownTime, mouseUpTime;

    public DrawPanelView(Model model) {

        this.model = model;
        this.setBackground(Color.WHITE);

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                mouseDownTime = System.currentTimeMillis();
                Point p = e.getPoint();
                TPoint tp = new TPoint(p);
                model.addStroke(tp, model.getSelectedColor(), model.getThickness());
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseUpTime = System.currentTimeMillis();
//                model.getStrokes().get(model.getStrokes().size()-1).setDrawTime(mouseUpTime-mouseDownTime);
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = e.getPoint();
                TPoint tp = new TPoint(p);
                model.updateStroke(tp);
                repaint();
            }
        });



    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        ArrayList<Stroke> strokes = model.getStrokes();
        double elapsedTime = model.getElapsedTime();

        if (elapsedTime > 0) {
            for (int i = 0; i < strokes.size(); i++) {
                Stroke stroke = strokes.get(i);
                g2d.setColor(stroke.getColor());
                g2d.setStroke(new BasicStroke(stroke.getThickness()));
                ArrayList<TPoint> tpoints = stroke.getPoints();
                double startTime = stroke.getStartTime();
                if (tpoints.size() == 1 && elapsedTime > 0) {
                    TPoint tp = tpoints.get(0);
                    if (tp.getTime() - startTime <= elapsedTime) {
                        elapsedTime -= tp.getTime() - startTime;
                        g2d.drawLine((int)tp.getPoint().getX(),
                                (int)tp.getPoint().getY(),
                                (int)tp.getPoint().getX(),
                                (int)tp.getPoint().getY());
                    }

                } else {
                    for (int j = 1; j < tpoints.size(); j++) {
                        TPoint tp1 = tpoints.get(j - 1);
                        TPoint tp2 = tpoints.get(j);
                        if (tp2.getTime() - tp1.getTime() <= elapsedTime) {
                            elapsedTime -= tp2.getTime() - tp1.getTime();

                            g2d.drawLine((int) tp1.getPoint().getX(),
                                    (int) tp1.getPoint().getY(),
                                    (int) tp2.getPoint().getX(),
                                    (int) tp2.getPoint().getY());
                        }
                    }
                }
            }
        }

        repaint();
    }



    public void updateView() {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
//        System.out.println("DrawPanelView: Model changed!");
        repaint();
    }
}
