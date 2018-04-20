package fithou.tam.smartnote.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by TAM on 17/11/2016.
 */

public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String al=intent.getExtras().getString("AL");
        Intent rington_i=new Intent(context,RingtoneService.class);
        rington_i.putExtra("AL",al);
        context.startService(rington_i);
    }
}
