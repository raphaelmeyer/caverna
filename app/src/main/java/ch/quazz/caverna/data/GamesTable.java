package ch.quazz.caverna.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateUtils;

import java.util.List;

import ch.quazz.caverna.games.Game;

public class GamesTable {

    static final class ColumnName {
        static final String Id = "id";
        static final String TimeStamp = "timestamp";
    }

    static final String createTableSql() {
        return "CREATE TABLE " + TableName +
                " ( " + ColumnName.Id + " INTEGER PRIMARY KEY" +
                " , " + ColumnName.TimeStamp + " INTEGER " +
                ")";
    }

    static final String deleteTableSql() {
        return "DROP TABLE IF EXISTS " + TableName;
    }

    private static final String TableName = "games";

    private final CavernaDbHelper dbHelper;

    public GamesTable(CavernaDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void load(List<Game> games) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        games.clear();

        Cursor cursor = db.query(TableName, null, null, null, null, null, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ColumnName.Id));
            long timestamp = cursor.getLong(cursor.getColumnIndex(ColumnName.TimeStamp));

            games.add(new Game(id, timestamp));
        }
        cursor.close();
    }

    public long add(long timestamp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ColumnName.TimeStamp, timestamp);

        return db.insert(TableName, null, values);
    }

}
