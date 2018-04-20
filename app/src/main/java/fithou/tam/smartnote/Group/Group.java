package fithou.tam.smartnote.Group;

/**
 * Created by TAM on 10/11/2016.
 */

public class Group {
    private int ID;
    private String Name;
    private byte[] Icon;

    public Group(int ID, String name, byte[] icon) {
        this.ID = ID;
        Name = name;
        Icon = icon;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public byte[] getIcon() {
        return Icon;
    }

    public void setIcon(byte[] icon) {
        Icon = icon;
    }
}
