package niebles.materialapp.contentProvider;

import android.app.FragmentManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import niebles.materialapp.interfaces.DBManager;

/**
 * Created by user on 22/03/2017.
 */
public class DBHelperComics extends SQLiteOpenHelper{

    static final String database_Name="Ensayo.db";
    static final int database_Version=6;

    public DBHelperComics(Context context) {
        super(context, database_Name,null,database_Version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ DBManager.Tables.comicFavorites+" ("
                    + BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +ContractComics.urisComics.titleColum+" TEXT,"
                    +ContractComics.urisComics.priceColum+" TEXT,"
                    +ContractComics.urisComics.pathColum+" TEXT,"
                    +ContractComics.urisComics.idColum+" TEXT,"
                    +ContractComics.urisComics.descriptionColum+" TEXT,"
                    +ContractComics.urisComics.pagesColum+" TEXT)");
        db.execSQL("CREATE TABLE "+DBManager.Tables.infoProfile+" ("
                    +BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +ContractComics.urisProfile.nameProfile+" TEXT,"
                    +ContractComics.urisProfile.apellido+" TEXT,"
                    +ContractComics.urisProfile.mail+" TEXT,"
                    +ContractComics.urisProfile.fecha+" TEXT,"
                    +ContractComics.urisProfile.IdFB+" TEXT)");
        db.execSQL("CREATE TABLE "+DBManager.Tables.idFB+" ("
                    + BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +ContractComics.urisIdFb.idFb+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DBManager.Tables.comicFavorites);
        db.execSQL("DROP TABLE IF EXISTS "+DBManager.Tables.infoProfile);
        db.execSQL("DROP TABLE IF EXISTS "+DBManager.Tables.idFB);
        onCreate(db);
    }
}
