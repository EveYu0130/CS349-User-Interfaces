import javax.swing.*;
import java.awt.*;

public class StrokePattern extends JComponent {

    private int width;

    private boolean isSelected = false;

    public StrokePattern(int width) {
        this.setPreferredSize(new Dimension(60,30));
        this.width = width;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (isSelected) {
            g2.setColor(Color.BLACK);
            g2.drawRect(0,0,60,30);
        }
        g2.setStroke(new BasicStroke(width));
        g2.setColor(Color.BLACK);
        g2.drawLine(15, 15, 45, 15);
    }

    public void onSelect() {
        isSelected = true;
        repaint();
    }

    public void unSelect() {
        isSelected = false;
        repaint();
    }

}
