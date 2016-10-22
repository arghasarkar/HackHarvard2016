package com.example.khang.hackharvardd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();

    ImageView ivCamera, ivGallery, ivUpload, ivImage;

    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;

    final int CAMERA_REQUEST = 13323;
    final int GALLERY_REQUEST = 22131;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        ivImage = (ImageView)findViewById(R.id.ivImage);
        ivCamera = (ImageView)findViewById(R.id.ivCamera);
        ivGallery = (ImageView)findViewById(R.id.ivGallery);
        ivUpload = (ImageView)findViewById(R.id.ivUpload);

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Wrong while taking photos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });

        //ivUpload.setOnClickListener();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){
                String photoPath = cameraPhoto.getPhotoPath();
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    ivImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Wrong while loading photos", Toast.LENGTH_SHORT).show();
                }

            }
            else if (requestCode == GALLERY_REQUEST){
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    ivImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Wrong while choosing photos", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

}
