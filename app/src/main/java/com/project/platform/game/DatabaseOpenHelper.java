package com.project.platform.game;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

@SuppressWarnings("WeakerAccess")
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Stats";
    public static final String TABLE_NAME_PLAYERS = "Players";
    public static final int DATABASE_VERSION = 1;
    public static final String COL_ID = "Id";
    public static final String COL_USERNAME = "Username";
    public static final String COL_PASSWORD = "Password";
    public static final String COL_SCORE = "Score";

    private static final String CREATE_TABLE_PLAYERS = "create table " + TABLE_NAME_PLAYERS
            + " ("
            + COL_ID + " integer primary key autoincrement, "
            + COL_USERNAME + " text, "
            + COL_PASSWORD + " text, "
            + COL_SCORE + " integer"

            + ");";


    private static final String DROP_TABLE_GYM_MEMBERS = "drop table if exists " + TABLE_NAME_PLAYERS + ";";

    private final Context mContext;

    public DatabaseOpenHelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL (CREATE_TABLE_PLAYERS);
        Toast.makeText (mContext, "onCreate", Toast.LENGTH_SHORT).show ();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL (DROP_TABLE_GYM_MEMBERS);
        onCreate (db);
        Toast.makeText (mContext, "onUpgrade", Toast.LENGTH_SHORT).show ();
    }
}
