package es.uca.espaciometronomo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {
    private final ArrayList<Booking> bookings;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView room;
        TextView date;
        Button show;

        public MyViewHolder(View v) {
            super(v);
            room = v.findViewById(R.id.room);
            date = v.findViewById(R.id.date);
            show = v.findViewById(R.id.show);
        }
    }

    public BookingAdapter(ArrayList<Booking> myDataset) {
        bookings = myDataset;
    }

    @NonNull
    @Override
    public BookingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int
            viewType) {
        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item,
                        parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.room.setText(roomTypeIntToString(bookings.get(position).getRoomType()));
        holder.date.setText(bookings.get(position).getDateString());
        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), BookingViewActivity.class);
                intent.putExtra("view_book", (Booking) bookings.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static String dateCalendarToString(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" +
                calendar.get(Calendar.YEAR);
    }

    public static String hourCalendarToString(Calendar calendar) {
        String hora;
        int horas = calendar.get(Calendar.HOUR_OF_DAY), min = calendar.get(Calendar.MINUTE);
        hora = horas + ":";
        if(min < 10) hora += "0" + min;
        else hora += min;
        return hora;
    }

    public static Calendar dateStringToCalendar(String date) {
        String[] parts = date.split("/");
        return new Calendar.Builder().setDate(Integer.parseInt(parts[2]),
                Integer.parseInt(parts[1]), Integer.parseInt(parts[0])).build();
    }

    public static Calendar hourStringToCalendar(String hour) {
        String[] parts = hour.split(":");
        return new Calendar.Builder().setTimeOfDay(Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]), 0).build();
    }

    public static String roomTypeIntToString(int roomType)
    {
        switch (roomType)
        {
            case 1:
                return "Básica";
            case 2:
                return "Grande";
            case 3:
                return "Vip";
            default:
                return "";
        }
    }

    public static int roomTypeStringToInt(String roomType)
    {
        switch (roomType)
        {
            case "Básica":
                return 1;
            case "Grande":
                return 2;
            case "Vip":
                return 3;
            default:
                return -1;
        }
    }

    public static String reasonIntToString(int roomType)
    {
        switch (roomType)
        {
            case 1:
                return "Ensayo";
            case 2:
                return "Clase";
            case 3:
                return "Concierto";
            default:
                return "";
        }
    }

    public static int reasonStringToInt(String roomType)
    {
        switch (roomType)
        {
            case "Ensayo":
                return 1;
            case "Clase":
                return 2;
            case "Concierto":
                return 3;
            default:
                return -1;
        }
    }
}
