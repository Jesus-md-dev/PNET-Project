package es.uca.espaciometronomo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BookingViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_view);
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
        Button editButton = findViewById(R.id.editButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        Intent intent = getIntent();
        Booking booking = (Booking) intent.getSerializableExtra("view_book");

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

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), BookingEditActivity.class);
                    intent.putExtra("edit_book", booking);
                    startActivity(intent);
                }
            });
        }
        else
            Toast.makeText(BookingViewActivity.this,"Error al cargar la reserva",
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