package es.uca.espaciometronomo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ImportantBookingViewActivity extends AppCompatActivity {

    private Booking booking;

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
        String roomTitle;
        TextView roomText = findViewById(R.id.roomTypeTitle);
        TextView nameText = findViewById(R.id.nameText);
        TextView dniText = findViewById(R.id.dniText);
        TextView phoneText = findViewById(R.id.phoneText);
        TextView emailText = findViewById(R.id.emailText);
        TextView dateText = findViewById(R.id.dateText);
        TextView startHourText = findViewById(R.id.editTextStartHour);
        TextView endHourText = findViewById(R.id.editTextEndHour);
        TextView reasonText = findViewById(R.id.reasonText);
        Button downloadButton = findViewById(R.id.dowloadButton);

        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, PackageManager.PERMISSION_GRANTED);

        if (booking != null)
        {
            roomTitle = getString(R.string.booking_room_title) + " " +
                    BookingAdapter.roomTypeIntToString(booking.getRoomType());
            roomText.setText(roomTitle);
            nameText.setText(booking.getName());
            dniText.setText(booking.getDni());
            phoneText.setText(booking.getPhone());
            emailText.setText(booking.getEmail());
            dateText.setText(BookingAdapter.dateCalendarToString(booking.getDate()));
            startHourText.setText(BookingAdapter.hourCalendarToString(booking.getStartHour()));
            endHourText.setText(BookingAdapter.hourCalendarToString(booking.getEndHour()));
            reasonText.setText(booking.getReasonString());

            downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generatePDF();
                    Toast.makeText(ImportantBookingViewActivity.this,
                            "PDF descargado", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
            Toast.makeText(ImportantBookingViewActivity.this,
                    "Error al cargar la reserva", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_booking_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        booking = (Booking) intent.getSerializableExtra("view_booking");
        GetAsyncTask getAsyncTask = new GetAsyncTask();
        getAsyncTask.execute();
    }

    private void generatePDF() {
        int ySpace = 20;
        int yPosition = ySpace;
        int xPosition = 10;
        String[] bookingData = {"Nombre: " + booking.getName(), "DNI: " + booking.getDni(),
                "Teléfono: " + booking.getPhone(), "Email: " + booking.getEmail(),
                "Fecha: " + booking.getDateString(),
                "Hora de entrada: " + booking.getStartHourString(),
                "Hora de salida: " + booking.getEndHourString(),
                "Motivo: " + booking.getReasonString()};
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Bitmap bitmap;

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo
                .Builder(250, 400, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.header);
        bitmap = Bitmap.createScaledBitmap(bitmap, pageInfo.getPageWidth(),
                Math.round(pageInfo.getPageWidth() / 4.6f), false);
        canvas.drawBitmap(bitmap, 0, 0 , paint);

        yPosition = (int) ((pageInfo.getPageWidth() / 4.6f) + (ySpace * 1.5));

        int endXPosition = pageInfo.getPageWidth() - 10;

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(14.0f);
        canvas.drawText("Sala " + booking.getRoomTypeString(), pageInfo.getPageWidth()/2,
                yPosition, paint);
        yPosition += ySpace * 1.5;

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(12.0f);
        for(int i = 0; i < bookingData.length; ++i)
        {
            canvas.drawText(bookingData[i], xPosition, yPosition, paint);
            canvas.drawLine(xPosition, yPosition + 3, endXPosition ,
                    yPosition + 3, paint);
            yPosition += ySpace;
        }

        pdfDocument.finishPage(page);

        File file =new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "/" + booking.getName() + booking.getReasonString() + ".pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();
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
