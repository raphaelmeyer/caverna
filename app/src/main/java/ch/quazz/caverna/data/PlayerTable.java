package ch.quazz.caverna.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ch.quazz.caverna.games.Player;

public final class PlayerTable {
    private static final String TableName = "players";

    private static final class ColumnName {
        static final String Id = "id";
        static final String Name = "name";
    }

    static final String createTableSql() {
        return "CREATE TABLE " + TableName +
                " ( " + ColumnName.Id + " INTEGER PRIMARY KEY" +
                " , " + ColumnName.Name + " INTEGER " +
                ")";
    }

    static final String deleteTableSql() {
        return "DROP TABLE IF EXISTS " + TableName;
    }

    private PlayerTable() {}

    public static long addPlayer(final CavernaDbHelper dbHelper, final String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ColumnName.Name, name);

        long id = db.insert(TableName, null, values);
        db.close();

        return id;
    }

    public static void deletePlayer(final CavernaDbHelper dbHelper, final long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TableName, ColumnName.Id + "=" + id, null);
        db.close();
    }

    public static List<Player> getPlayers(final CavernaDbHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<Player> players = new ArrayList<Player>();

        Cursor cursor = db.query(TableName, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(ColumnName.Id));
            String name = cursor.getString(cursor.getColumnIndex(ColumnName.Name));
            players.add(new Player(id, name));
        }
        cursor.close();
        db.close();

        return players;
    }

    public static String getName(final CavernaDbHelper dbHelper, final long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String name = null;
        String selection = ColumnName.Id + "=" + id;
        Cursor cursor = db.query(TableName, null, selection, null, null, null, null);

        if (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex(ColumnName.Name));
        }
        cursor.close();
        db.close();

        return name;
    }
}
