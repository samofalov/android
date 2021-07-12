package lt.vcs.notes;

import android.database.sqlite.SQLiteDatabase;

import lt.vcs.notes.database.DatabaseDataWorker;
import lt.vcs.notes.database.OpenHelper;

public class DatabaseWorker {

    public static DatabaseDataWorker worker;

    public static void setupWorker(MainActivity context){
        OpenHelper helper = new OpenHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        worker = new DatabaseDataWorker(database);
    }
}
