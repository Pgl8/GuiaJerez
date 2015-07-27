package com.pgl8.guajerez;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class activityLugares extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lugaresList);

        // Opción para aumentar la eficiencia del mapa
        recyclerView.setHasFixedSize(true);

        // Obtenemos y asignamos el linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // asignamos la decoración de los objetos del recycler view
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        // especificamos un adaptador y el listener del click
        CustomPlaceAdapter adapter = new CustomPlaceAdapter(this, getData());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new activityLugares.ClickListener() {
            // función que es llamada cuando se produce un click e inicializa la actividad pertinente
            @Override
            public void onClick(View view, int position) {
                view.playSoundEffect(SoundEffectConstants.CLICK);
                // CAMBIAR
                Intent intent = new Intent(getBaseContext(), activityInfoVinos.class);
                intent.putExtra("posicion", position);
                startActivity(intent);
                //view.setSelected(false);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    //Función que obtiene la lista de objetos para el recycler view
    public static List<CustomPlaceList> getData() {
        List<CustomPlaceList> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,};
        String[] titles = {"Williams & Humbert", "Fundador Pedro Domecq", "Gonzalez Byass",
                "Emilio Hidalgo", "Romate", "Álvaro Domecq", "Garvey", "José Estevez",
                "Tradición", "Diez Mérito", "Emilio Lustau", "Rey Fernando de Castilla"};
        String[] texts = {"Ctra N- IV, Km 641", "C/ San Ildefonso, 3", "C/ Manuel Mª González, 12",
                "C/ Clavel, 29 ", "C/ Lealas, 26", "Calle San Luis",
                "Ctra. Circunvalación", "Ctra. N-IV, Km. 640", "C/ Cordobeses, 3",
                "C/Diego Fernández de Herrera", "C/ Arcos, 53", "Calle San Francisco Javier, 3"};
        String[] distances = {"1km", "1km", "1km", "1km", "1km", "1km", "1km", "1km", "1km", "1km",
                              "1km", "1km"};
        for (int i = 0; i < titles.length && i < icons.length; i++) {
            CustomPlaceList lista = new CustomPlaceList();
            lista.iconId = icons[i];
            lista.title = titles[i];
            lista.text = texts[i];
            lista.distance = distances[i];
            data.add(lista);
        }
        return data;
    }

    //Clase interna que implementa el click en el recycler view
    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        //Declaramos las variables
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        //Constructor que recibe 3 parámetros
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });

        }

        //Función que detecta el click
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) { }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    //Interfaz destinada a ejecutar los eventos de click simple y click largo
    public static interface ClickListener {
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_lugares, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
