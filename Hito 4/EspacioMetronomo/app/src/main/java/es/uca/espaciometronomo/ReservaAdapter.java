package es.uca.espaciometronomo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class ReservaAdapter {
    private ArrayList<Reserva> reservas;
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView dni;
        TextView telefono;
        TextView email;
        Date fecha;
        TextView motivo;
        public MyViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre);
            dni = (TextView) v.findViewById(R.id.dni);
            telefono = (TextView) v.findViewById(R.id.telefono);
            email = (TextView) v.findViewById(R.id.email);
            fecha = (Date) v.findViewById(R.id.fecha);
            motivo = (TextView) v.findViewById(R.id.motivo);
        }

        public ReservaAdapter(ArrayList<Reserva> myDataset) {
            reservas = myDataset;
        }

        @Override
        public ReservaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int
                viewType) {
            View v =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                            parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            context = parent.getContext();
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.number.setText(String.valueOf(reservas.get(position).getNumber()));
            holder.name.setText(reservas.get(position).getName());
            holder.show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence text = "You've clicked on " +
                            pokemons.get(position).getName();
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return pokemons.size();
        }
    }
}
