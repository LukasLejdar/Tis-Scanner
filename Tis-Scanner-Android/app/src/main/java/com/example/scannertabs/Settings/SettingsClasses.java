package com.example.scannertabs.Settings;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public abstract class SettingsClasses {

    final String language;
    final Activity activity;
    final Context context;
    final View view;

    SettingsClasses(String language, View view, final Activity activity, Context context) {
        this.view = view;
        this.activity = activity;
        this.context = context;
        this.language = language;
    }
}
