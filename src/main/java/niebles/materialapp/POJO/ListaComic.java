package niebles.materialapp.POJO;

/**
 * Created by user on 20/03/2017.
 */
public class ListaComic {
    String name;
    String price;
    String path;
    String id;
    String description;
    String pages;

    public ListaComic(String name, String price,String thumbnail,
                      String id, String description, String pages) {
        this.name = name;
        this.price = price;
        this.path =thumbnail;
        this.id=id;
        this.description=description;
        this.pages=pages;
    }

    public ListaComic(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
