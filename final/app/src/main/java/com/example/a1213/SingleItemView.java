package com.example.a1213;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class SingleItemView extends LinearLayout {
    //어디서든 사용 할 수 있게 하려면
    TextView textView, textView2;
    ImageView imageView;

    public SingleItemView(Context context){
        super(context);
        init(context);

    }
    public SingleItemView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
    }
    //지금 만든 객체(xml 레이아웃)를 인플레이션화해서 붙여줌
    //Layoutlnflater를 써서 시스템 서비스를 참조할 수 있음
    //단말이 켜졌을 때 기본적으로 백그라운드에서 실행시키는 것을 시스템서비스라고 함
    private  void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.single_list_item, this, true);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView);
    }
        public void setTitle(String title){
        textView.setText(title);
    }  public void setWriter(String writer){
        textView2.setText(writer);
    }   public void setImage(int resld){
        imageView.setImageResource(resld);
    }

}
