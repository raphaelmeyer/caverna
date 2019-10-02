package ch.quazz.caverna.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ch.quazz.caverna.games.Player;

public final class PlayerTable {
    private static final String TABLE_NAME = "players";

    private static final class ColumnName {
        static final String ID = "ID";
        static final String NAME = "name";
    }

    static String createTableSql() {
        return "CREATE TABLE " + TABLE_NAME +
                " ( " + ColumnName.ID + " INTEGER PRIMARY KEY" +
                " , " + ColumnName.NAME + " INTEGER " +
                ")";
    }

    static String deleteTableSql() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    private PlayerTable() {}

    public static void addPlayer(final CavernaDbHelper dbHelper, final String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ColumnName.NAME, name);

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public static void deletePlayer(final CavernaDbHelper dbHelper, final long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, ColumnName.ID + "=" + id, null);
        db.close();
    }

    public static List<Player> getPlayers(final CavernaDbHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<Player> players = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(ColumnName.ID));
            String name = cursor.getString(cursor.getColumnIndex(ColumnName.NAME));
            players.add(new Player(id, name));
        }
        cursor.close();
        db.close();

        return players;
    }

    static String getName(final CavernaDbHelper dbHelper, final long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String name = null;
        String selection = ColumnName.ID + "=" + id;
        Cursor cursor = db.query(TABLE_NAME, null, selection, null, null, null, null);

        if (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex(ColumnName.NAME));
        }
        cursor.close();
        db.close();

        return name;
    }
}
