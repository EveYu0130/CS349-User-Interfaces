import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Doodle");

        Model model = new Model();

        MenuView menuView = new MenuView(model);
        PaletteView paletteView = new PaletteView(model);
        PlaybackView playbackView = new PlaybackView(model);
        DrawPanelView drawPanelView = new DrawPanelView(model);

        model.addView(menuView);
        model.addView(playbackView);
        model.addView(drawPanelView);


        JPanel panel = new JPanel(new BorderLayout());
        frame.getContentPane().add(panel);

        panel.add(playbackView, BorderLayout.SOUTH);
        panel.add(paletteView, BorderLayout.WEST);
        panel.add(drawPanelView, BorderLayout.CENTER);

        // Set up the window.
        frame.setTitle("Sketch");
        frame.setMinimumSize(new Dimension(128, 400));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        frame.setJMenuBar(menuView);
        frame.revalidate();

    }
}
