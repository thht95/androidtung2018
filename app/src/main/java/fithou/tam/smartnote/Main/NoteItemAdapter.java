package fithou.tam.smartnote.Main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fithou.tam.smartnote.Main.NoteItem;
import fithou.tam.smartnote.R;
import fithou.tam.smartnote.R;

/**
 * Created by TAM on 10/11/2016.
 */

public class NoteItemAdapter extends BaseAdapter {
    ArrayList<NoteItem> listItem;
    LayoutInflater inflater;

    public NoteItemAdapter(ArrayList<NoteItem> listItem, LayoutInflater inflater) {
        this.listItem = listItem;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public NoteItem getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        NoteItem ni=getItem(i);
        if (view == null) {
            view = inflater.inflate(R.layout.content_item, null);
        }
        Bitmap bmIcon= BitmapFactory.decodeByteArray(ni.getIcon(),0,ni.getIcon().length);
        ImageView icon= (ImageView) view.findViewById(R.id.imgicon);
        ImageView imgicon1= (ImageView) view.findViewById(R.id.imgicon1);
        ImageView imgicon2= (ImageView) view.findViewById(R.id.imgicon2);
        TextView txtConten= (TextView) view.findViewById(R.id.txtContent);
        TextView txtDate= (TextView) view.findViewById(R.id.txttime);
        TextView txtEmail = (TextView) view.findViewById(R.id.txtEmail);

        icon.setImageBitmap(bmIcon);
        if (ni.getNotify()==1){
            imgicon1.setImageResource(R.drawable.ic_notifications_white_24dp);
        }else
        {
            imgicon1.setImageResource(R.drawable.ic_notifications_off_white_24dp);
        }
        if (ni.getLocation()==""||ni.getLocation().length()==0){
            imgicon2.setImageResource(R.drawable.ic_location_off_white_24dp);
        }else{
            imgicon2.setImageResource(R.drawable.ic_place_white_24dp);
        }
        txtConten.setText(ni.getContent());
        txtDate.setText(ni.getDate().toString());
        txtEmail.setText("ni.getEmail()");
        return view;
    }
}
