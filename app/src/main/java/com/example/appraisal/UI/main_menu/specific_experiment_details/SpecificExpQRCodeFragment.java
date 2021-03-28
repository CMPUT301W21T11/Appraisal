package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.qr_scanner.CameraScanner;

public class SpecificExpQRCodeFragment extends Fragment {
    // This activity wouldn't be possible without Google's ML kit and its documentations
    // Author: Google
    // URL: https://developers.google.com/ml-kit/vision/barcode-scanning/android

    private Activity parent_activity;
    private final int CAMERA_SCANNER_REQUEST_CODE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent_activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_specific_exp_qr_code, container, false);

        Button generate_button = v.findViewById(R.id.fragment_spec_exp_qr_code_generate_button);
        Button scan_button = v.findViewById(R.id.fragment_spec_exp_qr_code_scan_button);
        Button register_button = v.findViewById(R.id.fragment_spec_exp_qr_code_register_button);

        generate_button.setOnClickListener(v1 -> startQRCodeGenerator());
        scan_button.setOnClickListener(v2 -> startCameraScanner());
        register_button.setOnClickListener(v3 -> startCodeRegister());

        return v;
    }

    private void startQRCodeGenerator() {

    }

    private void startCameraScanner() {
        Intent intent = new Intent(parent_activity, CameraScanner.class);
        startActivityForResult(intent, CAMERA_SCANNER_REQUEST_CODE);
    }

    private void startCodeRegister() {

    }
}
