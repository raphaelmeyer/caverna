package ch.quazz.caverna.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ch.quazz.caverna.games.Game;

public final class GamesTable {

    private static final String TableName = "games";

    private static final class ColumnName {
        static final String Id = "id";
        static final String TimeStamp = "timestamp";
    }

    static String createTableSql() {
        return "CREATE TABLE " + TableName +
                " ( " + ColumnName.Id + " INTEGER PRIMARY KEY" +
                " , " + ColumnName.TimeStamp + " INTEGER " +
                ")";
    }

    static String deleteTableSql() {
        return "DROP TABLE IF EXISTS " + TableName;
    }

    private GamesTable() {}

    public static List<Game> getGames(final CavernaDbHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Game> games = new ArrayList<Game>();
        Cursor cursor = db.query(TableName, null, null, null, null, null, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ColumnName.Id));
            long timestamp = cursor.getLong(cursor.getColumnIndex(ColumnName.TimeStamp));

            games.add(new Game(id, timestamp));
        }
        cursor.close();

        return games;
    }

    public static long addGame(final CavernaDbHelper dbHelper, final long timestamp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ColumnName.TimeStamp, timestamp);
        return db.insert(TableName, null, values);
    }

    public static void deleteGame(final CavernaDbHelper dbHelper, final long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TableName, ColumnName.Id + "=" + id, null);
    }
}
