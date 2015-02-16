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
    private ClickListener clickListener;
    List<CustomList> data = Collections.emptyList();

    //Constructor
    public CustomAdapter(Context context, List<CustomList> data){
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

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        CustomList current = data.get(position);
        viewHolder.titulo.setText(current.title);
        viewHolder.icono.setImageResource(current.iconId);

    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView icono;
        TextView titulo;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titulo = (TextView) itemView.findViewById(R.id.listText);
            icono = (ImageView) itemView.findViewById(R.id.listIcon);
        }

        @Override
        public void onClick(View v) {
            //context.startActivity(new Intent(context, InfoVinosActivity.class));
            if(clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }

    public interface ClickListener{
        public void itemClicked(View view, int position);
    }
}
