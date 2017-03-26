package niebles.materialapp.frontEnd;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import niebles.materialapp.contentProvider.ContractComics;
import niebles.materialapp.interfaces.ComicInterface;
import niebles.materialapp.R;
import niebles.materialapp.backEnd.AdapterComics;
import niebles.materialapp.POJO.ListaComic;
import niebles.materialapp.backEnd.serviceMarvel;

public class Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener, ComicInterface, SearchView.OnQueryTextListener {
    List<ListaComic> list = null;
    List<ListaComic> list2 = null;
    List<ListaComic> listaFiltrada=null;
    RecyclerView recyclerView;
    Uri profilesUri= ContractComics.urisIdFb.Content_Uri;
    private FirebaseAuth mAuth;
    int offset=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();



        list=new ArrayList<>();
        ContentResolver contentResolver=getContentResolver();
        serviceMarvel comicRequest=new serviceMarvel();
        List<ListaComic> listaComic=comicRequest.comicService(this,list,String.valueOf(offset),contentResolver);
        esperar();
        Log.i("Activity","inicio");

        FloatingActionButton floatingActionButton=
                (FloatingActionButton)findViewById(R.id.refreshButton);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        AdapterComics adapterComics= new AdapterComics(listaComic,this,this);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterComics);

        adminNavigator();
        fbAdmin();

        floatingActionButton.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        offset+=30;
        threadNew(offset);
    }
    public void threadNew(final int num){
        new Thread(new Runnable() {
            @Override
            public void run() {
                serviceMarvel comicRequest=new serviceMarvel();
                list2=new ArrayList<>();
                ContentResolver contentResolver1=getContentResolver();
                //final List<ListaComic> listaComic2=comicRequest.comicService(Profile.this,list2,String.valueOf(num));
                final List<ListaComic> listaComic2=comicRequest.comicService(Profile.this,list2,String.valueOf(num),contentResolver1);
                esperar();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AdapterComics adapterComics2= new AdapterComics(listaComic2,Profile.this,Profile.this);
                        GridLayoutManager layoutManager=new GridLayoutManager(Profile.this,2);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapterComics2);
                    }
                });
            }
        }).start();

    }
    @Override
    protected void onStart() {
        super.onStart();
    }


    private void fbAdmin(){

        /*Verificacion de cuenta en fb*/
        if(AccessToken.getCurrentAccessToken()==null){
            goLoginScreen();
        }


    }
    private void adminNavigator(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Administrador de navigator drawer*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void esperar(){
        try{
            Thread.sleep(3000);//Pone el hilo en curso a dormir.
        }catch (InterruptedException e){}//Captura el error pero no hace nada
    }

    private void goLoginScreen(){
        Intent intent=new Intent(this,LoginActivity.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.myProfile) {
            // Handle the camera action
            Intent intent=new Intent(Profile.this,PersonalProfile.class);
            startActivity(intent);


        } else if (id == R.id.favorites) {
            Intent intent=new Intent(this, Favorites.class);
            startActivity(intent);

        }  else if (id == R.id.outSesion) {
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            Intent intent=new Intent(this,LoginActivity.class);
            finish();
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void getInfo(String title,String price,String path,
                        String id, String description, String pages){
        Boolean flag=false;
        final Intent intent=new Intent(Profile.this,ComicSelect.class);
        intent.putExtra("Title",title);
        intent.putExtra("Price",price);
        intent.putExtra("Path",path);
        intent.putExtra("ID",id);
        intent.putExtra("Description",description);
        intent.putExtra("Pages",pages);
        intent.putExtra("Flag",flag);

        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText=newText.toLowerCase();
        ArrayList<ListaComic> newList=new ArrayList<>();

        for (ListaComic listC : listaFiltrada){
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