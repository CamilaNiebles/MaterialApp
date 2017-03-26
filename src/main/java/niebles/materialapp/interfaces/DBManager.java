package niebles.materialapp.interfaces;

/**
 * Created by user on 22/03/2017.
 */
public class DBManager {
    public interface ComicsFavorites{
        String titleColum="Title";
        String priceColum="Price";
        String pathColum="Path";
        String idColum="IDcomic";
        String descriptionColum="Description";
        String pagesColum="Pages";
    }
    public interface profile{
        String nameProfile="Nombre";
        String apellido="Apellido";
        String mail="Correo";
        String fecha="Fecha";
        String IdFB="idFB";

    }
    public interface idFb{
        String idFb="idFb";
    }
    public interface Tables{
        String comicFavorites="ComicsFavorites";
        String infoProfile="Profile";
        String idFB="idFb";
    }
}
