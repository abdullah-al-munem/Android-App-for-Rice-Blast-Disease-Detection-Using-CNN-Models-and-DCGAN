package com.example.ricediseasedetection;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ricediseasedetection.MainActivity;
import com.example.ricediseasedetection.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;


public class predict extends AppCompatActivity {

    private ImageView uploadedImg;
    private String encodedImage, result;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("inside onCreate");
        setContentView(R.layout.predict);

        Button back = findViewById(R.id.backBtnPredict);
        Button solution = findViewById(R.id.suggestionBtn);
        solution.setVisibility(View.INVISIBLE);

        Button ChooseImage = findViewById(R.id.btnChooseImage);
        Button TakePicture = findViewById(R.id.btnTakePicture);
        Button btnPredictDisease = findViewById(R.id.btnPredictDisease);
        txtResult = findViewById(R.id.txtResult);

        uploadedImg = findViewById(R.id.uploadedImg);

//        byte[] byteArray = getIntent().getByteArrayExtra("image");
//        if (byteArray != null  && byteArray.length > 0){
//            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//            uploadedImg.setImageBitmap(bmp);
//        }

//        String imageUrl = "";
        //*String imageUrl = getIntent().getStringExtra("image");


        Intent i = getIntent();
        if(i.hasExtra("image"))
        {
            byte[] byteArray = getIntent().getByteArrayExtra("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            uploadedImg.setImageBitmap(bmp);
        }

        if(i.hasExtra("image_from_gallery"))
        {

            System.out.println("inside predic.java image from gallery");
//            byte[] byteArray = getIntent().getByteArrayExtra("image_from_gallery");
//            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//            uploadedImg.setImageBitmap(bmp);


            try {
                String absPath = i.getStringExtra("image_from_gallery");
                String path = i.getStringExtra("path_gallery");
                System.out.println("the absolute path is: "+absPath);
                System.out.println("the path is: "+path);

                File f=new File(absPath, path);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                System.out.println("final bitmap size : "+b.getRowBytes() * b.getHeight());
                uploadedImg.setImageBitmap(b);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }



        }

        //*Uri mImageUri = Uri.parse(imageUrl);




//*        if (imageUrl.equals("xx")){
//            System.out.println("x");
//        }
//        else{
//            System.out.println("here in if else");
//            Uri mImageUri = Uri.parse(imageUrl);
//            uploadedImg.setImageURI(mImageUri);
//        }


//        if (sentValue.equals("xx")){
//            System.out.println("x");
//        }
//        else{
//            System.out.println("here in if else");
//            byte[] byteArray1;
//            byteArray1 = Base64.decode(sentValue, Base64.DEFAULT);
//            Bitmap imageBitmap = BitmapFactory.decodeByteArray(byteArray1, 0,
//                    byteArray1.length);
//            uploadedImg.setImageBitmap(imageBitmap);
//        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(predict.this, MainActivity.class);

                startActivity(i);
                finish();
            }
        });
        solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(predict.this, solution.class);
                i.putExtra("prediction",result);
                startActivity(i);
                finish();
            }
        });


        TakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAndRequestPermission()){
                    takePictureFromCamera();
                }
            }
        });

        ChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAndRequestPermission()){
                    takePictureFromGallery();
                }
            }
        });


        btnPredictDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadedImg.getDrawable() != null) {

                    BitmapDrawable bitmapDrawable = (BitmapDrawable) uploadedImg.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();

                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);//final base 64 image string


                    try {
                        //String URL = "http://10.0.2.2:8000/allimages";
                    String URL = "http://192.168.215.210:8000/allimages";
//                    String URL = "http://10.0.2.2:8000/allimages";
//                    String URL = "http://127.0.0.1:8000/allimages";
//                    String URL = "http://192.168.0.109:8000/allimages";
                        //String URL = "http://192.43.165:8000/allimages";

                        Map<String, String> params = new HashMap();
                        params.put("image_base64", encodedImage);
                        System.out.println("encoded Image length: " + encodedImage.length());
                        JSONObject parameters = new JSONObject(params);


                        //create a request queue
                        RequestQueue requestQueue = Volley.newRequestQueue(predict.this);

                        JsonObjectRequest objectRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                URL,
                                parameters,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            result = response.getString("prediction");
                                            System.out.println("the predicted result is : " + response.getString("prediction"));
                                            String a = " ফলাফল: ";
                                            String resInBangla = "null";
                                            if (result.equals("Brown Spot")) {
                                                resInBangla = "বাদামীদাগ রোগ";
                                                solution.setVisibility(View.VISIBLE);
                                            } else if (result.equals("Leaf Blast")) {
                                                resInBangla = "ব্লাস্ট রোগ";
                                                solution.setVisibility(View.VISIBLE);
                                            } else if (result.equals("Healthy leaf")) {
                                                resInBangla = "নীরোগ পাতা";
                                            } else if (result.equals("Invalid input")) {
                                                resInBangla = "প্রদত্ত ছবি ধানের নয়";
                                            }
                                            txtResult.setText(a.concat(resInBangla));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //if you have any error
                                        System.out.println("rest response erorr" + error.toString());
                                    }
                                }


                        );
                        requestQueue.add(objectRequest);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

                else {
                    Toast.makeText(predict.this, "অনুগ্রহ করে প্রথমে একটি ছবি নির্বাচন করুন ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean checkAndRequestPermission(){
        if(Build.VERSION.SDK_INT >= 23){
            int cameraPermission = ActivityCompat.checkSelfPermission(predict.this, Manifest.permission.CAMERA);
            if(cameraPermission == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(predict.this, new String[]{Manifest.permission.CAMERA}, 20);
                return false;
            }
        }
        return true;
    }

    private void takePictureFromCamera(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 2);
    }


    private void takePictureFromGallery(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            takePictureFromCamera();
        }
        else
        {
            Toast.makeText(predict.this, "Permission not Granted", Toast.LENGTH_SHORT).show();
        }

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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, null, null);
        System.out.println("path issss "+path);
        return Uri.parse(path);


    }

    public Bitmap getImageUri1(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);


        return inImage;


        //                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                    byte[] imageBytes = byteArrayOutputStream.toByteArray();
