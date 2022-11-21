package com.example.pramodn.myapplication;

import android.accounts.Account;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class HelpFeedback extends AppCompatActivity {

    EditText feedback;
    Button submit;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_feedback);
        setTitle("Help & Feedback");

        feedback=findViewById(R.id.feedback);
        submit=findViewById(R.id.submit);
        queue = Volley.newRequestQueue(this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedback!=null) {
                    String url = utility.ip+utility.feedback;
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.contains("Feedback sent"))

                                    {
                                        startActivity(new Intent(HelpFeedback.this, HelpFeedback.class));
                                    }

                                    Toast.makeText(HelpFeedback.this, response, Toast.LENGTH_SHORT).show();
                                    Log.d("Response", response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(HelpFeedback.this, error.toString(), Toast.LENGTH_SHORT).show();

                                    Log.d("Error.Response", error.toString());
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("uid", utility.id);
                            params.put("feedback", feedback.getText().toString());

                            return params;
                        }
                    };
                    queue.add(postRequest);
                }
            }
        });
    }
}
