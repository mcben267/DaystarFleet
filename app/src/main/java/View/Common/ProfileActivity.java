package View.Common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import View.Conductor.ConductorActivity;
import View.Driver.DriverActivity;
import View.Manager.DashboardActivity;
import View.Reception.ReceptionActivity;

public class ProfileActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private String UserEmail, Password;
    private Button changePassword;
    private EditText oldPassword, newPassword, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pref = getSharedPreferences("login", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        EditText user_name = findViewById(R.id.txtName);
        EditText user_email = findViewById(R.id.txtStaffEmail);
        EditText user_mobile = findViewById(R.id.txtContactMobile);
        oldPassword = findViewById(R.id.txtOldPassword);
        newPassword = findViewById(R.id.txtNewPassword);
        confirmPassword = findViewById(R.id.txtConfirmNewPassword);
        changePassword = findViewById(R.id.btnChangePassword);
        // CheckBox getPushNotification = findViewById(R.id.checkBoxPushNotice);

        user_name.setText(pref.getString("name", "NULL"));
        user_email.setText(pref.getString("email", "NULL"));
        user_mobile.setText(pref.getString("mobile", "NULL"));

        UserEmail = pref.getString("email", "NULL");

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String OldPassword = oldPassword.getText().toString().trim();
                String NewPassword = Password;

                if (validatePassword().equals(true)) {
                    ChangePassword(UserEmail, OldPassword, NewPassword);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        String user = pref.getString("user", "");
        switch (user) {
            case "Admin":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
            case "Driver":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), DriverActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
            case "Conductor":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), ConductorActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
            case "Reception":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), ReceptionActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
            case "Manager":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
        }
    }

    private Boolean validatePassword() {
        String inputA = newPassword.getEditableText().toString().trim();
        String inputB = confirmPassword.getEditableText().toString().trim();
        String inputC = oldPassword.getEditableText().toString().trim();
        String passwordPattern = "^" +
                "(?=.*[A-Za-z])" +  //any letter
                "(?=.*[0-9])" +     //at least 1 digit
                "(?=\\S+$)" +       //no whitespace
                ".{6,}" +            //at least 6 characters
                "$";

        if (inputA.isEmpty() && inputB.isEmpty() && inputC.isEmpty()) {
            oldPassword.setError("Field cannot be empty");
            newPassword.setError("Field cannot be empty");
            confirmPassword.setError("Field cannot be empty");
            return false;
        }

        if (inputC.isEmpty()) {
            oldPassword.setError("Field cannot be empty");
            return false;
        }

        if (!inputC.matches(passwordPattern)) {
            String error_msg = "Password requirements: letters, digits, minimum 6 characters";
            oldPassword.setError(error_msg);
            return false;
        }

        if (!inputA.equals(inputB)) {
            newPassword.setError("password do not match");
            confirmPassword.setError("password do not match");
            return false;
        }
        if (inputA.matches(passwordPattern) && inputB.matches(passwordPattern)) {
            Password = newPassword.getText().toString().trim();
            newPassword.setError(null);
            confirmPassword.setError(null);
            return true;
        } else {
            String error_msg = "Password requirements: letters, digits, minimum 6 characters";
            newPassword.setError(error_msg);
            confirmPassword.setError(error_msg);
            return false;
        }
    }

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ProfileActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ChangePassword(final String email, final String oldPassword, final String newPassword) {

        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_changepassword.php";
        StringRequest stringRequest;

        changePassword.setVisibility(View.INVISIBLE);

        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("volley_response", response);

                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("1")) {
                        showToast("Password Changed Successfully");
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        finish();

                    } else if (success.equals("2")) {
                        showToast("old Password doe not match");
                    } else {
                        showToast("Change Password Request failed");
                    }
                    changePassword.setVisibility(View.VISIBLE);

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    changePassword.setVisibility(View.VISIBLE);
                    showToast("Request failed");
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        changePassword.setVisibility(View.VISIBLE);
                        showToast("Error: Check Internet Connection");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("email", email);
                params.put("oldPassword", oldPassword);
                params.put("newPassword", newPassword);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}