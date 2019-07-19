package com.nstudio.temigreetings.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nstudio.temigreetings.R;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private int[] icons;
    private String[] titles;
    private OnMenuOptionClickListener clickListener;

    public MenuAdapter(int[] icons, String[] titles, OnMenuOptionClickListener clickListener) {
        this.icons = icons;
        this.titles = titles;
        this.clickListener = clickListener;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.imgIcon.setImageResource(icons[position]);
        holder.tvTitle.setText(titles[position]);


    }

    @Override
    public int getItemCount() {
        return icons.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imgIcon;
        TextView tvTitle;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(titles[getAdapterPosition()],MyViewHolder.this);
                }
            });
        }
    }



    public interface OnMenuOptionClickListener{
        void onClick(String action,MyViewHolder holder);
    }

}
