package es.uca.espaciometronomo;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Referenciamos al RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // Mejoramos rendimiento con esta configuración
        mRecyclerView.setHasFixedSize(true);
        // Creamos un LinearLayoutManager para gestionar el item.xml creado antes
        mLayoutManager = new LinearLayoutManager(this);
        // Lo asociamos al RecyclerView
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Creamos un ArrayList de Pokemons
         ArrayList<Reserva> reservas = new ArrayList<Reserva>();
        // Creamos un PokemonAdapter pasándole todos nuestro Pokemons
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reservas) {
            return true;
        }

        if (id == R.id.action_programa) {
            return true;
        }

        if (id == R.id.action_fechas) {
            return true;
        }

        if (id == R.id.action_localizacion) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}