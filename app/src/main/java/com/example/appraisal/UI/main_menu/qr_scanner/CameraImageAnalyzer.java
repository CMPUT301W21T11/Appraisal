package com.example.appraisal.UI.main_menu.qr_scanner;

import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public class CameraImageAnalyzer implements ImageAnalysis.Analyzer {

    @androidx.camera.core.ExperimentalGetImage
    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {
        Image media_image = imageProxy.getImage();
        if (media_image != null) {
            InputImage image = InputImage.fromMediaImage(media_image, imageProxy.getImageInfo().getRotationDegrees());

            BarcodeScanner scanner = BarcodeScanning.getClient();
            Task<List<Barcode>> result = scanner.process(image)
                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            for (Barcode barcode: barcodes) {
                                analyzeBarCode(barcode);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Error:", "Barcode scanning failed.");
                            e.printStackTrace();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                        @Override
                        public void onComplete(@NonNull Task<List<Barcode>> task) {
                            imageProxy.close();
                        }
                    });
        }
    }

    private void analyzeBarCode(Barcode barcode) {
    }

}
