package com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.model.core.MainModel;
import com.example.appraisal.model.main_menu.specific_experiment_details.QRAnalyzerModel;
import com.google.zxing.Result;

public class CameraScanResult extends AppCompatActivity {

    private final int CAMERA_SCANNER_REQUEST_CODE = 0x00000001;

    private QRAnalyzerModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scan_result);

        model = new QRAnalyzerModel(this);

        // start camera scanner immediately
        Intent intent = new Intent(this, CameraScanner.class);
        startActivityForResult(intent, CAMERA_SCANNER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_SCANNER_REQUEST_CODE) { // we are only processing camera scanner activity
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Result result = MainModel.getBarcodeResult(); // get result bar code
                    if (result == null) {// if null return
                        return;
                    }
                    // We are gonna display the detected code on the activity
                    model.readingQRCode(result);
                    String[] commands = model.decodeTrialQR(result.getText());
                    TextView trialType = findViewById(R.id.camera_scan_result_trial_type_display);
                    TextView trialValue = findViewById(R.id.camera_scan_result_trial_value_display);
                    Button finish_button = findViewById(R.id.camera_scan_result_finish_button);
                    if (commands != null && (commands.length > 4) && model.checkSignature(commands[0])) {
                        trialType.setText(commands[1]);
                        trialValue.setText(commands[2]);
                        finish_button.setText("ADD TO EXPERIMENT");
                        finish_button.setOnClickListener(v -> {
                            try {
                                model.addToExperiment(commands[3], commands[1], commands[2]);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            finish();
                        });
                    } else {
                        trialType.setText("Not recognized");
                        trialValue.setText("Not recognized");
                        finish_button.setOnClickListener(v -> finish());
                    }
                    //TODO: Once QR code decoding has finished, need to perform corresponding query
                } catch (Exception e) {
                    Log.e("CameraScanResult:", "Error when processing QR codes: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                finish();
            }
        }
    }
}