package fithou.tam.smartnote.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.Date;

import fithou.tam.smartnote.Main.NoteItem;
import fithou.tam.smartnote.Main.NoteItem;

/**
 * Created by TAM on 10/11/2016.
 */

public class NoteController extends SQLiteDataController {
    public NoteController(Context con) {
        super(con);
    }
    public ArrayList<NoteItem> getListNote() {
        ArrayList<NoteItem> ds = new ArrayList<>();
        try {
            //1. Mo ket noi database
            openDataBase();

            //2. Truy van
            String cauTV = "Select nt.ID, nt.Content,tp.ID,nt.notify,nt.location,nt.Date,tp.Icon From Note nt\n" +
                    "Inner Join Type tp ON nt.IDType = tp.ID order by nt.Date desc";
            Cursor cs = database.rawQuery(cauTV, null);

            //Doc du lieu
            NoteItem note;
            while (cs.moveToNext()) {
                Integer ID = cs.getInt(0);
                String  Content= cs.getString(1);
                Integer TypeID=cs.getInt(2);
                int notify = cs.getInt(3);
                String location=cs.getString(4);
                String date=cs.getString(5);
                byte[] Icon=cs.getBlob(6);
                note = new NoteItem(ID,Content,Icon,TypeID,notify,location,date);
                ds.add(note);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //3. Dong ket noi
            close();
        }

        return ds;
    }
    public boolean insertNote(NoteItem noteItem) {

        boolean result = false;
        try {
            openDataBase();
            ContentValues values = new ContentValues();
            values.put("Content", noteItem.getContent());
            values.put("IDType", noteItem.getTypeID());
            values.put("notify", noteItem.getNotify());
            values.put("location", noteItem.getLocation());
            values.put("Date", noteItem.getDate());
            long id = database.insert("Note", null, values);
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
    public boolean updateNote(NoteItem noteItem) {
        boolean result = false;
        try {
            openDataBase();
            ContentValues values = new ContentValues();
            //can update truong nao thi put vao truong day
            values.put("Content", noteItem.getContent());
            values.put("IDType", noteItem.getTypeID());
            values.put("notify", noteItem.getNotify());
            values.put("location", noteItem.getLocation());
            values.put("Date", noteItem.getDate());

            int id = database.update("Note", values,
                    "ID = " + noteItem.getID(), null);
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
    public boolean deleteNote(int id) {
        boolean result = false;
        try {
            openDataBase();
            int delete = database.delete("Note", "ID = " + id, null);
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
