package niebles.materialapp.frontEnd;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import niebles.materialapp.R;
import niebles.materialapp.backEnd.AdapterFavorites;
import niebles.materialapp.contentProvider.ContractComics;
import niebles.materialapp.interfaces.ComicInterface;

public class Favorites extends AppCompatActivity implements ComicInterface {
    RecyclerView RVFav;
    Uri comicsUri= ContractComics.urisComics.Content_Uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        RVFav=(RecyclerView)findViewById(R.id.rwFav);

        ContentResolver contentResolver=getContentResolver();
        ContentResolver contentResolver1=getContentResolver();

        Cursor cursor=contentResolver.query(comicsUri,null,null,null,null);
        AdapterFavorites adapterFavorites=new AdapterFavorites(this,cursor,contentResolver1,this);
        RVFav.setAdapter(adapterFavorites);
    }

    @Override
    public void getInfo(String title, String price, String path, String id, String description, String pages) {

        Boolean flag=true;
        Intent intent=new Intent(this,ComicSelect.class);
        intent.putExtra("Title",title);
        intent.putExtra("Price",price);
        intent.putExtra("Path",path);
        intent.putExtra("ID",id);
        intent.putExtra("Description",description);
        intent.putExtra("Pages",pages);
        intent.putExtra("Flag",flag);
        startActivity(intent);

    }
}
