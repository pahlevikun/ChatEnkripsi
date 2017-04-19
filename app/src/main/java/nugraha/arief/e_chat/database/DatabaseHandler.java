package nugraha.arief.e_chat.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nugraha.arief.e_chat.pojo.Profil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farhan on 10/17/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Versi Database
    public static final int DATABASE_VERSION = 2;

    // Nama Database
    public static final String DATABASE_NAME = "echat";

    // Nama Tabel
    public static final String TABLE_PROFIL = "profil";
    public static final String TABLE_FRIEND = "friend";

    //Tabel Profil
    public static final String KEY_ID_PROFIL = "_id";
    public static final String KEY_USERNAME_PROFIL = "username";

    //Tabel Riwayat
    public static final String KEY_ID_FRIEND = "_id";
    public static final String KEY_USERNAME_FRIEND = "username";

    public Resources res;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        res = context.getResources();
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PROFIL = "CREATE TABLE " + TABLE_PROFIL + "("
                + KEY_ID_PROFIL + " INTEGER PRIMARY KEY," + KEY_USERNAME_PROFIL + " TEXT"+")";

        String CREATE_TABLE_FRIEND = "CREATE TABLE " + TABLE_FRIEND + "("
                + KEY_ID_FRIEND + " INTEGER PRIMARY KEY," + KEY_USERNAME_FRIEND + " TEXT"+ ")";

        db.execSQL(CREATE_TABLE_PROFIL);
        db.execSQL(CREATE_TABLE_FRIEND);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);

        // Create tables again
        onCreate(db);
    }

    public void hapusDbaseProfil() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PROFIL);
    }

    public void hapusDbaseFriend() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FRIEND);
    }

    public void tambahProfil(Profil profil) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_USERNAME_PROFIL, profil.getUsername());

        db.insert(TABLE_PROFIL, null, values);
        db.close();
    }

    public List<Profil> getAllProfils() {
        List<Profil> profilList = new ArrayList<Profil>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PROFIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Profil profil = new Profil();
                profil.setID(Integer.parseInt(cursor.getString(0)));
                profil.setUsername(cursor.getString(1));
                // Adding contact to list
                profilList.add(profil);
            } while (cursor.moveToNext());
        }
        // return contact list
        return profilList;
    }

}
