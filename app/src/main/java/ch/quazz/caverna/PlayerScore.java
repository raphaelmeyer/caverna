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

    public PlayerScore() {
        itemCount = new HashMap<Item, Integer>();
        itemCount.put(Item.Dwarfs, 2);
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
        return familyScore() + inventoryScore() + animalScore() + homeboardScore();
    }

    private int familyScore() {
        return getCount(Item.Dwarfs);
    }

    private int animalScore() {
        return getCount(Item.Dogs) + farmAnimalScore();
    }

    private int homeboardScore() {
        return pastureScore() + mineScore();
    }

    private int inventoryScore() {
        return 0;
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

    private int pastureScore() {
        return 2 * getCount(Item.SmallPastures) + 4 * getCount(Item.LargePastures);
    }

    private int mineScore() {
        return 3 * getCount(Item.OreMines) + 4 * getCount(Item.RubyMines);
    }

    public void save(SQLiteDatabase db) {
        db.delete("player_score", null, null);

        ContentValues values = new ContentValues();

        values.put("dogs", itemCount.get(Item.Dogs));
        values.put("sheep", itemCount.get(Item.Sheep));
        values.put("small_pastures", itemCount.get(Item.SmallPastures));
        values.put("large_pastures", itemCount.get(Item.LargePastures));

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

            itemCount.put(Item.Dogs, cursor.getInt(cursor.getColumnIndex("dogs")));
            itemCount.put(Item.Sheep, cursor.getInt(cursor.getColumnIndex("sheep")));

            itemCount.put(Item.SmallPastures, cursor.getInt(cursor.getColumnIndex("small_pastures")));
            itemCount.put(Item.LargePastures, cursor.getInt(cursor.getColumnIndex("large_pastures")));
        }
    }

    public void erase(SQLiteDatabase db) {
        db.delete("player_score", null, null);
    }
}
