package niebles.materialapp.backEnd;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import niebles.materialapp.POJO.InfoCharacter;
import niebles.materialapp.POJO.ListaComic;
import niebles.materialapp.POJO.infoComic;
import niebles.materialapp.contentProvider.ContractComics;
import niebles.materialapp.frontEnd.Profile;

/**
 * Clase por medio de la cual se realizan todas las consultas a la API
 */
public class serviceMarvel {

    Uri comicsUri=ContractComics.urisComics.Content_Uri;

    public final static String keyPublic="2359abf629c50fbfd70cf59e778dabed";
    public final static String keyPrivate="aa879f60b89257f0d4711886a461c65f06ad349d";
    /**
     * Metodo para realizar consulta a la API
     */
    public List comicService(Context context, final List ComicList, String offset, final ContentResolver contentResolver){
        String URL= getUrlComic(offset);
        final Gson gson=new Gson();
        final String[] titles=new String [30];
        final String[] pricesList=new String [30];
        final String[] pathList=new String [30];
        final String[] idList=new String [30];
        final String[] pageCountList=new String [30];
        final String[] descList=new String [30];

        RequestQueue getComics= Volley.newRequestQueue(context);
        /*Se realiza la solicitud por medio de la libreria Volley*/

        JsonObjectRequest jsonComics=new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response",response.toString());
                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray jsonResults = jsonObject.getJSONArray("results");

                    /*Parsear la informacion segun las caracteristicas del JSON*/
                    infoComic[] infoComic = gson.fromJson(jsonResults.toString(), infoComic[].class);
                    /*Lista que contiene la informacion de consulta*/
                    List<infoComic> comics = Arrays.asList(infoComic);
                    ListaComic listaComic = null;

                    if (comics.size() != 0) {
                        for (int i = 0; i < 30; i++) {
                            titles[i] = comics.get(i).getTitle();
                            pricesList[i] = comics.get(i).getPrices();
                            pathList[i]=comics.get(i).getImages();
                            idList[i]=comics.get(i).getId();
                            descList[i]=comics.get(i).getDescription();
                            pageCountList[i]=comics.get(i).getPageCount();
                            pricesList[i]="$ "+pricesList[i];
                            if(pricesList[i].equals("$ 0")){
                                pricesList[i]="Out of stock";
                            }
                            if(comics.get(i).getImages()==null){
                                listaComic=new ListaComic(titles[i],pricesList[i]);
                            }else {
                                listaComic=new ListaComic(titles[i],pricesList[i],pathList[i],idList[i],
                                                            descList[i],pageCountList[i]);
                            }ComicList.add(listaComic);
                        }

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

        /*Se añade la solicitud a la cola de peticiones*/
        getComics.add(jsonComics);
        Log.i("Consulta","finalizada");

        return ComicList;
    }
    public List comicCharacters(String idComic, final Context context, final List nameList){
        String url=getUrlCharacters(idComic);
        final Gson gson=new Gson();
        final List<String> name = new ArrayList<>();

        RequestQueue getCharacters=Volley.newRequestQueue(context);
        JsonObjectRequest jsonComics=new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray jsonResults = jsonObject.getJSONArray("results");

                    String nameCh = null;
                    for (int j = 0; j<jsonResults.length(); j++) {
                        JSONObject js = (JSONObject) jsonResults.get(j);
                        nameCh = js.getString("name").toString();
                        Log.i("Nombre", nameCh);
                        nameList.add(nameCh);
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
        getCharacters.add(jsonComics);
        return nameList;

    }
   /* public void ensayo(Context context,List listaCh){
        Intent intent=new Intent(context, Profile.class);
        intent.putCharSequenceArrayListExtra("Lista", (ArrayList<CharSequence>) listaCh);
        context.startActivity(intent);
    }*/

    /**
     * Conformacion de la url a consultar
     * @return url de consulta.
     */
    private String getUrlComic(String offset){
        /*tiempo asociado a la peticion*/
        String time=getDateTime();

        /*Creacion del hash*/
        String hash=md5(time+keyPrivate+keyPublic);

        /*Crear URL de consulta*/
        String url="http://gateway.marvel.com/v1/public/comics?ts="+time+"&limit=30&offset="+offset+"&apikey="+keyPublic+"&hash="+hash;


        return url;
    }
    public String getUrlCharacters(String idComic){
        String url = null;
        /*tiempo asociado a la peticion*/
        String time=getDateTime();
        /*Creacion del hash*/
        String hash=md5(time+keyPrivate+keyPublic);
         url="http://gateway.marvel.com/v1/public/comics/"+idComic+"/characters?ts="+time+"&apikey="+keyPublic+"&hash="+hash;
        Log.i("UrlComic",url);

        return url;
    }

    /**
     * Metodo necesario para anexar el ts en la peticion
     * @return Fecha y hora del momento de la peticion.
     */
    private String getDateTime() {
        java.text.DateFormat dateFormat = new SimpleDateFormat("ss");
        Date date = new Date(); return dateFormat.format(date);
    }

    /**
     * Metodo neceario para anexar el hash que contiene informacion
     * de la fecha, clave publica y privada
     * @param s Sumatoria de las variables de interes
     * @return cadena de identificacion encriptada.
     */
    private String md5(String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
