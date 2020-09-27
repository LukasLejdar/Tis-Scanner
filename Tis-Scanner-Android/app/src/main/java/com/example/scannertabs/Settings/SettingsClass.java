package com.example.scannertabs.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scannertabs.R;

public class SettingsClass extends SettingsClasses implements PopupMenu.OnMenuItemClickListener{
    Button menu;
    EditText settingsUrl;

    SharedPreferences.Editor editor;
    SharedPreferences sharedpref;

    public SettingsClass(String language, View view, final Activity activity, Context context) {
        super(language, view, activity, context);

        menu = (Button) view.findViewById(R.id.menu);
        menu.setText(language, Button.BufferType.NORMAL);

        sharedpref = context.getSharedPreferences(activity.getString(R.string.sharedPreferences), 0); // 0 - for private mode
        editor = sharedpref.edit();

        settingsUrl = (EditText) view.findViewById(R.id.SettingsUrl);
        settingsUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch (i){
                    case EditorInfo.IME_ACTION_DONE:
                        editor.putString(activity.getString(R.string.Serverurl), textView.getText().toString());
                }
                return false;
            }
        });

        menu = (Button) view.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(activity, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.ToastLanguage),Toast.LENGTH_SHORT).show();

        switch (menuItem.getItemId()) {
            case R.id.item1:
                editor.putString(activity.getString(R.string.language), "");
                editor.commit();
                return true;

            case R.id.item2:
                editor.putString(activity.getString(R.string.language), "en");
                editor.commit();
                return true;

            default:
                return false;
        }
    }
}
