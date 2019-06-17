package com.project.platform.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatabaseManager {

    private SQLiteDatabase mDatabase;

    public DatabaseManager(Context context) {
        DatabaseOpenHelper helper = new DatabaseOpenHelper (context);
        mDatabase = helper.getWritableDatabase ();
    }

    /**
     * Insert into Database
     *
     * @param player New player to insert
     * @return long value to check for success
     */

    long insert(Player player) {
        ContentValues contentValues = new ContentValues ();
        contentValues.put (DatabaseOpenHelper.COL_ID, player.getId ());
        contentValues.put (DatabaseOpenHelper.COL_USERNAME, player.getUsername ());
        contentValues.put (DatabaseOpenHelper.COL_USERNAME, player.getUsername ());
        contentValues.put (DatabaseOpenHelper.COL_PASSWORD, player.getPassword ());
        contentValues.put (DatabaseOpenHelper.COL_SCORE, player.getScore ());

        return mDatabase.insert (DatabaseOpenHelper.TABLE_NAME_PLAYERS, null, contentValues);
    }

    private List<Player> getAllMembers() {
        Cursor cursor = mDatabase.query (DatabaseOpenHelper.TABLE_NAME_PLAYERS, null, null, null, null, null, null);

        List<Player> results = Collections.emptyList ();

        if (cursor.getColumnCount () > 0) {

            results = new ArrayList<> ();

            while (cursor.moveToNext ()) {
                int indexId = cursor.getColumnIndex (DatabaseOpenHelper.COL_ID);
                int indexName = cursor.getColumnIndex (DatabaseOpenHelper.COL_USERNAME);
                int indexPassword = cursor.getColumnIndex (DatabaseOpenHelper.COL_PASSWORD);
                int indexScore = cursor.getColumnIndex (DatabaseOpenHelper.COL_SCORE);

                int id = cursor.getInt (indexId);
                String name = cursor.getString (indexName);
                String password = cursor.getString (indexPassword);
                int score = cursor.getInt (indexScore);

                Player player = new Player (id, name, password, score);

                results.add (player);
            }
        }

        cursor.close ();

        return results;
    }

    private List<Player> getMembersByScore() {
        List<Player> res = getAllMembers ();
        Collections.sort (res, new Comparator<Player> () {
            @Override
            public int compare(Player o1, Player o2) {
                return Integer.compare (o1.getScore (), o2.getScore ());
            }
        });
        return res;
    }

    public long delete(Player player) {
        return mDatabase.delete (DatabaseOpenHelper.TABLE_NAME_PLAYERS, DatabaseOpenHelper.COL_ID + "='" + player.getId () + "'", null);
    }

    public long update(Player player, Player other) {
        ContentValues contentValues = new ContentValues ();
        contentValues.put (DatabaseOpenHelper.COL_ID, other.getId ());
        contentValues.put (DatabaseOpenHelper.COL_USERNAME, other.getUsername ());
        contentValues.put (DatabaseOpenHelper.COL_SCORE, other.getScore ());
        return mDatabase.update (DatabaseOpenHelper.TABLE_NAME_PLAYERS, contentValues, DatabaseOpenHelper.COL_ID + "='" + player.getId () + "'", null);
    }

    Player get(int id) {
        return getAllMembers ().get (id - 1);
    }

    int getIndexOf(Player player) {
        return getAllMembers ().indexOf (player);
    }

    public Player findByName(String username) {
        return query (DatabaseOpenHelper.COL_USERNAME + "='" + username + "'");
    }

    private Player query(String where) {
        Cursor cursor = mDatabase.query (DatabaseOpenHelper.TABLE_NAME_PLAYERS, null, where, null, null, null, null);
        int indexId = cursor.getColumnIndex (DatabaseOpenHelper.COL_ID);
        int indexName = cursor.getColumnIndex (DatabaseOpenHelper.COL_PASSWORD);
        int indexPassword = cursor.getColumnIndex (DatabaseOpenHelper.COL_PASSWORD);
        int indexScore = cursor.getColumnIndex (DatabaseOpenHelper.COL_SCORE);

        int id = cursor.getInt (indexId);
        String name = cursor.getString (indexName);
        String password = cursor.getString (indexPassword);
        int score = cursor.getInt (indexScore);

        Player player = new Player (id, name, password, score);

        cursor.close ();

        return password == null ? null : player;
    }
}
