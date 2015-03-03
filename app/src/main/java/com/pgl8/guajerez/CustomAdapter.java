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

/**
 * Created by WINDOWS 7 on 09/02/2015.
 * Adaptador para las listas personalizadas de las RecyclerViews
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    //private ClickListener clickListener;
    List<CustomList> data = Collections.emptyList();

    // constructor predeterminado con dos parámetros
    public CustomAdapter(Context context, List<CustomList> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // función que crea el viewholder para el adaptador
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    // función que vincula los elementos de la lista a los de la vista
    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        CustomList current = data.get(position);
        viewHolder.titulo.setText(current.title);
        viewHolder.icono.setImageResource(current.iconId);
    }

    // función que devuelve el tamaño de la lista
    @Override
    public int getItemCount() {
        return data.size();
    }

    // clase interna que extiende el recycler view, necesaria para localizar los elementos de la UI
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView icono;
        TextView titulo;

        // constructor predeterminado de un único parámetro
        public MyViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.listText);
            icono = (ImageView) itemView.findViewById(R.id.listIcon);
        }
    }
}
