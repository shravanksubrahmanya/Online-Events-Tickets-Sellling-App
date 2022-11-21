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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pramodn.myapplication.event_adapter.hosted_adapter;
import com.example.pramodn.myapplication.event_adapter.hosted_model;
import com.example.pramodn.myapplication.event_adapter.live_adapter;
import com.example.pramodn.myapplication.event_adapter.live_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiveInformation extends AppCompatActivity {

    private RecyclerView live_recycler;
    private com.example.pramodn.myapplication.event_adapter.live_adapter live_adapter;
    private List<com.example.pramodn.myapplication.event_adapter.live_model> live_model;
    RequestQueue requestQueue;
    com.example.pramodn.myapplication.utility utility;

    EditText addmessage;
    Button send;
    RequestQueue queue;
    String event_id, event_name;
    TextView ename;

    String edit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_information);
        setTitle("Live Information");

        live_recycler = findViewById(R.id.live_recycler);
        edit=getIntent().getStringExtra("editenabled");

        requestQueue = Volley.newRequestQueue(LiveInformation.this);

        utility = new utility();
        live_model = new ArrayList<>();
        live_adapter = new live_adapter(LiveInformation.this, live_model);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(LiveInformation.this, 1);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(LiveInformation.this, LinearLayoutManager.VERTICAL, true);

        live_recycler.setLayoutManager(linearLayoutManager1);
        live_recycler.setLayoutManager(mLayoutManager1);
        live_recycler.setItemAnimator(new DefaultItemAnimator());
        live_recycler.setAdapter(live_adapter);
        Load_data_from_DB();

        addmessage = findViewById(R.id.addmessage);
        send = findViewById(R.id.send);
        queue = Volley.newRequestQueue(this);

        event_id = getIntent().getStringExtra("event_id");
        event_name = getIntent().getStringExtra("event_name");

        ename = findViewById(R.id.ename);

        ename.setText(event_name);

         if (edit.equals("yes"))
        {
            addmessage.setVisibility(View.VISIBLE);
            send.setVisibility(View.VISIBLE);

        }
        else if (edit.equals("no")){
             addmessage.setVisibility(View.INVISIBLE);
             send.setVisibility(View.INVISIBLE);
         }


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addmessage != null) {
                    String url = utility.ip + utility.Enews;
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.contains("Message added")) {
                                        addmessage.getText().clear();
                                        Load_data_from_DB();
                                    }

                                    Toast.makeText(LiveInformation.this, response, Toast.LENGTH_SHORT).show();
                                    Log.d("Response", response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(LiveInformation.this, error.toString(), Toast.LENGTH_SHORT).show();

                                    Log.d("Error.Response", error.toString());
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("uid", utility.id);
                            params.put("event_id", event_id);
                            params.put("message", addmessage.getText().toString());

                            return params;
                        }
                    };
                    queue.add(postRequest);
                }
            }
        });


    }

    private void Load_data_from_DB() {


        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip + utility.Livemessages,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());


                        Toast.makeText(LiveInformation.this, response, Toast.LENGTH_SHORT).show();

                        com.example.pramodn.myapplication.event_adapter.live_model a;
                        live_model.clear();
                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String message_str = c.getString("message");

                                a = new live_model(message_str);
                                live_model.add(a);

                            }
                            live_adapter.notifyDataSetChanged();
                            Log.d("size_of_the_model",String.valueOf(live_model.size()));


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
                params.put("uid", utility.id);
                params.put("eid", event_id);
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