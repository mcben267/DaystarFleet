package View.Manager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewUserActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    String User;
    private Button submit;
    private ProgressBar progressBar;
    private EditText firstname, lastname, tel, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        Spinner category = findViewById(R.id.spinner);
        firstname = findViewById(R.id.txt_firstname);
        lastname = findViewById(R.id.txt_lastname);
        email = findViewById(R.id.txt_email);
        tel = findViewById(R.id.txt_mobile);
        progressBar = findViewById(R.id.progressBar);
        submit = findViewById(R.id.btnSubmit_user);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        category.setAdapter(new ArrayAdapter<>(NewUserActivity.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.users)));

        category.setOnItemSelectedListener(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = randomCode();
                String stamp = getDate();
                String password = randomPassword();
                String name = firstname.getText().toString().trim();
                String surname = lastname.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String mobile = tel.getText().toString().trim();

                if (validateInputs().equals(true)) {
                    if (User != null && !User.equals("Select")) {
                        newUser(id, User, name, surname, mail, mobile, password);
                    } else {
                        showToast("Choose account type");
                    }

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

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(NewUserActivity.this,
                        Text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected String randomCode() {
        int length = 4;
        return RandomStringUtils.random(length, false, true);
    }

    protected String randomPassword() {
        int length = 10;
        return RandomStringUtils.random(length, true, true);
    }

    private String getDate() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }

    private Boolean validateInputs() {
        validateName();
        validateSurname();
        validateEmail();
        validateMobile();

        return validateName() && validateSurname()
                && validateEmail() && validateMobile();
    }

    private Boolean validateName() {
        String text = firstname.getText().toString().trim();
        if (!text.isEmpty()) {
            firstname.setError(null);
            return true;
        } else {
            firstname.setError("Required");
            return false;
        }
    }

    private Boolean validateSurname() {
        String text = lastname.getText().toString().trim();
        if (!text.isEmpty()) {
            lastname.setError(null);
            return true;
        } else {
            lastname.setError("Required");
            return false;
        }
    }

    private Boolean validateEmail() {
        String text = email.getText().toString().trim();
        if (!text.isEmpty()) {
            email.setError(null);
            return true;
        } else {
            email.setError("Required");
            return false;
        }
    }

    private Boolean validateMobile() {
        String text = tel.getText().toString().trim();
        if (!text.isEmpty()) {
            tel.setError(null);
            return true;
        } else {
            tel.setError("Required");
            return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        User = item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void newUser(final String user_id, final String type, final String name, final String surname,
                         final String email, final String mobile, final String password) {

        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_newuser.php";
        StringRequest stringRequest;

        submit.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Test", response);

                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("1")) {
                        showToast("added successfully");
                        submit.setVisibility(View.VISIBLE);

                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        overridePendingTransition(0, 0);
                        finish();

                    } else if (success.equals("0")) {
                        showToast("failed");
                        submit.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    submit.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    showToast("Error: Fatal error");
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        submit.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        showToast("Error: Failed");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("user_id", user_id);
                params.put("name", name);
                params.put("surname", surname);
                params.put("email", email);
                params.put("mobile", mobile);
                params.put("password", password);
                params.put("type", type);
                Log.d("test", "Parmas >> " + params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}