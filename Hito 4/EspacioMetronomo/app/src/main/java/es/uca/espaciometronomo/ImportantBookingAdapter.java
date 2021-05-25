package es.uca.espaciometronomo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImportantBookingAdapter extends RecyclerView.Adapter<ImportantBookingAdapter.MyViewHolder> {
    private final ArrayList<Booking> bookings;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView room;
        TextView date;
        TextView reason;
        Button show;

        public MyViewHolder(View v) {
            super(v);
            room = v.findViewById(R.id.room);
            date = v.findViewById(R.id.date);
            reason = v.findViewById(R.id.reason);
            show = v.findViewById(R.id.show);
        }
    }

    public ImportantBookingAdapter(ArrayList<Booking> myDataset) {
        bookings = myDataset;
    }

    @NonNull
    @Override
    public ImportantBookingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int
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
        String room = "Sala " + BookingAdapter.roomTypeIntToString(bookings.get(position).getRoomType());
        holder.room.setText(room);
        holder.date.setText(bookings.get(position).getDateString());
        holder.reason.setText(bookings.get(position).getReasonString());
        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), ImportantBookingViewActivity.class);
                intent.putExtra("view_booking", bookings.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }
}
