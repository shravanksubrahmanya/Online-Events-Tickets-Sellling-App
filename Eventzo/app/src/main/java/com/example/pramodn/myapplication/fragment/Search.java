package com.example.pramodn.myapplication.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pramodn.myapplication.R;
import com.example.pramodn.myapplication.event_adapter.event_adapter;
import com.example.pramodn.myapplication.event_adapter.event_model;
import com.example.pramodn.myapplication.event_adapter.user_adapter;
import com.example.pramodn.myapplication.event_adapter.user_model;
import com.example.pramodn.myapplication.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.pramodn.myapplication.utility.Search_people;

public class Search extends Fragment {
    private RecyclerView event_recycler;
    private com.example.pramodn.myapplication.event_adapter.event_adapter event_adapter;
    private List<com.example.pramodn.myapplication.event_adapter.event_model> event_model;
    RequestQueue requestQueue;
    com.example.pramodn.myapplication.utility utility;

    private com.example.pramodn.myapplication.event_adapter.user_adapter user_adapter;
    private List<com.example.pramodn.myapplication.event_adapter.user_model> user_model;

    EditText searchitem;
    ImageButton search;
    Button people,events;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchitem=view.findViewById(R.id.searchitem);
        search=view.findViewById(R.id.search);
        people=view.findViewById(R.id.people);
        events=view.findViewById(R.id.events);
        event_recycler=view.findViewById(R.id.event_recycler);
        requestQueue= Volley.newRequestQueue(getActivity());
        utility=new utility();

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event_model = new ArrayList<>();
                event_adapter = new event_adapter(getActivity(), event_model);
                RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(getActivity(), 1);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
                event_recycler.setLayoutManager(linearLayoutManager1);
                event_recycler.setLayoutManager(mLayoutManager1);
                event_recycler.setItemAnimator(new DefaultItemAnimator());
                event_recycler.setAdapter(event_adapter);
                events.setBackgroundColor(Color.RED);
                people.setBackgroundColor(Color.GRAY);

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (searchitem.getText().toString().equals(""))
                        {
                            Toast.makeText(getContext(), "Enter the search details", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Load_data_from_DB();
                        }
                    }
                });

            }

        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_model = new ArrayList<>();
                user_adapter = new user_adapter(getActivity(), user_model);
                RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(getActivity(), 1);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
                event_recycler.setLayoutManager(linearLayoutManager1);
                event_recycler.setLayoutManager(mLayoutManager1);
                event_recycler.setItemAnimator(new DefaultItemAnimator());
                event_recycler.setAdapter(user_adapter);
                events.setBackgroundColor(Color.GRAY);
                people.setBackgroundColor(Color.RED);
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (searchitem.getText().toString().equals(""))
                        {
                            Toast.makeText(getContext(), "Enter the search details", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Load_data_from_people_DB();
                        }
                    }
                });
            }
        });

        return view;
    }


    private void Load_data_from_DB() {


        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+utility.Search_events,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());


                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                        event_model a;

                        try {

                            event_model.clear();

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String event_id = c.getString("event_id");
                                String event_name = c.getString("event_name");
                                String synopsis = c.getString("synopsis");
                                String image = c.getString("image");
                                int likes=c.getInt("likes");
                                int numberofcomments=c.getInt("numberofcomments");

                                a = new event_model(event_id, event_name,synopsis,image,likes,false,numberofcomments);
                                event_model.add(a);

                            }
                            event_adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put("searchitem",searchitem.getText().toString());
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                6 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        requestQueue.add(postRequest);


    }

    private void Load_data_from_people_DB() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+Search_people,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response_connection", response.toString());
                        com.example.pramodn.myapplication.event_adapter.user_model a;

                        if (response.equals("no connection found")||response.equals("no requests found")){
                            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
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
                params.put("searchitem",searchitem.getText().toString());
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
