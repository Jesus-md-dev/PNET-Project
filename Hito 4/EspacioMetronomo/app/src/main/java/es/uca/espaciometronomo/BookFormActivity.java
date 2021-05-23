package es.uca.espaciometronomo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class BookFormActivity extends AppCompatActivity {
    DatePickerDialog pickerDialog;
    TimePickerDialog timePickerDialog;
    Calendar date, startHour, endHour;
    String name, dni, phone, email, reason;
    int roomType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_form);

        Button sendForm = findViewById(R.id.sendFormBook);
        ImageButton dateBtn = findViewById(R.id.dateButton);
        ImageButton horaIniBtn = findViewById(R.id.startHourButton);
        ImageButton horaFinBtn = findViewById(R.id.endHourButton);
        EditText nameText = findViewById(R.id.editTextName);
        EditText dniText = findViewById(R.id.editTextDni);
        EditText phoneText = findViewById(R.id.editTextPhone);
        EditText emailText = findViewById(R.id.editTextEmail);
        EditText dateText = findViewById(R.id.dateTextView);
        EditText startHourText = findViewById(R.id.startHourText);
        EditText endHourText = findViewById(R.id.endHourText);
        EditText reasonText = findViewById(R.id.editTextReason);
        RadioGroup typeRadiusGroup = findViewById(R.id.typeRadioGroup);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = Calendar.getInstance();
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);
                pickerDialog = new DatePickerDialog(BookFormActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = new Calendar.Builder().setDate(year, month, dayOfMonth).build();
                        dateText.setText(BookAdapter.calendarDateToString(date));
                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });
        horaIniBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHour = Calendar.getInstance();
                int hora = startHour.get(Calendar.HOUR_OF_DAY);
                int min = startHour.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(BookFormActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startHour = new Calendar.Builder().setTimeOfDay(hourOfDay, minute,
                                0).build();
                        startHourText.setText(BookAdapter.calendarHourToString(startHour));
                    }
                }, hora, min, true);
                timePickerDialog.show();
            }
        });
        horaFinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endHour = Calendar.getInstance();
                int hora = endHour.get(Calendar.HOUR_OF_DAY);
                int min = endHour.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(BookFormActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endHour = new Calendar.Builder().setTimeOfDay(hourOfDay, minute,
                                0).build();
                        endHourText.setText(BookAdapter.calendarHourToString(endHour));
                    }
                }, hora, min, true);
                timePickerDialog.show();
            }
        });
        sendForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: pulsado");
                if(date == null)
                    Log.d("TAG", "onClick: null");
                else
                    Log.d("TAG", "onClick: " + BookAdapter.calendarDateToString(date));

                EditText[] editTexts = {nameText, dniText, phoneText, emailText, dateText,
                        startHourText, endHourText, reasonText};
                boolean completed = true;
                for (int i = 0; i < editTexts.length && completed; ++i)
                    if(editTextIsEmpty(editTexts[i]))
                        completed = false;

                int selectedId = typeRadiusGroup.getCheckedRadioButtonId();
                RadioButton selectedRadButton = findViewById(selectedId);

                switch (selectedRadButton.getText().toString()) {
                    case "Básica":
                        roomType = 1;
                        break;
                    case "Grande":
                        roomType = 2;
                        break;
                    case "Vip":
                        roomType = 3;
                        break;
                    default:
                        roomType = -1;
                        break;
                }

                Log.d("TAG", "onClick: " + roomType);

                if(completed && roomType != -1)
                {
                    name = nameText.getText().toString();
                    dni = dniText.getText().toString();
                    phone = phoneText.getText().toString();
                    email = emailText.getText().toString();
                    reason = reasonText.getText().toString();

                    Book actual = new Book(name, dni, phone, email, date, startHour, endHour,
                            reason, roomType);

                    Intent intent = new Intent(getApplicationContext(), BookMainActivity.class);
                    intent.putExtra("book", actual);
                    startActivity(intent);
                }
                else if(roomType == -1)
                {
                    Toast.makeText(BookFormActivity.this,"Sala sin elegir",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean editTextIsEmpty(EditText editText)
    {
        if(TextUtils.isEmpty(editText.getText().toString()))
        {
            Toast.makeText(BookFormActivity.this, editText.getHint() + " sin completar",
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