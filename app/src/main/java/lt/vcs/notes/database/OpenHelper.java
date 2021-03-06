package lt.vcs.notes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

public class OpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Notes";
    private static final int DATABASE_VERSION = 2;

    public OpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.NotesEntry.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.NotesEntry.DROP_TABLE_SQL);
        db.execSQL(DatabaseContract.NotesEntry.CREATE_TABLE_SQL);
    }
}
