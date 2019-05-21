import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;

public class ColorPalette extends JPanel implements IView {

    private Model model;

    private ArrayList<Color> colors = new ArrayList<Color>(
            Arrays.asList(
                    Color.RED,
                    Color.MAGENTA,
                    Color.PINK,
                    Color.BLUE,
                    Color.CYAN,
                    Color.GREEN,
                    Color.YELLOW,
                    Color.ORANGE,
                    Color.BLACK,
                    Color.DARK_GRAY,
                    Color.GRAY,
                    Color.LIGHT_GRAY)
    );

    /**
     * Create a new View.
     */
    public ColorPalette(Model model) {
        this.model = model;

        this.setMinimumSize(new Dimension(80, 300));
        this.setPreferredSize(new Dimension(80, 300));
        this.setBackground(Color.LIGHT_GRAY);

        ColorPanel colorPanel = new ColorPanel(Color.WHITE);
        Color selectedColor = colorPanel.getColor();

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        this.setLayout(gridbag);
        c.fill = GridBagConstraints.BOTH;


        c.insets = new Insets(4, 4, 4, 4);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;



        for (int i = 0 ; i < colors.size(); i++) {

            ColorButton colorButton = new ColorButton(colors.get(i));

            this.add(colorButton, c);
            colorButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Color color = colorButton.getColor();
                    colorPanel.setColor(color);
                    model.setSelectedColor(color);
                }
            });
            if (i % 2 == 0) {
                c.gridx = 1;
            } else {
                c.gridx = 0;
                c.gridy++;
            }
        }

        c.gridwidth = 2;
        c.gridheight = 2;

        JButton colorChooser = new JButton("More");
        colorChooser.setBackground(Color.WHITE);
//        colorChooser.setOpaque(true);
//        colorChooser.setBorderPainted(false);
        colorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Choose a Colour", selectedColor);
                colorPanel.setColor(c);
                model.setSelectedColor(c);
            }
        });
        colorChooser.setPreferredSize(new Dimension(40, 20));
        this.add(colorChooser, c);

        c.gridy+= 2;

        this.add(colorPanel, c);

    }

    public void updateView() {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
//        System.out.println("ColorPalette: Model changed!");
    }
}
