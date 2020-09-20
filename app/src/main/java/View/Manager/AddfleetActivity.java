package View.Manager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cliffdevops.alpha.dufleet.R;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddfleetActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 1;
    private ImageView upLoadImage;
    private Button submit;
    private Bitmap bitmap;
    private Uri filePath = null;
    private ProgressBar progressBar;
    private EditText fleetType, fleetCapacity, fleetRegNo, fleetCommissionDate,
            fleetComMileage, fleetChaise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfleet);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);
        fleetType = findViewById(R.id.txtFleet_type);
        fleetCapacity = findViewById(R.id.txtFleet_capacity);
        fleetRegNo = findViewById(R.id.txtFleet_reg);
        fleetCommissionDate = findViewById(R.id.txtFleet_CommissionDate);
        fleetComMileage = findViewById(R.id.txtFleet_comMileage);
        fleetChaise = findViewById(R.id.txtFleet_chaise);
        upLoadImage = findViewById(R.id.fleet_Image);
        Button uploadButton = findViewById(R.id.btnUploadImage);
        submit = findViewById(R.id.btnSubmit_fleet);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
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
                String id = randomCode().toUpperCase();
                String type = fleetType.getText().toString().trim();
                String capacity = fleetCapacity.getText().toString().trim();
                String reg = fleetRegNo.getText().toString().trim();
                String date = fleetCommissionDate.getText().toString().trim();
                String mileage = fleetComMileage.getText().toString().trim();
                String chassis = fleetChaise.getText().toString().trim();

                if (validateInputs().equals(true) && filePath != null) {
                    uploadFleet(id, type, capacity, reg, date, mileage, chassis);
                } else {
                    showToast("Image is required");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        overridePendingTransition(0, 0);
        finish();
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

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AddfleetActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }

    protected String randomCode() {
        int length = 6;
        return "F-DU-" + RandomStringUtils.random(length, true, true);
    }

    public String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private Boolean validateInputs() {
        validateType();
        validateCapacity();
        validateReg();
        validateDate();
        validateMileage();
        validateChassis();

        return validateType().equals(true)
                && validateCapacity().equals(true)
                && validateReg().equals(true)
                && validateDate().equals(true)
                && validateMileage().equals(true)
                && validateChassis().equals(true);
    }

    private Boolean validateType() {
        String val = fleetType.getEditableText().toString().trim();

        if (val.isEmpty()) {
            fleetType.setError("Field cannot be empty");
            return false;
        } else {
            fleetType.setError(null);
            return true;
        }
    }

    private Boolean validateCapacity() {
        String val = fleetCapacity.getEditableText().toString().trim();

        if (val.isEmpty()) {
            fleetCapacity.setError("Field cannot be empty");
            return false;
        } else {
            fleetCapacity.setError(null);
            return true;
        }
    }

    private Boolean validateReg() {
        String val = fleetRegNo.getEditableText().toString().trim();

        if (val.isEmpty()) {
            fleetRegNo.setError("Field cannot be empty");
            return false;
        } else {
            fleetRegNo.setError(null);
            return true;
        }
    }

    private Boolean validateDate() {
        String val = fleetCommissionDate.getEditableText().toString().trim();

        if (val.isEmpty()) {
            fleetCommissionDate.setError("Field cannot be empty");
            return false;
        } else {
            fleetCommissionDate.setError(null);
            return true;
        }
    }

    private Boolean validateMileage() {
        String val = fleetComMileage.getEditableText().toString().trim();

        if (val.isEmpty()) {
            fleetComMileage.setError("Field cannot be empty");
            return false;
        } else {
            fleetComMileage.setError(null);
            return true;
        }
    }

    private Boolean validateChassis() {
        String val = fleetChaise.getEditableText().toString().trim();

        if (val.isEmpty()) {
            fleetChaise.setError("Field cannot be empty");
            return false;
        } else {
            fleetChaise.setError(null);
            return true;
        }
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                Log.d("Test", "id " + id + " image" + imageToString(bitmap));

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