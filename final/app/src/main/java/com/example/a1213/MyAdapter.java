package com.example.a1213;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Board> mData=null;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.mainitem,parent,false);
        MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = mData.get(position).getTitle();
        holder.textView1.setText(text);
        holder.imageView1.setBackgroundResource(R.drawable.dog);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView1;
        public ImageView imageView1;
        ViewHolder(View itemView){
            super(itemView);
            textView1=itemView.findViewById(R.id.textView200);
            imageView1=itemView.findViewById(R.id.imageView200);
        }

    }

    MyAdapter(ArrayList<Board> list){
        mData=list;
    }

}
