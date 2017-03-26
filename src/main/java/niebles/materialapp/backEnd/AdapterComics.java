package niebles.materialapp.backEnd;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import niebles.materialapp.interfaces.ComicInterface;
import niebles.materialapp.POJO.ListaComic;
import niebles.materialapp.R;

/**
 * Created by user on 20/03/2017.
 */
public class AdapterComics extends RecyclerView.Adapter<AdapterComics.viewHolder> {

    List<ListaComic> comicList;
    Context context;
    ComicInterface comicInterface;

    public AdapterComics(List<ListaComic> comicList,Context context,ComicInterface comicInterface) {
        this.comicList = comicList;
        this.context=context;
        this.comicInterface=comicInterface;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comic,parent,false);

        return new viewHolder(view,comicInterface,comicList);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        ListaComic listaComic=comicList.get(position);
        holder.title.setText(listaComic.getName());
        holder.price.setText(listaComic.getPrice());
        Glide.with(context).load(listaComic.getPath()).into(holder.imageComic);
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ComicInterface comicInterface;
        public List<ListaComic> comicList;
        public TextView title, price;
        public ImageView imageComic;

        public viewHolder(View itemView,ComicInterface comicInterface,List<ListaComic> comicList) {
            super(itemView);
            this.comicInterface=comicInterface;
            this.comicList=comicList;


            /*Reconocimiento de los widgets*/
            title=(TextView)itemView.findViewById(R.id.titlePut);
            price=(TextView)itemView.findViewById(R.id.pricePut);
            imageComic=(ImageView)itemView.findViewById(R.id.imagePut);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            ListaComic selectComic=comicList.get(getAdapterPosition());
            comicInterface.getInfo(selectComic.getName(),selectComic.getPrice(),selectComic.getPath(),
                    selectComic.getId(),selectComic.getDescription(),selectComic.getPages());
        }
    }
    public void setFilter(List<ListaComic> listaComics){
        comicList=new ArrayList<>();
        comicList.addAll(listaComics);
        notifyDataSetChanged();

    }
}
