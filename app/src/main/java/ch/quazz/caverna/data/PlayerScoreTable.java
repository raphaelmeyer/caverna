package ch.quazz.caverna.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ch.quazz.caverna.score.GameItem;
import ch.quazz.caverna.score.PlayerScore;

public class PlayerScoreTable {

    private final static class Field {
        public final GameItem item;
        public final String name;
        public final String type;

        public Field(GameItem item, String name, String type) {
            this.item = item;
            this.name = name;
            this.type = type;
        }
    }

    private static final String Name = "player_score";
    private static final Field Fields[] = {
            new Field(GameItem.Dogs, "dogs", "INTEGER"),
            new Field(GameItem.Sheep, "sheep", "INTEGER"),
            new Field(GameItem.Donkeys, "donkeys", "INTEGER"),

            new Field(GameItem.SmallPastures, "small_pastures", "INTEGER"),
            new Field(GameItem.LargePastures, "large_pastures", "INTEGER"),

            new Field(GameItem.OreMines, "ore_mines", "INTEGER"),
            new Field(GameItem.RubyMines, "ruby_mines", "INTEGER")
    };

    private final CavernaDbHelper dbHelper;

    public PlayerScoreTable(CavernaDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void save(PlayerScore playerScore) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("player_score", null, null);

        ContentValues values = new ContentValues();

        values.put("dogs", playerScore.getCount(GameItem.Dogs));
        values.put("sheep", playerScore.getCount(GameItem.Sheep));
        values.put("small_pastures", playerScore.getCount(GameItem.SmallPastures));
        values.put("large_pastures", playerScore.getCount(GameItem.LargePastures));

        db.insert("player_score", "null", values);
    }

    public void load(PlayerScore playerScore) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "dogs", "sheep",
                "small_pastures", "large_pastures"
        };

        Cursor cursor = db.query("player_score", columns, null, null, null, null, null, "1");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            playerScore.setCount(GameItem.Dogs, cursor.getInt(cursor.getColumnIndex("dogs")));
            playerScore.setCount(GameItem.Sheep, cursor.getInt(cursor.getColumnIndex("sheep")));

            playerScore.setCount(GameItem.SmallPastures, cursor.getInt(cursor.getColumnIndex("small_pastures")));
            playerScore.setCount(GameItem.LargePastures, cursor.getInt(cursor.getColumnIndex("large_pastures")));
        }
    }

    public void erase() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("player_score", null, null);
    }

    static String createTableSql() {
        return "CREATE TABLE player_score ( id INTEGER PRIMARY KEY" +
                ", dogs INTEGER" +
                ", sheep INTEGER" +
                ", small_pastures INTEGER" +
                ", large_pastures INTEGER" +
                ")";
    }

    public static String deleteTableSql() {
        return "DROP TABLE IF EXISTS player_score";
    }

}