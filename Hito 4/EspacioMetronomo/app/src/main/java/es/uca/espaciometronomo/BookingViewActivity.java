package es.uca.espaciometronomo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class BookingViewActivity extends AppCompatActivity {
    private Booking booking;

    private class DeleteAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params){
            String text = null;
            HttpURLConnection urlConnection = null;
            try {
                URL urlToRequest = new URL(Endpoint.getBooking() + booking.getId());
                urlConnection = (HttpURLConnection) urlToRequest.openConnection();
                urlConnection.setRequestMethod("DELETE");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

                try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(),
                                StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                    text = response.toString();
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
            if (results != null) {
                JSONObject respJSON = null;
                try {
                    respJSON = new JSONObject(results);
                    Toast.makeText(BookingViewActivity.this,
                            "Reserva eliminada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), BookingMainActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(BookingViewActivity.this, "Error al eliminar",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class GetAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params){
            String text = null;
            HttpURLConnection urlConnection = null;
            try {
                URL urlToRequest = new URL(Endpoint.getBooking() + booking.getId());
                urlConnection = (HttpURLConnection) urlToRequest.openConnection();
                urlConnection.setRequestMethod("GET");
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
                JSONArray array = new JSONArray(results);

                JSONObject jsonObject = array.getJSONObject(0);

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

                setConfig();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setConfig()
    {
        TextView roomText = findViewById(R.id.roomTypeTitle);
        TextView nameText = findViewById(R.id.nameText);
        TextView dniText = findViewById(R.id.dniText);
        TextView phoneText = findViewById(R.id.phoneText);
        TextView emailText = findViewById(R.id.emailText);
        TextView dateText = findViewById(R.id.dateText);
        TextView startHourText = findViewById(R.id.editTextStartHour);
        TextView endHourText = findViewById(R.id.editTextEndHour);
        TextView reasonText = findViewById(R.id.reasonText);
        Button editButton = findViewById(R.id.editButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        if (booking != null)
        {
            roomText.setText(roomText.getText() + " " + booking.getRoomTypeString());
            nameText.setText(booking.getName());
            dniText.setText(booking.getDni());
            phoneText.setText(booking.getPhone());
            emailText.setText(booking.getEmail());
            dateText.setText(BookingAdapter.dateCalendarToString(booking.getDate()));
            startHourText.setText(BookingAdapter.hourCalendarToString(booking.getStartHour()));
            endHourText.setText(BookingAdapter.hourCalendarToString(booking.getEndHour()));
            reasonText.setText(booking.getReasonString());

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), BookingEditActivity.class);
                    intent.putExtra("edit_booking", booking);
                    startActivity(intent);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteAsyncTask deleteAsyncTask = new DeleteAsyncTask();
                    deleteAsyncTask.execute();
                }
            });
        }
        else
            Toast.makeText(BookingViewActivity.this,"Error al cargar la reserva",
                    Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        booking = (Booking) intent.getSerializableExtra("view_booking");
        GetAsyncTask getAsyncTask = new GetAsyncTask();
        getAsyncTask.execute();
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