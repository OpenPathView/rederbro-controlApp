package fr.openpathview.controlapp.zmq_thread;

import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.zeromq.ZMQ;

import fr.openpathview.controlapp.ControlApp;
import fr.openpathview.controlapp.data.Port;

public class GenerateMap extends Thread {
    private ZMQ.Socket socket;
    private String adress;
    private ZMQ.Context context;

    private static WebView leaflet;

    private static JSONArray msg;

    public GenerateMap(WebView leaflet){
        this.leaflet = leaflet;

        context = ZMQ.context(1);
        this.socket= context.socket(ZMQ.REQ);

        this.adress = "tcp://"+ ControlApp.getAddress()+":"+ Port.campaignReq;
    }
    public void run(){
        socket.connect(this.adress);
        socket.send("{}");
        try {
            this.msg = new JSONArray(socket.recvStr());
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    GenerateMap.leaflet.loadUrl("javascript:clearMarker();");
                    for (int i = 0; i<GenerateMap.msg.length(); i++){
                        try {
                            GenerateMap.leaflet.loadUrl("javascript:addMarker([" + GenerateMap.msg.getJSONObject(i).get("lat") + ", " + GenerateMap.msg.getJSONObject(i).get("lon") + "]);");
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
