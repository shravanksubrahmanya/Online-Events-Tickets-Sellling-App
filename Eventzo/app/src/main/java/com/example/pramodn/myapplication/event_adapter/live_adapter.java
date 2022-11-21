package com.example.pramodn.myapplication.event_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.example.pramodn.myapplication.LiveInformation;
import com.example.pramodn.myapplication.R;
import com.example.pramodn.myapplication.utility;

import java.util.List;

public class live_adapter extends RecyclerView.Adapter<live_adapter.MyViewHolder> {


    private Context mContext;
    private List<live_model> albumList1;
    RequestQueue queue;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView message;

        public MyViewHolder(View view) {
            super(view);
            message=view.findViewById(R.id.message);
        }
    }


    public live_adapter(Context mContext, List<live_model> albumList1) {
        this.mContext = mContext;
        this.albumList1 = albumList1;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_adapter_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final live_model album = albumList1.get(position);
        Log.d("size_from_adapter",String.valueOf(albumList1.size()));


        holder.message.setText(album.getMessage());
    }
    @Override
    public int getItemCount() {
        return albumList1.size();
    }

}
