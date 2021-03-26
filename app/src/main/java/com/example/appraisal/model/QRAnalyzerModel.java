package com.example.appraisal.model;

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

import java.io.Serializable;
import java.util.List;
import java.util.Observable;

public class QRAnalyzerModel implements ImageAnalysis.Analyzer {
    // A lot of the code are referenced from this blog post:
    // Author: Miguel Lasa (URL: https://www.linkedin.com/in/miguellasa/)
    // URL: https://miguel-lasa.medium.com/barcode-scanner-with-camerax-and-mlkit-bde53fbc2b8f

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
        } else {
            imageProxy.close();
        }
    }

    private void analyzeBarCode(Barcode barcode) {
    }

}
