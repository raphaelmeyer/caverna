package ch.quazz.caverna.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import ch.quazz.caverna.games.Game;

public class ScoreTable {

    private static final String TableName = "score";

    private static final class ColumnName {
        static final String Id = "id";
        static final String GameId = "game_id";

        static final String Animals = "animals";
        static final String MissingFarmAnimal = "missing_animal";
        static final String Grain = "grain";
        static final String Vegetable = "vegetable";
        static final String Ruby = "ruby";
        static final String Dwarf = "dwarf";
        static final String UnusedSpace = "unused_space";
        static final String Tiles = "tiles";
        static final String Parlors = "parlors";
        static final String Storages = "storages";
        static final String Chambers = "chambers";
        static final String Assets = "assets";
    }

    static final String createTableSql() {
        return "CREATE TABLE " + TableName +
                " ( " + ColumnName.Id + " INTEGER PRIMARY KEY" +
                " , " + ColumnName.GameId + " INTEGER " +
                " , " + ColumnName.Animals + " INTEGER " +
                " , " + ColumnName.MissingFarmAnimal + " INTEGER " +
                " , " + ColumnName.Grain + " INTEGER " +
                " , " + ColumnName.Vegetable + " INTEGER " +
                " , " + ColumnName.Ruby + " INTEGER " +
                " , " + ColumnName.Dwarf + " INTEGER " +
                " , " + ColumnName.UnusedSpace + " INTEGER " +
                " , " + ColumnName.Tiles + " INTEGER " +
                " , " + ColumnName.Parlors + " INTEGER " +
                " , " + ColumnName.Storages + " INTEGER " +
                " , " + ColumnName.Chambers + " INTEGER " +
                " , " + ColumnName.Assets + " INTEGER " +
                ")";
    }

    static final String deleteTableSql() {
        return "DROP TABLE IF EXISTS " + TableName;
    }

    private final CavernaDbHelper dbHelper;

    public ScoreTable(CavernaDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
}
