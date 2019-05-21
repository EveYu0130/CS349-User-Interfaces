import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlaybackView extends JPanel implements IView {

    private Model model;

    private JButton playPauseBtn = new JButton();

    private JButton startBtn = new JButton();

    private JButton endBtn = new JButton();

    private JSlider playbackBar = new JSlider(JSlider.HORIZONTAL, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);

    public class Play extends Thread {
        public void run() {
            while (model.getElapsedTime() < model.getTotalTime()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                model.setElapsedTime(model.getElapsedTime() + 100);
                model.notifyObservers();
            }
            if (model.getElapsedTime() < 0) {
                model.setElapsedTime(0);
            }
            else if (model.getElapsedTime() > model.getTotalTime()) {
                model.setElapsedTime(model.getTotalTime());
            }
            model.notifyObservers();
        }
    }

    public PlaybackView(Model model) {
        this.model = model;

        this.setBackground(Color.DARK_GRAY);
        // use BoxLayout
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));


        playPauseBtn.setPreferredSize(new Dimension(32, 32));
        ImageIcon playPauseIcon = new ImageIcon("src/icons/play_pause_button.png");
        playPauseIcon.setImage(playPauseIcon.getImage().getScaledInstance(16, 16,Image.SCALE_DEFAULT ));
        playPauseBtn.setIcon(playPauseIcon);
        playPauseBtn.setBackground(Color.LIGHT_GRAY);
        playPauseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Play playback = new Play();
                playback.run();
            }
        });
        this.add(playPauseBtn);

        playbackBar.setPaintTicks(true);
        playbackBar.setBackground(Color.WHITE);
        playbackBar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = playbackBar.getValue();
                model.setElapsedTime(value);
            }
        });
        this.add(playbackBar);

        startBtn.setPreferredSize(new Dimension(32, 32));
        ImageIcon startIcon = new ImageIcon("./src/icons/start_button.png");
        startIcon.setImage(startIcon.getImage().getScaledInstance(16, 16,Image.SCALE_DEFAULT ));
        startBtn.setIcon(startIcon);
        startBtn.setBackground(Color.LIGHT_GRAY);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setElapsedTime(0);
            }
        });
        this.add(startBtn);


        endBtn.setPreferredSize(new Dimension(32, 32));
        ImageIcon endIcon = new ImageIcon("src/icons/end_button.png");
        endIcon.setImage(endIcon.getImage().getScaledInstance(16, 16,Image.SCALE_DEFAULT ));
        endBtn.setIcon(endIcon);
        endBtn.setBackground(Color.LIGHT_GRAY);
        endBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setElapsedTime(model.getTotalTime());
            }
        });
        this.add(endBtn);
    }

    public void updateView() {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        int ticks = model.getTicks();
        if (ticks > 0) {
            playbackBar.setMajorTickSpacing(ticks);
            playbackBar.setValue(model.getTickValue());
        }
    }

}

