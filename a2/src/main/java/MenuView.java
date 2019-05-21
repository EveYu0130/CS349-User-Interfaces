import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuView extends JMenuBar implements IView {
    private JButton button;

    private Model model;

    /**
     * Create a new View.
     */
    public MenuView(Model model) {
        this.model = model;

        JMenu file = new JMenu("File");

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("save");
            }
        });
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));

        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("load");
            }
        });
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));

        JMenuItem newf = new JMenuItem("New File");
        newf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("newf");
            }
        });
        newf.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("exit");
            }
        });
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));

        file.add(save);
        file.add(load);
        file.add(newf);
        file.add(exit);

        this.add(file);
    }

    /**
     * Update with data from the model.
     */
    public void updateView() {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
//        System.out.println("MenuView: Model changed!");
    }

}
