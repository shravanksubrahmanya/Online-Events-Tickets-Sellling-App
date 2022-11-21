package com.example.pramodn.myapplication.event_adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.pramodn.myapplication.HostedNext;
import com.example.pramodn.myapplication.R;
import com.example.pramodn.myapplication.description;
import com.example.pramodn.myapplication.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class hosted_adapter extends RecyclerView.Adapter<hosted_adapter.MyViewHolder> {


    private Context mContext;
    private List<hosted_model> albumList1;
    RequestQueue queue;





    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event_id, event_name,event_description,nolike,nocomment,noshare;
        public ImageView event_iamge;
        public RelativeLayout category_main_layout;
        public Button view_more;
        public  ImageView event_image;
        public ImageButton likes,comments,share;


        public MyViewHolder(View view) {
            super(view);


            event_name = (TextView) view.findViewById(R.id.event_name);
            event_description = view.findViewById(R.id.description);

            event_image=view.findViewById(R.id.event_image);

            view_more=view.findViewById(R.id.view_more);
            likes=view.findViewById(R.id.likes);
            comments=view.findViewById(R.id.comments);
            nolike=view.findViewById(R.id.nolike);
            nocomment=view.findViewById(R.id.nocomment);
            queue = Volley.newRequestQueue(mContext);
        }
    }


    public hosted_adapter(Context mContext, List<hosted_model> albumList1) {
        this.mContext = mContext;
        this.albumList1 = albumList1;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hosted_adapter_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final hosted_model album = albumList1.get(position);

        holder.event_name.setText(album.getEvent_name());
        holder.event_description.setText(album.getEvent_desc());


        holder.view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext, HostedNext.class);
                intent.putExtra("event_id",album.getEvent_id());
                mContext.startActivity(intent);
            }
        });

        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, HostedNext.class);
                intent.putExtra("event_id", album.getEvent_id());
                mContext.startActivity(intent);
            }
        });
        holder.nolike.setText(String.valueOf(album.getLikes()));

        holder.nocomment.setText(String.valueOf(album.getnumberofcomments()));


        holder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("is_liked",String.valueOf(album.isIs_checked()));


                if (album.isIs_checked()==true){
                    album.setLikes(album.getLikes()-1);
                    holder.nolike.setText(String.valueOf(album.getLikes()));
                    album.setIs_checked(false);
                    holder.likes.setImageResource(R.drawable.ic_favorite_black_24dp);
                    like(album.getEvent_id());
                    Log.d("is_liked",String.valueOf(album.isIs_checked()));
                }
                else {                 album.setLikes(album.getLikes()+1);

                    holder.nolike.setText(String.valueOf(album.getLikes()));

                    album.setIs_checked(true);
                    holder.likes.setImageResource(R.drawable.ic_favorite_red_24dp);
                    like(album.getEvent_id());

                    Log.d("is_liked",String.valueOf(album.isIs_checked()));
                }

            }
        });




        Glide.with(mContext).load(utility.IMAGE_URL + album.getEvent_image()).into(holder.event_image);


    }
    @Override
    public int getItemCount() {
        return albumList1.size();
    }
    public void like(final String eid){
        String url = utility.ip + utility.LIKE;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();



                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", utility.id);
                params.put("eid", eid);

                return params;
            }
        };
        queue.add(postRequest);

    }

}
