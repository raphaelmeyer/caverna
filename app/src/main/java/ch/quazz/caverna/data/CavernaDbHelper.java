package ch.quazz.caverna.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CavernaDbHelper extends SQLiteOpenHelper {

    private static final String DatabaseName = "caverna.db";
    private static final int DatabaseVersion = 1;

    public CavernaDbHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PlayerScoreTable.createTableSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PlayerScoreTable.deleteTableSql());
        db.execSQL(PlayerScoreTable.createTableSql());
    }
}
