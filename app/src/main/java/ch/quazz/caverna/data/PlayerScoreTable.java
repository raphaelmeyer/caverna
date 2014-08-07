package ch.quazz.caverna.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ch.quazz.caverna.score.GameItem;
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
}