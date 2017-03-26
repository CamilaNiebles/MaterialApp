package niebles.materialapp.POJO;

/**
 * Created by user on 20/03/2017.
 */
public class pricesComic {
    String type="";
    String price="";

    public pricesComic(String price, String type) {
        this.price = price;
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
