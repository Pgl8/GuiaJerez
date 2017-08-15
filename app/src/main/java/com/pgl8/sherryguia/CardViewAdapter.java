package com.pgl8.sherryguia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.pgl8.sherryguia.models.Comentario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Custom adapter for the CardView.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CustomViewHolder>{

	private Context context;
	private List<Comentario> comentarios;

	public CardViewAdapter(Context context, List<Comentario> comentarios) {
		this.context = context;
		this.comentarios = comentarios;
	}

	@Override
	public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.comment_row, parent, false);
		return new CustomViewHolder(view);
	}

	@Override
	public void onBindViewHolder(CustomViewHolder holder, int position) {
		Comentario comentario = comentarios.get(position);

		String date;
		Date date1 = null;
		SimpleDateFormat inputFormatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		try {
			date1 = inputFormatter1.parse(comentario.getFecha());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
		date = format.format(date1);

		holder.fecha.setText(date);
		holder.usuario.setText(comentario.getUsuario());
		holder.comentario.setText(comentario.getComentario());
		holder.ratingBar.setRating(comentario.getPuntuacion());
	}

	@Override
	public int getItemCount() {
		return comentarios.size();
	}

	public class CustomViewHolder extends RecyclerView.ViewHolder {
		TextView fecha;
		TextView usuario;
		TextView comentario;
		RatingBar ratingBar;

		public CustomViewHolder(View itemView) {
			super(itemView);

			fecha = (TextView) itemView.findViewById(R.id.dateText);
			usuario = (TextView) itemView.findViewById(R.id.userText);
			comentario = (TextView) itemView.findViewById(R.id.commentText);
			ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
		}
	}
}
