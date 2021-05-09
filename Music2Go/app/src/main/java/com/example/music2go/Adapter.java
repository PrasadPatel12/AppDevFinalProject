package com.example.music2go;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<com.example.music2go.Adapter.ViewHolder> {

    private List<ModelClass> userList;

    public Adapter(List<ModelClass>userList){ this.userList=userList;}

    @NonNull
    @Override
    public com.example.music2go.Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.music2go.Adapter.ViewHolder holder, int position) {
//        int resource = userList.get(position).getImageView1();
        String name = userList.get(position).getTextView1();
//        String time = userList.get(position).getTextView2();
        String msg = userList.get(position).getTextView3();
        String line = userList.get(position).getDivider();

        holder.setData(name, msg, line);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//        private ImageView imageView;
        private TextView textView;
//        private TextView textView2;
        private TextView textView3;
        private TextView divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            imageView = itemView.findViewById(R.id.imageView1);
            textView = itemView.findViewById(R.id.textView);
//            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            divider = itemView.findViewById(R.id.divider);
        }

        public void setData(String name, String msg, String line) {
//            imageView.setImageResource(resource);
            textView.setText(name);
//            textView2.setText(time);
            textView3.setText(msg);
            divider.setText(line);
        }
    }
}
