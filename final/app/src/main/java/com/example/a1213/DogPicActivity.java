package com.example.a1213;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DogPicActivity extends AppCompatActivity implements AutoPermissionsListener {
    EditText dname,dinfo,dkinds;
    Button dimagbtn,dreg;
    ImageView dimageview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_info);

        dname=findViewById(R.id.edname);
        dinfo=findViewById(R.id.edInfo);
        dkinds=findViewById(R.id.edkinds);
        dimagbtn=findViewById(R.id.dogImgBtn);
        dreg=findViewById(R.id.registerButton);
        dimageview=findViewById(R.id.dimage);

        dimagbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"tLQKF",Toast.LENGTH_SHORT).show();
                openGallery();


            }
        });
        AutoPermissions.Companion.loadAllPermissions(this,101);

        dreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dog_name=dname.getText().toString();
                String dog_info=dinfo.getText().toString();
                String dog_kinds=dkinds.getText().toString();

                RegisterTask task = new RegisterTask();
                task.execute(dog_name, dog_info, dog_kinds);
                Intent intent= new Intent(getApplicationContext(), MainBoardActivity.class);
                startActivity(intent);

            }
        });

    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,101);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){
            if(resultCode==RESULT_OK){
                Uri fileUri=data.getData();
                ContentResolver resolver = getContentResolver();
                try{
                    InputStream is = resolver.openInputStream(fileUri);
                    Bitmap bitmap= BitmapFactory.decodeStream(is);
                    dimageview.setImageBitmap(bitmap);
                    is.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this,requestCode,permissions,this);
    }
    @Override
    public void onDenied(int i, String[] strings) {
        Toast.makeText(this,"permission denied:"+ strings.length,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int i, String[] strings) {
        Toast.makeText(this,"permission granted:"+ strings.length,Toast.LENGTH_LONG).show();
    }

    class RegisterTask extends AsyncTask<String,String,String> {
        String sendMsg, receiverMsg;


        @Override
        protected String doInBackground(String... strings) {
            try{
                String str="";
                URL url=new URL("http://192.168.0.42:8092/AndroidPro/dogjoin.do");
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw=new
                        OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                sendMsg="dog_name="+strings[0]+"&dog_info="+strings[1]+"&dog_kinds="+strings[2];
                osw.write(sendMsg);
                osw.flush();
                osw.close();

                if(conn.getResponseCode()==conn.HTTP_OK){
                    InputStreamReader isr=new
                            InputStreamReader(conn.getInputStream(),"UTF-8");
                    BufferedReader reader=new BufferedReader(isr);
                    StringBuffer buffer=new StringBuffer();
                    while((str=reader.readLine())!=null){
                        buffer.append(str);
                    }
                    receiverMsg=buffer.toString();
                }


            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            return receiverMsg;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}
