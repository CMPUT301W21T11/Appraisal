package com.example.appraisal.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appraisal.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * This class parses and display the result from the camera scanner
 */
public class QRAnalyzerModel {

    private final Activity parent_activity;
    private final ImageView qr_code_image;

    public QRAnalyzerModel(@NonNull Activity parent_activity) {
        this.parent_activity = parent_activity;
        this.qr_code_image = parent_activity.findViewById(R.id.activity_camera_scan_result_qr_code_display);
    }


    /**
     * This method displays the detected Barcode and its raw value to the parent activity
     * @param result -- the detection result
     * @throws WriterException -- when MultiFormatWriter refuse to write
     */
    public void displayQRCode(@NonNull Result result) throws WriterException {
        String qr_display = result.getText();

        // Display the raw value of the code to activity
        TextView raw_value_display = parent_activity.findViewById(R.id.camera_scan_result_raw_value_display);
        raw_value_display.setText(qr_display);

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix bitMatrix;
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        if (result.getBarcodeFormat() == BarcodeFormat.QR_CODE) {
            // Since for QR code there is a chance of being our generated code, need to handle differently
            bitMatrix = writer.encode(qr_display,
                    result.getBarcodeFormat(),
                    300,
                    300); // DO NOT CHANGE WIDTH AND HEIGHT VALUES
        } else {
            // Display other type of barcode
            bitMatrix = writer.encode(qr_display,
                    result.getBarcodeFormat(),
                    700,
                    300); // DO NOT CHANGE WIDTH AND HEIGHT VALUES
        }
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        Log.d("CameraScanResult:", "bitmap generation complete");
        qr_code_image.setImageBitmap(bitmap);
    }

    public boolean decodeTrialQR(Result result) {
        // TODO: decode QR code
        return true;
    }

    public boolean isRegisteredCode(Result result) {
        // TODO: check if the barcode is registered, and perform corresponding action
        return true;
    }
}
