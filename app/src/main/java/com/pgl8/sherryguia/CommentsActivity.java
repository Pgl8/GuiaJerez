package com.pgl8.sherryguia;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.pgl8.sherryguia.models.Comentario;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class CommentsActivity extends AppCompatActivity {

	private static final String TAG = "CommentsActivity";
	private List<Comentario> comentarios;
	private RecyclerView listaComentarios;
	private CardViewAdapter adaptador;
	private ImageView imageView;
	public String accountName;
	public String accountPhoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comments);
		imageView = (ImageView) findViewById(R.id.imageComment);

		Intent intent = getIntent();
		accountName = intent.getStringExtra("accountName");
		accountPhoto = intent.getStringExtra("accountPhoto");

		Picasso.with(this).load(accountPhoto).resize(150, 150).transform(new CropCircleTransformation()).into(imageView);

		listaComentarios = (RecyclerView) findViewById(R.id.recyclerView);
		listaComentarios.addItemDecoration(new DividerItemDecoration(this));
		LinearLayoutManager manager = new LinearLayoutManager(this);
		manager.setOrientation(LinearLayoutManager.VERTICAL);
		listaComentarios.setLayoutManager(manager);

		data();
		inicializaAdaptador();

	}

	private void data() {
		comentarios = new ArrayList<>();
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 5));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 5));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 5));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 4));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 4));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 4));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 4));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 4));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 4));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 4));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 4));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 4));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 4));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 4));
		comentarios.add(new Comentario("Paco Perez", "Comentario prueba", new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(new Date(2017, 8, 4)), 4));

		if(comentarios.size() <= 0){

		}
	}

	private void inicializaAdaptador() {
		adaptador = new CardViewAdapter(this, comentarios);
		listaComentarios.setAdapter(adaptador);
	}

}
