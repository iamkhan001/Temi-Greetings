package com.nstudio.temigreetings.windows;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nstudio.temigreetings.R;
import com.nstudio.temigreetings.adapter.LocationListAdapter;

import java.util.List;
import java.util.Objects;

/**
 * Created by Imran on 06-Nov-17.
 */

public class ListLocationsWindow extends Dialog {


    private Context context;
    private OnLocationSelectListener selectListener;
    private List<String> locations;

    public ListLocationsWindow(@NonNull Context context, List<String> locations, OnLocationSelectListener selectListener) {
        super(context);
        this.context = context;
        this.selectListener = selectListener;
        this.locations = locations;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Objects.requireNonNull(getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.window_list_loaction);
        Objects.requireNonNull(getWindow()).setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(context.getResources().getDrawable(R.drawable.window_bg));


        findViewById(R.id.tvTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        RecyclerView rvLocation = findViewById(R.id.rvLocations);
        rvLocation.setLayoutManager(new LinearLayoutManager(context));

        LocationListAdapter adapter = new LocationListAdapter(context,locations,selectListener);

        rvLocation.setAdapter(adapter);



    }


    public interface OnLocationSelectListener{
        void onLocationSelect(String name);
    }

}
