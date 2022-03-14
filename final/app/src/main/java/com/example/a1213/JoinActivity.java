package com.example.a1213;

import android.content.Intent;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JoinActivity extends AppCompatActivity {
    EditText edId,edPw,edName,edLocation;
    RadioGroup dogG;
    RadioButton dogO,dogX;
    Button btn,valBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        edId=findViewById(R.id.idText);
        edPw=findViewById(R.id.passwordText);
        edName=findViewById(R.id.nameText);
        edLocation=findViewById(R.id.location);
        dogG=findViewById(R.id.dogGroup);
        dogO=findViewById(R.id.dogO);
        dogX=findViewById(R.id.dogX);
        btn=findViewById(R.id.registerButton);
        valBtn=findViewById(R.id.valBtn);


        dogG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton select = findViewById(i);
                if(i==R.id.dogO){
                    btn.setText("강아지 등록하기");

                }else if(i==R.id.dogX){
                    btn.setText("가입하기");
                }
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dogG.getCheckedRadioButtonId()==R.id.dogO) {

                    String id = edId.getText().toString();
                    String pw = edPw.getText().toString();
                    String name = edName.getText().toString();
                    String location = edLocation.getText().toString();

                    String dog_ox = "";
                    if (dogG.getCheckedRadioButtonId() == R.id.dogO) {
                        dog_ox = "1";
                    }
                    if (dogG.getCheckedRadioButtonId() == R.id.dogX) {
                        dog_ox = "0";
                    }

                    //RegisterTask task = new RegisterTask();
                    //task.execute(id, pw, name, location, dog_ox,"등록");
                    Intent intent = new Intent(getApplicationContext(),DogPicActivity.class);

                    startActivity(intent);
                }
                if (dogG.getCheckedRadioButtonId() == R.id.dogX) {
                    String id = edId.getText().toString();
                    String pw = edPw.getText().toString();
                    String name = edName.getText().toString();
                    String location = edLocation.getText().toString();

                    String dog_ox = "";
                    if (dogG.getCheckedRadioButtonId() == R.id.dogO) {
                        dog_ox = "1";
                    }
                    if (dogG.getCheckedRadioButtonId() == R.id.dogX) {
                        dog_ox = "0";
                    }

                    RegisterTask task = new RegisterTask();
                    task.execute(id, pw, name, location, dog_ox);
                    Intent intent = new Intent(getApplicationContext(),BoardActivity.class);
                    startActivity(intent);
                }

            }
        });


        valBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=edId.getText().toString();
                RegisterTask task=new RegisterTask();
                task.execute(id);


            }
        });



    }

    class RegisterTask extends AsyncTask<String,String,String> {
        String sendMsg, receiverMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                if (strings[5].equals("등록")) {
                String str = "";
                URL url = new URL("http://192.168.0.42:8092/AndroiProd/join.do");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new
                        OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                sendMsg = "id=" + strings[0] + "&pw=" + strings[1] + "&name=" + strings[2] + "&location=" + strings[3]
                        + "&dog_ox=" + strings[4];
                osw.write(sendMsg);
                osw.flush();
                osw.close();

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader isr = new
                            InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(isr);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiverMsg = buffer.toString();
                }
                }

                }catch(MalformedURLException e){
                    e.printStackTrace();
                }catch(IOException e){
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
