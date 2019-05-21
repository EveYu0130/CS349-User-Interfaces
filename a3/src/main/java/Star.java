import javax.swing.*;
import java.awt.*;

public class Star extends JButton {
    ImageIcon fullStar = new ImageIcon("./src/icons/star_full_icon.png");

    ImageIcon emptyStar = new ImageIcon("./src/icons/star_empty_icon.png");

    boolean filled;

    int index;

    public Star(boolean filled, int index) {
        this.setSize(new Dimension(20, 20));

        this.filled = filled;
        this.index = index;

    }

    public int getIndex() {
        return index;
    }

    public ImageIcon getEmptyStar() {
        return emptyStar;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public ImageIcon getFullStar() {
        return fullStar;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (filled) {
            fullStar.setImage(fullStar.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            this.setIcon(fullStar);

        } else {
            emptyStar.setImage(emptyStar.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
            this.setIcon(emptyStar);
        }

        this.revalidate();
    }



}
