package org.vinegarhq.redeye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageProxy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.canhub.cropper.CropImageView;

import java.io.File;
import java.util.Objects;

public class EditImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        Uri imageUri = null;
        File imageFile = null;
        File cachePath = null;

        if (getIntent().getExtras()!= null) {
            imageFile = new File(Objects.requireNonNull(getIntent().getStringExtra("imageFile")));
            cachePath = new File(Objects.requireNonNull(getIntent().getStringExtra("cachePath")));
            imageUri = Uri.fromFile(imageFile);
        } else {
            throw new RuntimeException();
        }

        CropImageView civ = findViewById(R.id.cropimageView);
        civ.setImageUriAsync(imageUri);
        //TODO: Fix orientation.
    }
}