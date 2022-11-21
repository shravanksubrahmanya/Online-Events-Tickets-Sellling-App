package com.example.pramodn.myapplication.event_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.pramodn.myapplication.R;
import com.example.pramodn.myapplication.SendNotifications;
import com.example.pramodn.myapplication.description;
import com.example.pramodn.myapplication.utility;

import java.util.List;

public class notification_adapter extends RecyclerView.Adapter<notification_adapter.MyViewHolder> {


    private Context mContext;
    private List<notification_model> albumList1;
    RequestQueue queue;
    private String event_id;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ename,synopsis;
        public ImageView event_image;

        public MyViewHolder(View view) {
            super(view);
            ename=view.findViewById(R.id.ename);
            synopsis=view.findViewById(R.id.synopsis);
            event_image=view.findViewById(R.id.eventimage);
            queue = Volley.newRequestQueue(mContext);

        }

    }



    public notification_adapter(Context mContext, List<notification_model> albumList1) {
        this.mContext = mContext;
        this.albumList1 = albumList1;
        Log.d("sizeadapter",String.valueOf(albumList1.size()));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_adapter_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final notification_model album = albumList1.get(position);

        event_id=album.getEvent_id();
        holder.ename.setText(album.getEvent_name());
        holder.synopsis.setText(album.getSynopsis());

        Glide.with(mContext).load(utility.IMAGE_URL + album.getImage()).into(holder.event_image);

        holder.ename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, description.class);
                intent.putExtra("event_id", event_id);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return albumList1.size();
    }

}

