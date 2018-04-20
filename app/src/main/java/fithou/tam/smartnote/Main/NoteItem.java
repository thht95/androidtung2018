package fithou.tam.smartnote.Main;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by TAM on 10/11/2016.
 */

public class NoteItem implements Serializable {
    private int ID;
    private String Content;
    private byte[] Icon;
    private int TypeID;
    private int notify;
    private String location;
    private String date;

    public NoteItem(int ID, String content, byte[] icon, int typeID, int notify, String location, String date) {
        this.ID = ID;
        Content = content;
        Icon = icon;
        TypeID = typeID;
        this.notify = notify;
        this.location = location;
        this.date = date;
    }

    public NoteItem(int ID, String content, int typeID, int notify, String location, String date) {
        this.ID = ID;
        Content = content;
        TypeID = typeID;
        this.notify = notify;
        this.location = location;
        this.date = date;
    }

    public NoteItem(String content, int typeID, int notify, String location, String date) {
        Content = content;
        TypeID = typeID;
        this.notify = notify;
        this.location = location;
        this.date = date;
    }

    public NoteItem(int ID, String content, byte[] icon, int notify, String location, String date) {
        this.ID = ID;
        Content = content;
        Icon = icon;
        this.notify = notify;
        this.location = location;
        this.date=date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public byte[] getIcon() {
        return Icon;
    }

    public void setIcon(byte[] icon) {
        Icon = icon;
    }

    public int getTypeID() {
        return TypeID;
    }

    public void setTypeID(int typeID) {
        TypeID = typeID;
    }

    public int getNotify() {
        return notify;
    }

    public void setNotify(int notify) {
        this.notify = notify;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
