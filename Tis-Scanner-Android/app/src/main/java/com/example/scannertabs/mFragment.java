package com.example.scannertabs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class mFragment extends Fragment implements FragmentLifecycle {
    private static boolean active = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        active = true;
    }

    @Override
    public void onPauseFragment() {
        hideKeyboard(getActivity());
    }

    @Override
    public void onResumeFragment() {

    }

    public static void hideKeyboard(Activity activity) {
        if (active) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}


