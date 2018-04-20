package fithou.tam.smartnote.Group;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fithou.tam.smartnote.R;

/**
 * Created by TAM on 10/11/2016.
 */

public class GroupAdapter extends BaseAdapter {
    ArrayList<Group> List;
    LayoutInflater inflater;

    public GroupAdapter(ArrayList<Group> list, LayoutInflater inflater) {
        List = list;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Group getItem(int i) {
        return List.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Group group=getItem(i);
        if (view == null) {
            view = inflater.inflate(R.layout.group_item, null);
        }
        ImageView imgIconType= (ImageView) view.findViewById(R.id.imgType);
        TextView txtTypeName= (TextView) view.findViewById(R.id.txtNameType);
        Bitmap bmIcon= BitmapFactory.decodeByteArray(group.getIcon(),0,group.getIcon().length);
        imgIconType.setImageBitmap(bmIcon);
        txtTypeName.setText(group.getName());
        return view;
    }
}
