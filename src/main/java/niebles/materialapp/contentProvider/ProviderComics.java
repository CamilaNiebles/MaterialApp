package niebles.materialapp.contentProvider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;


import niebles.materialapp.interfaces.DBManager;

/**
 * Created by user on 22/03/2017.
 */

public class ProviderComics extends ContentProvider {


    public static final UriMatcher uriMatcher;

    public static final int comics_favorites =101;
    public static final int info_profile=201;
    public static final int id_register=301;



    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ContractComics.autoridad,ContractComics.actividad,comics_favorites);
        uriMatcher.addURI(ContractComics.autoridad,ContractComics.actividad2,info_profile);
        uriMatcher.addURI(ContractComics.autoridad,ContractComics.actividad3,id_register);

    }

    private DBHelperComics dbHelperComics;
    ContentResolver contentResolver;


    @Override
    public boolean onCreate() {
        dbHelperComics=new DBHelperComics(getContext());
        contentResolver=getContext().getContentResolver();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase bd=dbHelperComics.getWritableDatabase();

        int match=uriMatcher.match(uri);
        Cursor cursor = null;
        switch (match){
            case comics_favorites:
                cursor=bd.query(DBManager.Tables.comicFavorites,projection,selection,
                        selectionArgs,null,null,sortOrder);
                cursor.setNotificationUri(contentResolver,ContractComics.urisComics.Content_Uri);
                break;
            case info_profile:
                cursor=bd.query(DBManager.Tables.infoProfile,projection,selection,
                        selectionArgs,null,null,sortOrder);
                cursor.setNotificationUri(contentResolver,ContractComics.urisProfile.Content_Uri);
                break;
            case id_register:
                cursor=bd.query(DBManager.Tables.idFB,projection,selection,
                        selectionArgs,null,null,sortOrder);
                cursor.setNotificationUri(contentResolver,ContractComics.urisIdFb.Content_Uri);

        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case comics_favorites:
                return ContractComics.urisComics.multiple_MIME;
            case info_profile:
                return ContractComics.urisProfile.multiple_MIME;
            case id_register:
                return ContractComics.urisIdFb.multiple_MIME;
            default:
                break;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase bd=dbHelperComics.getWritableDatabase();

        long registro;
        Uri nuevaUri = null;
        Cursor cursor = null;
        if(uriMatcher.match(uri)==comics_favorites){
            String idIngresado=new String(values.get(ContractComics.urisComics.idColum).toString());
             cursor=bd.rawQuery("SELECT * FROM ComicsFavorites WHERE IDcomic=?", new String[]{idIngresado});
            if(cursor.moveToFirst()){
                Toast.makeText(getContext(),"Comic already added",Toast.LENGTH_SHORT).show();
                do{
                }while (cursor.moveToNext());
            }else {
                registro=bd.insert(DBManager.Tables.comicFavorites,null,values);
                nuevaUri= ContentUris.withAppendedId(ContractComics.urisComics.Content_Uri,registro);
                Toast.makeText(getContext(),"Comic added to favorites",Toast.LENGTH_SHORT).show();
            }
        }else if(uriMatcher.match(uri)==info_profile){

            String idIngresado=new String(values.get(ContractComics.urisProfile.IdFB).toString());
            cursor=bd.rawQuery("SELECT * FROM Profile WHERE idFB=?", new String[]{idIngresado});
            Log.i("idFB",idIngresado);
            if(cursor.moveToFirst()){
                do{
                    Log.i("Cursor",String.valueOf(cursor.getPosition()));
                    Toast.makeText(getContext(),"Welcome, you have a profile",Toast.LENGTH_SHORT).show();
                }while (cursor.moveToNext());
            }else {
                Log.i("CursorInicial",String.valueOf(cursor.getPosition()));
                Toast.makeText(getContext(),"Go to create your profile",Toast.LENGTH_SHORT).show();
                registro=bd.insert(DBManager.Tables.infoProfile,null,values);
                nuevaUri= ContentUris.withAppendedId(ContractComics.urisProfile.Content_Uri,registro);
            }

        }else if(uriMatcher.match(uri)==id_register){

            String idIngresado=new String(values.get(ContractComics.urisIdFb.idFb).toString());
            cursor=bd.rawQuery("SELECT * FROM idFb WHERE idFB=?", new String[]{idIngresado});
            if(cursor.moveToFirst()){
                do{
                }while (cursor.moveToNext());
            }else {
                registro=bd.insert(DBManager.Tables.idFB,null,values);
                nuevaUri= ContentUris.withAppendedId(ContractComics.urisIdFb.Content_Uri,registro);
            }

        }


        return nuevaUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
