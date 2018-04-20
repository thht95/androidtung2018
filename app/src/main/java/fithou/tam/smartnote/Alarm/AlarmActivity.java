package fithou.tam.smartnote.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import fithou.tam.smartnote.CustomizeNote.CustomizeNoteActivity;
import fithou.tam.smartnote.Database.NoteController;
import fithou.tam.smartnote.Main.MainActivity;
import fithou.tam.smartnote.Main.NoteItem;
import fithou.tam.smartnote.Main.NoteItemAdapter;
import fithou.tam.smartnote.R;

public class AlarmActivity extends AppCompatActivity {
    AlarmManager alarmManager;
    TimePicker timePicker;
    Button btnSetAlarm,btnStopAlarm;
    Context context;
    PendingIntent pendingIntent;
    final Calendar calendar=Calendar.getInstance();
    int TypeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        this.context=this;
        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        timePicker= (TimePicker) findViewById(R.id.timePicker);
        btnSetAlarm= (Button) findViewById(R.id.btnSetAlarm);
        btnStopAlarm= (Button) findViewById(R.id.btnStopAlarm);
        btnSetAlarm.setOnClickListener(OnStart_Alarm);
        btnStopAlarm.setOnClickListener(OnStop_Alarm);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("Title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TypeID=getIntent().getExtras().getInt("TypeID");
    }
    private View.OnClickListener OnStart_Alarm=new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
            Intent my_intent =new Intent(context,Alarm_Receiver.class);
            calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());
            my_intent.putExtra("AL","on");
            pendingIntent=PendingIntent.getBroadcast(AlarmActivity.this,0, my_intent,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(alarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

            String date= DateFormat.getDateInstance().format(new Date());
            String content=""+calendar.getTime().getHours()+":"+calendar.getTime().getMinutes();
            NoteItem ni = new NoteItem(content,TypeID,1,"",date);
            NoteController sql = new NoteController(AlarmActivity.this);
            boolean kq = sql.insertNote(ni);
            if (kq) {
                Toast.makeText(context, "Báo thức bật vào "+calendar.getTime().getHours()+"h"+calendar.getTime().getMinutes(), Toast.LENGTH_SHORT).show();
                Intent back = new Intent();
                setResult(100,back);
                finish();
            } else {
                Toast.makeText(context, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    };
    private View.OnClickListener OnStop_Alarm=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent stsv = new Intent(AlarmActivity.this, RingtoneService.class);
            stopService(stsv);
            Intent i=new Intent(AlarmActivity.this, MainActivity.class);
            startActivity(i);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
