package uitstart.uit.multiplealarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Khang on 2/23/2017.
 */

public class AlarmAdapter extends ArrayAdapter<Alarm> {
    private MainActivity mainActivity;
    private List<Alarm> list;
    private int resource;
    private Context context;

    public AlarmAdapter(Context context, int resource,List<Alarm> list) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
        this.list=list;
        this.mainActivity=(MainActivity)context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(resource,null);

        TextView tv_alarm= (TextView) v.findViewById(R.id.tv_alarm);
        tv_alarm.setText(list.get(position).toString());

        tv_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.removeAlarm(position);
            }
        });


        return v;
    }
}
