package View.Common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cliffdevops.alpha.dufleet.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ParcelDetailsActivity extends AppCompatActivity {

    private String parcel_id;
    private ProgressBar progressBar;
    private Button deliver;
    private TextView category, status, name, mobile, address, destination, origin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_details);
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);
        category = findViewById(R.id.txtParcelCategory);
        status = findViewById(R.id.txtParcelStatus);
        name = findViewById(R.id.txtName_R);
        mobile = findViewById(R.id.txtMobile_R);
        address = findViewById(R.id.txtAddress_R);
        destination = findViewById(R.id.txtDestination_R);
        origin = findViewById(R.id.txtOrion_R);
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
}