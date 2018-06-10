package fr.openpathview.controlapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import fr.openpathview.controlapp.ControlApp;
import fr.openpathview.controlapp.R;
import fr.openpathview.controlapp.zmq_thread.GenerateMap;
import fr.openpathview.controlapp.zmq_thread.ReceiveThread;

public class MenuActivity extends Activity {
    private TextView lat;
    private TextView lon;
    private TextView alt;
    private TextView angle;
    private TextView bat;

    private ReceiveThread receiveThread;

    private static WebView leaflet;

    private Button gopro;
    private Button sensors;
    private Button campaign;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        this.lat = findViewById(R.id.lat);
        this.lon = findViewById(R.id.lon);
        this.alt = findViewById(R.id.alt);
        this.angle = findViewById(R.id.angle);
        this.bat = findViewById(R.id.bat);

        this.receiveThread = new ReceiveThread(this.lat, this.lon, this.alt, this.angle, this.bat);
        this.receiveThread.start();

        this.leaflet = findViewById(R.id.leaflet);
        this.leaflet.getSettings().setJavaScriptEnabled(true);
        this.leaflet.loadUrl("file:///android_asset/leaflet/index.html");
        this.leaflet.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                GenerateMap generateMap = new GenerateMap(MenuActivity.leaflet);
                generateMap.start();
            }
        });

        this.gopro = findViewById(R.id.goproButton);
        this.gopro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, GoproActivity.class));
            }
        });

        this.sensors = findViewById(R.id.sensorsButton);
        this.sensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, SensorsActivity.class));
            }
        });

        this.campaign = findViewById(R.id.campaignButton);
        this.campaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("------------------");
                startActivity(new Intent(MenuActivity.this, CampaignActivity.class));
            }
        });
    }

    public static WebView getLeaflet() {
        return leaflet;
    }

    protected void onResume(){
        super.onResume();
        ControlApp.setCurrentActivity(this);

        GenerateMap generateMap = new GenerateMap(this.leaflet);
        generateMap.start();
    }
}
