package es.uca.espaciometronomo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Scanner;

public class BookingFormActivity extends AppCompatActivity {
    DatePickerDialog pickerDialog;
    TimePickerDialog timePickerDialog;
    Calendar date, startHour, endHour;
    String name, dni, phone, email, reason, roomType;
    Booking booking;

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

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", booking.getName());
                jsonObject.put("dni", booking.getDni());
                jsonObject.put("phone", booking.getPhone());
                jsonObject.put("email", booking.getEmail());
                jsonObject.put("date", booking.getDateString());
                jsonObject.put("startHour", booking.getStartHourString());
                jsonObject.put("endHour", booking.getEndHourString());
                jsonObject.put("reason", booking.getReason());
                jsonObject.put("roomType", booking.getRoomType());

                try {
                    DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                    os.writeBytes(jsonObject.toString());
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);

        Button sendForm = findViewById(R.id.sendFormBooking);
        ImageButton dateBtn = findViewById(R.id.dateButton);
        ImageButton startHourBtn = findViewById(R.id.startHourButton);
        ImageButton endHourBtn = findViewById(R.id.endHourButton);
        EditText nameText = findViewById(R.id.editTextName);
        EditText dniText = findViewById(R.id.editTextDni);
        EditText phoneText = findViewById(R.id.editTextPhone);
        EditText emailText = findViewById(R.id.editTextEmail);
        EditText dateText = findViewById(R.id.editTextDate);
        EditText startHourText = findViewById(R.id.editTextStartHour);
        EditText endHourText = findViewById(R.id.editTextEndHour);
        Spinner reasonSpinner = findViewById(R.id.reasonsSpinner);
        RadioGroup typeRadiusGroup = findViewById(R.id.typeRadioGroup);

        reasonSpinner.setAdapter(ArrayAdapter.createFromResource(this,
                R.array.reasons_array, R.layout.support_simple_spinner_dropdown_item));

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(date == null)
                    date = Calendar.getInstance();

                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);
                pickerDialog = new DatePickerDialog(BookingFormActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = new Calendar.Builder().setDate(year, month, dayOfMonth).build();
                        dateText.setText(BookingAdapter.dateCalendarToString(date));
                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });
        startHourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startHour == null)
                    startHour = Calendar.getInstance();
                int hora = startHour.get(Calendar.HOUR_OF_DAY);
                int min = startHour.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(BookingFormActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startHour = new Calendar.Builder().setTimeOfDay(hourOfDay, minute,
                                0).build();
                        startHourText.setText(BookingAdapter.hourCalendarToString(startHour));
                    }
                }, hora, min, true);
                timePickerDialog.show();
            }
        });
        endHourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (endHour == null)
                    endHour = Calendar.getInstance();
                int hora = endHour.get(Calendar.HOUR_OF_DAY);
                int min = endHour.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(BookingFormActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endHour = new Calendar.Builder().setTimeOfDay(hourOfDay, minute,
                                0).build();
                        endHourText.setText(BookingAdapter.hourCalendarToString(endHour));
                    }
                }, hora, min, true);
                timePickerDialog.show();
            }
        });
        sendForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText[] editTexts = {nameText, dniText, phoneText, emailText, dateText,
                        startHourText, endHourText};
                boolean completed = true;
                for (int i = 0; i < editTexts.length && completed; ++i)
                    if(editTextIsEmpty(editTexts[i]))
                        completed = false;

                int selectedId = typeRadiusGroup.getCheckedRadioButtonId();
                RadioButton selectedRadButton = findViewById(selectedId);

                if(completed && selectedRadButton.isChecked())
                {
                    name = nameText.getText().toString();
                    dni = dniText.getText().toString();
                    phone = phoneText.getText().toString();
                    email = emailText.getText().toString();
                    reason = reasonSpinner.getSelectedItem().toString();
                    roomType = selectedRadButton.getText().toString();

                    Log.d("TAG", "onClick: " + reason);

                    booking = new Booking("0", name, dni, phone, email, date, startHour,
                            endHour, reason, roomType);

                    LongRunningGetIO myInvokeTask = new LongRunningGetIO();
                    myInvokeTask.execute();
                    Intent intent = new Intent(getApplicationContext(), BookingMainActivity.class);
                    startActivity(intent);
                }
                else if(!selectedRadButton.isChecked())
                {
                    Toast.makeText(BookingFormActivity.this,"Sala sin elegir",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean editTextIsEmpty(EditText editText)
    {
        if(TextUtils.isEmpty(editText.getText().toString()))
        {
            Toast.makeText(BookingFormActivity.this, editText.getHint() + " sin completar",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
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