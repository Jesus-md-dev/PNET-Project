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

public class BookMainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button formButton = findViewById(R.id.bookFormButton);
        formButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");

                Intent bookFormActivity = new Intent(getApplicationContext(),
                        BookFormActivity.class);
                startActivity(bookFormActivity);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

         ArrayList<Book> books = new ArrayList<Book>();

        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");

        if (book != null)
            books.add(book);

        books.add(new Book("Paco", "42477551G", "586283087",
                "pakito@gmail.com", new Calendar.Builder().setDate(2021, 7,
                14).build(),
                new Calendar.Builder().setTimeOfDay(12, 30, 0).build(),
                new Calendar.Builder().setTimeOfDay(12, 30, 0).build() ,
                "me gusta",1));
        books.add(new Book( "Manolo", "42477551G", "586283087",
                "pakito@gmail.com", new Calendar.Builder().setDate(2021, 7,
                14).build(),
                new Calendar.Builder().setTimeOfDay(12, 30, 0).build(),
                new Calendar.Builder().setTimeOfDay(12, 30, 0).build() ,
                "me gusta",2));
        books.add(new Book( "Antonia", "42477551G", "586283087",
                "pakito@gmail.com", new Calendar.Builder().setDate(2021, 7,
                14).build(),
                new Calendar.Builder().setTimeOfDay(12, 30, 0).build(),
                new Calendar.Builder().setTimeOfDay(12, 30, 0).build() ,
                "me gusta",3));
        books.add(new Book( "Gertrudis", "42477551G", "586283087",
                "pakito@gmail.com",
                new Calendar.Builder().setDate(2021, 7, 14).build(),
                new Calendar.Builder().setTimeOfDay(12, 30, 0).build(),
                new Calendar.Builder().setTimeOfDay(12, 30, 0).build() ,
                "me gusta",2));
        books.add(new Book( "Jose Antonio", "42477551G", "586283087",
                "pakito@gmail.com",new Calendar.Builder().setDate(2021, 7,
                14).build(),
                new Calendar.Builder().setTimeOfDay(12, 30, 0).build(),
                new Calendar.Builder().setTimeOfDay(12, 30, 0).build() ,
                "me gusta",3));

        mAdapter = new BookAdapter(books);
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
        if (id == R.id.action_books) {
            startActivity(new Intent(getApplicationContext(), BookMainActivity.class));
        }

        if (id == R.id.action_program) {
            return true;
        }

        if (id == R.id.action_dates) {
            return true;
        }

        if (id == R.id.action_location) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}