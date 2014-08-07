package ch.quazz.caverna.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ch.quazz.caverna.score.PlayerScore;

public class PlayerScoreTable {
    private final CavernaDbHelper dbHelper;

    public PlayerScoreTable(CavernaDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void save(PlayerScore playerScore) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("player_score", null, null);

        ContentValues values = new ContentValues();

        values.put("dogs", playerScore.getCount(PlayerScore.Item.Dogs));
        values.put("sheep", playerScore.getCount(PlayerScore.Item.Sheep));
        values.put("small_pastures", playerScore.getCount(PlayerScore.Item.SmallPastures));
        values.put("large_pastures", playerScore.getCount(PlayerScore.Item.LargePastures));

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

            playerScore.setCount(PlayerScore.Item.Dogs, cursor.getInt(cursor.getColumnIndex("dogs")));
            playerScore.setCount(PlayerScore.Item.Sheep, cursor.getInt(cursor.getColumnIndex("sheep")));

            playerScore.setCount(PlayerScore.Item.SmallPastures, cursor.getInt(cursor.getColumnIndex("small_pastures")));
            playerScore.setCount(PlayerScore.Item.LargePastures, cursor.getInt(cursor.getColumnIndex("large_pastures")));
        }
    }

    public void erase() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("player_score", null, null);
    }
}