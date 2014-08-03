package ch.quazz.caverna;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CavernaDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "caverna.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_PLAYER_SCORE =
            "CREATE TABLE player_score ( id INTEGER PRIMARY KEY" +
                    ", dogs INTEGER" +
                    ", sheep INTEGER" +
                    ", small_pastures INTEGER" +
                    ", large_pastures INTEGER" +
            ")";

    private static final String DELETE_PLAYER_SCORE =
            "DROP TABLE IF EXISTS player_score";

    public CavernaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLAYER_SCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_PLAYER_SCORE);
        db.execSQL(CREATE_PLAYER_SCORE);
    }
}
