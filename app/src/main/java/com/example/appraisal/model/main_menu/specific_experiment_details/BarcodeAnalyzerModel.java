package com.example.appraisal.model.main_menu.specific_experiment_details;

import android.app.Activity;
import android.util.Log;

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

    private BarcodeScanResult parent_activity;

    public BarcodeAnalyzerModel(@NonNull BarcodeScanResult parent_activity) {
        super(parent_activity);
        this.parent_activity = parent_activity;
    }

    public void checkBarcode(@NonNull Barcode barcode) {
        String raw_value = barcode.getRawValue();
        try {
            String exp_id = barcode.getCurrentExperiment().getExpId();
            DocumentReference user = MainModel.getUserReference();
            CollectionReference barcode_list = user
                    .collection("Experiments")
                    .document(exp_id)
                    .collection("Barcodes");

            if (barcode_list == null) {
                addToDatabase(barcode_list, barcode);
            }

            barcode_list.addSnapshotListener((value, error) -> {
                if (value == null) {
                    error.printStackTrace();
                    return;
                }
                for (QueryDocumentSnapshot document: value) {
                    String document_value = document.getId();
                    String old_value = document.get("action").toString();
                    if (document_value.equalsIgnoreCase(raw_value)) {
                        boolean doOverride = parent_activity.askIfOverride(barcode, old_value);
                        if (doOverride) {
                            barcode_list.document(raw_value).set(barcode.getData());
                        } else {
                            return;
                        }
                    } else {
                        addToDatabase(barcode_list, barcode);
                    }
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
    }

    private void createBarcodeList(DocumentReference user) {

    }
}
