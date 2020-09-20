package View.Reception;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cliffdevops.alpha.dufleet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import View.Manager.DashboardActivity;

public class AddParcelActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private final int PICK_IMAGE_REQUEST = 1;
    private ImageView upLoadImage;
    private Bitmap bitmap;
    private Uri filePath = null;
    private Button submit;
    private String parcelCategory, staffID;
    private Spinner category, staff;
    private ProgressBar progressBar;
    private ArrayList<String> staffName = new ArrayList<String>();
    private EditText sender_name, sender_tel, receiver_name, receiver_tel, origin, dest, description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addparcel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        upLoadImage = findViewById(R.id.parcel_image);
        Button uploadButton = findViewById(R.id.btnUploadImage);
        category = findViewById(R.id.spinner2);
        staff = findViewById(R.id.spinner3);

        sender_name = findViewById(R.id.txtSender_name);
        sender_tel = findViewById(R.id.txtsender_tel);
        receiver_name = findViewById(R.id.txtReceiver_name);
        receiver_tel = findViewById(R.id.txtReceiver_mobile);
        origin = findViewById(R.id.txtorigin);
        dest = findViewById(R.id.txtdestination);
        description = findViewById(R.id.txtdescription);
        progressBar = findViewById(R.id.progressBar);
        submit = findViewById(R.id.btnSubmitParcel);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        category.setAdapter(new ArrayAdapter<String>(AddParcelActivity.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.category)));

        category.setOnItemSelectedListener(this);
        staff.setOnItemSelectedListener(this);
        getStaffName();

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("Select")) {
                    parcelCategory = null;
                } else {
                    parcelCategory = item;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        staff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                staffID = item.substring(0, 4);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateInputs().equals(true)) {

                }
            }
        });

    }

    private void goBack() {
        startActivity(new Intent(getApplicationContext(), ReceptionActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AddParcelActivity.this,
                        Text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                upLoadImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private Boolean validateInputs() {
        validateS_name();
        validateR_name();
        validateS_tel();
        validateR_tel();
        validateOrigin();
        validateDestination();
        validateDescription();

        return validateS_name().equals(true)
                && validateR_name().equals(true)
                && validateS_tel().equals(true)
                && validateR_tel().equals(true)
                && validateOrigin().equals(true)
                && validateDestination().equals(true)
                && validateDescription().equals(true);
    }

    private Boolean validateS_name() {
        String val = sender_name.getEditableText().toString().trim();

        if (val.isEmpty()) {
            sender_name.setError("Required");
            return false;
        } else {
            sender_name.setError(null);
            return true;
        }
    }

    private Boolean validateR_name() {
        String val = receiver_name.getEditableText().toString().trim();

        if (val.isEmpty()) {
            receiver_name.setError("Required");
            return false;
        } else {
            receiver_name.setError(null);
            return true;
        }
    }

    private Boolean validateS_tel() {
        String val = sender_tel.getEditableText().toString().trim();

        if (val.isEmpty()) {
            sender_tel.setError("Required");
            return false;
        } else {
            sender_tel.setError(null);
            return true;
        }
    }

    private Boolean validateR_tel() {
        String val = receiver_tel.getEditableText().toString().trim();

        if (val.isEmpty()) {
            receiver_tel.setError("Required");
            return false;
        } else {
            receiver_tel.setError(null);
            return true;
        }
    }

    private Boolean validateOrigin() {
        String val = origin.getEditableText().toString().trim();

        if (val.isEmpty()) {
            origin.setError("Require");
            return false;
        } else {
            origin.setError(null);
            return true;
        }
    }

    private Boolean validateDestination() {
        String val = dest.getEditableText().toString().trim();

        if (val.isEmpty()) {
            dest.setError("Required");
            return false;
        } else {
            dest.setError(null);
            return true;
        }
    }

    private Boolean validateDescription() {
        String val = description.getEditableText().toString().trim();

        if (val.isEmpty()) {
            description.setError("Required");
            return false;
        } else {
            description.setError(null);
            return true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getStaffName() {
        String url = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_staffname.php";
        StringRequest stringRequest;

        RequestQueue queue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            if (!response.equals("error")) {
                                JSONArray array = new JSONArray(response);

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);

                                    String id = object.getString("staff_id");
                                    String name = object.getString("surname");
                                    String staff_name = id + " " + name;

                                    staffName.add(staff_name);
                                    staff.setAdapter(new ArrayAdapter<String>(AddParcelActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item, staffName));
                                }
                            }

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                            showToast(ex.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Error: Request Failed");
            }
        });

        queue.add(stringRequest);

    }

    public void uploadFleet(final String id, final String type, final String capacity, final String reg,
                            final String date, final String mileage, final String Chassis) {

        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_addfleet.php";
        StringRequest stringRequest;

        submit.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Test", response);

                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("1")) {
                        showToast("Fleet added");
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        finish();
                    } else {
                        submit.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        showToast("upload failed");
                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    submit.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    showToast("Error: " + ex.toString());
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        submit.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        showToast("Fatal error " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("id", id);
                params.put("type", type);
                params.put("capacity", capacity);
                params.put("reg", reg);
                params.put("com_date", date);
                params.put("mileage", mileage);
                params.put("chassis", Chassis);
                params.put("image", imageToString(bitmap));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}