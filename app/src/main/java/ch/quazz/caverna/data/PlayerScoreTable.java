package ch.quazz.caverna.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

import ch.quazz.caverna.score.GameItem;
import ch.quazz.caverna.score.PlayerScore;

public class PlayerScoreTable {

    private static final String TableName = "player_score";
    private static final Map<GameItem, String> ItemNames =
            new HashMap<GameItem, String>(){
                {
                    put(GameItem.Dogs, "dogs");
                    put(GameItem.Sheep, "sheep");

                    put(GameItem.SmallPastures, "small_pastures");
                    put(GameItem.LargePastures, "large_pastures");
                }
    };

    private final CavernaDbHelper dbHelper;

    public PlayerScoreTable(CavernaDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void save(PlayerScore playerScore) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TableName, null, null);

        ContentValues values = new ContentValues();

        for (Map.Entry<GameItem, String> column : ItemNames.entrySet()) {
            values.put(column.getValue(), playerScore.getCount(column.getKey()));
        }

        db.insert(TableName, "null", values);
    }

    public void load(PlayerScore playerScore) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = ItemNames.values().toArray(new String[ItemNames.size()]);
        Cursor cursor = db.query(TableName, columns, null, null, null, null, null, "1");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (Map.Entry<GameItem, String> column : ItemNames.entrySet()) {
                playerScore.setCount(column.getKey(), cursor.getInt(cursor.getColumnIndex(column.getValue())));
            }
        }
    }

    public void erase() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TableName, null, null);
    }

    static String createTableSql() {
        String sql = "CREATE TABLE " + TableName + " ( id INTEGER PRIMARY KEY";

        for (String column : ItemNames.values()) {
            sql += ", " + column + " INTEGER";
        }

        sql += ")";

        return sql;
    }

    static String deleteTableSql() {
        return "DROP TABLE IF EXISTS " + TableName;
    }

}