package com.bottlebattle.bottlebattle.Activities.RecycleItemActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.widget.Button;

import com.bottlebattle.bottlebattle.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutionException;
import android.media.MediaPlayer;


public class ScanningBarcodeActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_barcode);



        // First, we check whether the user has granted 'camera' permission:
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // If the user hasn't granted 'camera' permission, we have to request for it:
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }


        PreviewView previewView = findViewById(R.id.previewView);
        Button captureButton = findViewById(R.id.captureButton);

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider, previewView);
            } catch (ExecutionException | InterruptedException e) {
                // Error
            }
        }, ContextCompat.getMainExecutor(this));

        captureButton.setOnClickListener(v -> captureImage());
    }


    // Binding the preview to the camera, so that we can simulatanously display camera feed:
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider, PreviewView previewView) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        imageCapture = new ImageCapture.Builder().setTargetResolution(new Size(1280, 720)).build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
    }



    // Handling capture of image:
    private void captureImage() {
        if (imageCapture == null) {
            return;
        }

        imageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                Bitmap bitmap = imageProxyToBitmap(image);
                Log.d("captureImage", "captureImage: ");
                processImage(bitmap);
                image.close();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e("BarcodeScanner", "Image capture failed", exception);
            }
        });
    }

    // This function converts the ImageProxy object to a Bitmap object (that Firebase ML-kit works with):
    private Bitmap imageProxyToBitmap(ImageProxy image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
    }

    private void processImage(Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);

        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                                Barcode.FORMAT_QR_CODE,
                                Barcode.FORMAT_CODE_128,
                                Barcode.FORMAT_CODE_39,
                                Barcode.FORMAT_CODE_93,
                                Barcode.FORMAT_EAN_13,
                                Barcode.FORMAT_EAN_8,
                                Barcode.FORMAT_UPC_A,
                                Barcode.FORMAT_UPC_E,
                                Barcode.FORMAT_PDF417,
                                Barcode.FORMAT_AZTEC)
                        .build();

        BarcodeScanner scanner = BarcodeScanning.getClient(options);

        scanner.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        String rawValue = "No barcode found in the image";


                        //Toast.makeText(ScanningBarcodeActivity.this, "in general", Toast.LENGTH_SHORT).show();

                        if (!barcodes.isEmpty()) {
                            //Toast.makeText(ScanningBarcodeActivity.this, "barcodes not empty", Toast.LENGTH_SHORT).show();
                            // Get the first barcode found in the image:
                            Barcode barcode = barcodes.get(0);
                            rawValue = barcode.getRawValue();
                            // Toast the barcode value:
                            //Toast.makeText(ScanningBarcodeActivity.this, "First barcode found: " + rawValue, Toast.LENGTH_SHORT).show();

                            mediaPlayer = MediaPlayer.create(ScanningBarcodeActivity.this, R.raw.successsound);
                            mediaPlayer.start();

                        } else {

                            mediaPlayer = MediaPlayer.create(ScanningBarcodeActivity.this, R.raw.errorsound);
                            mediaPlayer.start();

                            //Toast.makeText(ScanningBarcodeActivity.this, "barcodes empty", Toast.LENGTH_SHORT).show();

                            // Indicate the user that the a barcode was not found in the image he has taken:
                            //Toast.makeText(ScanningBarcodeActivity.this, "No barcode found in the image", Toast.LENGTH_SHORT).show();
                        }
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("key", rawValue);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();

                        Log.d("processImage", barcodes.size() + " ");
                        // This loop is for TESTING purposes, it will print ALL the barcodes found in the image:
                        /*for (Barcode barcode : barcodes) {
                            String rawValue = barcode.getRawValue();
                            // Handle the scanned barcode value:
                            Log.d("BarcodeScanner", "Barcode value: " + rawValue);
                            // Also toast the barcode value:
                            //Toast.makeText(ScanningBarcodeActivity.this, "Barcode value: " + rawValue, Toast.LENGTH_SHORT).show();

                        }*/
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("BarcodeScanner", "Barcode scanning failed", e);
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            processImage(imageBitmap);



        }
    }





}