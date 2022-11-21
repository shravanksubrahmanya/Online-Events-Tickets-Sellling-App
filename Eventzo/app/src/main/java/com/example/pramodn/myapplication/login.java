package com.example.pramodn.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity implements View.OnClickListener {

    EditText uname,password;
    Button login;
    RequestQueue queue;
    TextView signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        uname = findViewById(R.id.username);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        password = findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(this);
        queue = Volley.newRequestQueue(this);

    }

    @Override
    public void onClick(View v) {

        if (v == login) {
            if (uname.getText().toString().equals("")) {
                uname.setError("Please enter email");
            } else if (password.getText().toString().equals("")) {
                password.setError("Please enter password");
            } else {
                String url = utility.ip + utility.Login;
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("response",response);


                                if (response.contains("login fail")) {
                                    Toast.makeText(com.example.pramodn.myapplication.login.this, "Login failed", Toast.LENGTH_SHORT).show();

                                } else {

                                    Intent intent = new Intent(com.example.pramodn.myapplication.login.this, Home.class);
                                    utility.id = response;
                                    startActivity(intent);
                                    finish();
                                }


                                Log.d("Response", response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(com.example.pramodn.myapplication.login.this, error.toString(), Toast.LENGTH_SHORT).show();

                                Log.d("Error.Response", error.toString());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", uname.getText().toString());
                        params.put("password", password.getText().toString());

                        return params;
                    }
                };
                queue.add(postRequest);
            }
        }

        else if (v==signup)
        {
            Intent intent=new  Intent(login.this,register.class);
            startActivity(intent);

        }

    }

}

