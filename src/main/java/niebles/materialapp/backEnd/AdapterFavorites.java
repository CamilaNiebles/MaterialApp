package niebles.materialapp.backEnd;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import niebles.materialapp.POJO.ListaComic;
import niebles.materialapp.R;
import niebles.materialapp.contentProvider.DBHelperComics;
import niebles.materialapp.interfaces.ComicInterface;

/**
 * Created by user on 23/03/2017.
 */
public class AdapterFavorites extends RecyclerView.Adapter<AdapterFavorites.viewHolder> {

    ComicInterface comicInterface;
    public Cursor cursor;
    ContentResolver contentResolver;
    Context context;


    public AdapterFavorites(ComicInterface comicInterface, Cursor cursor, ContentResolver contentResolver,Context context) {
        this.comicInterface=comicInterface;
        this.cursor=cursor;
        this.contentResolver=contentResolver;
        this.context=context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_favorites,parent,false);
        viewHolder viewHolder=new viewHolder(view,comicInterface,cursor);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {

        cursor.moveToPosition(position);
        String valor;
        valor=cursor.getString(Columnas.titleInt);
        holder.titleFav.setText(valor);

        valor=cursor.getString(Columnas.priceInt);
        holder.priceFav.setText(valor);

        Glide.with(context).load(cursor.getString(Columnas.pathInt)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (cursor != null)
            return cursor.getCount();
        return 0;
    }
    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ComicInterface comicInterface;
        public TextView titleFav, priceFav;
        public ImageView imageView;
        Cursor cursor;

        public viewHolder(View itemView, ComicInterface comicInterface, Cursor cursor) {
            super(itemView);
            this.comicInterface = comicInterface;
            this.cursor=cursor;

            titleFav=(TextView)itemView.findViewById(R.id.favTitle);
            priceFav=(TextView)itemView.findViewById(R.id.favPrice);
            imageView=(ImageView)itemView.findViewById(R.id.favImage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cursor.moveToPosition(getAdapterPosition());
            String fvTitle=cursor.getString(Columnas.titleInt);
            String fvPrice=cursor.getString(Columnas.priceInt);
            String fvPath=cursor.getString(Columnas.pathInt);
            String fvDescription=cursor.getString(Columnas.descriptionInt);
            String fvPages=cursor.getString(Columnas.pagesInt);

            comicInterface.getInfo(fvTitle,fvPrice,fvPath,null,fvDescription,fvPages);

        }
    }
    interface Columnas{
        int titleInt=1;
        int priceInt=2;
        int pathInt=3;
        int idInt=4;
        int descriptionInt=5;
        int pagesInt=6;

    }


}
