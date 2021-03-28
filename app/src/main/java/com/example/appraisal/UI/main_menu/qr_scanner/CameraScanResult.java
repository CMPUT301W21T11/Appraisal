package com.example.appraisal.UI.main_menu.qr_scanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.budiyev.android.codescanner.BarcodeUtils;
import com.example.appraisal.R;
import com.example.appraisal.model.MainModel;
import com.example.appraisal.model.trial.QRAnalyzerModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class CameraScanResult extends AppCompatActivity {

    private final int CAMERA_SCANNER_REQUEST_CODE = 1;

    private ImageView qr_code_image;
    private QRAnalyzerModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scan_result);

        qr_code_image = findViewById(R.id.activity_camera_scan_result_qr_code_display);
        model = new QRAnalyzerModel();

        // start camera scanner immediately
        Intent intent = new Intent(this, CameraScanner.class);
        startActivityForResult(intent, CAMERA_SCANNER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_SCANNER_REQUEST_CODE) { // we are only processing camera scanner activity
            try {
                MultiFormatWriter writer = new MultiFormatWriter();
                Result result = MainModel.getBarcodeResult(); // get result bar code

                String qr_display = result.getText(); // get the message as text

                // We are gonna display the detected code on the activity
                // We will set it as a bitmap
                BitMatrix bitMatrix;
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                boolean isSuccess;
                if (result.getBarcodeFormat() == BarcodeFormat.QR_CODE) {
                    // Since for QR code there is a chance of being our generated code, need to handle differently
                    bitMatrix = writer.encode(qr_display,
                            result.getBarcodeFormat(),
                            300,
                            300); // DO NOT CHANGE WIDTH AND HEIGHT VALUES
                    isSuccess = model.decodeTrialQR(result);
                    if (!isSuccess) {
                        isSuccess = model.isRegisteredCode(result);
                    }
                } else {
                    // Display other type of barcode
                    bitMatrix = writer.encode(qr_display,
                            result.getBarcodeFormat(),
                            300,
                            600); // DO NOT CHANGE WIDTH AND HEIGHT VALUES
                    isSuccess = model.isRegisteredCode(result);
                }
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                Log.d("CameraScanResult:", "bitmap generation complete");
                qr_code_image.setImageBitmap(bitmap);

                //TODO: Once QR code decoding has finished, need to perform corresponding query
            } catch (Exception e) {
                Log.e("CameraScanResult:","Error when processing QR codes: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}