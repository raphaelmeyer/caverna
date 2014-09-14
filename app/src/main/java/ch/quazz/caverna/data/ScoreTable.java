package ch.quazz.caverna.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.quazz.caverna.score.ScoreSheet;
import ch.quazz.caverna.score.Tile;
import ch.quazz.caverna.score.Token;
import ch.quazz.caverna.score.PlayerScore;

public final class ScoreTable {

    private static final String TableName = "player_score";

    private static final class ColumnName {
        private static final String Id = "id";

        private static final String PlayerId = "player_id";
        private static final String GameId = "game_id";

        private static final String Tiles = "tiles";
    }

    private static final Map<Token, String> TokenColumns =
            new HashMap<Token, String>(){
                {
                    put(Token.Dwarfs, "dwarfs");
                    put(Token.Dwellings, "dwellings");

                    put(Token.Dogs, "dogs");
                    put(Token.Sheep, "sheep");
                    put(Token.Donkeys, "donkeys");
                    put(Token.Boars, "boars");
                    put(Token.Cattle, "cattle");

                    put(Token.Grains, "grains");
                    put(Token.Vegetables, "vegetables");

                    put(Token.Rubies, "rubies");
                    put(Token.Gold, "gold");
                    put(Token.BeggingMarkers, "begging_markers");

                    put(Token.SmallPastures, "small_pastures");
                    put(Token.LargePastures, "large_pastures");

                    put(Token.OreMines, "ore_mines");
                    put(Token.RubyMines, "ruby_mines");

                    put(Token.UnusedSpace, "unused_space");

                    put(Token.Stone, "stone");
                    put(Token.Ore, "ore");

                    put(Token.Weapons, "weapons");
                    put(Token.AdjacentDwellings, "adjacent_dwellings");
                }
            };

    static String createTableSql() {
        String sql = "CREATE TABLE " + TableName;

        sql += " ( " + ColumnName.Id + " INTEGER PRIMARY KEY";

        sql += " , " + ColumnName.PlayerId + " INTEGER";
        sql += " , " + ColumnName.GameId + " INTEGER";

        for (String column : TokenColumns.values()) {
            sql += " , " + column + " INTEGER";
        }

        sql += " , " + ColumnName.Tiles + " TEXT";
        sql += ")";

        return sql;
    }

    static String deleteTableSql() {
        return "DROP TABLE IF EXISTS " + TableName;
    }

    private ScoreTable() {}

    public static long addScore(final CavernaDbHelper dbHelper, final long playerId, final long gameId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        PlayerScore score = new PlayerScore(playerId);

        ContentValues values = columnValues(score);
        values.put(ColumnName.PlayerId, playerId);
        values.put(ColumnName.GameId, gameId);

        long id = db.insert(TableName, null, values);
        db.close();

        return id;
    }

    public static void setScore(final CavernaDbHelper dbHelper, final PlayerScore score, final long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = columnValues(score);

        db.update(TableName, values, ColumnName.Id + "=" + id, null);
        db.close();
    }

    public static PlayerScore getScore(final CavernaDbHelper dbHelper, final long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        PlayerScore score = null;

        String selection = ColumnName.Id + "=" + id;
        Cursor cursor = db.query(TableName, null, selection, null, null, null, null);

        if (cursor.moveToNext()) {
            score = parseScore(cursor);
        }
        cursor.close();
        db.close();

        return score;
    }

    public static void deleteScore(final CavernaDbHelper dbHelper, final long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = ColumnName.Id + "=" + id;
        db.delete(TableName, selection, null);
    }

    public static void deleteScores(final CavernaDbHelper dbHelper, final long gameId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = ColumnName.GameId + "=" + gameId;
        db.delete(TableName, selection, null);
    }

    public static List<ScoreSheet> getScoringPad(final CavernaDbHelper dbHelper, final long gameId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ScoreSheet> scoringPad = new ArrayList<ScoreSheet>();

        String selection = ColumnName.GameId + "=" + gameId;
        Cursor cursor = db.query(TableName, null, selection, null, null, null, null);

        int player = 1;
        while(cursor.moveToNext()) {
            long playerId = cursor.getLong(cursor.getColumnIndex(ColumnName.PlayerId));
            String name = PlayerTable.getName(dbHelper, playerId);
            PlayerScore score = parseScore(cursor);
            scoringPad.add(score.scoreSheet(player, name));
            player++;
        }
        cursor.close();
        db.close();

        return scoringPad;
    }

    public static long numberOfPlayers(final CavernaDbHelper dbHelper, final long gameId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = ColumnName.GameId + "=" + gameId;
        long players = DatabaseUtils.queryNumEntries(db, TableName, selection);
        db.close();
        return players;
    }

    public static long numberOfGames(final CavernaDbHelper dbHelper, final long playerId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = ColumnName.PlayerId + "=" + playerId;
        long games = DatabaseUtils.queryNumEntries(db, TableName, selection);
        db.close();
        return games;
    }

    private static ContentValues columnValues(PlayerScore score) {
        ContentValues values = new ContentValues();

        for (Map.Entry<Token, String> column : TokenColumns.entrySet()) {
            values.put(column.getValue(), score.getCount(column.getKey()));
        }

        JSONArray furnishings = new JSONArray();
        for (Tile tile : Tile.values()) {
            if(score.has(tile)) {
                furnishings.put(tile);
            }
        }
        values.put(ColumnName.Tiles, furnishings.toString());

        return values;
    }

    private static PlayerScore parseScore(Cursor cursor) {
        PlayerScore score;
        score = new PlayerScore(cursor.getLong(cursor.getColumnIndex(ColumnName.Id)));
        for (Map.Entry<Token, String> column : TokenColumns.entrySet()) {
            score.setCount(column.getKey(), cursor.getInt(cursor.getColumnIndex(column.getValue())));
        }

        try {
            JSONArray read = new JSONArray(cursor.getString(cursor.getColumnIndex(ColumnName.Tiles)));

            for (int i = 0; i < read.length(); i++) {
                Tile tile = Tile.valueOf(read.getString(i));
                score.set(tile);
            }
        } catch(JSONException exception) {
            score = null;
        }
        return score;
    }
}