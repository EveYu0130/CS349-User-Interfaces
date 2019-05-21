import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ImageCollectionView extends JPanel implements IView{

    private ImageCollectionModel ICmodel;

    ImageCollectionView(ImageCollectionModel ICmodel) {

        this.ICmodel = ICmodel;
        this.setLayout(new GridLayout());

    }

    public void updateView() {
        ArrayList<ImageModel> images = ICmodel.getImages();
        this.removeAll();
        if (ICmodel.isGridLayout()) {
            setLayout(new GridLayout(0,ICmodel.getGridCol()));
        } else {
            setLayout(new GridLayout(0,1));
        }
        for (ImageModel im : images) {
            im.setGridLayout(ICmodel.isGridLayout());
            ImageView iv = new ImageView(im);
            this.add(iv);

        }
        revalidate();
        repaint();
    }

}
