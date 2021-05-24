package es.uca.espaciometronomo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class BookingEditActivity extends AppCompatActivity {
    DatePickerDialog pickerDialog;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);

        ImageButton dateBtn = findViewById(R.id.dateButton);
        ImageButton startHourBtn = findViewById(R.id.startHourButton);
        ImageButton endHourBtn = findViewById(R.id.endHourButton);
        EditText nameEditText = findViewById(R.id.editTextName);
        EditText dniEditText = findViewById(R.id.editTextDni);
        EditText phoneEditText = findViewById(R.id.editTextPhone);
        EditText emailEditText = findViewById(R.id.editTextEmail);
        EditText dateEditText = findViewById(R.id.editTextDate);
        EditText startHourEditText = findViewById(R.id.editTextStartHour);
        EditText endHourEditText = findViewById(R.id.editTextEndHour);
//        EditText reasonEditText = findViewById(R.id.editTextReason);
        RadioGroup radioGroup = findViewById(R.id.typeRadioGroup);
        Button editButton = findViewById(R.id.bookingFormButton);

//        editButton.setText("Confirmar cambios");

        Intent intent = getIntent();
        Booking booking = (Booking) intent.getSerializableExtra("edit_book");

        if (booking != null)
        {
            nameEditText.setText(booking.getName());
            dniEditText.setText(booking.getDni());
            phoneEditText.setText(booking.getPhone());
            emailEditText.setText(booking.getEmail());
            dateEditText.setText(booking.getDateString());
            startHourEditText.setText(booking.getStartHourString());
            endHourEditText.setText(booking.getEndHourString());
//            reasonEditText.setText(booking.getReason());

            radioGroup.clearCheck();

            switch (booking.getRoomType())
            {
                case 1:
                    radioGroup.check(R.id.radioButtonType1);
                    break;
                case 2:
                    radioGroup.check(R.id.radioButtonType2);
                    break;
                case 3:
                    radioGroup.check(R.id.radioButtonType3);
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
        }
        else
            Toast.makeText(BookingEditActivity.this,"Error al cargar la reserva",
                    Toast.LENGTH_SHORT).show();
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