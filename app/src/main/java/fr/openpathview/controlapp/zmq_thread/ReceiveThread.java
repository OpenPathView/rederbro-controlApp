package fr.openpathview.controlapp.zmq_thread;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.zeromq.ZMQ;

import fr.openpathview.controlapp.ControlApp;
import fr.openpathview.controlapp.data.Port;

/**
 * Created by simon on 27/01/18.
 */

public class ReceiveThread extends Thread {
    private ZMQ.Context context;
    private ZMQ.Socket gpsSocket;
    private ZMQ.Socket campaignSocket;
    private String gpsAdress;
    private String campaignAdress;
    private ZMQ.Poller poller;

    protected static TextView lat;
    protected static TextView lon;
    protected static TextView alt;
    protected static TextView angle;
    protected static TextView bat;

    protected static JSONObject gpsMsg;
    protected static JSONObject campaignMsg;

    public ReceiveThread(TextView lat,TextView lon,TextView alt,TextView angle, TextView bat){
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.angle = angle;
        this.bat = bat;

        context = ZMQ.context(1);
        this.poller = context.poller();

        this.gpsSocket = context.socket(ZMQ.SUB);
        this.campaignSocket = context.socket(ZMQ.SUB);

        this.gpsAdress = "tcp://"+ ControlApp.getAddress()+":"+ Port.gpsPub;
        this.campaignAdress = "tcp://"+ControlApp.getAddress()+":"+Port.campaignPub;
    }
    public void run(){
        this.gpsSocket.connect(this.gpsAdress);
        this.gpsSocket.subscribe("".getBytes());

        this.campaignSocket.connect(this.campaignAdress);
        this.campaignSocket.subscribe("".getBytes());

        this.poller.register(this.gpsSocket, ZMQ.Poller.POLLIN);
        this.poller.register(this.campaignSocket, ZMQ.Poller.POLLIN);

        while (!Thread.currentThread().isInterrupted()){
            try{
                this.poller.poll();
                if (this.poller.pollin(0)){
                    this.gpsMsg = new JSONObject(this.gpsSocket.recvStr());
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            try {
                                ReceiveThread.lat.setText(String.format("%.3f", ReceiveThread.gpsMsg.get("lat")));
                                ReceiveThread.lon.setText(String.format("%.3f", ReceiveThread.gpsMsg.get("lon")));
                                ReceiveThread.alt.setText(String.format("%.3f", ReceiveThread.gpsMsg.get("alt")));
                                ReceiveThread.angle.setText(String.format("%.3f", ReceiveThread.gpsMsg.get("head")));
                                ReceiveThread.bat.setText(String.format("%.3f", ReceiveThread.gpsMsg.get("battVoltage")));
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
                if (this.poller.pollin(1)){
                    this.campaignMsg = new JSONObject(this.campaignSocket.recvStr());
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            try {
                                Toast.makeText(ControlApp.getCurrentActivity(), ReceiveThread.campaignMsg.get("info") + " : " + ReceiveThread.campaignMsg.get("error"),
                                        Toast.LENGTH_SHORT).show();
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
}