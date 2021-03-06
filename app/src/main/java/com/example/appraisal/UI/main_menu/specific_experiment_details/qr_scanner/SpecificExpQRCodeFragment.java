package com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appraisal.R;

/**
 * This fragment shows the QR tab in the specific experiment section
 */
public class SpecificExpQRCodeFragment extends Fragment {
    // This activity wouldn't be possible without Google's ML kit and its documentations
    // Author: Google
    // URL: https://developers.google.com/ml-kit/vision/barcode-scanning/android

    private Activity parent_activity;

    /**
     * This method sets the parent activity for this fragment
     *
     * @param savedInstanceState -- prevoius saved instance state
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent_activity = getActivity();
    }

    /**
     * This method creates the view for the QR code fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

    /**
     * This methods gets called Generate QR button is clicked
     */
    private void startQRCodeGenerator() {
        Intent intent = new Intent(parent_activity, QRPromptActivity.class);
        startActivity(intent);
    }

    /**
     * This method gets called when Scan code button is clicked
     */
    private void startCameraScanner() {
        Intent intent = new Intent(parent_activity, CameraScanResultActivity.class);
        startActivity(intent);
    }

    /**
     * This method gets called when Register Bar code button is clicked
     */
    private void startCodeRegister() {
        Intent intent = new Intent(parent_activity, RegisterBarcodeResultActivity.class);
        startActivity(intent);
    }
}
