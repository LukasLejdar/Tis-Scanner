package com.example.scannertabs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.zxing.Result;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerClass extends mFragment implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;
    ImageButton lightButton;
    TextView Pacient;
    TextView Napoveda;
    Button confirm;

    private boolean light = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scanner_layout, container, false);

        scannerView = (ZXingScannerView) view.findViewById(R.id.ScannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();

        lightButton = (ImageButton) view.findViewById(R.id.flashlight);
        lightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (light) {
                    light = false;
                    lightButton.setBackground(getResources().getDrawable(R.drawable.flash_light_of_circle));
                    scannerView.setFlash(light);
                } else {
                    light = true;
                    lightButton.setBackground(getResources().getDrawable(R.drawable.flash_light_on_circle));
                    scannerView.setFlash(light);
                }
            }
        });

        confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(R.layout.incorrect_dialog_layout);
            }
        });

        Pacient = (TextView) view.findViewById(R.id.pacient);
        Napoveda = (TextView) view.findViewById(R.id.hint);

        return view;
    }

    @Override
    public void handleResult(Result result) {
        Pacient.setText(getString(R.string.Pacient) +" "+  result.getText());
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    private void showAlertDialog(int layout){
        View layoutView = getLayoutInflater().inflate(layout, null);
        Button dialogButton = layoutView.findViewById(R.id.btnDialog);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setView(layoutView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        final Vibrator[] v = new Vibrator[1];
        if (layout == R.layout.incorrect_dialog_layout) {
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    v[0] = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    long[] mVibratePattern = new long[]{0, 200, 200};
                    v[0].vibrate(mVibratePattern, 0);

                }
            });
        }

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                getActivity().finish();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                try {
                    v[0].cancel();
                } catch (Exception e) {}
            }
        });

        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }
}
