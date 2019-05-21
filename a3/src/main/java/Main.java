import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Fotag");

        ImageCollectionModel ICmodel = new ImageCollectionModel();



        Toolbar toolbar = new Toolbar(ICmodel);
        ICmodel.addView(toolbar);

        ImageCollectionView ICview = new ImageCollectionView(ICmodel);
        ICmodel.addView(ICview);
        JScrollPane scrollPane = new JScrollPane(ICview);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

        JPanel panel = new JPanel(new BorderLayout());
        frame.getContentPane().add(panel);
        panel.add(toolbar, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.setTitle("Fotag");
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                int width = scrollPane.getViewport().getWidth();
                int col = width / 350;
                ICmodel.setGridCol(col);
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ICmodel.save();
                frame.dispose();

            }
        });


        ICmodel.load();
    }
}
