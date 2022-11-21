package com.example.pramodn.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pramodn.myapplication.event_adapter.user_adapter;
import com.example.pramodn.myapplication.event_adapter.user_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.pramodn.myapplication.utility.Search_people;
import static com.example.pramodn.myapplication.utility.View_group_info;

public class ViewGroupInfo extends AppCompatActivity {
    private com.example.pramodn.myapplication.event_adapter.user_adapter user_adapter;
    private List<com.example.pramodn.myapplication.event_adapter.user_model> user_model;
    RequestQueue requestQueue;
    com.example.pramodn.myapplication.utility utility;
    private RecyclerView user_recycler;

    String groupname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group_info);

        groupname=getIntent().getStringExtra("groupname");
        setTitle(groupname);

        user_recycler=findViewById(R.id.user_recycler);
        requestQueue= Volley.newRequestQueue(ViewGroupInfo.this);
        utility=new utility();
        user_model = new ArrayList<>();
        user_adapter = new user_adapter(ViewGroupInfo.this, user_model);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(ViewGroupInfo.this, 1);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(ViewGroupInfo.this, LinearLayoutManager.VERTICAL, true);
        user_recycler.setLayoutManager(linearLayoutManager1);
        user_recycler.setLayoutManager(mLayoutManager1);
        user_recycler.setItemAnimator(new DefaultItemAnimator());
        user_recycler.setAdapter(user_adapter);
        Load_data_from_DB();
    }

    private void Load_data_from_DB() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+View_group_info,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response_connection", response.toString());
                        com.example.pramodn.myapplication.event_adapter.user_model a;

                        if (response.equals("No members found")){
                            Toast.makeText(ViewGroupInfo.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                user_model.clear();

                                JSONArray data = new JSONArray(response);

                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject c = data.getJSONObject(i);

                                    String fname_str = c.getString("fname");
                                    String lname_str=c.getString("lname");
                                    String image_str = c.getString("image");
                                    String uid_str = c.getString("uid");
                                    String dob_dtr = c.getString("dob");
                                    String gender_str= c.getString("gender");
                                    String moreinfo_str = c.getString("moreinfo");


                                    a = new user_model(fname_str,lname_str,image_str,dob_dtr,gender_str,moreinfo_str,uid_str);
                                    user_model.add(a);

                                }
                                user_adapter.notifyDataSetChanged();
                                Log.d("size",String.valueOf(user_model.size()));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

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
                params.put("groupname",groupname);
                params.put("uid",utility.id);
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

