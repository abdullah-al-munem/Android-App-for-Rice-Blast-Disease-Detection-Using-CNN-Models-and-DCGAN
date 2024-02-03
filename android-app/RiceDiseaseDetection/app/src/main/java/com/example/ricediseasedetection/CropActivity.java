package com.example.ricediseasedetection;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CropActivity extends AppCompatActivity {
    CropUtils.CropImageView imageview1;
    int flag = 0;
    String absPath, path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        System.out.println("inside onCreate of CropActivity");

        Button button1 = findViewById(R.id.done_button);
        Button button2 = findViewById(R.id.back_button);
        imageview1 = findViewById(R.id.cropimageview);

//        String encodedImage2 = getIntent().getStringExtra("image");
//        byte[] byteArray = getIntent().getByteArrayExtra("image");
//        byte[] decodedString = Base64.decode(encodedImage2, Base64.DEFAULT);
//        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);


        //*String imageUrl = getIntent().getStringExtra("image");



        Intent i = getIntent();
        if(i.hasExtra("image_from_gallery"))
        {
            //for gallery
            String imageUrl = getIntent().getStringExtra("image_from_gallery");
            Uri mImageUri = Uri.parse(imageUrl);
            imageview1.setImageURI(mImageUri);
            flag = 1;
        }
        else
        {
            //for camera
            byte[] byteArray = getIntent().getByteArrayExtra("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0 , byteArray.length);
            imageview1.setImageBitmap(bmp);

        }



        //*Uri mImageUri = Uri.parse(imageUrl);







        //*imageview1.setImageURI(mImageUri);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = imageview1.getCroppedImage();
                byte[] byteArray = new byte[0]; //flag = 0
                Uri imageUri = null; // flag = 1
                if(flag==1)
                {
                    //gallery
                    System.out.println("ok1");


                    ContextWrapper cw = new ContextWrapper(getApplicationContext());
                    System.out.println("ok2");
                    // path to /data/data/yourapp/app_data/imageDir
                    File directory = cw.getDir("imageDir1", Context.MODE_PRIVATE);
                    System.out.println("ok3");
                    // Create imageDir

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

                    // on below line we are creating a variable
                    // for current date and time and calling a simple date format in it.
                    String currentDateAndTime = sdf.format(new Date());
                    String child1 = "galleryimage" + currentDateAndTime;
                    File mypath=new File(directory,child1);
                    System.out.println("ok4");

                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(mypath);
                        // Use the compress method on the BitMap object to write image to the OutputStream
                        //Bitmap croppedBmp = getImageUri1(CropActivity.this, bitmap);
                        System.out.println("initial bitmap size : "+bitmap.getRowBytes() * bitmap.getHeight());

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, fos);
                    } catch (Exception e) {
                        System.out.println("notok1");
                        e.printStackTrace();
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    path = child1;
                    absPath = directory.getAbsolutePath();







//
//                    //imageUri = getImageUri(CropActivity.this, bitmap);
//                    Bitmap croppedBmp = getImageUri1(CropActivity.this, bitmap);
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    croppedBmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                    byteArray = stream.toByteArray();

                }


                if(flag==0)
                {
                    //camera
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byteArray = stream.toByteArray();
                }



                //*Uri imageUri = getImageUri(CropActivity.this, bitmap);
                //Bitmap imageBitmap2 = getImageUri1(CropActivity.this, bitmap);
                Intent intent = new Intent(CropActivity.this, predict.class);
                //*intent.putExtra("image",imageUri.toString());
                //intent.putExtra("image",imageBitmap2.toString());
                if(flag ==1)
                {

                    ///intent.putExtra("image_from_gallery",imageUri.toString());

                    //*intent.putExtra("image_from_gallery",byteArray);
                    //*System.out.println("from gallery size is: "+byteArray.length);
                    intent.putExtra("image_from_gallery", absPath);
                    intent.putExtra("path_gallery", path);

                }
                else
                {
                    intent.putExtra("image",byteArray);
                    System.out.println("from camera size is: "+byteArray.length);
                }




                startActivity(intent);
                finish();
//                WeakReference<Bitmap> result1 = new WeakReference<>(Bitmap.createScaledBitmap(bitmap, bitmap.getHeight(),bitmap.getWidth(), false).copy(Bitmap.Config.RGB_565, true));
//                Bitmap bitmapImage2 = result1.get();
//                Uri imageUri = null;
//                try {
//                    imageUri = saveImage(bitmap, CropActivity.this);
//                    Intent intent = new Intent(CropActivity.this, predict.class);
//                    intent.putExtra("image",imageUri);
//
//                    startActivity(intent);
//                    finish();
//                } catch (IOException e) {
//                        e.printStackTrace();
//                }

//                Intent intent = new Intent(CropActivity.this, predict.class);
//
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                intent.putExtra("image",byteArray);
//
//                startActivity(intent);
//                finish();
            }


        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    private Uri saveImage(Bitmap image, Context context) throws IOException {

        File imagesFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;

        imagesFolder.mkdirs();
        File file = new File(imagesFolder, "captured_image.jpg");
        FileOutputStream stream = new FileOutputStream(file);
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        stream.flush();
        stream.close();
        uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.example.ricediseasedetection" + ".provider", file);
        return uri;
    }


    public Bitmap getImageUri1(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        return inImage;

    }




    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);

    }


}
