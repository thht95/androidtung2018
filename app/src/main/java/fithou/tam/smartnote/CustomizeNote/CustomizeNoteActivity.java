package fithou.tam.smartnote.CustomizeNote;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fithou.tam.smartnote.Alarm.AlarmActivity;
import fithou.tam.smartnote.Database.NoteController;
import fithou.tam.smartnote.Main.MainActivity;
import fithou.tam.smartnote.Main.NoteItem;
import fithou.tam.smartnote.Main.NoteItemAdapter;
import fithou.tam.smartnote.R;
import fithou.tam.smartnote.System.GPS_Service;
import fithou.tam.smartnote.System.NotifyService;

public class CustomizeNoteActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText edtNote;
    EditText edtEmail;
    ImageView imgLocation, imgWeather, imgNotify;
    TextView txtweather, txtlocation, txtnotify;
    ArrayList<String> arrWeather=null;
    BroadcastReceiver broadcastReceiver;
    private boolean mAutoHighlight;
    double lat;
    double log;
    String date;
    String time;
    String slocation="";
    int notify;
    int TypeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_note);
        LoadControlls();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("Title"));
        imgWeather.setOnClickListener(this);
        imgNotify.setOnClickListener(this);
        imgLocation.setOnClickListener(this);
        TypeID=getIntent().getExtras().getInt("TypeID");

    }

    private void LoadControlls() {
        edtNote = (EditText) findViewById(R.id.edtNote);
        edtEmail =  (EditText) findViewById(R.id.edtEmail);
        imgLocation = (ImageView) findViewById(R.id.imgLocation);
        imgNotify = (ImageView) findViewById(R.id.imgNotify);
        imgWeather = (ImageView) findViewById(R.id.imgWeather);
        txtweather = (TextView) findViewById(R.id.txtweather);
        txtlocation = (TextView) findViewById(R.id.txtlocation);
        txtnotify = (TextView) findViewById(R.id.txtnotify);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (edtNote.getText().length()>0){
                    OnBack();
                }else {
                    onBackPressed();
                }
                break;
            case R.id.mnclear:
                if (edtNote.getText().length() > 0) {
                    OnClear();
                } else {
                    Toast.makeText(this, "Nội dung vẫn trống!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mnsave:
                String content=edtNote.getText().toString();
                if (txtnotify.getText().length()>0){
                     notify=1;
                }else {
                     notify=0;
                }

                String date= DateFormat.getDateInstance().format(new Date());
                NoteItem ni = new NoteItem(content,TypeID,notify,slocation,date);
                ni.setEmail(edtEmail.getText().toString());
                NoteController sql = new NoteController(CustomizeNoteActivity.this);
                boolean kq = sql.insertNote(ni);
                if (kq) {
                    Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(CustomizeNoteActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    AlertDialog dialog,dlback;

    private void OnClear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomizeNoteActivity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Bạn chắc chắn muốn xóa toàn bộ nội dung đã tạo?");
        builder.setPositiveButton("Có", onYesClick);
        builder.setNegativeButton("Hủy", onNoClick);
        dialog = builder.create();
        dialog.show();
    }

    private DialogInterface.OnClickListener onYesClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialog.dismiss();
            edtNote.setText("");
        }
    };

    private DialogInterface.OnClickListener onNoClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialog.dismiss();
        }
    };


    private void OnBack() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(CustomizeNoteActivity.this);
        builder2.setMessage("Bạn có muốn quay lại?");
        builder2.setPositiveButton("Có", onYes_Click);
        builder2.setNegativeButton("Hủy", onNo_Click);
        dlback = builder2.create();
        dlback.show();
    }

    private DialogInterface.OnClickListener onYes_Click = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dlback.dismiss();
            onBackPressed();
        }
    };

    private DialogInterface.OnClickListener onNo_Click = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dlback.dismiss();
        }
    };

    private class LoadData extends AsyncTask<String, Void, String> {
//        ProgressDialog dialog;

        @Override
        protected String doInBackground(String... params) {
            return ReadURL(params[0]);
        }

        @Override
        protected void onPreExecute() {

//            dialog = new ProgressDialog(CustomizeNoteActivity.this);
//            dialog.setMessage("Loading...");
//            dialog.setCancelable(false);
//            dialog.show();
        }

        @Override
        protected void onPostExecute(String json) {
            //dialog.dismiss();
            arrWeather = new ArrayList<String>();

            if (json != null && json.length() > 0) {
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    String skq = "";

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        skq =""+object.getString("temp")+" độ";
                        skq+="\n"+object.getString("description");

                        arrWeather.add(skq);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(CustomizeNoteActivity.this, "Tải dữ liệu thành công!", Toast.LENGTH_SHORT).show();
                txtweather.setText(arrWeather.toString());
            } else {
                Toast.makeText(CustomizeNoteActivity.this, "Dịch vụ thời tiết không khả dụng!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String ReadURL(String url) {
        StringBuilder sb = new StringBuilder();//ket qua doc json
        try {
            URL url1 = new URL(url);
            URLConnection urlConnection = url1.openConnection();
            //Dung bufferreader de doc
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s + " " + " \n ");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        date = "" + dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd;
        Calendar now = Calendar.getInstance();
        final TimePickerDialog tpd = TimePickerDialog.newInstance(
                CustomizeNoteActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                tpd.dismiss();
            }
        });
        tpd.show(getFragmentManager(), "Timepickerdialog");

    }

    @Override
    protected void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (dpd != null) dpd.setOnDateSetListener(this);
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    lat= (double) intent.getExtras().get("lat");
                    log= (double) intent.getExtras().get("log");

                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_updates"));
    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("add", "" + strReturnedAddress.toString());
            } else {
                Log.w("add", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("add", "Canont get Address!");
        }
        return strAdd;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String hourStringEnd = hourOfDayEnd < 10 ? "0" + hourOfDayEnd : "" + hourOfDayEnd;
        String minuteStringEnd = minuteEnd < 10 ? "0" + minuteEnd : "" + minuteEnd;
        time = "" + hourStringEnd + "h" + minuteStringEnd;

        txtnotify.append(""+time+" "+date);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgWeather:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new LoadData().execute("https://api.myjson.com/bins/4rrby");
                    }
                });
                break;
            case R.id.imgLocation:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getApplicationContext(), GPS_Service.class);
                        startService(i);
                        slocation= getCompleteAddressString(lat,log);
                        txtlocation.setText(slocation);
                    }
                });

                break;
            case R.id.imgNotify:
                mAutoHighlight = true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(CustomizeNoteActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAutoHighlight(mAutoHighlight);
                dpd.show(getFragmentManager(), "Datepickerdialog");
                Intent sv = new Intent(this, NotifyService.class);
                startService(sv);
                break;

        }
    }
}
