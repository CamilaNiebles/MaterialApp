package niebles.materialapp.frontEnd;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import niebles.materialapp.R;
import niebles.materialapp.backEnd.serviceMarvel;
import niebles.materialapp.contentProvider.ContractComics;

public class ComicSelect extends AppCompatActivity {
    ImageView imageSelect;
    TextView TextDescription,TextPages,TextTitle,
            TextPrice,TextCharacters;
    Button favorites;
    Uri comicsUri=ContractComics.urisComics.Content_Uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_select);
        imageSelect=(ImageView)findViewById(R.id.imageSelect);
        TextDescription=(TextView)findViewById(R.id.descriptionPut);
        TextCharacters=(TextView)findViewById(R.id.charactersPut);
        TextTitle=(TextView)findViewById(R.id.selectTitle);
        TextPrice=(TextView)findViewById(R.id.selectPrice);
        TextPages=(TextView)findViewById(R.id.selectPages);
        favorites=(Button)findViewById(R.id.favoritesButton);

        getComicInfo();
        tabInit();

    }
    public void getComicInfo(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String title=bundle.getString("Title");
        final String price=bundle.getString("Price");
        final String path=bundle.getString("Path");
        final String id=bundle.getString("ID");
        final String description=bundle.getString("Description");
        final String pages=bundle.getString("Pages");
        Boolean flag=bundle.getBoolean("Flag");

        TextDescription.setText(description);
        TextTitle.setText(title);
        TextPrice.setText(price);
        TextPages.setText(pages);
        Glide.with(this).load(path).into(imageSelect);
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                ContentResolver contentResolver=getContentResolver();
                contentValues.put(ContractComics.urisComics.titleColum,title);
                contentValues.put(ContractComics.urisComics.priceColum,price);
                contentValues.put(ContractComics.urisComics.pathColum,path);
                contentValues.put(ContractComics.urisComics.idColum,id);
                contentValues.put(ContractComics.urisComics.descriptionColum,description);
                contentValues.put(ContractComics.urisComics.pagesColum,pages);

                Uri nuevo=contentResolver.insert(comicsUri,contentValues);
            }
        });

        if(flag){
            favorites.setVisibility(View.GONE);
        }
        serviceMarvel sm=new serviceMarvel();
        String url= sm.getUrlCharacters(id);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsCh=new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonResults = null;
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            jsonResults = jsonObject.getJSONArray("results");
                            String[] ch=new String[jsonResults.length()];
                            String name;
                            for (int j = 0; j<jsonResults.length(); j++) {
                                JSONObject js = (JSONObject) jsonResults.get(j);
                                ch[j] = js.getString("name").toString();
                            }if(ch.length !=0){
                                for (int m=1;m<ch.length;m++){
                                    ch[m] = ch[m-1] + "\n" + ch[m];
                                }
                                TextCharacters.setText(ch[ch.length-1]);
                            }else{
                                TextCharacters.setText("No characters available");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsCh);

    }
    public void tabInit(){
        TabHost tabHost=(TabHost)findViewById(R.id.tabComics);
        tabHost.setup();
        TabHost.TabSpec tabDes=tabHost.newTabSpec("Tab1");
        tabDes.setIndicator("Description");
        tabDes.setContent(R.id.tabDescription);
        tabHost.addTab(tabDes);

        /*Segundo tab*/
        TabHost.TabSpec tabCharacters=tabHost.newTabSpec("Tab2");
        tabCharacters.setIndicator("Characters");
        tabCharacters.setContent(R.id.tabCharacters);
        tabHost.addTab(tabCharacters);
    }
}
