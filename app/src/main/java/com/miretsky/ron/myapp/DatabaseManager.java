package com.miretsky.ron.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseManager {

    private static SQLiteDatabase mDatabase;

    public DatabaseManager(Context context) {
        DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
        mDatabase = helper.getWritableDatabase();
    }

    public static long insertIntoDatabase(Player player) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.COL_NAME, player.getName());
        contentValues.put(DatabaseOpenHelper.COL_SCORE, player.getScore());
        contentValues.put(DatabaseOpenHelper.COL_PLACE, player.getPlace());
        contentValues.put(DatabaseOpenHelper.COL_IMAGE, player.getImage());

        return mDatabase.insert(DatabaseOpenHelper.TABLE_NAME_PLAYERS, null, contentValues);
    }

    public static List<Player> getAllMembers() {
        Cursor cursor = mDatabase.query(DatabaseOpenHelper.TABLE_NAME_PLAYERS, null, null, null, null, null, null);

        List<Player> results = Collections.emptyList();

        if (cursor.getColumnCount() > 0) {

            results = new ArrayList<>();

            while (cursor.moveToNext()) {
                int indexName = cursor.getColumnIndex(DatabaseOpenHelper.COL_NAME);
                int indexScore = cursor.getColumnIndex(DatabaseOpenHelper.COL_SCORE);
                int indexPlace = cursor.getColumnIndex(DatabaseOpenHelper.COL_PLACE);
                int indexImage = cursor.getColumnIndex(DatabaseOpenHelper.COL_IMAGE);

                String name = cursor.getString(indexName);
                int score = cursor.getInt(indexScore);
                int place = cursor.getInt(indexPlace);
                String image = cursor.getString(indexImage);

                Player player = new Player(name, score, place, image);

                results.add(player);
            }
        }

        cursor.close();

        return results;
    }
}
