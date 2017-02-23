package uitstart.uit.multiplealarm;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final SimpleDateFormat FORMAT=new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static final SimpleDateFormat FORMAT_DATE=new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat FORMAT_TIME=new SimpleDateFormat("HH:mm");

    Button btnTime, btnDate, btnSetAlarm;
    ListView lvAlarm;

    public static Calendar calendar;

    public ArrayList<Alarm> list;
    public AlarmAdapter adapter;


    public AlarmManager alarmManager;

    int ID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar=Calendar.getInstance();

        btnDate= (Button) findViewById(R.id.btnDate);
        btnTime= (Button) findViewById(R.id.btnTime);
        btnSetAlarm= (Button) findViewById(R.id.btnSetAlarm);

        lvAlarm= (ListView) findViewById(R.id.lvAlarm);


        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

        list=new ArrayList<>();

        adapter=new AlarmAdapter(this,R.layout.item,list);
        lvAlarm.setAdapter(adapter);

        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnSetAlarm.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btnDate: setDate(); break;
            case R.id.btnTime: setTime(); break;
            case R.id.btnSetAlarm: setAlarm(); break;
        }
    }

    private void setAlarm() {
        Alarm alarm=new Alarm(ID,btnDate.getText().toString(),btnTime.getText().toString());

        calendar.set(Calendar.SECOND,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),alarm.createPendingIntent(this));


        list.add(alarm);
        adapter.notifyDataSetChanged();

        ID++;
    }

    private void setTime() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.SECOND,0);

                btnTime.setText(FORMAT_TIME.format(calendar.getTime()));
            }
        };

        TimePickerDialog timePickerDialog=new TimePickerDialog(this,onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();
    }

    private void setDate() {
        DatePickerDialog.OnDateSetListener onDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                btnDate.setText(FORMAT_DATE.format(calendar.getTime()));
            }
        };

        DatePickerDialog datePickerDialog=new DatePickerDialog(this,onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public void removeAlarm(int position) {
        Alarm alarm=list.get(position);
        Toast.makeText(this,"Đã xóa "+alarm,Toast.LENGTH_LONG).show();

        alarmManager.cancel(alarm.createPendingIntent(this));

        list.remove(alarm);
        adapter.notifyDataSetChanged();
    }
}
