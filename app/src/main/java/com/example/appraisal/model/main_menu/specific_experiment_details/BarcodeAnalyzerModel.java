package com.example.appraisal.model.main_menu.specific_experiment_details;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.appraisal.model.core.MainModel;

import java.util.ArrayList;
import java.util.List;

public class BarcodeAnalyzerModel extends QRAnalyzerModel{


    public BarcodeAnalyzerModel(@NonNull Activity parent_activity) {
        super(parent_activity);
    }
    ArrayList<Barcode> assignedBarcodes = MainModel.getAssignedBarcodes();

    public boolean assignBarcode(Barcode barcode) {
        if (uniqueBarcode(barcode)) {
            assignedBarcodes.add(barcode);
            return true;
        }
        return false;
    }

    // check that this barcode hasn't been added by this user to another experiment
    public boolean uniqueBarcode(Barcode newBarcode) {
        for (Barcode barcode : assignedBarcodes) {
            // The set (rawValue, currentUser, currentExperiment) must be unique
            if (newBarcode.getRawValue().equals(barcode.getRawValue()) && newBarcode.getCurrentUser() == barcode.getCurrentUser() && newBarcode.getCurrentExperiment() == barcode.getCurrentExperiment()) {
                return false;
            }
        }
        return true;
    }

    // test code
    public void showBarcodes(){
        for (Barcode barcode : assignedBarcodes) {
            Log.d("barcodes: ", barcode.getRawValue() + " " + barcode.getCurrentUser() + " " + barcode.getCurrentExperiment() + " " + barcode.getData());
        }
    }
}
