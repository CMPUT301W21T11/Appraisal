package com.example.appraisal.model.main_menu.specific_experiment_details;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.appraisal.model.core.MainModel;
import com.google.zxing.Result;

public class CameraScannerModel {
    private final CodeScanner code_scanner;
    private final CodeScannerView view;
    private final Context parent_context;

    public CameraScannerModel(@NonNull Context context, @NonNull CodeScannerView view) {
        code_scanner = new CodeScanner(context, view);
        parent_context = context;
        this.view = view;
    }

    public CameraScannerModel(@NonNull Context context, @NonNull CodeScannerView view, int cameraId) {
        code_scanner = new CodeScanner(context, view, cameraId);
        parent_context = context;
        this.view = view;
    }

    /**
     * This function set the decode call back for the code scanner
     * @param callback -- decode call back
     */
    public void setDecodeCallback(DecodeCallback callback) {
        code_scanner.setDecodeCallback(callback);
    }

    /**
     * This function enables code scanner to be refreshed when tapped
     */
    public void enableCodeScannerViewRefresh() {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_scanner.startPreview();
            }
        });
    }

    /**
     * This function starts the preview of the code scanner
     */
    public void startScanner() {
        code_scanner.startPreview();
    }

    /**
     * This function pauses the code scanner and release its resources
     */
    public void pauseScanner() {
        code_scanner.releaseResources();
    }

    /**
     * This function stores the result to main model
     */
    public void storeBarCode(Result result) {
        try {
            MainModel.setBarcodeResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
