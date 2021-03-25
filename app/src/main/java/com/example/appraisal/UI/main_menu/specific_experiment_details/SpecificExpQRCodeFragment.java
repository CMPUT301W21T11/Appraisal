package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageAnalysis;
import androidx.fragment.app.Fragment;

import com.example.appraisal.R;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;

public class SpecificExpQRCodeFragment extends Fragment {
    // This activity wouldn't be possible without Google's ML kit and its documentations
    // Author: Google
    // URL1: https://developers.google.com/ml-kit/vision/barcode-scanning/android

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_specific_exp_qr_code, container, false);

        // enable all barcode format options
        BarcodeScannerOptions scanner_options =
                new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build();

        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        return v;
    }
}
