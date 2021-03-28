package com.example.appraisal.UI.main_menu.qr_scanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.appraisal.R;
import com.example.appraisal.model.MainModel;
import com.google.zxing.Result;

public class CamerScanResult extends AppCompatActivity {

    private final int CAMERA_SCANNER_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camer_scan_result);

        // start camera scanner immediately
        Intent intent = new Intent(this, CameraScanner.class);
        startActivityForResult(intent, CAMERA_SCANNER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_SCANNER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Result result;
                try {
                    result = MainModel.getBarcodeResult();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

            }
        }
    }
}