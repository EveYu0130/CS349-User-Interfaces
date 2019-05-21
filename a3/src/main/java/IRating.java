import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IRating extends JPanel implements IView {

    private ImageModel Imodel;

    private ArrayList<Star> stars = new ArrayList<Star>();

    public IRating(ImageModel Imodel) {

        this.Imodel = Imodel;
        Imodel.setIrating(this);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        int rating = Imodel.getRateValue();
        for (int i = 0; i < 5; i++) {
            Star star;
            if (i < rating) {
                star = new Star(true, i);
            } else {
                star = new Star(false, i);
            }
            stars.add(star);
            star.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int j = 0; j < 5; j++) {
                        if (j <= star.getIndex()) {
                            ImageIcon fullStar = star.getFullStar();
                            fullStar.setImage(fullStar.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
                            stars.get(j).setIcon(fullStar);
                        } else {
                            ImageIcon emptyStar = star.getEmptyStar();
                            emptyStar.setImage(emptyStar.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
                            stars.get(j).setIcon(emptyStar);
                        }
                    }
                    Imodel.setRateValue(star.getIndex() + 1);
                }
            });
            this.add(stars.get(i));
        }


    }

    public void clear() {
        for (int i = 0; i < 5; i++) {
            Star star = stars.get(i);
            ImageIcon emptyStar = star.getEmptyStar();
            emptyStar.setImage(emptyStar.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
            stars.get(i).setIcon(emptyStar);
        }
        Imodel.setRateValue(0);
    }

    /**
     * Update with data from the model.
     */
    public void updateView() {
        int rate = Imodel.getRateValue();
        for (int i = 0; i <= rate - 1; i++) {
            stars.get(i).setFilled(true);
        }
        for (int i = rate; i < 5; i++) {
            stars.get(i).setFilled(false);
        }
    }
}
