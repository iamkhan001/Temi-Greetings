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
import com.nstudio.temigreetings.R;

import java.util.Objects;

/**
 * Created by Imran on 06-Nov-17.
 */

public class SaveLocationWindow extends Dialog {


    private Context context;
    private OnLocationSaveListener listener;

    public SaveLocationWindow(@NonNull Context context,OnLocationSaveListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
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
        setContentView(R.layout.window_save_location);
        Objects.requireNonNull(getWindow()).setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(context.getResources().getDrawable(R.drawable.window_bg));


        final EditText etName = findViewById(R.id.etName);


        findViewById(R.id.tvTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                if (name.isEmpty()){
                    etName.setError("Enter Location Name");
                    return;
                }

                listener.onSave(name);
                dismiss();
            }
        });
    }


    public interface OnLocationSaveListener{
        void onSave(String name);
    }

}
