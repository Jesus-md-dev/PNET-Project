package es.uca.espaciometronomo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {
    private final ArrayList<Book> books;
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

    public BookAdapter(ArrayList<Book> myDataset) {
        books = myDataset;
    }

    @NonNull
    @Override
    public BookAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int
            viewType) {
        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,
                        parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        switch (books.get(position).getRoomType())
        {
            case 1:
                holder.room.setText("Básica");
                break;
            case 2:
                holder.room.setText("Grande");
                break;
            case 3:
                holder.room.setText("Vip");
                break;
        }

        holder.date.setText(calendarDateToString(books.get(position).getDate()));
        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = "You've clicked on " +
                        books.get(position).getName();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static String calendarDateToString(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" +
                calendar.get(Calendar.YEAR);
    }

    public static String calendarHourToString(Calendar calendar) {
        String hora;
        int horas = calendar.get(Calendar.HOUR_OF_DAY), min = calendar.get(Calendar.MINUTE);
        hora = horas + ":";
        if(min < 10) hora += "0" + min;
        else hora += min;
        return hora;
    }
}
