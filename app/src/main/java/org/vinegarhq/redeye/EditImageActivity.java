package org.vinegarhq.redeye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageProxy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.media.Image;
import android.os.Bundle;
import android.widget.Toast;

import com.canhub.cropper.CropImageView;
public class EditImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        Bitmap bitmap = null;

        if (getIntent().getExtras()!= null) {
            byte[] byteArray = getIntent().getByteArrayExtra("bitmap");

            if (byteArray != null) {

                bitmap = BitmapFactory.decodeByteArray(
                        byteArray,
                        0,
                        byteArray.length
                );
            } else {
                throw new NullPointerException();
            }
        }

        CropImageView civ = findViewById(R.id.cropimageView);
        civ.setImageBitmap(bitmap);
        //TODO: Fix orientation.
    }
}