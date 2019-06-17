package com.project.platform.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
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
            System.out.println (Arrays.toString (cursor.getColumnNames ()));
            results = new ArrayList<> ();

            while (cursor.moveToNext ()) {
                try {
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
                } catch (IllegalStateException e) {
                    System.out.println ("Catch");
                    int indexId = cursor.getColumnIndex ("Id");
                    int indexName = cursor.getColumnIndex ("Username");
                    int indexPassword = cursor.getColumnIndex ("Password");
                    int indexScore = cursor.getColumnIndex ("Score");

                    int id = cursor.getInt (indexId);
                    String name = cursor.getString (indexName);
                    String password = cursor.getString (indexPassword);
                    int score = cursor.getInt (indexScore);

                    Player player = new Player (id, name, password, score);

                    results.add (player);
                }
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
        return query (DatabaseOpenHelper.COL_USERNAME + "=" + username);
    }

    private Player query(String where) {
        /*Cursor cursor = mDatabase.query (DatabaseOpenHelper.TABLE_NAME_PLAYERS, null, where, null, null, null, null);
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

        return password == null ? null : player;*/
        if (getAllMembers () == null)
            return null;
        for (Player p : getAllMembers ()) {
            if (p.getId () == -1)
                return null;
            if (where.contains ("!=")) {
                String s = where.split ("!=")[1];
                int i = s.matches ("^d+$") ? Integer.parseInt (s) : -1;


                if ((where.contains ("id")
                        && p.getId () != i)
                        || (where.contains ("username")
                        && !p.getUsername ().equals (s))
                        || (where.contains ("password")
                        && !p.getPassword ().equals (s))
                        || (where.contains ("score")
                        && p.getScore () != i))
                    return p;
            } else if (where.contains ("=")) {
                String s = where.split ("=")[1];
                int i = s.matches ("^d+$") ? Integer.parseInt (s) : -1;


                if ((where.contains ("id")
                        && p.getId () == i)
                        || (where.contains ("username")
                        && p.getUsername ().equals (s))
                        || (where.contains ("password")
                        && p.getPassword ().equals (s))
                        || (where.contains ("score")
                        && p.getScore () == i))
                    return p;
            } else if (where.contains ("contains")) {
                String s = where.split (" contains ")[1];

                if ((where.contains ("username")
                        && p.getUsername ().contains (s))
                        || (where.contains ("password")
                        && p.getPassword ().contains (s)))
                    return p;
            } else if (where.contains ("<")) {
                String s = where.split ("<")[1];
                int i;
                if (s.matches ("^d+$"))
                    i = Integer.parseInt (s);
                else
                    return null;

                if ((where.contains ("id")
                        && p.getId () < i)
                        || (where.contains ("score")
                        && p.getScore () < i))
                    return p;
            } else if (where.contains (">")) {
                String s = where.split (">")[1];
                int i;
                if (s.matches ("^d+$"))
                    i = Integer.parseInt (s);
                else
                    return null;

                if ((where.contains ("id")
                        && p.getId () > i)
                        || (where.contains ("score")
                        && p.getScore () > i))
                    return p;
            }
        }
        return null;
    }

    public void deleteAll() {
        mDatabase.delete (DatabaseOpenHelper.TABLE_NAME_PLAYERS, null, null);
    }

    public List<Player> sortByScore () {
        List<Player> players = getAllMembers();
        List<Player> newList = new ArrayList<Player>();


        Player max = players.get(0);
        for (int i = 0; i < players.size(); i++) {
            for (Player p : players) {
                if (max.getScore() < p.getScore())
                    max = p;
            }
            newList.add(max);
        }

        return newList;
    }
}
