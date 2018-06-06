package fr.openpathview.controlapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.openpathview.controlapp.activity.MenuActivity;

public class ControlApp extends AppCompatActivity{
    private Button connectButton;
    private static EditText textAddress;

    private static String address;
    private static Activity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_page);

        this.connectButton = findViewById(R.id.connectButton);
        this.connectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ControlApp.textAddress = findViewById(R.id.serverAddress);
                ControlApp.address = ControlApp.textAddress.getText().toString();

                startActivity(new Intent(ControlApp.this, MenuActivity.class));
            }
        });
    }

    protected void onResume(){
        super.onResume();
        ControlApp.setCurrentActivity(this);
    }

    public static String getAddress(){ return ControlApp.address; }

    public static void setCurrentActivity(Activity activity){ ControlApp.currentActivity = activity; }
    public static Activity getCurrentActivity() { return ControlApp.currentActivity; }
}
