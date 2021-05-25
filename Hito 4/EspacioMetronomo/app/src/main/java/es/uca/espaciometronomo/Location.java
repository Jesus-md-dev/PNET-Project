package es.uca.espaciometronomo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class Location extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Referenciamos al RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_localizacion);

        // Mejoramos rendimiento con esta configuración
        mRecyclerView.setHasFixedSize(true);

        // Creamos un LinearLayoutManager para gestionar el item_localizacion.xml creado antes
        mLayoutManager = new LinearLayoutManager(this);
        // Lo asociamos al RecyclerView
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Creamos un ArrayList de Salas
        ArrayList<Room> rooms = new ArrayList<Room>();

        rooms.add(new Room("Tipo 1", "Las salas de tipo 1 tienen un tamaño de 25 metros cuadrados. Cuentan con un piano acústico, teclado eléctrico, un micrófono vocal y un atril."));
        rooms.add(new Room("Tipo 2", "Las salas de tipo 2 tienen un tamaño de 25 metros cuadrados. Cuentan con un piano acústico, teclado eléctrico, un micrófono vocal y un atril."));
        rooms.add(new Room("Tipo 3", "Las salas de tipo 3 tienen un tamaño de 30 metros cuadrados. Cuentan con un piano acústico, teclado eléctrico, un micrófono vocal y un atril."));

        // Creamos un RoomAdapter pasándole todas nuestras salas
        mAdapter = new RoomAdapter(rooms);
        // Asociamos el adaptador al RecyclerView
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.

        switch (item.getItemId()){
            case R.id.action_dates:
                Intent intentDates = new Intent(Location.this, ImportantDates.class);
                startActivity(intentDates);
                return true;
            case R.id.action_location:
                Intent intentLocation = new Intent(Location.this, Location.class);
                startActivity(intentLocation);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}