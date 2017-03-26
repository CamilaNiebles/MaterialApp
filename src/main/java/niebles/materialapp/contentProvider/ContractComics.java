package niebles.materialapp.contentProvider;

import android.net.Uri;

import niebles.materialapp.interfaces.DBManager;

/**
 * Created by user on 22/03/2017.
 */
public class ContractComics {
    //Definicion de las direcciones URI
    public final static String autoridad="niebles.materialapp.contentProvider.ContentProviderComics";
    /*Direccion*/
    public final static String actividad="Comics";
    public final static String actividad2="profile";
    public final static String actividad3="IdFb";

    private final static Uri base=Uri.parse("content://"+autoridad);

    //Declarar una interfaz con la cual sea posible comunicar entre clases


    /*Generar el controlador de los datos*/
    public static class urisComics implements DBManager.ComicsFavorites {

        /*Construccion de la URI*/
        public static final Uri Content_Uri=base.buildUpon().appendPath(actividad).build();
        //Definición de los tipos MIME
        public static final String single_MIME=
                "vnd.android.cursor.item/vnd."+autoridad+"/"+actividad;
        public static final String multiple_MIME=
                "vnd.android.cursor.dir/vnd."+autoridad+"/"+actividad;
    }
    /*Generar el controlador de los datos*/
    public static class urisProfile implements DBManager.profile{

        /*Construccion de la URI*/
        public static final Uri Content_Uri=base.buildUpon().appendPath(actividad2).build();
        //Definición de los tipos MIME
        public static final String single_MIME=
                "vnd.android.cursor.item/vnd."+autoridad+"/"+actividad2;
        public static final String multiple_MIME=
                "vnd.android.cursor.dir/vnd."+autoridad+"/"+actividad2;
    }
    public static class urisIdFb implements DBManager.idFb{

        /*Construccion de la URI*/
        public static final Uri Content_Uri=base.buildUpon().appendPath(actividad3).build();
        //Definición de los tipos MIME
        public static final String single_MIME=
                "vnd.android.cursor.item/vnd."+autoridad+"/"+actividad3;
        public static final String multiple_MIME=
                "vnd.android.cursor.dir/vnd."+autoridad+"/"+actividad3;

    }




}
