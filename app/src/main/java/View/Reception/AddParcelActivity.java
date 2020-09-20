package View.Reception;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.Objects;

public class AddParcelActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private final int PICK_IMAGE_REQUEST = 1;
    private ImageView upLoadImage;
    private Bitmap bitmap;
    private Uri filePath = null;
    private String parcelCategory, staffID;
    private Spinner category, staff;
    private ArrayList<String> staffName = new ArrayList<String>();


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

}