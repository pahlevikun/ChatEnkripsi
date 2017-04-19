package nugraha.arief.e_chat.pojo;

/**
 * Created by Arief Nugraha on 3/9/2017.
 */

public class Profil {
    //private variables
    int _id;
    String _username;

    // Empty constructor
    public Profil() {
    }

    // constructor
    public Profil(int id, String username) {
        this._id = id;
        this._username = username;
    }

    // constructor
    public Profil(String username) {
        this._username = username;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting judul
    public String getUsername() {
        return this._username;
    }

    // setting judul
    public void setUsername(String username) {
        this._username = username;
    }

}