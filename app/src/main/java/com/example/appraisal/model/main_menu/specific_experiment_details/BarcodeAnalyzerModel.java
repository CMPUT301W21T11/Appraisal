package com.example.appraisal.model.main_menu.specific_experiment_details;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appraisal.UI.main_menu.specific_experiment_details.qr_scanner.BarcodeScanResult;
import com.example.appraisal.backend.specific_experiment.Barcode;
import com.example.appraisal.model.core.MainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class BarcodeAnalyzerModel extends QRAnalyzerModel{

    private final BarcodeScanResult parent_activity;

    public BarcodeAnalyzerModel(@NonNull BarcodeScanResult parent_activity) {
        super(parent_activity);
        this.parent_activity = parent_activity;
    }

    /**
     * This method will add the registered barcode to the database
     * If the barcode is already registered we ask the user if wants to overwrite
     * @param barcode -- barcode object
     */
    public void checkBarcode(@NonNull Barcode barcode) {
        String raw_value = barcode.getRawValue();
        try {
            String exp_id = barcode.getCurrentExperiment().getExpId();
            DocumentReference user = MainModel.getUserReference();
            CollectionReference barcode_list = user
                    .collection("Experiments")
                    .document(exp_id)
                    .collection("Barcodes");

            barcode_list.addSnapshotListener((value, error) -> {
                if (value == null) {
                    // if either the collection is empty or not yet created
                    if (error != null) {
                        Log.d("Barcode Analyzer Model", "Error returned from firebase");
                        error.printStackTrace();
                    }
                    addToDatabase(barcode_list, barcode); // Add the barcode to firebase
                } else {
                    // check if barcode is already in the collection
                    for (QueryDocumentSnapshot document: value) {
                        String document_value = document.getId();
                        String old_value = document.get("action").toString();
                        if (document_value.equalsIgnoreCase(raw_value)) { // if the barcode is found
                            boolean doOverride = parent_activity.askIfOverride(barcode, old_value); // ask user if they want to override
                            // if user declined override, return
                            if (doOverride) {
                                barcode_list.document(raw_value).set(barcode.getData()); // override data
                            } else {
                                Toast.makeText(parent_activity, "Barcode value is NOT overriden", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }
                    }
                    addToDatabase(barcode_list, barcode); // Add the barcode to firebase
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addToDatabase(@NonNull CollectionReference barcode_list, @NonNull Barcode barcode) {
        Map<String, String> barcode_data = new HashMap<>();
        barcode_data.put("action", barcode.getData());
        barcode_list.add(barcode_data);
        Toast.makeText(parent_activity, "Successfully set barcode action!", Toast.LENGTH_SHORT).show();
    }
}
