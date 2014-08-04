package ch.quazz.caverna;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PlayerScore {

    private Cattle cattle;
    private Family family;
    private Homeboard homeboard;
    private Inventory inventory;

    public PlayerScore() {
        cattle = new Cattle();
        family = new Family();
        homeboard = new Homeboard();
        inventory = new Inventory();
    }

    public int score()
    {
        return family.score() + inventory.score() + cattle.score() + homeboard.score();
    }

    public Cattle getCattle() {
        return cattle;
    }

    public Family getFamily() {
        return family;
    }

    public Homeboard getHomeboard() {
        return homeboard;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void save(SQLiteDatabase db) {
        db.delete("player_score", null, null);

        ContentValues values = new ContentValues();
        values.put("dogs", cattle.dogs());
        values.put("sheep", cattle.sheep());
        values.put("small_pastures", homeboard.smallPastures());
        values.put("large_pastures", homeboard.largePastures());

        long id = db.insert("player_score", "null", values);
    }

    public void load(SQLiteDatabase db) {
        String[] columns = {
          "dogs", "sheep",
          "small_pastures", "large_pastures"
        };

        Cursor cursor = db.query("player_score", columns, null, null, null, null, null, "1");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            cattle.setDogs(cursor.getInt(cursor.getColumnIndex("dogs")));
            cattle.setSheep(cursor.getInt(cursor.getColumnIndex("sheep")));

            homeboard.setSmallPastures(cursor.getInt(cursor.getColumnIndex("small_pastures")));
            homeboard.setLargePastures(cursor.getInt(cursor.getColumnIndex("large_pastures")));
        }
    }

    public void erase(SQLiteDatabase db) {
        db.delete("player_score", null, null);
    }
}
