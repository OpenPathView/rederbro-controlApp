package fr.openpathview.controlapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import fr.openpathview.controlapp.ControlApp;
import fr.openpathview.controlapp.R;
import fr.openpathview.controlapp.zmq_thread.SendMessageThread;

public class SensorsActivity extends Activity {
    private Button cord;

    private Button automodeOn;
    private Button automodeOff;

    private Button setDist;
    private static EditText automodeDist;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensors);

        this.cord = findViewById(R.id.cord);
        this.cord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject args = new JSONObject();
                try {
                    args.put("command", "cord");
                    args.put("args", "");
                    args.put("topic", "sensors");
                }catch(Exception e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(args);
                sendMessageThread.run();
            }
        });

        this.automodeOn = findViewById(R.id.automodeOn);
        this.automodeOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject args = new JSONObject();
                try {
                    args.put("command", "automode");
                    args.put("args", "on");
                    args.put("topic", "sensors");
                }catch(Exception e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(args);
                sendMessageThread.run();
            }
        });

        this.automodeOff = findViewById(R.id.automodeOff);
        this.automodeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject args = new JSONObject();
                try {
                    args.put("command", "automode");
                    args.put("args", "off");
                    args.put("topic", "sensors");
                }catch(Exception e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(args);
                sendMessageThread.run();
            }
        });

        this.automodeDist = findViewById(R.id.distance);
        this.setDist = findViewById(R.id.setDistance);
        this.setDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject args = new JSONObject();
                try {
                    args.put("command", "distance");
                    args.put("args", SensorsActivity.automodeDist.getText());
                    args.put("topic", "sensors");
                }catch(Exception e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(args);
                sendMessageThread.run();
            }
        });
    }

    protected void onResume(){
        super.onResume();
        ControlApp.setCurrentActivity(this);
    }
}
