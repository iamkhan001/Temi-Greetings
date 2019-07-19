package com.nstudio.temigreetings.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nstudio.temigreetings.R;
import com.nstudio.temigreetings.utils.ColorPicker;
import com.nstudio.temigreetings.windows.ListLocationsWindow;

import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.MyViewHolder>{

    private Context context;
    private List<String> locations;
    private ListLocationsWindow.OnLocationSelectListener selectListener;

    public LocationListAdapter(Context context, List<String> locations, ListLocationsWindow.OnLocationSelectListener selectListener) {
        this.context = context;
        this.locations = locations;
        this.selectListener = selectListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String name = locations.get(position);

        char icon;
        holder.tvName.setText(name);
        icon=name.charAt(0);

        holder.bgShape.setColor(Color.parseColor(ColorPicker.getColor(icon)));
        holder.tvIcon.setText(icon+"");
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvIcon,tvName;
        private GradientDrawable bgShape;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIcon = itemView.findViewById(R.id.tvIcon);
            tvName = itemView.findViewById(R.id.tvName);
            bgShape = (GradientDrawable) tvIcon.getBackground();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectListener.onLocationSelect(locations.get(getAdapterPosition()));
                }
            });
        }
    }

}
