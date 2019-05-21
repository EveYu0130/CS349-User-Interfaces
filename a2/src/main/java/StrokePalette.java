import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class StrokePalette extends JPanel implements IView {

    private Model model;
    private ArrayList<StrokePattern> strokePatterns;

    public StrokePalette(Model model) {
        this.model = model;
        this.strokePatterns = new ArrayList<>();

        this.setMinimumSize(new Dimension(80, 240));
        this.setPreferredSize(new Dimension(80, 240));
        this.setBackground(Color.LIGHT_GRAY);

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        this.setLayout(gridbag);
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;

        for (int i = 0; i < 6; i++) {
            StrokePattern strokePattern = new StrokePattern(i+1);
            strokePattern.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    strokePatterns.get(model.getThickness()-1).unSelect();
                    strokePattern.onSelect();
                    model.setThickness(strokePatterns.indexOf(strokePattern)+1);
                }
            });
            strokePatterns.add(strokePattern);
            this.add(strokePattern, c);
            c.gridy++;
        }

    }

    public void updateView() {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
//        System.out.println("ColorPalette: Model changed!");
    }
}
