package es.uca.espaciometronomo;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.MyViewHolder>{
    private ArrayList<Reserva> reservas;
    private Context context;

    public ReservaAdapter(ArrayList<Reserva> myDataset) {
        reservas = myDataset;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView date;
        ImageButton show;

        public MyViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            date = (TextView) v.findViewById(R.id.date);
            show = (ImageButton) v.findViewById(R.id.show);
        }
    }
    @Override
    public ReservaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        context = parent.getContext();
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(reservas.get(position).getNombre());
        holder.date.setText(calendarToString(reservas.get(position).getFecha()));
        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = "You've clicked on " + reservas.get(position).getNombre();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    public static String calendarToString (Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" +
                calendar.get(Calendar.YEAR);
    }
}
