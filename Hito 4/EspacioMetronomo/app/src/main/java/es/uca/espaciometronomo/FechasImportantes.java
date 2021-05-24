package es.uca.espaciometronomo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FechasImportantes extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fechas_importantes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Referenciamos al RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // Mejoramos rendimiento con esta configuración
        mRecyclerView.setHasFixedSize(true);

        // Creamos un LinearLayoutManager para gestionar el item_fechas.xml creado antes
        mLayoutManager = new LinearLayoutManager(this);
        // Lo asociamos al RecyclerView
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Creamos un ArrayList de Resevas
        ArrayList<Reserva> reservas = new ArrayList<Reserva>();

        reservas.add(new Reserva(1, "Reserva 1", new Calendar.Builder().setDate(2021, 05, 22).build()));
        reservas.add(new Reserva(2, "Reserva 2", new Calendar.Builder().setDate(2021, 05, 23).build()));
        reservas.add(new Reserva(3, "Reserva 3", new Calendar.Builder().setDate(2021, 05, 24).build()));
        reservas.add(new Reserva(4, "Reserva 4", new Calendar.Builder().setDate(2021, 07, 24).build()));

        // Creamos un ReservaAdapter pasándole todas nuestras reservas
        mAdapter = new ReservaAdapter(reservas);
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
            case R.id.action_fechas:
                Intent intentFechas = new Intent(FechasImportantes.this, FechasImportantes.class);
                startActivity(intentFechas);
                return true;
            case R.id.action_localizacion:
                Intent intentLocalizacion = new Intent(FechasImportantes.this, Localizacion.class);
                startActivity(intentLocalizacion);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}