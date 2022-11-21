package com.example.pramodn.myapplication.event_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.pramodn.myapplication.R;
import com.example.pramodn.myapplication.ViewAllComments;
import com.example.pramodn.myapplication.description;
import com.example.pramodn.myapplication.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class comment_adapter extends RecyclerView.Adapter<comment_adapter.MyViewHolder> {


    private Context mContext;
    private List<comment_model> albumList1;
    RequestQueue queue;





    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username,comments;
        public ImageView uimage;

        public MyViewHolder(View view) {
            super(view);


            username = (TextView) view.findViewById(R.id.username);
            uimage = view.findViewById(R.id.uimage);
            comments=view.findViewById(R.id.comments);
            queue= Volley.newRequestQueue(mContext);
        }
    }


    public comment_adapter(Context mContext, List<comment_model> albumList1) {
        this.mContext = mContext;
        this.albumList1 = albumList1;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_adapter_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final comment_model album = albumList1.get(position);

        holder.username.setText(album.getUsername());
        holder.comments.setText(album.getComment_str());
        Glide.with(mContext).load(utility.IMAGE_URL + album.getImage_str()).into(holder.uimage);


    }
    @Override
    public int getItemCount() {
        return albumList1.size();
    }

}
