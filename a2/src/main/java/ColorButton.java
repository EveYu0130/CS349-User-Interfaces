import javax.swing.*;
import java.awt.*;

public class ColorButton extends JComponent {

    private Color color;

    public ColorButton(Color color) {
        this.setPreferredSize(new Dimension(20,20));
        this.color = color;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(1,1,18,18);
        g.setColor(Color.WHITE);
        g.drawRect(1, 1, 18, 18);
        g.setColor(Color.BLACK);
        g.drawRect(0,0,20,20);
    }

    public Color getColor() {
        return color;
    }
}
