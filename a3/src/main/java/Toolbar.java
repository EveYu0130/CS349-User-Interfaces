import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Toolbar extends JPanel implements IView {

    private ImageCollectionModel ICmodel;

    private JPanel layoutPanel;
    private JLabel title;
    private JPanel filterPanel;

    private JButton gridBtn;
    private JButton listBtn;
    private JButton loadBtn;
    private JLabel filterText;
    private ICRating rating;
    private JButton clearBtn;

    Toolbar(ImageCollectionModel ICmodel) {
        this.ICmodel = ICmodel;

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Color.WHITE);


        layoutPanel = new JPanel();
        layoutPanel.setLayout(new FlowLayout());
        layoutPanel.setBackground(Color.WHITE);

        gridBtn = new JButton();
        gridBtn.setBackground(Color.WHITE);
        gridBtn.setSelected(true);
        ImageIcon gridIcon = new ImageIcon("./src/icons/grid_icon.png");
        gridIcon.setImage(gridIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        gridBtn.setIcon(gridIcon);
        gridBtn.setSelected(true);

        listBtn = new JButton();
        listBtn.setBackground(Color.WHITE);
        ImageIcon listIcon = new ImageIcon("./src/icons/list_icon.png");
        listIcon.setImage(listIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        listBtn.setIcon(listIcon);


        gridBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridBtn.setSelected(true);
                listBtn.setSelected(false);
                ICmodel.setGridLayout(true);
            }
        });


        listBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridBtn.setSelected(false);
                listBtn.setSelected(true);
                ICmodel.setGridLayout(false);
            }
        });


        layoutPanel.add(gridBtn);
        layoutPanel.add(listBtn);

        this.add(layoutPanel, BorderLayout.WEST);

        title = new JLabel();
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
        title.setText("Fotag!");

        this.add(title, BorderLayout.CENTER);


        filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());
        filterPanel.setBackground(Color.WHITE);

        loadBtn = new JButton();
        loadBtn.setBackground(Color.WHITE);
        loadBtn.setSize(new Dimension(50, 50));
        ImageIcon loadIcon = new ImageIcon("./src/icons/load_file_icon.png");
        loadIcon.setImage(loadIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        loadBtn.setIcon(loadIcon);
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int returnVal = fileChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File[] files = fileChooser.getSelectedFiles();
                    for (File file:files) {
                        BufferedImage bImage = null;
                        try {
                            bImage = ImageIO.read(file);

                        } catch (Exception ex) {

                        }
                        if (bImage != null) {
                            ImageModel im = new ImageModel(file.getAbsolutePath());
                            ImageView iv = new ImageView(im);
                            im.setIview(iv);
                            ICmodel.addImage(im);
                        }
                    }
                }
            }
        });
        filterPanel.add(loadBtn);

        filterText = new JLabel();
        filterText.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        filterText.setText("Filter by: ");
        filterPanel.add(filterText);

        rating = new ICRating(ICmodel);
        filterPanel.add(rating);

        clearBtn = new JButton("clear");
        clearBtn.setBackground(Color.WHITE);
        clearBtn.setSize(new Dimension(50, 50));
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rating.clear();
            }
        });
        filterPanel.add(clearBtn);

        this.add(filterPanel, BorderLayout.EAST);



    }

    /**
     * Update with data from the model.
     */
    public void updateView() {

    }
}
