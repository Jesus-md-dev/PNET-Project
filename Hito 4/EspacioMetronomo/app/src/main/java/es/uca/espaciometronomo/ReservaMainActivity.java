package es.uca.espaciometronomo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReservaMainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserva_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button formButton = findViewById(R.id.reservaFormButton);
        formButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");

                Intent reservaFormActivity = new Intent(getApplicationContext(),
                        ReservaFormActivity.class);
                startActivity(reservaFormActivity);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

         ArrayList<Reserva> reservas = new ArrayList<Reserva>();

        reservas.add(new Reserva(1, "Paco", "42477551G", "586283087",
                "pakito@gmail.com", new Calendar.Builder().setDate(2021, 7,
                14).build(), "me gusta"));
        reservas.add(new Reserva(2, "Manolo", "42477551G", "586283087",
                "pakito@gmail.com", new Calendar.Builder().setDate(2021, 7,
                14).build(), "me gusta"));
        reservas.add(new Reserva(3, "Antonia", "42477551G", "586283087",
                "pakito@gmail.com", new Calendar.Builder().setDate(2021, 7,
                14).build(), "me gusta"));
        reservas.add(new Reserva(4, "Gertrudis", "42477551G", "586283087",
                "pakito@gmail.com", new Calendar.Builder().setDate(2021, 7,
                14).build(), "me gusta"));
        reservas.add(new Reserva(5, "Jose Antonio", "42477551G", "586283087",
                "pakito@gmail.com",new Calendar.Builder().setDate(2021, 7,
                14).build(), "me gusta"));

        mAdapter = new ReservaAdapter(reservas);
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
            startActivity(new Intent(getApplicationContext(), ReservaMainActivity.class));
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