package ch.quazz.caverna.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class CavernaDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "caverna.db";
    private static final int DATABASE_VERSION = 1;

    public CavernaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GamesTable.createTableSql());
        db.execSQL(ScoreTable.createTableSql());
        db.execSQL(PlayerTable.createTableSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(GamesTable.deleteTableSql());
        db.execSQL(GamesTable.createTableSql());

        db.execSQL(ScoreTable.deleteTableSql());
        db.execSQL(ScoreTable.createTableSql());

        db.execSQL(PlayerTable.deleteTableSql());
        db.execSQL(PlayerTable.createTableSql());
    }
}
