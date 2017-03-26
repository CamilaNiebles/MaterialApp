package niebles.materialapp.frontEnd;

import android.content.ContentResolver;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import niebles.materialapp.POJO.ListaComic;
import niebles.materialapp.R;
import niebles.materialapp.backEnd.AdapterComics;
import niebles.materialapp.backEnd.serviceMarvel;
import niebles.materialapp.interfaces.ComicInterface;

public class SearchComics extends AppCompatActivity implements ComicInterface,SearchView.OnQueryTextListener{
    Toolbar toolbar;
    List<ListaComic> list = null;
    List<ListaComic> listaComic2=null;
    RecyclerView recyclerView;
    int offset=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_comics);


        toolbar=(Toolbar)findViewById(R.id.barSearch);
        recyclerView=(RecyclerView)findViewById(R.id.rvSearch);
        setSupportActionBar(toolbar);
        threadNew(offset);
        esperar();





    }
    public void threadNew(final int num){
        new Thread(new Runnable() {
            @Override
            public void run() {
                serviceMarvel comicRequest=new serviceMarvel();
                list=new ArrayList<>();
                ContentResolver contentResolver1=getContentResolver();
                //final List<ListaComic> listaComic2=comicRequest.comicService(Profile.this,list2,String.valueOf(num));
                final List<ListaComic> listaComic2=comicRequest.comicService(SearchComics.this,list,String.valueOf(num),contentResolver1);
                esperar();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AdapterComics adapterComics2= new AdapterComics(listaComic2,SearchComics.this,SearchComics.this);
                        GridLayoutManager layoutManager=new GridLayoutManager(SearchComics.this,2);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapterComics2);
                    }
                });
            }
        }).start();

    }
    private void esperar(){
        try{
            Thread.sleep(3000);//Pone el hilo en curso a dormir.
        }catch (InterruptedException e){}//Captura el error pero no hace nada
    }

    @Override
    public void getInfo(String title, String price, String path, String id, String description, String pages) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_comic,menu);

        MenuItem menuItem=menu.findItem(R.id.searchcomicIcon);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);


        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<ListaComic> newList=new ArrayList<>();


        for (ListaComic listC : listaComic2){
            String title=listC.getName().toLowerCase();
            String price=listC.getPrice().toLowerCase();
            String path=listC.getPath().toLowerCase();
            String id=listC.getId().toLowerCase();
            String desc=listC.getDescription().toLowerCase();
            String pag=listC.getPages().toLowerCase();
            if(title.contains(newText)){
                ListaComic listaComic=new ListaComic(title,price,path,id,desc,pag);
                newList.add(listaComic);
            }
        }
        AdapterComics adapterComics=new AdapterComics(newList,this,this);
        adapterComics.setFilter(newList);
        return true;
    }
}
