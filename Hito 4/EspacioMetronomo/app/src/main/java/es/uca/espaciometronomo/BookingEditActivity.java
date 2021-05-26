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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

public class BookingEditActivity extends AppCompatActivity {
    DatePickerDialog pickerDialog;
    TimePickerDialog timePickerDialog;
    Booking booking;

    private class LongRunningGetIO extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params){
            String text;
            HttpURLConnection urlConnection = null;
            try {
                URL urlToRequest = new URL("http://10.0.2.2:3000/bookings/" + booking.getId());
                urlConnection = (HttpURLConnection) urlToRequest.openConnection();
                urlConnection.setRequestMethod("PUT");
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
                Toast.makeText(BookingEditActivity.this,"Reserva modificada con exito",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), BookingViewActivity.class);
                intent.putExtra("view_booking", booking);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(BookingEditActivity.this,"Error al modificar",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);

        ImageButton dateBtn = findViewById(R.id.dateButton);
        ImageButton startHourBtn = findViewById(R.id.startHourButton);
        ImageButton endHourBtn = findViewById(R.id.endHourButton);
        TextView titleTextView = findViewById(R.id.booking_form_title);
        EditText nameEditText = findViewById(R.id.editTextName);
        EditText dniEditText = findViewById(R.id.editTextDni);
        EditText phoneEditText = findViewById(R.id.editTextPhone);
        EditText emailEditText = findViewById(R.id.editTextEmail);
        EditText dateEditText = findViewById(R.id.editTextDate);
        EditText startHourEditText = findViewById(R.id.editTextStartHour);
        EditText endHourEditText = findViewById(R.id.editTextEndHour);
        Spinner reasonSpinner = findViewById(R.id.reasonsSpinner);
        RadioGroup typeRadioGroup = findViewById(R.id.typeRadioGroup);
        Button editButton = findViewById(R.id.sendFormBooking);

        reasonSpinner.setAdapter(ArrayAdapter.createFromResource(this,
                R.array.reasons_array, R.layout.support_simple_spinner_dropdown_item));

        titleTextView.setText("Modifique su Reserva");
        editButton.setText("Confirmar cambios");

        Intent intent = getIntent();
        booking = (Booking) intent.getSerializableExtra("edit_booking");

        if (booking != null)
        {
            nameEditText.setText(booking.getName());
            dniEditText.setText(booking.getDni());
            phoneEditText.setText(booking.getPhone());
            emailEditText.setText(booking.getEmail());
            dateEditText.setText(booking.getDateString());
            startHourEditText.setText(booking.getStartHourString());
            endHourEditText.setText(booking.getEndHourString());

            reasonSpinner.setSelection(booking.getReason() - 1);

            typeRadioGroup.clearCheck();

            switch (booking.getRoomType())
            {
                case 1:
                    typeRadioGroup.check(R.id.radioButtonType1);
                    break;
                case 2:
                    typeRadioGroup.check(R.id.radioButtonType2);
                    break;
                case 3:
                    typeRadioGroup.check(R.id.radioButtonType3);
                    break;
            }

            dateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int day = booking.getDate().get(Calendar.DAY_OF_MONTH);
                    int month = booking.getDate().get(Calendar.MONTH);
                    int year = booking.getDate().get(Calendar.YEAR);
                    pickerDialog = new DatePickerDialog(BookingEditActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month,
                                                      int dayOfMonth)
                                {
                                    booking.setDate(new Calendar.Builder().setDate(year, month,
                                            dayOfMonth).build());
                                    dateEditText.setText(booking.getDateString());
                                }
                            }, year, month, day);
                    pickerDialog.show();
                }
            });
            startHourBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int hora = booking.getStartHour().get(Calendar.HOUR_OF_DAY);
                    int min = booking.getStartHour().get(Calendar.MINUTE);
                    timePickerDialog = new TimePickerDialog(BookingEditActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    booking.setStartHour(new Calendar.Builder().setTimeOfDay(hourOfDay,
                                            minute,0).build());
                                    startHourEditText.setText(booking.getStartHourString());
                                }
                            }, hora, min, true);
                    timePickerDialog.show();
                }
            });
            endHourBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int hora = booking.getEndHour().get(Calendar.HOUR_OF_DAY);
                    int min = booking.getEndHour().get(Calendar.MINUTE);
                    timePickerDialog = new TimePickerDialog(BookingEditActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    booking.setEndHour(new Calendar.Builder().setTimeOfDay(hourOfDay,
                                            minute,0).build());
                                    endHourEditText.setText(booking.getEndHourString());
                                }
                            }, hora, min, true);
                    timePickerDialog.show();
                }
            });
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText[] editTexts = {nameEditText, dniEditText, phoneEditText,
                            emailEditText, dateEditText, startHourEditText, endHourEditText};
                    boolean completed = true;
                    for (int i = 0; i < editTexts.length && completed; ++i)
                        if(editTextIsEmpty(editTexts[i]))
                            completed = false;

                    int selectedId = typeRadioGroup.getCheckedRadioButtonId();
                    RadioButton selectedRadButton = findViewById(selectedId);

                    if(completed && selectedRadButton.isChecked())
                    {
                        booking.setName(nameEditText.getText().toString());
                        booking.setDni(dniEditText.getText().toString());
                        booking.setPhone(phoneEditText.getText().toString());
                        booking.setEmail(emailEditText.getText().toString());
                        booking.setReason(reasonSpinner.getSelectedItem().toString());
                        booking.setRoomType(selectedRadButton.getText().toString());

                        LongRunningGetIO myInvokeTask = new LongRunningGetIO();
                        myInvokeTask.execute();
                    }
                    else if(!selectedRadButton.isChecked())
                    {
                        Toast.makeText(BookingEditActivity.this,"Sala sin elegir",
                                Toast.LENGTH_SHORT).show();
                    }
                    LongRunningGetIO myInvokeTask = new LongRunningGetIO();
                    myInvokeTask.execute();
                }
            });
        }
        else
            Toast.makeText(BookingEditActivity.this,"Error al cargar la reserva",
                    Toast.LENGTH_SHORT).show();
    }

    private boolean editTextIsEmpty(EditText editText)
    {
        if(TextUtils.isEmpty(editText.getText().toString()))
        {
            Toast.makeText(BookingEditActivity.this, editText.getHint() + " sin completar",
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