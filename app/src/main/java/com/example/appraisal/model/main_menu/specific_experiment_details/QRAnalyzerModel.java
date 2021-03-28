package com.example.appraisal.model.main_menu.specific_experiment_details;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.trial.Trial;
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
    int firebase_num_trials = 0;

    public QRAnalyzerModel(@NonNull Activity parent_activity) {
        this.parent_activity = parent_activity;
        this.qr_code_image = parent_activity.findViewById(R.id.activity_camera_scan_result_qr_code_display);
    }


    /**
     * This method displays the detected Barcode and its raw value to the parent activity
     * @param result -- the detection result
     * @throws WriterException -- when MultiFormatWriter refuse to write
     */
    public void readingQRCode(@NonNull Result result) throws WriterException {
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

        String[] commands = decodeTrialQR(qr_display);
    }

    /**
     * Obtain the commands in the string passed in the QR
     * 0 index is for Type (binomial, count,...)
     * 1 index value {binomial -> "0/1" (0 for fail, 1 for success)
     *                count -> "int number"
     *                non-neg -> "int num"
     *                measurement -> "double"
     *                }
     * 2 the experiment ID.
     * @param encoded_info
     * @return
     */
    public String[] decodeTrialQR(String encoded_info) {
        String[] commands = encoded_info.split(";");


        return commands;
    }

    /**
     * From here, acquire everything related to the experiment via Listener query.
     * Create an dummy experiment and load it into MainModel's target_qr_experiment
     * Make the Trial object.
     * After that, add the Trial, and upload.
     *
     * @param target_qr
     * @param just_created
     */
    public void modifyExperiment(Experiment target_qr, Trial just_created) {
        // TODO
    }

    public boolean isRegisteredCode(Result result) {
        // TODO: check if the barcode is registered, and perform corresponding action
        return true;
    }
}
