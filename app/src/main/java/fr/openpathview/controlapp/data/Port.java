package fr.openpathview.controlapp.data;

/**
 * Created by simon on 13/01/18.
 */

public enum Port {
    mainClient(8547),
    gpsPub(8549),
    campaignPub(8551),
    campaignReq(8552);

    private final int value;

    Port(int value) {
        this.value = value;
    }

    public String toString(){
        return ""+value;
    }
}
