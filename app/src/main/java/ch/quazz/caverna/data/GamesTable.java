package ch.quazz.caverna.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import ch.quazz.caverna.games.Game;

public class GamesTable {

    static final String createTableSql() {
        return "CREATE TABLE " + TableName +
                " ( id INTEGER PRIMARY KEY" +
                " , title TEXT " +
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
        Cursor cursor = db.query(TableName, null, null, null, null, null, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));

            games.add(new Game(id, title));
        }
        cursor.close();
    }

}
