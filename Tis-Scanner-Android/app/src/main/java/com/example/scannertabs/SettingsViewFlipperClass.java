package com.example.scannertabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.example.scannertabs.Settings.SettingsClass;
import com.example.scannertabs.Settings.SettingsPasswordClass;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SettingsViewFlipperClass extends mFragment {

    String language;
    ViewFlipper viewFlipper;
    SettingsClass settingsClass;
    SettingsPasswordClass settingsPasswordClass;

    SettingsViewFlipperClass(String language) {
        this.language = language;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_view_flipper, container, false);

        viewFlipper = view.findViewById(R.id.SettingsViewFlipper);

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View passwordLayout = factory.inflate(R.layout.settings_password, null);
        settingsPasswordClass = new SettingsPasswordClass(language, passwordLayout, getActivity(), getContext(), viewFlipper);

        final View settingsLayout = factory.inflate(R.layout.settings_layout, null);
        settingsClass = new SettingsClass(language, settingsLayout, getActivity(), getContext());

        viewFlipper.addView(passwordLayout);
        viewFlipper.addView(settingsLayout);

        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right));

        return view;
    }
}
