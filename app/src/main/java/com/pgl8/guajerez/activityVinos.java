package com.pgl8.guajerez;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class activityVinos extends ActionBarActivity implements CustomAdapter.ClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinos);
        recyclerView = (RecyclerView) findViewById(R.id.vinosList);



        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // setting the items decoration
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));

        // specify an adapter (see also next example)
        adapter = new CustomAdapter(this, getData());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

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

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate()
        return super.onCreateView(parent, name, context, attrs);
    }*/

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

    @Override
    public void itemClicked(View view, int position) {
        Intent intent = new Intent(this, InfoVinosActivity.class);
        intent.putExtra("posicion", position);
        startActivity(intent);
        //Log.e("ActivityVinos", "Posicion->"+position);
    }
}
