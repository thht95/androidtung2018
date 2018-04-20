package fithou.tam.smartnote.Main;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import fithou.tam.smartnote.Alarm.AlarmActivity;
import fithou.tam.smartnote.CustomizeNote.CustomizeNoteActivity;
import fithou.tam.smartnote.CustomizeNote.EditNoteActivity;
import fithou.tam.smartnote.Database.NoteController;
import fithou.tam.smartnote.Database.SQLiteDataController;
import fithou.tam.smartnote.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    ListView lvContentdata;
    NoteItemAdapter adapter;
    int position;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initDatabase();
        View view = inflater.inflate(R.layout.fragment_main, null);
        lvContentdata = (ListView) view.findViewById(R.id.lvAlldata);
        adapter = new NoteItemAdapter(getData(), getLayoutInflater(Bundle.EMPTY));
        lvContentdata.setAdapter(adapter);
        lvContentdata.setOnItemClickListener(OnItem_Click);
        lvContentdata.setOnItemLongClickListener(OnLong_Click);
        return view;
    }

    private AdapterView.OnItemClickListener OnItem_Click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            int TypeID = adapter.getItem(i).getTypeID();
            if (TypeID == 1) {
                Intent edit = new Intent(getActivity(), EditNoteActivity.class);
                NoteItem ni = new NoteItem(adapter.getItem(i).getID(), adapter.getItem(i).getContent(), adapter.getItem(i).getTypeID(), adapter.getItem(i).getNotify(), adapter.getItem(i).getLocation(), adapter.getItem(i).getDate());
                edit.putExtra("Item", ni);
                edit.putExtra("TypeID", 1);
                edit.putExtra("Title", "Ghi chú");
                startActivity(edit);
            } else if (TypeID == 3) {
                Intent bt = new Intent(getActivity(), AlarmActivity.class);
                bt.putExtra("TypeID", 3);
                bt.putExtra("Title", "Báo thức");
                startActivity(bt);
            } else if (TypeID == 4) {
                Intent nn = new Intent(getActivity(), EditNoteActivity.class);
                NoteItem ni = new NoteItem(adapter.getItem(i).getID(), adapter.getItem(i).getContent(), adapter.getItem(i).getTypeID(), adapter.getItem(i).getNotify(), adapter.getItem(i).getLocation(), adapter.getItem(i).getDate());
                nn.putExtra("Item", ni);
                nn.putExtra("TypeID", 4);
                nn.putExtra("Title", "Nhật ký");
                startActivity(nn);
            }

        }
    };

    private AdapterView.OnItemLongClickListener OnLong_Click = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            position = i;
            MenuInflater menuInflater = getActivity().getMenuInflater();
            PopupMenu popup = new PopupMenu(getContext(), view);
            menuInflater.inflate(R.menu.popup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(onPopupItem_Click);
            popup.show();
            return false;
        }
    };
    private PopupMenu.OnMenuItemClickListener onPopupItem_Click = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mndel:
                    onDelete();
                    break;
            }
            return false;
        }
    };
    AlertDialog dialog;

    private void onDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Bạn chắc chắn muốn xóa?");
        builder.setPositiveButton("Có", onYesClick);
        builder.setNegativeButton("Hủy", onNoClick);
        dialog = builder.create();
        dialog.show();
    }

    private DialogInterface.OnClickListener onYesClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialog.dismiss();
            NoteController controller = new NoteController(getContext());
            boolean kqua = controller.deleteNote(adapter.getItem(position).getID());
            if (kqua) {
                Toast.makeText(getActivity(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Xóa thất bại!", Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
            adapter = new NoteItemAdapter(getData(), getLayoutInflater(Bundle.EMPTY));
            lvContentdata.setAdapter(adapter);
        }
    };

    private DialogInterface.OnClickListener onNoClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialog.dismiss();
        }
    };

    private void initDatabase() {
        try {
            SQLiteDataController sql = new SQLiteDataController(getContext());
            sql.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<NoteItem> getData() {
        NoteController controller = new NoteController(getContext());
        return controller.getListNote();
    }


}
