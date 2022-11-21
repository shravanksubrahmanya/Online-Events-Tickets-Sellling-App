package com.example.pramodn.myapplication;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HostedNext extends AppCompatActivity implements View.OnClickListener {

    String event_id;
    RequestQueue requestQueue;
    String editenabled="yes";

    TextView eventname,synopsis,date,time,description,type,allcomments,totalcomments,liveinfo,send_notifications,videolink;
    ImageView event_image;
    Button book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_hosted_next);

        event_id=getIntent().getStringExtra("event_id");
        send_notifications=findViewById(R.id.send_notifications);
        send_notifications.setOnClickListener(this);

        requestQueue= Volley.newRequestQueue(this);

        eventname=findViewById(R.id.event_name);
        synopsis=findViewById(R.id.synopsis);
        date=findViewById(R.id.event_date);
        time=findViewById(R.id.event_time);
        totalcomments=findViewById(R.id.totalcomments);
        liveinfo=findViewById(R.id.liveinfo);

        event_image=findViewById(R.id.event_image);
        book=findViewById(R.id.book);
        description=findViewById(R.id.description);
        type=findViewById(R.id.type);
        load_event_description(event_id);
        videolink=findViewById(R.id.videolink);

        book.setOnClickListener(this);

        allcomments=findViewById(R.id.allcomments);
        allcomments.setOnClickListener(this);
        liveinfo.setOnClickListener(this);
        allcomments.setOnClickListener(this);

    }

    private void load_event_description(final String event_id) {
        StringRequest postRequest = new StringRequest(Request.Method.POST,utility.ip+ utility.load_evet_description,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(HostedNext.this, response, Toast.LENGTH_SHORT).show();
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

                                if (totalcomments.getText().toString().equals("0")){
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


                                Glide.with(com.example.pramodn.myapplication.HostedNext.this).load(utility.IMAGE_URL +image_str).into(event_image);

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

                        Toast.makeText(HostedNext.this, error.toString(), Toast.LENGTH_SHORT).show();

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
        Intent intent=new Intent(HostedNext.this,Home.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
   if (v==book){
            Intent intent=new Intent(HostedNext.this,SoldTickets.class);
            intent.putExtra("event_id",event_id);
            startActivity(intent);
        }
        else if (v==allcomments){
            if (!totalcomments.getText().toString().equals(0)) {
                Intent intent = new Intent(HostedNext.this, ViewAllComments.class);
                intent.putExtra("event_id", event_id);
                startActivity(intent);
            }
        }
        else if (v==liveinfo){
            Intent intent=new Intent(HostedNext.this,LiveInformation.class);
            intent.putExtra("event_id",event_id);
       intent.putExtra("event_name",eventname.getText().toString());
            intent.putExtra("editenabled",editenabled);
            startActivity(intent);
        }
        else if (v==send_notifications){
       Intent intent=new Intent(HostedNext.this,Notify.class);
       intent.putExtra("event_id",event_id);
       intent.putExtra("event_name",eventname.getText().toString());
       intent.putExtra("event_image",event_image.getImageMatrix().toString());
       intent.putExtra("synopsis",synopsis.getText().toString());
       intent.putExtra("type","group");
       startActivity(intent);
   }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
