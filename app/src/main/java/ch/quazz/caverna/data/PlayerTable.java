package ch.quazz.caverna.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public final class PlayerTable {
    private static final String TableName = "players";

    private static final class ColumnName {
        static final String Id = "id";
        static final String Name = "name";
    }

    static final String createTableSql() {
        return "CREATE TABLE " + TableName +
                " ( " + ColumnName.Id + " INTEGER PRIMARY KEY" +
                " , " + ColumnName.Name + " INTEGER " +
                ")";
    }

    static final String deleteTableSql() {
        return "DROP TABLE IF EXISTS " + TableName;
    }

    private PlayerTable() {}

    public static String getName(final CavernaDbHelper dbHelper, final long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String name = null;
        String selection = ColumnName.Id + "=" + id;
        Cursor cursor = db.query(TableName, null, selection, null, null, null, null);

        if (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex(ColumnName.Name));
        }
        cursor.close();
        db.close();

        return name;
    }
}
