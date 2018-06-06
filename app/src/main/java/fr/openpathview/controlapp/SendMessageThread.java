package fr.openpathview.controlapp;

import org.json.JSONException;
import org.json.JSONObject;
import org.zeromq.ZMQ;

/**
 * Created by simon on 27/01/18.
 */

public class SendMessageThread extends Thread {
    private JSONObject args;
    private ZMQ.Socket socket;
    private String adress;
    private ZMQ.Context context;
    public SendMessageThread(JSONObject args){
        this.args = args;

        context = ZMQ.context(1);
        this.socket= context.socket(ZMQ.PUSH);

        this.adress = "tcp://"+MainActivity.address+":"+Port.mainClient;
    }
    public void run(){
        socket.connect(this.adress);
        socket.send(this.args.toString());
    }
}