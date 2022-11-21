package com.example.pramodn.myapplication;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pramodn.myapplication.event_adapter.comment_adapter;
import com.example.pramodn.myapplication.event_adapter.comment_model;
import com.example.pramodn.myapplication.event_adapter.hosted_adapter;
import com.example.pramodn.myapplication.event_adapter.hosted_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAllComments extends AppCompatActivity {
    private RecyclerView comment_recycler;
    private com.example.pramodn.myapplication.event_adapter.comment_adapter comment_adapter;
    private List<com.example.pramodn.myapplication.event_adapter.comment_model> comment_model;
    RequestQueue requestQueue;
    com.example.pramodn.myapplication.utility utility;

    String event_id;
    Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_comments);
        setTitle("All Comments");

        event_id = getIntent().getStringExtra("event_id");

        comment_recycler=findViewById(R.id.comment_recycler);
        back= findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewAllComments.this,description.class);
                intent.putExtra("event_id",event_id);
                startActivity(intent);
            }
        });


        requestQueue= Volley.newRequestQueue(ViewAllComments.this);

        utility=new utility();
        comment_model = new ArrayList<>();
        comment_adapter = new comment_adapter(ViewAllComments.this,comment_model);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(ViewAllComments.this, 1);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(ViewAllComments.this, LinearLayoutManager.VERTICAL, true);

        comment_recycler.setLayoutManager(linearLayoutManager1);
        comment_recycler.setLayoutManager(mLayoutManager1);
        comment_recycler.setItemAnimator(new DefaultItemAnimator());
        comment_recycler.setAdapter(comment_adapter);
        Load_data_from_DB();
    }

    private void Load_data_from_DB() {


        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+utility.Loadcomments,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());


                        Toast.makeText(ViewAllComments.this, response, Toast.LENGTH_SHORT).show();

                        com.example.pramodn.myapplication.event_adapter.comment_model a;

                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String fname_str= c.getString("fname");
                                String lname_str=c.getString("lname");
                                String comment_str = c.getString("comment");
                                String image_str = c.getString("image");

                                String username=fname_str+" "+lname_str;


                                a = new comment_model(username,image_str,comment_str);
                                comment_model.add(a);

                            }
                            comment_adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Toast.makeText(getActivity(),constants.NETWORK_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();

                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("eid",event_id);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                6 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        requestQueue.add(postRequest);

    }

}

