package ch.quazz.caverna;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PlayerScore {

    public enum Item {
        Dogs,
        Sheep,
        Donkeys,
        Boars,
        Cattle,

        Dwarfs,

        Grains,
        Vegetables,

        Rubies,
        Gold,
        BeggingMarkers,

        Stone,
        Ore,
        Wood,

        SmallPastures,
        LargePastures,
        OreMines,
        RubyMines
    };

    private Map<Item, Integer> itemCount;

    private Cattle cattle;
    private Homeboard homeboard;
    private Inventory inventory;

    public PlayerScore() {
        itemCount = new HashMap<Item, Integer>();

        itemCount.put(Item.Dwarfs, 2);

        cattle = new Cattle();
        homeboard = new Homeboard();
        inventory = new Inventory();
    }

    public void setCount(Item item, int count) {
        itemCount.put(item, count);
    }

    public int getCount(Item item) {
        if (itemCount.containsKey(item)) {
            return itemCount.get(item);
        }
        return 0;
    }

    public int score()
    {
        return familyScore() + inventory.score() + animalScore() + homeboard.score();
    }

    private int familyScore() {
        return getCount(Item.Dwarfs);
    }

    private int animalScore() {
        return getCount(Item.Dogs) + farmAnimalScore();
    }

    private  int farmAnimalScore() {
        int sum = 0;
        for(Item farmAnimal : new Item[]{ Item.Sheep, Item.Donkeys, Item.Boars, Item.Cattle }) {
            if (itemCount.containsKey(farmAnimal)) {
                sum += getCount(farmAnimal);
            } else {
                sum -= 2;
            }
        }
        return sum;
    }

    public Cattle getCattle() {
        return cattle;
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
