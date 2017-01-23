package com.pgl8.sherryguia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Custom adapter for the CardView.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CustomViewHolder>{
	private Context context;


	public CardViewAdapter(Context context) {
		this.context = context;
	}

	@Override
	public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.comment_row, parent, false);
		return new CustomViewHolder(view);
	}

	@Override
	public void onBindViewHolder(CustomViewHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return 0;
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
