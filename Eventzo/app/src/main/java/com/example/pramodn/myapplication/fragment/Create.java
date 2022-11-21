package com.example.pramodn.myapplication.fragment;

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
import com.example.pramodn.myapplication.register;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.io.IOException;

public class Create extends Fragment {

    EditText ename,synopsis,location,date,time,description,videopicker;
    ImageView locationpicker,datepicker,timepicker,imagepicker;
    Button next;
    CheckBox hosttype;
    RequestQueue queue;
    Spinner type;
    String selected_type;
    boolean isChecked;
    final Calendar myCalendar = Calendar.getInstance();
    String costtext ="free";
    Bitmap bitmap;


    private final int GALLERY = 1;
    private String upload_URL = utility.ip+"upload_image.php";
    JSONObject jsonObject;
    RequestQueue rQueue;

    private static final int RESULT_LOAD_IMAGE =1;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create, container, false);

       queue= Volley.newRequestQueue(getActivity());
       type=view.findViewById(R.id.type);
       hosttype=view.findViewById(R.id.hosttype);
       ename=view.findViewById(R.id.ename);
       synopsis=view.findViewById(R.id.synopsis);
       location=view.findViewById(R.id.location);
       date=view.findViewById(R.id.date);
       time=view.findViewById(R.id.time);
       description=view.findViewById(R.id.description);
       datepicker=view.findViewById(R.id.datepicker);
       timepicker=view.findViewById(R.id.timepicker);

       videopicker=view.findViewById(R.id.videopicker);
       next=view.findViewById(R.id.next);

        imagepicker=view.findViewById(R.id.imagepicker);

        requestMultiplePermissions();


        final String etype[] = {"Event Type","Sports"," Social Competition","Festival","Cultural Programs","College Events", "School Events","Programs","Dancing Programs","Singing Programs","Others"};

// Selection of the spinner

// Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(),   android.R.layout.simple_spinner_item, etype);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        type.setAdapter(spinnerArrayAdapter);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_type=etype[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_type.equals("")|| ename.getText().toString().equals("")||imagepicker.getImageMatrix().toString().equals("") || synopsis.getText().toString() .equals("")|| date.getText().toString() .equals("")|| time.getText().toString() .equals("")) {
                    Toast.makeText(getContext(), "fill the required fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (!hosttype.isChecked()) {
                        Log.d("type","paid");

                        uploadImage(bitmap);
                    }
                    else{
                        Log.d("type","free");

                        uploadImage(bitmap);
                        }
                    }
                }        });

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog=new DatePickerDialog(getActivity(), dates, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(new Date().getTime());
                dialog.show();
            }
        });

        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        imagepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                     bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    imagepicker.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }    DatePickerDialog.OnDateSetListener dates = new DatePickerDialog.OnDateSetListener() {

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

        date.setText(sdf.format(myCalendar.getTime()));
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(getActivity())
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
    private void uploadImage(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        try {
            jsonObject = new JSONObject();
            String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
            jsonObject.put("name", imgname);
            //  Log.e("Image name", etxtUpload.getText().toString().trim());
            jsonObject.put("image", encodedImage);
            jsonObject.put("type", type.getSelectedItem().toString());
            jsonObject.put("uid", utility.id);
            jsonObject.put("ename", ename.getText().toString());
            jsonObject.put("synopsis", synopsis.getText().toString());
            jsonObject.put("date", date.getText().toString());
            jsonObject.put("time", time.getText().toString());
            jsonObject.put("location", location.getText().toString());
            jsonObject.put("images", imagepicker.getImageMatrix().toString());
            jsonObject.put("description", description.getText().toString());
            jsonObject.put("videopicker", videopicker.getText().toString());
            if (hosttype.isChecked()){
                costtext="free";
            }
            else{
                costtext="cost";
            }
            jsonObject.put("hosttype", costtext);
            // jsonObject.put("aa", "aa");
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
                            String e_id=jsonObject.getString("e_id");
                            if(hosttype.isChecked()){
                                    Intent intent=new Intent(getActivity(), HostedEvents.class);
                                    intent.putExtra("event_id",e_id);
                                    startActivity(intent);
                            }
                            else {
                                    costtext="cost";
                                Intent intent=new Intent(getActivity(),createnext.class);
                                intent.putExtra("event_id",e_id);
                                startActivity(intent);

                            }
                            Log.d("e_id",e_id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("aaaaaaa", volleyError.toString());

            }
        });

        rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(jsonObjectRequest);

    }

}

