import javax.swing.*;
import java.awt.*;

public class ColorPanel extends JComponent {

    private Color color;

    public ColorPanel(Color color) {
        this.setPreferredSize(new Dimension(46,40));
        this.color = color;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(2,2,42,36);
        g.setColor(Color.WHITE);
        g.drawRect(2, 2, 42, 36);
        g.setColor(Color.BLACK);
        g.drawRect(0,0,46,40);
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

}
