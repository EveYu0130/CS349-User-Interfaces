import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ICRating extends JPanel implements IView {

    private ImageCollectionModel ICmodel;

    private ArrayList<Star> stars = new ArrayList<Star>();

    public ICRating(ImageCollectionModel ICmodel) {

        this.ICmodel = ICmodel;
        ICmodel.addView(this);

        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        for (int i = 0; i < 5; i++) {
            Star star = new Star(false, i);
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
                    ICmodel.setRating(star.getIndex() + 1);
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
        ICmodel.setRating(0);
    }

    /**
     * Update with data from the model.
     */
    public void updateView() {
        int rate = ICmodel.getRating();
        if (rate == 0) {
            for (int i = 0; i < 5; i++) {
                stars.get(i).setFilled(false);
            }
        } else {
            for (int i = 0; i <= rate - 1; i++) {
                stars.get(i).setFilled(true);
            }
            for (int i = rate; i < 5; i++) {
                stars.get(i).setFilled(false);
            }
        }
    }
}
