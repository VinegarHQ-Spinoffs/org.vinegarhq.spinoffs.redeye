package org.vinegarhq.redeye;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.canhub.cropper.CropImageView;

import java.io.File;
import java.util.Objects;

public class EditImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        Uri imageUri;
        File imageFile;
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

        SeekBar onionBar = findViewById(R.id.onionBar);
        ImageView onionImage = findViewById(R.id.onionImage);
        onionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                onionImage.setImageAlpha((int) Math.floor(255 * (progress / 100.0)));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });

    }
}