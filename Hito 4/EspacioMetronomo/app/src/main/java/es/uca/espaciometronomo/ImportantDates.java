package es.uca.espaciometronomo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ImportantDates extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Booking> bookings = new ArrayList<Booking>();

    private class LongRunningGetIO extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params){
            String text = null;
            HttpURLConnection urlConnection = null;
            try {
                URL urlToRequest = new URL("http://10.0.2.2:3000/bookings");
                urlConnection = (HttpURLConnection) urlToRequest.openConnection();
                urlConnection.connect();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    text = new Scanner(in).useDelimiter("\\A").next();
                }
                else
                {
                    text = "error";
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return text;
        }

        @Override
        protected void onPostExecute(String results) {
            super .onPostExecute(results);
            try {
                bookings.clear();
                JSONArray array = new JSONArray(results);

                for (int i = 0; i < array.length(); i++) {
                    Booking booking = new Booking();

                    JSONObject jsonObject = array.getJSONObject(i);

                    booking.setId((String) jsonObject.get("_id"));
                    booking.setName((String) jsonObject.get("name"));
                    booking.setDni((String) jsonObject.get("dni"));
                    booking.setPhone((String) jsonObject.get("phone"));
                    booking.setEmail((String) jsonObject.get("email"));
                    booking.setDate((String) jsonObject.get("date"));
                    booking.setStartHour((String) jsonObject.get("startHour"));
                    booking.setEndHour((String) jsonObject.get("endHour"));
                    booking.setReason((int) jsonObject.get("reason"));
                    booking.setRoomType((int) jsonObject.get("roomType"));

                    Log.d("date", "onPostExecute: "+ booking.getDate());

                    bookings.add(booking);
                }

                Comparator<Booking> compareByDate = (Booking b1, Booking b2)
                        -> b1.getDate().compareTo(b2.getDate());

                Collections.sort(bookings, compareByDate);

                ArrayList<Booking> importantBookings = (ArrayList<Booking>) bookings.stream()
                        .filter(b -> b.getReason() != 1).collect(Collectors.toList());

                mAdapter = new DatesAdapter(importantBookings);

                mRecyclerView.setAdapter(mAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        Intent intent = getIntent();
        Booking booking = (Booking) intent.getSerializableExtra("new_book");

        if (booking != null)
            bookings.add(booking);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LongRunningGetIO myInvokeTask = new LongRunningGetIO();
        myInvokeTask.execute();
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
            startActivity(new Intent(getApplicationContext(), BookingMainActivity.class));
        }

        if (id == R.id.action_program) {
            startActivity(new Intent(getApplicationContext(), ImportantBookingsActivity.class));
        }

        if (id == R.id.action_dates) {
            startActivity(new Intent(getApplicationContext(), ImportantDates.class));
        }

        if (id == R.id.action_location) {
            startActivity(new Intent(getApplicationContext(), Location.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
