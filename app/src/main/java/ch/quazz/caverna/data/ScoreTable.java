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
import ch.quazz.caverna.score.PlayerScore;
import ch.quazz.caverna.score.Token;

public final class ScoreTable {

    private static final String TABLE_NAME = "player_score";

    private static final class ColumnName {
        private static final String ID = "ID";

        private static final String PLAYER_ID = "player_id";
        private static final String GAME_ID = "game_id";

        private static final String TILES = "tiles";
    }

    private static final Map<Token, String> TokenColumns =
            new HashMap<Token, String>(){
                {
                    put(Token.DWARFS, "dwarfs");
                    put(Token.DWELLINGS, "dwellings");

                    put(Token.DOGS, "dogs");
                    put(Token.SHEEP, "sheep");
                    put(Token.DONKEYS, "donkeys");
                    put(Token.BOARS, "boars");
                    put(Token.CATTLE, "cattle");

                    put(Token.GRAINS, "grains");
                    put(Token.VEGETABLES, "vegetables");

                    put(Token.RUBIES, "rubies");
                    put(Token.GOLD, "gold");
                    put(Token.BEGGING_MARKERS, "begging_markers");

                    put(Token.SMALL_PASTURES, "small_pastures");
                    put(Token.LARGE_PASTURES, "large_pastures");

                    put(Token.ORE_MINES, "ore_mines");
                    put(Token.RUBY_MINES, "ruby_mines");

                    put(Token.UNUSED_SPACE, "unused_space");

                    put(Token.STONE, "stone");
                    put(Token.ORE, "ore");

                    put(Token.WEAPONS, "weapons");
                    put(Token.ADJACENT_DWELLINGS, "adjacent_dwellings");
                }
            };

    static String createTableSql() {
        StringBuilder sql = new StringBuilder("CREATE TABLE " + TABLE_NAME);

        sql.append(" ( " + ColumnName.ID + " INTEGER PRIMARY KEY");

        sql.append(" , " + ColumnName.PLAYER_ID + " INTEGER");
        sql.append(" , " + ColumnName.GAME_ID + " INTEGER");

        for (String column : TokenColumns.values()) {
            sql.append(" , ").append(column).append(" INTEGER");
        }

        sql.append(" , " + ColumnName.TILES + " TEXT");
        sql.append(")");

        return sql.toString();
    }

    static String deleteTableSql() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    private ScoreTable() {}

    public static long addScore(final CavernaDbHelper dbHelper, final long playerId, final long gameId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        PlayerScore score = new PlayerScore(playerId);

        ContentValues values = columnValues(score);
        values.put(ColumnName.PLAYER_ID, playerId);
        values.put(ColumnName.GAME_ID, gameId);

        long id = db.insert(TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public static void setScore(final CavernaDbHelper dbHelper, final PlayerScore score, final long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = columnValues(score);

        db.update(TABLE_NAME, values, ColumnName.ID + "=" + id, null);
        db.close();
    }

    public static PlayerScore getScore(final CavernaDbHelper dbHelper, final long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        PlayerScore score = null;

        String selection = ColumnName.ID + "=" + id;
        Cursor cursor = db.query(TABLE_NAME, null, selection, null, null, null, null);

        if (cursor.moveToNext()) {
            score = parseScore(cursor);
        }
        cursor.close();
        db.close();

        return score;
    }

    public static void deleteScore(final CavernaDbHelper dbHelper, final long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = ColumnName.ID + "=" + id;
        db.delete(TABLE_NAME, selection, null);
    }

    public static void deleteScores(final CavernaDbHelper dbHelper, final long gameId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = ColumnName.GAME_ID + "=" + gameId;
        db.delete(TABLE_NAME, selection, null);
    }

    public static List<ScoreSheet> getScoringPad(final CavernaDbHelper dbHelper, final long gameId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ScoreSheet> scoringPad = new ArrayList<>();

        String selection = ColumnName.GAME_ID + "=" + gameId;
        Cursor cursor = db.query(TABLE_NAME, null, selection, null, null, null, null);

        int player = 1;
        while(cursor.moveToNext()) {
            long playerId = cursor.getLong(cursor.getColumnIndex(ColumnName.PLAYER_ID));
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
        String selection = ColumnName.GAME_ID + "=" + gameId;
        long players = DatabaseUtils.queryNumEntries(db, TABLE_NAME, selection);
        db.close();
        return players;
    }

    public static long numberOfGames(final CavernaDbHelper dbHelper, final long playerId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = ColumnName.PLAYER_ID + "=" + playerId;
        long games = DatabaseUtils.queryNumEntries(db, TABLE_NAME, selection);
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
        values.put(ColumnName.TILES, furnishings.toString());

        return values;
    }

    private static PlayerScore parseScore(Cursor cursor) {
        PlayerScore score;
        score = new PlayerScore(cursor.getLong(cursor.getColumnIndex(ColumnName.ID)));
        for (Map.Entry<Token, String> column : TokenColumns.entrySet()) {
            score.setCount(column.getKey(), cursor.getInt(cursor.getColumnIndex(column.getValue())));
        }

        try {
            JSONArray read = new JSONArray(cursor.getString(cursor.getColumnIndex(ColumnName.TILES)));

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