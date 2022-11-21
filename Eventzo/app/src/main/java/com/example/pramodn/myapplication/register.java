package com.example.pramodn.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.pramodn.myapplication.R.string.terms;
import static com.example.pramodn.myapplication.utility.Register;
import static java.security.AccessController.getContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.io.IOException;
import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pramodn.myapplication.HostedEvents;
import com.example.pramodn.myapplication.R;
import com.example.pramodn.myapplication.createnext;
import com.example.pramodn.myapplication.utility;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.io.IOException;
public class register extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE =1;
    private static final String TAG = "MainActivity";
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    EditText fname,lname,mailid,password,mobileno,dateofbirth;
    ImageView imagetoupload;
    TextView termsconditions;
    Button signup;
    ImageView picker;
    RadioGroup gender;
    RadioButton male,female;
    CheckBox checkterms;
    String selectedgender;
    RequestQueue queue;
    final Calendar myCalendar = Calendar.getInstance();

    Bitmap bitmap;


    private final int GALLERY = 1;
    private String upload_URL = utility.ip+Register;
    JSONObject jsonObject;
    RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Register");
        setContentView(R.layout.register);

        fname=findViewById(R.id.fname);
        picker=findViewById(R.id.picker);
        gender=findViewById(R.id.gender);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        lname=findViewById(R.id.lname);
        mailid=findViewById(R.id.mailid);
        password=findViewById(R.id.password);
        mobileno=findViewById(R.id.mobileno);
        imagetoupload=(ImageView) findViewById(R.id.imagetoupload);
        termsconditions=findViewById(R.id.termsconditions);
        signup=findViewById(R.id.signup);
        checkterms=findViewById(R.id.checkterms);
        queue = Volley.newRequestQueue(this);

        signup.setVisibility(View.INVISIBLE);

        requestMultiplePermissions();

        male.setChecked(true);
        selectedgender="Male";

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                final String value =
                        ((RadioButton)findViewById(gender.getCheckedRadioButtonId()))
                                .getText().toString();
                selectedgender=value;


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fname.getText().toString().equals("") || password.getText().toString().equals("") || mailid.getText().toString().equals("") || dateofbirth.equals("") || mobileno.getText().toString().equals("") || bitmap==null || selectedgender.equals("")) {
                    if (fname.getText().toString().equals(""))
                    {
                        fname.setError("Please enter email");
                    }
                    else if (password.getText().toString().equals("")){
                        password.setError("Please enter email");
                    }
                    else if (mailid.getText().toString().equals("")){
                        mailid.setError("Please enter email");
                    }
                    else if (dateofbirth.getText().toString().equals("")){
                        dateofbirth.setError("Please enter email");
                    }
                    else if (mobileno.getText().toString().equals("")){
                        mobileno.setError("Please enter email");
                    }
                    else if (bitmap==null){
                        Toast.makeText(register.this, "Select the image", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                        uploadImage(bitmap);

                }
            }        });


        checkterms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked)
                {
                    signup.setVisibility(View.INVISIBLE);
                }
                else {

                    signup.setVisibility(View.VISIBLE);
                }
            }
        });

        imagetoupload.setOnClickListener(this);
        termsconditions.setOnClickListener(this);
        picker.setOnClickListener(this);

        dateofbirth=(EditText) findViewById(R.id.dateofbirth);
        dateofbirth.setOnClickListener(this);

    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(register.this)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(register.this, "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                       Toast.makeText(register.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void onClick(View v) {
        if (v == imagetoupload) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        }


            else if (v==picker) {

            DatePickerDialog dialog=new DatePickerDialog(register.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMaxDate(new Date().getTime());
            dialog.show();

        }
        else if (v==termsconditions)
        {

            AlertDialog alertDialog = new AlertDialog.Builder(register.this).create();
            alertDialog.setTitle("Terms & Conditions");
            alertDialog.setMessage("1. Data charges are applied." +
                    " 2. Allows the user to make purchases from within this app. 3. Usesbone or more of: accounts on the device, profile data. 4. Uses the Device\\'s location. 5. Uses one or more of: files on the device, such as images, videos, or audio; the devices external storage. 6. Allows the app to view information about Wi-Fi networking, such as whether Wi-Fi is enabled and names of the connected Wi-Fi devices. 7. Allows the app to determine the phone number and device IDs, Whether a call is active, and the remote number connected by the call.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == register.this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(register.this.getContentResolver(), contentURI);
                    imagetoupload.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(register.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateofbirth.setText(sdf.format(myCalendar.getTime()));
    }

    private void uploadImage(Bitmap bitmap){
        Log.d("abc","abc");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        try {
            jsonObject = new JSONObject();
            String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
            jsonObject.put("name", imgname);
            //  Log.e("Image name", etxtUpload.getText().toString().trim());
            jsonObject.put("image", encodedImage);
            jsonObject.put("fname", fname.getText().toString());
            jsonObject.put("lname", lname.getText().toString());
            jsonObject.put("email", mailid.getText().toString());
            jsonObject.put("password", password.getText().toString());
            jsonObject.put("mobileno", mobileno.getText().toString());
            jsonObject.put("selectedgender", selectedgender);
            jsonObject.put("dateofbirth", dateofbirth.getText().toString());
            jsonObject.put("images", imagetoupload.getImageMatrix().toString());
            Log.d("jsonobject",jsonObject.toString());

        } catch (JSONException e) {
            Log.e("JSONObject Here", e.toString());
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, upload_URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.e("aaaaaaa", jsonObject.toString());
                        rQueue.getCache().clear();
                        try {

                            if (jsonObject.getString("success").toString().equals("1")) {
                                Toast.makeText(register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(register.this, login.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("aaaaaaa", volleyError.toString());

            }
        });

        rQueue = Volley.newRequestQueue(register.this);
        rQueue.add(jsonObjectRequest);

    }



}
