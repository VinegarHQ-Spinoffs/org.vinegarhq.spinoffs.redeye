package org.vinegarhq.redeye;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.FloatRange;
import androidx.appcompat.app.AppCompatActivity;

import com.canhub.cropper.CropImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.Objects;



public class EditImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        // Receive files from intent.
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


        // Set up crop image view.
        CropImageView civ = findViewById(R.id.cropimageView);
        civ.setImageUriAsync(imageUri);

        // Set up rotate button modes.
        FloatingActionButton rotateButton = findViewById(R.id.rotateButton);

        // yes i am aware enums exist, but at the cost of performance.
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                civ.rotateImage(90); // TODO: figure out how to do sub-90 rotations without breaking crop
            }
        });

        // Set up multiPurposeBar.
        SeekBar multiPurposeBar = findViewById(R.id.multiPurposeBar);
        ImageView onionImage = findViewById(R.id.onionImage);


        multiPurposeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    onionImage.setAlpha((float)(progress / 100.0));
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