package niebles.materialapp.POJO;

/**
 * Created by user on 22/03/2017.
 */
public class ImageComic {
    String path;
    String extension;

    public ImageComic(String extension, String path) {
        this.extension = extension;
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
