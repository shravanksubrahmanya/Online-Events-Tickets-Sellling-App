package com.example.pramodn.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.pramodn.myapplication.event_adapter.event_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class description extends AppCompatActivity implements View.OnClickListener {

    String event_id;
    RequestQueue requestQueue;
    String editenabled="no";
    String fname,lname,image;

    TextView eventname,synopsis,date,time,description,type,allcomments,totalcomments,liveinfo,videolink;
    ImageView event_image;
    EditText addcomment;
    Button book,submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Event Description");
        setContentView(R.layout.activity_description);

        event_id=getIntent().getStringExtra("event_id");

        Toast.makeText(this, event_id, Toast.LENGTH_SHORT).show();

        requestQueue= Volley.newRequestQueue(this);

        eventname=findViewById(R.id.event_name);
        synopsis=findViewById(R.id.synopsis);
        date=findViewById(R.id.event_date);
        time=findViewById(R.id.event_time);
        totalcomments=findViewById(R.id.totalcomments);
        liveinfo=findViewById(R.id.liveinfo);
        videolink=findViewById(R.id.videolink);


        event_image=findViewById(R.id.event_image);
        book=findViewById(R.id.book);
        description=findViewById(R.id.description);
        type=findViewById(R.id.type);
        load_event_description(event_id);

        book.setOnClickListener(com.example.pramodn.myapplication.description.this);

        allcomments=findViewById(R.id.allcomments);
        addcomment=findViewById(R.id.addcomment);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(this);
        allcomments.setOnClickListener(this);
        liveinfo.setOnClickListener(this);

        load_current_user_details();

    }

    private void load_current_user_details() {
        StringRequest postRequest = new StringRequest(Request.Method.POST,utility.ip+ utility.Load_current_user_details,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(description.this, response, Toast.LENGTH_SHORT).show();
                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String fname_str = c.getString("fname");
                                String lname_str = c.getString("lname");
                                String image_str = c.getString("image");

                                fname=fname_str;
                                lname=lname_str;
                                image=image_str;

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(description.this, error.toString(), Toast.LENGTH_SHORT).show();

                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("uid",utility.id);


                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    private void load_event_description(final String event_id) {
        StringRequest postRequest = new StringRequest(Request.Method.POST,utility.ip+ utility.load_evet_description,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(description.this, response, Toast.LENGTH_SHORT).show();
                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String event_id = c.getString("event_id");
                                String event_name_str = c.getString("event_name");
                                String description_str = c.getString("description");
                                String image_str = c.getString("image");
                                String synopsis_str = c.getString("synopsis");
                                String type_str = c.getString("type");
                                String date_str = c.getString("date");
                                String time_str = c.getString("time");
                                String hosttype_str = c.getString("hosttype");
                                String numberofcomments = c.getString("numberofcomments");
                                String videolink_str = c.getString("videolink");


                                eventname.setText(event_name_str);
                                synopsis.setText(synopsis_str);
                                date.setText(date_str);
                                time.setText(time_str);
                                type.setText(type_str);
                                date.setText(date_str);
                                description.setText(description_str);
                                totalcomments.setText(numberofcomments);
                                videolink.setText(videolink_str);

                                if (totalcomments.getText().toString().equals(0)){
                                    allcomments.setVisibility(View.INVISIBLE);
                                }

                                if (hosttype_str.equals("cost") ){
                                    book.setVisibility(View.VISIBLE);
                                    liveinfo.setVisibility(View.INVISIBLE);
                                }
                                else {
                                    book.setVisibility(View.INVISIBLE);
                                    liveinfo.setVisibility(View.VISIBLE);
                                }


                                Glide.with(com.example.pramodn.myapplication.description.this).load(utility.IMAGE_URL +image_str).into(event_image);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(description.this, error.toString(), Toast.LENGTH_SHORT).show();

                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("event_id",event_id);


                return params;
            }
        };
        requestQueue.add(postRequest);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(description.this,Home.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v==submit){
            if (addcomment.getText().toString().equals("")) {
                Toast.makeText(description.this, "Write the comment in the add comment box", Toast.LENGTH_SHORT).show();
            }
            else {
                String url = utility.ip+utility.Comment;
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.contains("Comment added successfully"))
                                {
                                    Intent intent=new Intent(description.this,ViewAllComments.class);
                                    addcomment.getText().clear();
                                    intent.putExtra("event_id",event_id);
                                    startActivity(intent);
                                }
                                Toast.makeText(description.this, response, Toast.LENGTH_SHORT).show();
                                Log.d("Response", response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(description.this, error.toString(), Toast.LENGTH_SHORT).show();

                                Log.d("Error.Response", error.toString());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("event_id",event_id);
                        params.put("uid", utility.id);
                        params.put("comment",addcomment.getText().toString());
                        params.put("fname",fname);
                        params.put("lname",lname);
                        params.put("image",image);

                        return params;
                    }
                };
                requestQueue.add(postRequest);
            }
        }
        else if (v==book){
            Intent intent=new Intent(description.this,TicketPurchase.class);
            intent.putExtra("event_id",event_id);
            intent.putExtra("event_name",eventname.getText().toString());
            startActivity(intent);
        }
        else if (v==allcomments){
            Intent intent=new Intent(description.this,ViewAllComments.class);
            intent.putExtra("event_id",event_id);
            startActivity(intent);
        }
        else if (v==liveinfo){
            Intent intent=new Intent(description.this,LiveInformation.class);
            intent.putExtra("event_id",event_id);
            intent.putExtra("event_name",eventname.getText().toString());
            intent.putExtra("editenabled",editenabled);
            startActivity(intent);
        }
    }
}