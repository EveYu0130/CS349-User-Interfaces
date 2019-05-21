import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageModel {

    private ImageView Iview;
    private IRating Irating;
    private String path;
    private String date;
    private String filename;
    private int rateValue;
    private boolean gridLayout;

    ImageModel(String path) {

        this.path = path;
        File file = new File(path);
        this.filename = file.getName();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        date = df.format(file.lastModified());
        this.rateValue = 0;

    }

    public void setIview(ImageView iview) {
        this.Iview = iview;
        Iview.updateView();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
        this.Irating.updateView();

    }

    public int getRateValue() {
        return rateValue;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean isGridLayout() {
        return gridLayout;
    }

    public void setGridLayout(boolean gridLayout) {
        this.gridLayout = gridLayout;
    }

    public void setIrating(IRating irating) {
        Irating = irating;
    }
}
