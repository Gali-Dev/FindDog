package com.example.a1213;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        //게시판 어댑터 소스
        ListView listView = findViewById(R.id.listView);
        SingleAdapter adapter = new SingleAdapter();
        adapter.addItem(new SingleItem("제목1", "작성자1", R.drawable.dog));
        adapter.addItem(new SingleItem("제목2", "작성자2", R.drawable.dog));
        adapter.addItem(new SingleItem("제목3", "작성자3", R.drawable.dog));
        adapter.addItem(new SingleItem("제목4", "작성자4", R.drawable.dog));
        listView.setAdapter(adapter);
    }

    class SingleAdapter extends BaseAdapter{
        //데이터가 들어가있지 않고 어떻게 담을지만 정의
        ArrayList<SingleItem> items = new ArrayList<SingleItem>();

        @Override
        public int getCount() {
            return items.size();
        }
        public void addItem(SingleItem item){
            items.add(item);
        }
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SingleItemView singleItemView = null;
            //코드를 재사용 할 수 잇도록
            if(convertView == null){
                singleItemView=new SingleItemView(getApplicationContext());
            }else{
                singleItemView = (SingleItemView)convertView;
            }
            SingleItem item = items.get(position);
            singleItemView.setTitle(item.getTitle());
            singleItemView.setWriter(item.getWriter());
            singleItemView.setImage(item.getResld());
            return  singleItemView;
        }

    }
}
