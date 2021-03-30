package com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appraisal.R;

public class SpecificExpQRCodeFragment extends Fragment {
    // This activity wouldn't be possible without Google's ML kit and its documentations
    // Author: Google
    // URL: https://developers.google.com/ml-kit/vision/barcode-scanning/android

    private Activity parent_activity;
    private TextView trial_value_qr_input;

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

        trial_value_qr_input = v.findViewById(R.id.trial_value_qr_input);

        return v;
    }

    private void startQRCodeGenerator() {
        if (trial_value_qr_input.getText().toString().equals("")) {
            return;
        }
        else {
            Intent intent = new Intent(parent_activity, QRGeneratorActivity.class);

            String value = trial_value_qr_input.getText().toString();
            intent.putExtra("val", value);

            startActivity(intent);
        }
    }

    private void startCameraScanner() {
        Intent intent = new Intent(parent_activity, CameraScanResult.class);
        startActivity(intent);
    }

    private void startCodeRegister() {

    }
}
