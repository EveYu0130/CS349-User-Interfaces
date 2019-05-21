import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaletteView extends JPanel {

    private ColorPalette colorPalette;
    private StrokePalette strokePalette;

    public PaletteView(Model model) {

        this.setLayout(new GridBagLayout());
        this.setMinimumSize(new Dimension(80, 400));
        GridBagConstraints c = new GridBagConstraints();

        colorPalette = new ColorPalette(model);
        strokePalette = new StrokePalette(model);
        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weighty = 0.5;
        this.add(colorPalette, c);
        c.weighty = 1;
        this.add(strokePalette, c);

    }
}
