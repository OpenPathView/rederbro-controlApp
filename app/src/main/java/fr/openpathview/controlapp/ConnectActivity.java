package fr.openpathview.controlapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by simon on 13/01/18.
 */

public class ConnectActivity extends Activity {
    private Button relayOn;
    private Button relayOff;
    private Button takepic;
    private Button goproOn;
    private Button goproOff;
    private Button automodeOn;
    private Button automodeOff;
    private Button automodeSet;
    private Button getCord;
    private static EditText automodeDistance;
    private Button campaignNew;
    private Button campaignAttach;
    private static EditText campaignName;

    private TextView lat;
    private TextView lon;
    private TextView alt;
    private TextView angle;

    public static Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;

        this.relayOn = findViewById(R.id.relayOn);
        this.relayOff = findViewById(R.id.relayOff);
        this.takepic = findViewById(R.id.takePicture);
        this.goproOn = findViewById(R.id.goproOn);
        this.goproOff = findViewById(R.id.goproOff);
        this.automodeOn = findViewById(R.id.automodeOn);
        this.automodeOff = findViewById(R.id.automodeOff);
        this.automodeSet = findViewById(R.id.automodeSet);
        this.automodeDistance = findViewById(R.id.automodeDistance);
        this.campaignNew = findViewById(R.id.newCampaign);
        this.campaignAttach = findViewById(R.id.attachCampaign);
        this.campaignName = findViewById(R.id.campaignName);
        this.getCord = findViewById(R.id.getcord);

        this.lat = findViewById(R.id.latitude);
        this.lon = findViewById(R.id.longitude);
        this.alt = findViewById(R.id.altitude);
        this.angle = findViewById(R.id.angle);

        ReceiveThread receiveThread = new ReceiveThread(this.lat, this.lon, this.alt, this.angle);
        receiveThread.start();

        this.relayOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                try{
                    obj.put("topic", "gopro");
                    obj.put("command", "relay");
                    obj.put("args", "on");

                }catch(JSONException e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(obj);
                sendMessageThread.start();

            }
        });
        this.relayOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                try{
                    obj.put("topic", "gopro");
                    obj.put("command", "relay");
                    obj.put("args", "off");

                }catch(JSONException e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(obj);
                sendMessageThread.start();
            }
        });
        this.takepic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                try{
                    obj.put("topic", "gopro");
                    obj.put("command", "takepic");
                    obj.put("args", true);

                }catch(JSONException e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(obj);
                sendMessageThread.start();
            }
        });
        this.goproOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                try{
                    obj.put("topic", "gopro");
                    obj.put("command", "gopro");
                    obj.put("args", "on");

                }catch(JSONException e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(obj);
                sendMessageThread.start();
            }
        });
        this.goproOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                try{
                    obj.put("topic", "gopro");
                    obj.put("command", "gopro");
                    obj.put("args", "off");

                }catch(JSONException e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(obj);
                sendMessageThread.start();
            }
        });
        this.automodeOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                try{
                    obj.put("topic", "sensors");
                    obj.put("command", "automode");
                    obj.put("args", "on");

                }catch(JSONException e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(obj);
                sendMessageThread.start();
            }
        });
        this.automodeOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    JSONObject obj = new JSONObject();
                    try{
                        obj.put("topic", "sensors");
                        obj.put("command", "automode");
                        obj.put("args", "off");

                    }catch(JSONException e){
                        e.printStackTrace();
                    }

                    SendMessageThread sendMessageThread = new SendMessageThread(obj);
                    sendMessageThread.start();
            }
        });
        this.automodeSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    JSONObject obj = new JSONObject();
                    try{
                        obj.put("topic", "sensors");
                        obj.put("command", "distance");
                        obj.put("args", ConnectActivity.automodeDistance.getText());

                    }catch(JSONException e){
                        e.printStackTrace();
                    }

                    SendMessageThread sendMessageThread = new SendMessageThread(obj);
                    sendMessageThread.start();

            }
        });
        this.getCord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                try{
                    obj.put("topic", "sensors");
                    obj.put("command", "cord");
                    obj.put("args", true);

                }catch(JSONException e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(obj);
                sendMessageThread.start();

            }
        });
        this.campaignNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    JSONObject obj = new JSONObject();
                    try{
                        obj.put("topic", "campaign");
                        obj.put("command", "new");
                        obj.put("args", ConnectActivity.campaignName.getText());

                    }catch(JSONException e){
                        e.printStackTrace();
                    }

                    SendMessageThread sendMessageThread = new SendMessageThread(obj);
                    sendMessageThread.start();

            }
        });
        this.campaignAttach.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                try{
                    obj.put("topic", "campaign");
                    obj.put("command", "attach");
                    obj.put("args", ConnectActivity.campaignName.getText());

                }catch(JSONException e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(obj);
                sendMessageThread.start();
            }
        });
    }
}
