package uitstart.uit.multiplealarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Khang on 2/23/2017.
 */

public class Alarm implements Serializable{
    private int ID;
    private String date;
    private String time;

    public Alarm(int ID, String date, String time) {
        this.ID = ID;
        this.date = date;
        this.time = time;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public PendingIntent createPendingIntent(Context context){
        Intent intent=new Intent(context,BroadCastReceiver.class);
        intent.putExtra("ID",this.toString());
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,ID,intent,PendingIntent.FLAG_ONE_SHOT);
        return pendingIntent;
    }

    @Override
    public String toString() {
        return ID+" "+date+" "+time;
    }
}
