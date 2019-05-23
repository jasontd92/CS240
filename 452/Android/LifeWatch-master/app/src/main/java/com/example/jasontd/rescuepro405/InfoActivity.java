package com.example.jasontd.rescuepro405;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class InfoActivity extends AppCompatActivity {

    private Button mEmergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("Employee Status");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtName = (TextView)findViewById(R.id.name);
        TextView txtVehicle = (TextView)findViewById(R.id.vehicle);
        TextView txtTime = (TextView)findViewById(R.id.timeofaccident);
        TextView txtLocation = (TextView)findViewById(R.id.location);
        TextView txtSpeed = (TextView)findViewById(R.id.speedofimpact);
        Button btnCall = (Button)findViewById(R.id.callbutton);
        TextView txtStatus = (TextView)findViewById(R.id.status);
        ImageView btnColor = (ImageView)findViewById(R.id.btnColor);
        mEmergency = (Button)findViewById(R.id.emergency_button);

        Bundle b = getIntent().getExtras();
        if (b != null)
        {
            String name = b.getString("name");
            txtName.setText(name);

            if (name.equals("John Doe"))
            {
                txtVehicle.setText("Vehicle: Ford F-150 2013");
                txtTime.setText("Time of Accident: N/A");
                txtLocation.setText("Location: Boise, ID");
                txtSpeed.setText("Crash Speed: N/A");
                btnCall.setText("Call John");
                txtStatus.setText("Status: OK");
                btnColor.setBackgroundResource(R.drawable.bigcircle);
                setStatus("ok");

            }
            else if (name.equals("Jane Smith"))
            {
                txtVehicle.setText("Vehicle: Chevrolet 2016");
                txtTime.setText("Time of Accident: N/A");
                txtLocation.setText("Location: Pasco, WA");
                txtSpeed.setText("Crash Speed: N/A");
                btnCall.setText("Call Jane");
                txtStatus.setText("Status: Crash detected, driver ok");
                btnColor.setBackgroundResource(R.drawable.bigwarningcircle);
                setStatus("warning");
            }
            else if (name.equals("Jack Murdoch"))
            {
                txtVehicle.setText("Vehicle: Dodge Ram 2015");
                txtTime.setText("Time of Accident: N/A");
                txtLocation.setText("Location: Kennewick, WA");
                txtSpeed.setText("Crash Speed: N/A");
                btnCall.setText("Call Jack");
                txtStatus.setText("Status: Crash detected, no response from driver");
                btnColor.setBackgroundResource(R.drawable.bigdangercircle);
                setStatus("danger");
            }
            else if (name.equals("Mike Stevens"))
            {
                txtVehicle.setText("Vehicle: Ford F-150 2011");
                txtTime.setText("Time of Accident: 4:05 PM");
                txtLocation.setText("Location: Payson, UT");
                txtSpeed.setText("Crash Speed: 60mph");
                btnCall.setText("Call Mike");
                txtStatus.setText("Status: OK");
                btnColor.setBackgroundResource(R.drawable.bigcircle);
                setStatus("ok");
            }
        }
    }

    private void setStatus(String status){
        if (status.toLowerCase().equals("ok")){
            mEmergency.setEnabled(false);
            //mEmergency.setBackgroundColor(getResources().getColor(R.color.grey_disabled));
            mEmergency.setVisibility(View.INVISIBLE);
        }
        if (status.toLowerCase().equals("warning")){
            mEmergency.setEnabled(false);
            //mEmergency.setBackgroundColor(getResources().getColor(R.color.logo_blue));
            mEmergency.setVisibility(View.INVISIBLE);
        }
        if (status.toLowerCase().equals("danger")){
            mEmergency.setEnabled(true);
            //mEmergency.setBackgroundColor(getResources().getColor(R.color.logo_blue));
        }
    }

    //Up button
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                startTopActivity(this.getApplicationContext(), false);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Logging out or just returning to MapFragment
    private void startTopActivity(Context context, boolean newInstance)
    {
        Intent intent = new Intent(context, MainActivity.class);
        if (newInstance)
        {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        else
        {
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        context.startActivity(intent);
    }
}
