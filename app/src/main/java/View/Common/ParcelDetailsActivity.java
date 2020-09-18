package View.Common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cliffdevops.alpha.dufleet.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParcelDetailsActivity extends AppCompatActivity {

    private String parcel_id;
    private ProgressBar progressBar;
    private Button deliver;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_details);
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);
        TextView category = findViewById(R.id.txtParcelCategory);
        TextView status = findViewById(R.id.txtParcelStatus);
        TextView name = findViewById(R.id.txtName_R);
        TextView mobile = findViewById(R.id.txtMobile_R);
        TextView address = findViewById(R.id.txtAddress_R);
        TextView destination = findViewById(R.id.txtDestination_R);
        TextView origin = findViewById(R.id.txtOrion_R);
        deliver = findViewById(R.id.btnDeliver);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        String user = pref.getString("user", "");
        final LocalDate currentDate = LocalDate.now();

        //Extracting Data from previous activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        assert extras != null;

        parcel_id = extras.getString("id");
        category.setText(extras.getString("category"));
        status.setText(extras.getString("status"));
        name.setText(extras.getString("name"));
        mobile.setText(extras.getString("mobile"));
        address.setText(extras.getString("address"));
        destination.setText(extras.getString("destination"));
        origin.setText(extras.getString("origin"));
        String image = extras.getString("image");

        getParcelImage(image);

        if (status.getText().equals("Delivered")) {
            deliver.setVisibility(View.GONE);
        }

        if (user.equals("Manager") || user.equals("Admin")) {
            deliver.setVisibility(View.GONE);
        }

        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemDelivery(currentDate.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        startActivity(new Intent(getApplicationContext(), ParcelActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }

    public void getParcelImage(String imageUrl) {
        ImageView parcelImage = findViewById(R.id.parcel_Image);
        Picasso.get().load(imageUrl).into(parcelImage);
    }

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ParcelDetailsActivity.this,
                        Text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void itemDelivery(final String systemDate) {
        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_delivery.php";
        StringRequest stringRequest;

        deliver.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("1")) {
                        goBack();
                        showToast("Successful");

                    } else {
                        showToast("Failed");
                        deliver.setVisibility(View.VISIBLE);
                    }
                    progressBar.setVisibility(View.GONE);

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    deliver.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    showToast("Request failed");
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        deliver.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        showToast("Error: Check Internet Connection");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", parcel_id);
                params.put("date", systemDate);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}