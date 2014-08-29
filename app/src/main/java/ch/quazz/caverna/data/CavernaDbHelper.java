package ch.quazz.caverna.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class CavernaDbHelper extends SQLiteOpenHelper {

    private static final String DatabaseName = "caverna.db";
    private static final int DatabaseVersion = 1;

    public CavernaDbHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
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
