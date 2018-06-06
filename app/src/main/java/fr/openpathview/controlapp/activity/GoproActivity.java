package fr.openpathview.controlapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import fr.openpathview.controlapp.ControlApp;
import fr.openpathview.controlapp.R;
import fr.openpathview.controlapp.zmq_thread.SendMessageThread;

public class GoproActivity extends Activity {
    private Button relayOn;
    private Button relayOff;

    private Button goproOn;
    private Button goproOff;

    private Button takepic;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gopro);

        this.relayOn = findViewById(R.id.relayOn);
        this.relayOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject args = new JSONObject();
                try {
                    args.put("command", "relay");
                    args.put("args", "on");
                    args.put("topic", "gopro");
                }catch(Exception e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(args);
                sendMessageThread.run();
            }
        });

        this.relayOff = findViewById(R.id.relayOff);
        this.relayOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject args = new JSONObject();
                try {
                    args.put("command", "relay");
                    args.put("args", "off");
                    args.put("topic", "gopro");
                }catch(Exception e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(args);
                sendMessageThread.run();
            }
        });

        this.goproOn = findViewById(R.id.goproOn);
        this.goproOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject args = new JSONObject();
                try {
                    args.put("command", "gopro");
                    args.put("args", "on");
                    args.put("topic", "gopro");
                }catch(Exception e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(args);
                sendMessageThread.run();
            }
        });

        this.goproOff = findViewById(R.id.goproOff);
        this.goproOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject args = new JSONObject();
                try {
                    args.put("command", "gopro");
                    args.put("args", "off");
                    args.put("topic", "gopro");
                }catch(Exception e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(args);
                sendMessageThread.run();
            }
        });

        this.takepic = findViewById(R.id.takepic);
        this.takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject args = new JSONObject();
                try {
                    args.put("command", "takepic");
                    args.put("args", "");
                    args.put("topic", "gopro");
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
