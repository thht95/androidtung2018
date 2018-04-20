package fithou.tam.smartnote.Group;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;

import fithou.tam.smartnote.Database.SQLiteDataController;
import fithou.tam.smartnote.Main.NoteItem;
import fithou.tam.smartnote.Database.SQLiteDataController;

/**
 * Created by TAM on 10/11/2016.
 */

public class GroupController extends SQLiteDataController {
    public GroupController(Context con) {
        super(con);
    }
    public ArrayList<Group> getListGroup() {
        ArrayList<Group> ds = new ArrayList<>();
        try {
            //1. Mo ket noi database
            openDataBase();

            //2. Truy van
            String cauTV = "Select tp.ID,tp.Name,tp.Icon From Type tp";
            Cursor cs = database.rawQuery(cauTV, null);

            //Doc du lieu
            Group gr;
            while (cs.moveToNext()) {
                Integer ID = cs.getInt(0);
                String  Name= cs.getString(1);
                byte[] Icon=cs.getBlob(2);
                gr = new Group(ID,Name,Icon);
                ds.add(gr);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //3. Dong ket noi
            close();
        }

        return ds;
    }
    public boolean insertGroup(Group group) {

        boolean result = false;
        try {
            openDataBase();
            ContentValues values = new ContentValues();
            values.put("Name", group.getName());
            long id = database.insert("Type", null, values);
            //id chinh la id duoc tu tang cua bang nhan vien
            //Neu ma insert thanh cong thi id no se phai >=0
            if (id > 0) {
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }
    public boolean updateGroup(Group group) {
        boolean result = false;
        try {
            openDataBase();
            ContentValues values = new ContentValues();
            //can update truong nao thi put vao truong day
            values.put("Name", group.getName());

            int id = database.update("Type", values,
                    "ID = " + group.getID(), null);
            //id cua dong duoc update
            //
            if (id > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }
    public boolean deleteGroup(int id) {
        boolean result = false;
        try {
            openDataBase();
            int delete = database.delete("Type", "ID = " + id, null);
            if (delete > 0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }
}
