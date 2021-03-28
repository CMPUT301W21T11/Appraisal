package com.example.appraisal.model.main_menu.specific_experiment_details;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appraisal.R;
import com.example.appraisal.model.core.MainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class parses and display the result from the camera scanner
 */
public class QRAnalyzerModel {

    private final Activity parent_activity;
    private final ImageView qr_code_image;
    int firebase_num_trials = 0;
    private String experimenterID;
//    private CollectionReference experiment_reference;

    public QRAnalyzerModel(@NonNull Activity parent_activity) {
        this.parent_activity = parent_activity;
        this.qr_code_image = parent_activity.findViewById(R.id.activity_camera_scan_result_qr_code_display);

    }


    /**
     * This method displays the detected Barcode and its raw value to the parent activity
     * @param result -- the detection result
     * @throws WriterException -- when MultiFormatWriter refuse to write
     */
    public void readingQRCode(@NonNull Result result) throws Exception {
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
     */
    public String[] decodeTrialQR(String encoded_info) {
        String[] commands = encoded_info.split(";");
        if (!commands[0].equalsIgnoreCase(parent_activity.getResources().getString(R.string.app_name))) {
            //TODO
        }
        return commands;
    }

    /**
     * From here, acquire everything related to the experiment via Listener query.
     * Create an dummy experiment and load it into MainModel's target_qr_experiment
     * Make the Trial object.
     * After that, add the Trial, and upload.
     *
     * @param exp_id
     * @param trial_type
     * @param value
     */
    public void addToExperiment(String exp_id, String trial_type, String value) throws Exception {
        CollectionReference experiment_reference = MainModel.getExperimentReference();

        experiment_reference.document(exp_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if ((document != null) && document.exists()) {
                        try {
                            firebase_num_trials = Integer.parseInt(document.get("numOfTrials").toString());
                            Log.d("numtrials listener", String.valueOf(firebase_num_trials));
                            modifyExperiment(exp_id, trial_type, value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
    }

    private void modifyExperiment(String ID, String trial_type, String value) throws Exception {
        CollectionReference ref = MainModel.getExperimentReference();

        Map<String, Object> trial_info = new HashMap<>();

        int trial_count = firebase_num_trials + 1;
        String name = "Trial" + trial_count;


        // put trial date as current date
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String date_string = formatter.format(Calendar.getInstance().getTime());
        trial_info.put("date", date_string);


        try {
            experimenterID = MainModel.getCurrentUser().getID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        trial_info.put("experimenterID", experimenterID);

        // create new document for experiment with values from hash map
        ref.document(ID).collection("Trials").document(name).set(trial_info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("***", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("***", "Error writing document", e);
                    }
                });

        ref.document(ID).update("numOfTrials", FieldValue.increment(1));
    }

    public boolean isRegisteredCode(Result result) {
        // TODO: check if the barcode is registered, and perform corresponding action
        return true;
    }
}
