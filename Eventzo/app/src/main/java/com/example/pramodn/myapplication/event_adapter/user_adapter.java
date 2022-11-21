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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.pramodn.myapplication.HostedNext;
import com.example.pramodn.myapplication.R;
import com.example.pramodn.myapplication.SendNotifications;
import com.example.pramodn.myapplication.description;
import com.example.pramodn.myapplication.fragment.Groups;
import com.example.pramodn.myapplication.register;
import com.example.pramodn.myapplication.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.pramodn.myapplication.utility.Search_people;

public class user_adapter extends RecyclerView.Adapter<user_adapter.MyViewHolder> {


    private Context mContext;
    private List<user_model> albumList1;
    RequestQueue queue;
    private String userid;
    private String fname,lname,imagename,gendername,dob,moinformation;
    private String creategroup="yes";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username,dateofbirth,gender,moreinfo;
        public ImageView user_image;
        public Button connect;

        public MyViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.username);
            gender = view.findViewById(R.id.gender);
            moreinfo=view.findViewById(R.id.moreinfo);
            dateofbirth=view.findViewById(R.id.dateofbirth);
            user_image=view.findViewById(R.id.user_image);
            connect=view.findViewById(R.id.connect);
            queue = Volley.newRequestQueue(mContext);

        }

    }



    public user_adapter(Context mContext, List<user_model> albumList1) {
        this.mContext = mContext;
        this.albumList1 = albumList1;
        Log.d("sizeadapter",String.valueOf(albumList1.size()));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_adapter_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final user_model album = albumList1.get(position);


        fname=album.getFname_str();
        lname=album.getLname_str();
        holder.username.setText(fname+" "+lname);
        holder.gender.setText(album.getGender());
        holder.dateofbirth.setText(album.getDob());
        holder.moreinfo.setText(album.getMoreinfo());



        Glide.with(mContext).load(utility.IMAGE_URL + album.getImage()).into(holder.user_image);

        holder.connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname=album.getFname_str();
                lname=album.getLname_str();
                imagename=album.getImage();


                userid=album.getUid();
                gendername=album.getGender();
                dob=album.getDob();
                moinformation=album.getMoreinfo();


                Intent intent = new Intent(mContext, SendNotifications.class);
                intent.putExtra("cfname", fname);
                intent.putExtra("cid", userid);
                intent.putExtra("clname", lname);
                intent.putExtra("cimage", imagename);
                intent.putExtra("cgender", gendername);
                intent.putExtra("cdob", dob);
                intent.putExtra("cmoreinfo", moinformation);
                intent.putExtra("creategroup", creategroup);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return albumList1.size();
    }

}
