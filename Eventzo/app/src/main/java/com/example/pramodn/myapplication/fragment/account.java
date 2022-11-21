package com.example.pramodn.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.pramodn.myapplication.EditAccount;
import com.example.pramodn.myapplication.HelpFeedback;
import com.example.pramodn.myapplication.HostedEvents;
import com.example.pramodn.myapplication.Purchases;
import com.example.pramodn.myapplication.R;
import com.example.pramodn.myapplication.UploadedEvents;
import com.example.pramodn.myapplication.login;
import com.example.pramodn.myapplication.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class account extends Fragment  {

    private static final int RESULT_LOAD_IMAGE =1 ;
    private static final String TAG = "MainActivity";
    ImageView profileimage;
    TextView username,email,gender,phno,dob,extra,uploaded,hosted,purchases,complaint,delacc,logout;
    ImageButton edit;
    RequestQueue requestQueue;


   @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);


        username=view.findViewById(R.id.username);
        email=view.findViewById(R.id.email);
        gender=view.findViewById(R.id.gender);
        profileimage=view.findViewById(R.id.profileimage);
        phno=view.findViewById(R.id.phno);
        dob=view.findViewById(R.id.dob);
        extra=view.findViewById(R.id.extra);
        edit=view.findViewById(R.id.edit);
        uploaded=view.findViewById(R.id.uploaded);
        hosted=view.findViewById(R.id.hosted);
        purchases=view.findViewById(R.id.purchases);
        complaint=view.findViewById(R.id.complaint);
        delacc=view.findViewById(R.id.delacc);
        logout=view.findViewById(R.id.logout);


        requestQueue= Volley.newRequestQueue(getActivity());
        Load_user_profile();

        uploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), UploadedEvents.class);
                    startActivity(intent);
            }
        });

        hosted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), HostedEvents.class);
                startActivity(intent);
            }
        });

        purchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Purchases.class);
                startActivity(intent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new  Intent(getActivity(), EditAccount.class);
                    startActivity(intent);
            }
        });
        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), HelpFeedback.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), login.class);
                startActivity(intent);
            }
        });
        delacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String url = utility.ip+utility.delacc;
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.contains("Your account has been deleted"))

                                    {
                                        startActivity(new Intent(getActivity(), login.class));
                                    }

                                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                    Log.d("Response", response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

                                    Log.d("Error.Response", error.toString());
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("uid", utility.id);

                            return params;
                        }
                    };
                    requestQueue.add(postRequest);
                }

        });

        return view;
    }
    private void Load_user_profile() {

        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+ utility.load_profile,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());


                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String image_str = c.getString("image");
                                String fname = c.getString("fname");
                                String lname = c.getString("lname");
                                String emailid = c.getString("email");
                                String gender_str = c.getString("gender");
                                String phone = c.getString("phone");
                                String dob_str = c.getString("dob");
                                String moreinfo = c.getString("moreinfo");
                                String password_str = c.getString("password");

                                username.setText(fname+" "+lname);
                                email.setText(emailid);
                                gender.setText(gender_str);
                                phno.setText(phone);
                                dob.setText(dob_str);
                                extra.setText(moreinfo);

                                Glide.with(getActivity()).load(utility.IMAGE_URL +image_str).into(profileimage);

                            }


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
