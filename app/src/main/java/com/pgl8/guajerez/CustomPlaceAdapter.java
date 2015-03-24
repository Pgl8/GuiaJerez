package com.pgl8.guajerez;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class CustomPlaceAdapter extends RecyclerView.Adapter<CustomPlaceAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    List<CustomPlaceList> data = Collections.emptyList();

    // constructor predeterminado con dos parámetros
    public CustomPlaceAdapter(Context context, List<CustomPlaceList> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    // función que vincula los elementos de la lista a los de la vista
    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        CustomPlaceList current = data.get(position);
        viewHolder.titulo.setText(current.title);
        viewHolder.icono.setImageResource(current.iconId);
        viewHolder.texto.setText(current.text);
        viewHolder.distancia.setText(current.distance);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // clase interna que extiende el recycler view, necesaria para localizar los elementos de la UI
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView icono;
        TextView titulo;
        TextView texto;
        TextView distancia;

        // constructor predeterminado de un único parámetro
        public MyViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.listTitle);
            icono = (ImageView) itemView.findViewById(R.id.listIcon);
            texto = (TextView) itemView.findViewById(R.id.listText);
            distancia = (TextView) itemView.findViewById(R.id.listDistance);
        }
    }
}
