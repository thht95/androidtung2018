package fithou.tam.smartnote.Group;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

import fithou.tam.smartnote.Database.SQLiteDataController;
import fithou.tam.smartnote.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {
    ListView lvGroup;
    GroupAdapter adapter;

    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initDatabase();
        View view = inflater.inflate(R.layout.fragment_group, null);
        lvGroup= (ListView) view.findViewById(R.id.lvGroup);
        adapter=new GroupAdapter(getData(),getLayoutInflater(Bundle.EMPTY));
        lvGroup.setAdapter(adapter);
        return view;
    }
    private void initDatabase() {
        try {
            SQLiteDataController sql = new SQLiteDataController(getContext());
            sql.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private ArrayList<Group> getData() {
        GroupController controller=new GroupController(getContext());
        return controller.getListGroup();
    }
}
