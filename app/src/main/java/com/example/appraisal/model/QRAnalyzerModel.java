package com.example.appraisal.model;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.appraisal.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

public class QRAnalyzerModel {
    private final CodeScanner code_scanner;
    private final CodeScannerView view;
    private final Context parent_context;

    public QRAnalyzerModel(@NonNull Context context, @NonNull CodeScannerView view) {
        code_scanner = new CodeScanner(context, view);
        parent_context = context;
        this.view = view;
    }

    public QRAnalyzerModel(@NonNull Context context, @NonNull CodeScannerView view, int cameraId) {
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
            MainModel.setBarcodeResult(result.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
