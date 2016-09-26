package com.example.fileio_demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ImageView imgCamera;
    private Button btnCamera, btnDelete;
    private TextView tvImgID;

    private String PackagePath = "";
    private File packageDir;
    private File photo;
    private Uri picUri;
    private static int CameraAction = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //decide path to save file when app Launch
        CheckSDCard();
        init();

    }

    protected void init() {
        imgCamera = (ImageView) findViewById(R.id.ImgCamera);
        btnCamera = (Button) findViewById(R.id.btnCamera);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        tvImgID = (TextView) findViewById(R.id.tvImgID);

        btnCamera.setOnClickListener(mListener);
        btnDelete.setOnClickListener(mListener);
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btnCamera:
                    takePackagePic();
                    break;

                case R.id.btnDelete:
                    deleteFile();
                    break;
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult ", "requestCode: " + requestCode + " resultCode: " + resultCode + " data: " + data);
        imgCamera.setImageURI(picUri);
        String fileName = photo.getName();
        tvImgID.setText(fileName);
    }

    /*************************************************************************************************
     * Check if SD Card Write available and Decide File Path
     **/

    //region Check SD Card Write available and Decide File Path
    public void CheckSDCard() {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            //No SD or Read Only
            PackagePath = Environment.getRootDirectory().getAbsolutePath() + "/DCIM/Demo/File";
            Log.i("File Path", "SD Card are NOT Available ! ");
        } else {
            PackagePath = Environment.getExternalStorageDirectory() + "/DCIM/Demo/File";
            Log.i("File Path", "SD Card OK file save to : " + PackagePath);
        }

        // make dir
        packageDir = new File(PackagePath);

        if (!packageDir.exists()) {
            packageDir.mkdirs();
        }


    }

    private static boolean isExternalStorageReadOnly() {
        String SdCardState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(SdCardState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String SdCardState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(SdCardState)) {
            return true;
        }
        return false;
    }
//endregion


    /*************************************************************************************************
     * 相機功能
     **/

    //region Camera Funtion
    public void takePackagePic() {
        String fileName = new SimpleDateFormat("yyMMddhhmmss").format(new Date(System.currentTimeMillis())) + ".jpg";

        String path = PackagePath;

        photo = new File(path, fileName);
        picUri = Uri.fromFile(photo);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);


        startActivityForResult(intent, CameraAction);

    }


    public void deleteFile() {
        if (photo != null) {
            boolean del = photo.delete();

            if (del) {
                imgCamera.setImageResource(R.drawable.noimg);
                Toast.makeText(this, "delete success!", Toast.LENGTH_SHORT).show();
            }
        }

        Toast.makeText(this, "File not exist!", Toast.LENGTH_SHORT).show();
    }




}
