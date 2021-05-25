package es.uca.espaciometronomo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder>{
    private ArrayList<Room> salas;
    private Context context;

    public RoomAdapter(ArrayList<Room> myDataset) {
        salas = myDataset;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;

        public MyViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            description = (TextView) v.findViewById(R.id.description);
        }
    }
    @Override
    public RoomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        context = parent.getContext();
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(salas.get(position).getName());
        holder.description.setText(salas.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return salas.size();
    }
}
