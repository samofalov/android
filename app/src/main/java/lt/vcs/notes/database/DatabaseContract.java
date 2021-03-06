package lt.vcs.notes.database;

import java.time.LocalDateTime;

public class DatabaseContract {

    private DatabaseContract(){}

    public static class NotesEntry {

        public static String TABLE_NAME = "Notes";
        public static String COLUMN_ID = "id";
        public static String COLUMN_NAME = "name";
        public static String COLUMN_CONTENT = "content";
        public static String COLUMN_CREATEDATE = "createDate";
        public static String COLUMN_UPDATEDATE = "updateDate";
        public static String COLUMN_ISDELETED = "isDeleted";

        public static String CREATE_TABLE_SQL =
        "CREATE TABLE " + TABLE_NAME + " (			                        " +
                "" + COLUMN_ID + "         INTEGER  PRIMARY KEY NOT NULL,   " +
                "" + COLUMN_NAME + "       TEXT     NOT NULL,               " +
                "" + COLUMN_CONTENT + "    TEXT     NOT NULL,               " +
                "" + COLUMN_CREATEDATE + " TEXT     NOT NULL,               " +
                "" + COLUMN_UPDATEDATE + " TEXT             ,               " +
                "" + COLUMN_ISDELETED +  " BOOLEAN  DEFAULT (0) NOT NULL    " +
                ");                                                         ";

        public static String DROP_TABLE_SQL = "DROP TABLE " + TABLE_NAME;
    }
}
