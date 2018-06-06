package fr.openpathview.controlapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button connectButton;
    public static String address;
    private static EditText textAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);
        this.connectButton = findViewById(R.id.connectButton);

        this.connectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.textAddress = findViewById(R.id.serverAddress);
                MainActivity.address = MainActivity.textAddress.getText().toString();
                startActivity(new Intent(MainActivity.this, ConnectActivity.class));
            }
        });
    }
}
