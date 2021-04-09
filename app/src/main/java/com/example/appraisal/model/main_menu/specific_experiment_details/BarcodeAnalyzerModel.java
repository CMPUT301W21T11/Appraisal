package com.example.appraisal.model.main_menu.specific_experiment_details;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner.RegisterBarcodeResultActivity;
import com.example.appraisal.backend.specific_experiment.Barcode;
import com.example.appraisal.model.core.MainModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is the model for Barcode Scan Result
 */
public class BarcodeAnalyzerModel extends QRAnalyzerModel{

    private final RegisterBarcodeResultActivity parent_activity;
    private final String TARGET_EXP_ID_FIELD = "targetExperimentId";
    private final String TARGET_EXP_DESC_FIELD = "targetExperimentDesc";
    private final String TRIAL_TYPE_FIELD = "trialType";
    private final String ACTION_FIELD = "action";
    private CollectionReference barcode_list;

    public BarcodeAnalyzerModel(@NonNull RegisterBarcodeResultActivity parent_activity) {
        super(parent_activity);
        this.parent_activity = parent_activity;
    }

    /**
     * This method will add the registered barcode to the database
     * If the barcode is already registered we ask the user if wants to overwrite
     * @param barcode -- barcode object
     */
    public void checkBarcode(@NonNull Barcode barcode) {
        try {
            DocumentReference user = MainModel.getUserReference();
            barcode_list = user.collection("Barcodes"); // finds the barcode collection

            // try to find if the barcode already exist. If not, add it to firebase
            barcode_list.document(barcode.getRawValue()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot barcode_document = task.getResult();
                    if (barcode_document.exists()) {
                        String old_action = "Add " +
                                barcode_document.get(TRIAL_TYPE_FIELD).toString() +
                                ": " +
                                barcode_document.get(ACTION_FIELD).toString() +
                                " to " +
                                barcode_document.get(TARGET_EXP_DESC_FIELD).toString() +
                                " Experiment";

                        parent_activity.askIfOverride(barcode, old_action);
                    } else {
                        addToDatabase(barcode);
                    }
                } else {
                    Exception e = task.getException();
                    Log.e("Barcode Analyzer Model:", "barcode list query failed with the following message: " + e.getMessage());
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method adds the given barcode to the firebase
     * @param barcode -- the barcode to be added
     */
    public void addToDatabase(@NonNull Barcode barcode) {
        Map<String, String> barcode_data = new HashMap<>();
        barcode_data.put(TARGET_EXP_ID_FIELD, barcode.getCurrentExperiment().getExpId()); // set target experiment
        barcode_data.put(TARGET_EXP_DESC_FIELD, barcode.getCurrentExperiment().getDescription()); // set experiment description
        barcode_data.put(TRIAL_TYPE_FIELD, barcode.getCurrentExperiment().getType()); // set trial type
        barcode_data.put(ACTION_FIELD, barcode.getData()); // set field value
        barcode_list.document(barcode.getRawValue())
                .set(barcode_data)
                .addOnSuccessListener(aVoid -> Toast.makeText(parent_activity, "Successfully set barcode action!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(parent_activity, "Failed to set barcode: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                })
                .addOnCompleteListener(task -> {
                    // finish parent when the firebase is finished
                    parent_activity.finish();
                });
    }
}
