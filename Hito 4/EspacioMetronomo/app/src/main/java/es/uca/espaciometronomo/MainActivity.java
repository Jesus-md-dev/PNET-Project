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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    TextView myInvokeText;

    private class LongRunningGetIO extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params){
            String text = null;
            HttpURLConnection urlConnection = null;
            try {
                URL urlToRequest = new URL("http://10.0.2.2:3000/bookings");
                urlConnection = (HttpURLConnection) urlToRequest.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("name", "ermanolo");
                jsonParam.put("dni", "ermanolo");
                jsonParam.put("date", "ermanolo");
                jsonParam.put("time", "ermanolo");
                jsonParam.put("text", "ermanolo");

//                String jsonInputString = "{\"name\":\"ErManolo\",\"dni\":\"52-9801561\"," +
//                        "\"phone\":\"1177277054\",\"email\":\"irubie0@amazon.co.jp\"," +
//                        "\"date\":\"17/03/2021\",\"startHour\":\"23:09\",\"endHour\":\"4:57\"," +
//                        "\"reason\":3,\"roomType\":3}";

                try {
                    DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                    os.writeBytes(jsonParam.toString());
                    os.flush();
                    os.close();
                } catch (Exception e) {  e.printStackTrace(); }
                urlConnection.getContent();

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
            super.onPostExecute(results);
            try {
                Log.d("TAG", "onPostExecute: " + results);
                myInvokeText.setText(results);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        myInvokeText = findViewById(R.id.myInvokeText);
        Button myInvokeButton = findViewById(R.id.myInvokeButton);
        myInvokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LongRunningGetIO myInvokeTask = new LongRunningGetIO();
                myInvokeTask.execute();
            }
        });
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
            return true;
        }

        if (id == R.id.action_dates) {
            return true;
        }

        if (id == R.id.action_location) {
            return true;
        }

        if (id == R.id.action_test) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}