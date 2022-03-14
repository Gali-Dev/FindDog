package com.example.a1213;

        import androidx.appcompat.app.AppCompatActivity;


        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ListView;

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick_login(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void onClick_join(View view){
        Intent intent = new Intent(this,JoinActivity.class);
        startActivity(intent);
    }
}


