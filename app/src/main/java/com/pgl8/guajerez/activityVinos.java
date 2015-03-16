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


public class activityVinos extends ActionBarActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinos);
        recyclerView = (RecyclerView) findViewById(R.id.vinosList);

        // Opción para aumentar la eficiencia del mapa
        recyclerView.setHasFixedSize(true);

        // Obtenemos y asignamos el linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // asignamos la decoración de los objetos del recycler view
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        // especificamos un adaptador y el listener del click
        adapter = new CustomAdapter(this, getData());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            // función que es llamada cuando se produce un click e inicializa la actividad pertinente
            @Override
            public void onClick(View view, int position) {
                view.setSelected(true);
                view.playSoundEffect(SoundEffectConstants.CLICK);
                Intent intent = new Intent(getBaseContext(), activityInfoVinos.class);
                intent.putExtra("posicion", position);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    //Función que obtiene la lista de objetos para el recycler view
    public static List<CustomList> getData() {
        List<CustomList> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                       R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                       R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                       R.drawable.ic_launcher};
        String[] titles = {"Fino", "Manzanilla", "Amontillado", "Oloroso", "Palo Cortado", "Pale Cream",
                            "Medium", "Cream", "Moscatel", "Pedro Ximenez"};
        for (int i = 0; i < titles.length && i < icons.length; i++) {
            CustomList lista = new CustomList();
            lista.iconId = icons[i];
            lista.title = titles[i];
            data.add(lista);
        }
        return data;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vinos, menu);
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
    }

    //Interfaz destinada a ejecutar los eventos de click simple y click largo
    public static interface ClickListener {
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }
}
