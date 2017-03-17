package com.dev.irving.imgurapi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dev.irving.imgurapi.helpers.IntentHelper;
import com.dev.irving.imgurapi.servicios.UploadService;
import com.example.irving.imgurapi.R;
import com.dev.irving.imgurapi.helpers.DocumentHelper;
import com.dev.irving.imgurapi.modeloimgur.ImageResponse;
import com.dev.irving.imgurapi.modeloimgur.Upload;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.imageview)
    ImageView uploadImage;
    @Bind(R.id.editText_titulo)
    EditText uploadTitle;
    @Bind(R.id.editText_descripcion)
    EditText uploadDesc;

    private Upload upload;
    private File chosenFile;
    static final Integer WRITE_EXST = 0x3;
    static final Integer READ_EXST = 0x4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        permismo_lectura();

    }
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission))
            {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else
            {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " Se acepto el permiso .", Toast.LENGTH_SHORT).show();
        }
    }
    public void permismo_lectura()
    {
        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_EXST);
        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,READ_EXST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==READ_EXST )
        {

            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Aceptas el acceso", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Negaste el acceso", Toast.LENGTH_LONG).show();
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri returnUri;

        if(requestCode != IntentHelper.FILE_PICK)
        {
            return;
        }
        if(resultCode!=RESULT_OK)
        {
            return;
        }
        returnUri=data.getData();
        String filePath=DocumentHelper.getPath(this,returnUri);
        if(filePath==null || filePath.isEmpty())
        {
            Log.e("ErrorImagen",filePath);
            return;
        }
        else
        {
            chosenFile=new File(filePath);
            Picasso.with(getBaseContext())
                    .load(chosenFile)
                    .placeholder(R.drawable.ic_photo_library_black)
                    .fit()
                    .into(uploadImage);
        }


    }

    private void clearInput()
    {
        uploadTitle.setText("");
        uploadDesc.clearFocus();
        uploadDesc.setText("");
        uploadTitle.clearFocus();
        uploadImage.setImageResource(R.drawable.ic_photo_library_black);
    }

    @OnClick(R.id.imageview)
    public void onChooseImage()
    {
        uploadDesc.clearFocus();
        uploadTitle.clearFocus();
        IntentHelper.chooseFileIntent(this);
    }

    private void createUpload(File image) {
        upload = new Upload();

        upload.image = image;
        upload.title = uploadTitle.getText().toString();
        upload.description = uploadDesc.getText().toString();
    }
    @OnClick(R.id.fab)
    public void uploadImage()
    {

        if (chosenFile == null)
            return;
        createUpload(chosenFile);

    /*
       Iniciar a subir la imagen
     */
        new UploadService(this).Execute(upload, new UiCallback());
    }


    private class UiCallback implements Callback<ImageResponse>
    {

        @Override
        public void success(ImageResponse imageResponse, Response response) {
            clearInput();
        }

        @Override
        public void failure(RetrofitError error)
        {

            if (error == null)
            {
                Snackbar.make(findViewById(R.id.rootView), "No hay conexion a Internet", Snackbar.LENGTH_SHORT).show();
            }

        }
    }








}
