package ch.quazz.caverna.data;

public class PlayerTable {
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

    private final CavernaDbHelper dbHelper;

    public PlayerTable(CavernaDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

}