//                    String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("11");
        switch (requestCode)
        {

            case 1:  //from gallery
                if(resultCode == RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    uploadedImg.setImageURI(selectedImageUri);
                    Intent intent = new Intent(predict.this, CropActivity.class);
                    intent.putExtra("image_from_gallery", selectedImageUri.toString());
                    startActivity(intent);






//                    BitmapDrawable bitmapDrawable = (BitmapDrawable) uploadedImg.getDrawable();
//                    Bitmap bitmapImage = bitmapDrawable.getBitmap();
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                    byte[] byteArray = stream.toByteArray();
//                    Intent intent = new Intent(predict.this, CropActivity.class);
//                    intent.putExtra("image",byteArray);
//                    startActivity(intent);

                }
                break;
            case 2:  //from camera
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmapImage = (Bitmap) bundle.get("data");
                    uploadedImg.setImageBitmap(bitmapImage);
//                    Uri ImageUri = getImageUri(bitmapImage);
//                    uploadedImg.setImageBitmap(bitmapImage);

//                    WeakReference<Bitmap> result1 = new WeakReference<>(Bitmap.createScaledBitmap(bitmapImage, bitmapImage.getHeight(),bitmapImage.getWidth(), false).copy(Bitmap.Config.RGB_565, true));
//                    Bitmap bitmapImage2 = result1.get();
//                    Uri imageUri = null;
                    //*Uri imageUri = getImageUri(predict.this, bitmapImage);



                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    //Bitmap imagebitmap = getImageUri1(predict.this, bitmapImage);
                    //*uploadedImg.setImageURI(imageUri);


                    Intent intent = new Intent(predict.this, CropActivity.class);
                    //*intent.putExtra("image",imageUri.toString());
                    intent.putExtra("image",byteArray);

                    startActivity(intent);





//                    try {
//                        imageUri = saveImage(bitmapImage2, predict.this);
//                        uploadedImg.setImageURI(imageUri);
//                        Intent intent = new Intent(predict.this, CropActivity.class);
//                        intent.putExtra("image",imageUri);
//
//                        startActivity(intent);
//                    } catch (IOException e) {
////                        e.printStackTrace();
//                        uploadedImg.setImageBitmap(bitmapImage);
//                    }


//                    Intent intent = new Intent(predict.this, CropActivity.class);

//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    byte[] byteArray = stream.toByteArray();
//                    intent.putExtra("image",imageUri);

//                    startActivity(intent);
//                    finish();
                }
                break;
        }
    }




}
