package com.example.a1213;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InfoDialog {
    private Context context;
    public InfoDialog(Context context){
        this.context=context;
    }
    TextView tvName,tvKinds,tvInfo,tvRelationship;
    Button cancelButton,addFriend;
    ImageView imageView;
    public void callFunction(final int no,final int image,final String name,final String kinds,final String relationship,final String info){

        final Dialog dig=new Dialog(context);
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dig.setContentView(R.layout.dialog);
        dig.show();
        cancelButton=(Button)dig.findViewById(R.id.close);
        addFriend=(Button)dig.findViewById(R.id.addFriend);

        imageView=dig.findViewById(R.id.digImage);
        tvName=dig.findViewById(R.id.tvDogName);
        tvKinds=dig.findViewById(R.id.tvDogKinds);
        tvRelationship=dig.findViewById(R.id.tvRelationship);
        tvInfo=dig.findViewById(R.id.tvDogInfo);



        imageView.setBackgroundResource(image);
        tvName.setText(name);
        tvKinds.setText(kinds);
        tvRelationship.setText(relationship);
        tvInfo.setText(info);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원번호 + 강아지 번호 + idx(친구or블랙) 서버전송
                RegisterTask task=new RegisterTask();
                task.execute(Integer.toString(no),"1");
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dig.dismiss();
            }
        });


    }
    class RegisterTask extends AsyncTask<String,String,String> {

        String sendMsg, receiverMsg;

        @Override
        protected String doInBackground(String... strings) {
            try{
                String str="";
                URL url=new URL("http://192.168.0.42:8092/AndroidPro/relationship.do");
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                sendMsg="dogNo="+strings[0]+"&idx="+strings[1];
                osw.write(sendMsg);
                osw.flush();
                osw.close();
                if(conn.getResponseCode()==conn.HTTP_OK){
                    InputStreamReader isr=new InputStreamReader(conn.getInputStream(),"UTF-8");
                    BufferedReader reader=new BufferedReader(isr);
                    StringBuffer buffer=new StringBuffer();
                    while((str=reader.readLine())!=null){
                        buffer.append(str);
                    }
                    receiverMsg=buffer.toString();
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
            if(s.equals("fail")){
                addFriend.setText("전산오류");
                addFriend.setEnabled(false);
            }else if(s.equals("success")){
                addFriend.setText("성공적");
                addFriend.setEnabled(false);
            }

        }

    }
}
