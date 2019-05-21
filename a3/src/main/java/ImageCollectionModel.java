
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

public class ImageCollectionModel {

    private ArrayList<ImageModel> images = new ArrayList<ImageModel>();
    private ArrayList<IView> views = new ArrayList<IView>();
    private int rating;
    private boolean gridLayout;
    private int gridCol = 2;


    ImageCollectionModel() {
        rating = 0;
        gridLayout = true;
    }

    public void addView(IView view) {
        views.add(view);
    }

    public void addImage(ImageModel imageModel) {
        this.images.add(imageModel);
        notifyViews();

    }

    public ArrayList<ImageModel> getImages() {
        if (rating == 0) {
            return images;
        } else {
            ArrayList<ImageModel> filteredImages = new ArrayList<ImageModel>();
            for (ImageModel im : images) {
                if (im.getRateValue() >= rating) {
                    filteredImages.add(im);
                }
            }
            return filteredImages;
        }
    }

    public void setImages(ArrayList<ImageModel> images) {
        this.images = images;
        notifyViews();
    }

    public boolean isGridLayout() {
        return gridLayout;
    }

    public void setGridLayout(boolean gridLayout) {
        this.gridLayout = gridLayout;
        notifyViews();
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
        notifyViews();
    }

    public void setGridCol(int gridCol) {
        this.gridCol = gridCol;
        notifyViews();
    }

    public int getGridCol() {
        return gridCol;
    }

    public void save() {
        try {
            File save = new File("save");
            if (!save.exists()) {
                save.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(save);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();


        } catch (Exception e) {

        }
    }

    public void load() {
        try {
            File saved = new File("save");
            FileInputStream fis = new FileInputStream(saved);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ImageCollectionModel ICmodel = (ImageCollectionModel) ois.readObject();
            ois.close();
            fis.close();
            this.setImages(ICmodel.getImages());
            for (ImageModel im : this.images) {
                ImageView iv = new ImageView(im);
                im.setIview(iv);
            }
        } catch (Exception e) {

        }
    }


    public void notifyViews() {
        for (IView view : this.views) {
            view.updateView();
        }
    }

}
