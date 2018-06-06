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

public class CampaignActivity extends Activity {
    private static EditText campaignName;

    private Button newCampaign;
    private Button attachCampaign;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campaign);

        this.campaignName = findViewById(R.id.campaignName);

        this.newCampaign = findViewById(R.id.newCampaign);
        this.newCampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject args = new JSONObject();
                try {
                    args.put("command", "new");
                    args.put("args", CampaignActivity.campaignName.getText());
                    args.put("topic", "campaign");
                }catch(Exception e){
                    e.printStackTrace();
                }

                SendMessageThread sendMessageThread = new SendMessageThread(args);
                sendMessageThread.run();
            }
        });

        this.attachCampaign = findViewById(R.id.attachCampaign);
        this.attachCampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject args = new JSONObject();
                try {
                    args.put("command", "attach");
                    args.put("args", CampaignActivity.campaignName.getText());
                    args.put("topic", "campaign");
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
