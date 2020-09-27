package com.example.scannertabs.Settings;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.scannertabs.R;

public class SettingsPasswordClass extends SettingsClasses {

    public SettingsPasswordClass(String language, View view, Activity activity, final Context context, final ViewFlipper viewFlipper) {
        super(language, view, activity, context);

        Button check = view.findViewById(R.id.check);
        final TextView passwordView = view.findViewById(R.id.passwordView);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordView.getText().toString().equals(context.getString(R.string.password))) {
                    viewFlipper.showPrevious();

                } else {
                    Toast.makeText(context.getApplicationContext(), "špatné heslo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
