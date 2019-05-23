package com.example.jasontd.rescuepro405;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("Logout");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextMessage = (TextView) findViewById(R.id.message);
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void showInfo(View view)
    {
        Intent intent = new Intent(this, InfoActivity.class);
        Bundle b = new Bundle();
        TableRow row = (TableRow)view;
        LinearLayout nameLayout = (LinearLayout)row.getChildAt(0);
        TextView personName = (TextView)nameLayout.getChildAt(1);
        b.putString("name", personName.getText().toString());
        intent.putExtras(b);
        startActivity(intent);
        finish();
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
        Intent intent = new Intent(context, LoginActivity.class);
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
