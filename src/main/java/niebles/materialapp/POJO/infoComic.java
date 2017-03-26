package niebles.materialapp.POJO;

import java.util.ArrayList;

/**
 * Created by user on 20/03/2017.
 */
public class infoComic {
    String title="";
    String id="";
    String description;
    String pageCount;
    //String series;
    ArrayList<pricesComic> prices;
    ArrayList<ImageComic> images;


    public infoComic(ArrayList<pricesComic> prices, ArrayList<ImageComic> images, String title,
                    String id,String description,String pagesCount ) {
        this.prices = prices;
        this.title = title;
        this.images=images;
        this.id=id;
        this.description=description;
        this.pageCount =pagesCount;

    }

   /* public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }*/

    public String getDescription() {
        if (description==null){
            description="No description";
        }
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

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getImages() {
        String path="";
        if(images.size()!=0) {
            path = images.get(0).getPath();
            path=path+"/portrait_xlarge.jpg";
        }
        return path;
    }

    public void setImages(ArrayList<ImageComic> images) {
        this.images = images;
    }

    public String getPrices() {
        String price=prices.get(0).getPrice();
        return price;
    }

    public void setPrices(ArrayList<pricesComic> prices) {
        this.prices = prices;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
