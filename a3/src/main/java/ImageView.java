import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageView extends JPanel implements IView {

    private ImageModel Imodel;

    ImageView(ImageModel Imodel) {
        this.Imodel = Imodel;
        setLayout();
    }

    private void setLayout() {
        this.setSize(new Dimension(250, 250));

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        File file = new File(Imodel.getPath());
        BufferedImage bImage = null;
        try {
            bImage = ImageIO.read(file);

        } catch (Exception ex) {

        }
        JButton imageBtn = new JButton();
        ImageIcon imageIcon = new ImageIcon(bImage);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH));
        imageBtn.setIcon(imageIcon);
        imageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame iFrame = new JFrame();

                JLabel imageLabel = new JLabel();
                try {
                    ImageIcon iIcon = new ImageIcon(ImageIO.read(file));
                    iIcon.setImage(iIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH));
                    imageLabel.setIcon(iIcon);
                    iFrame.getContentPane().add(imageLabel);
                } catch (Exception ex) {

                }

                iFrame.setTitle(Imodel.getFilename());
                iFrame.setSize(400, 300);
                iFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                iFrame.setVisible(true);
            }
        });

        JPanel metadata = new JPanel();
        metadata.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        JLabel filenameLabel = new JLabel();
        filenameLabel.setText(Imodel.getFilename());

        JLabel dateLabel = new JLabel();
        dateLabel.setText(Imodel.getDate());

        IRating fileRating = new IRating(Imodel);

        JButton clearBtn = new JButton("clear");
        clearBtn.setBackground(Color.WHITE);
        clearBtn.setSize(new Dimension(50, 50));
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileRating.clear();
            }
        });

        gc.fill = GridBagConstraints.VERTICAL;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weighty = 1;
        metadata.add(filenameLabel, gc);
        metadata.add(dateLabel, gc);
        metadata.add(fileRating, gc);
        metadata.add(clearBtn,gc);

        if (Imodel.isGridLayout()) {
            c.fill = GridBagConstraints.VERTICAL;
            c.gridwidth = GridBagConstraints.REMAINDER;
            c.gridheight = 3;
            this.add(imageBtn, c);
            this.add(metadata, c);
        } else {

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 3;
            c.gridheight = GridBagConstraints.REMAINDER;
            this.add(imageBtn, c);


            this.add(metadata, c);

        }
    }

    /**
     * Update with data from the model.
     */
    public void updateView() {
    }
}
