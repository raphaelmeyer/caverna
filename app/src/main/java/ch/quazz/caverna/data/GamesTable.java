package ch.quazz.caverna.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ch.quazz.caverna.games.Game;

public final class GamesTable {

    private static final String TABLE_NAME = "games";

    private static final class ColumnName {
        static final String ID = "ID";
        static final String TIMESTAMP = "timestamp";
    }

    static String createTableSql() {
        return "CREATE TABLE " + TABLE_NAME +
                " ( " + ColumnName.ID + " INTEGER PRIMARY KEY" +
                " , " + ColumnName.TIMESTAMP + " INTEGER " +
                ")";
    }

    static String deleteTableSql() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    private GamesTable() {}

    public static List<Game> getGames(final CavernaDbHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Game> games = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ColumnName.ID));
            long timestamp = cursor.getLong(cursor.getColumnIndex(ColumnName.TIMESTAMP));

            games.add(new Game(id, timestamp));
        }
        cursor.close();

        return games;
    }

    public static long addGame(final CavernaDbHelper dbHelper, final long timestamp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ColumnName.TIMESTAMP, timestamp);
        return db.insert(TABLE_NAME, null, values);
    }

    public static void deleteGame(final CavernaDbHelper dbHelper, final long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, ColumnName.ID + "=" + id, null);
    }
}
