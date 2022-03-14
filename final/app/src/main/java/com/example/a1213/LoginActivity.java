package com.example.a1213;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    EditText idText,pwText;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idText=findViewById(R.id.idText);
        pwText=findViewById(R.id.passwordText);
        btn=findViewById(R.id.btn);
        tv=findViewById(R.id.tvLogin);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=idText.getText().toString();
                String pw=pwText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MainBoardActivity.class);
                startActivity(intent);



            }
        });


    }

    class RegisterTask extends AsyncTask<String,String,String> {
        String sendMsg, receiverMsg;


        @Override
        protected String doInBackground(String... strings) {
            try{
                String str="";
                URL url=new URL("http://192.168.0.42:8092/AndroidPro/login.do");
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw=new
                        OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                sendMsg="id="+strings[0]+"&pw="+strings[1];
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
            tv.setText(s);
            if(s.equals("fail")){
                Intent intent = new Intent(getApplicationContext(), MainBoardActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(getApplicationContext(), MainBoardActivity.class);
                intent.putExtra("id", s);
                startActivity(intent);
            }
        }
    }
}