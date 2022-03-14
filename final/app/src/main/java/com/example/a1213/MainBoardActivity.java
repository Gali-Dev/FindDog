package com.example.a1213;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;

public class MainBoardActivity extends AppCompatActivity {
    ArrayList<Board> boardList=new ArrayList<Board>();
    ArrayList<Board> boardListCafe=new ArrayList<Board>();
    ArrayList<Board> boardListWalk=new ArrayList<Board>();
    ArrayList<Board> boardListHospital=new ArrayList<Board>();
    TextView tv1,tv2,tv3;
    RecyclerView rv1,rv2,rv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testetest);
        tv1=findViewById(R.id.textView1700);
        tv2=findViewById(R.id.textView1800);
        tv3=findViewById(R.id.textView1900);
        rv1=findViewById(R.id.recyclerView1);
        rv2=findViewById(R.id.recyclerView2);
        rv3=findViewById(R.id.recyclerView3);
        rv1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv3.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


    }

    @Override
    protected void onStart() {
        super.onStart();
        RegisterTask task= new MainBoardActivity.RegisterTask();
        task.execute("1");


    }
    class RegisterTask extends AsyncTask<String,String,String> {

        String sendMsg, receiverMsg;

        @Override
        protected String doInBackground(String... strings) {
            try{
                String str="";
                URL url=new URL("http://192.168.0.42:8092/AndroidPro/boardList.do");
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                sendMsg="category="+strings[0];
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
            MyAdapter adapter;
            boardList.clear();
            try {
                JSONArray jsonArray=new JSONArray(s);
                for(int i=0; i<jsonArray.length(); i++){
                    Board board = new Board();
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    board.setNo(jsonObject.getInt("no"));
                    board.setBno(jsonObject.getInt("bno"));
                    board.setTitle(jsonObject.getString("title"));
                    board.setContent(jsonObject.getString("content"));
                    board.setCategory(jsonObject.getString("category"));
                    boardList.add(board);

                }
                for(int i=0;i<boardList.size();i++){
                    if(boardList.get(i).getCategory().equals("1")){
                        boardListCafe.add(boardList.get(i));
                    }else if(boardList.get(i).getCategory().equals("2")){
                        boardListWalk.add(boardList.get(i));
                    }else if(boardList.get(i).getCategory().equals("3")){
                        boardListHospital.add(boardList.get(i));
                    }
                }

                adapter = new MyAdapter(boardListCafe);
                rv1.setAdapter(adapter);
                adapter = new MyAdapter(boardListWalk);
                rv2.setAdapter(adapter);
                adapter = new MyAdapter(boardListHospital);
                rv3.setAdapter(adapter);



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
    public void onClick_Find(View view) {
        Intent intent = new Intent(this, FindMapActivity.class);
        startActivity(intent);
    }
    public void onClick_Dowalk(View view) {
        Intent intent = new Intent(this, doWalkActivity.class);
        startActivity(intent);
    }
    public void onClick_Board(View view) {
        Intent intent = new Intent(this, MainBoardActivity.class);
        startActivity(intent);
    }

}