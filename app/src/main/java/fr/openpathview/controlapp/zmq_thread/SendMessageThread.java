package fr.openpathview.controlapp.zmq_thread;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.zeromq.ZMQ;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import fr.openpathview.controlapp.ControlApp;
import fr.openpathview.controlapp.data.Port;

/**
 * Created by simon on 27/01/18.
 */

public class SendMessageThread extends Thread {
    private JSONObject args;
    private ZMQ.Socket socket;
    private ZMQ.Socket answer;
    private String adress;
    private ZMQ.Context context;
    protected static JSONObject data;

    public SendMessageThread(JSONObject args){
        this.args = args;

        context = ZMQ.context(1);
        this.socket = context.socket(ZMQ.PUSH);
        this.answer = context.socket(ZMQ.PULL);

        this.adress = "tcp://"+ ControlApp.getAddress()+":"+ Port.mainClient;
    }
    public void run(){
        try {
            this.args.put("answer_url", this.getLocalIpAddress());
            this.args.put("answer_port", this.answer.bindToRandomPort("tcp://*"));
        } catch(Exception e) {
            e.printStackTrace();
        }

        this.socket.connect(this.adress);
        this.socket.send(this.args.toString());

        try {
            this.data = new JSONObject(this.answer.recvStr());
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    try {
                        Toast.makeText(ControlApp.getCurrentActivity(), SendMessageThread.data.get("msg") + "\nError : " + SendMessageThread.data.get("error"),
                                Toast.LENGTH_SHORT).show();
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            });
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}