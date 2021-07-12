package lt.vcs.notes.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import lt.vcs.notes.Note;
import lt.vcs.notes.helper.FormatHelper;

public class DatabaseDataWorker {

    private SQLiteDatabase database;
    private DatabaseContract.NotesEntry entry = new DatabaseContract.NotesEntry();

    public DatabaseDataWorker(SQLiteDatabase database) {
        this.database = database;
    }

    public long insertNote(Note note) {

        String name = note.getName();
        String content = note.getContent();
        LocalDateTime createDate = note.getCreateDate();
        LocalDateTime updateDate = note.getUpdateDate();

        String createDateText = FormatHelper.convertToText(createDate);
        String updateDateText = FormatHelper.convertToText(updateDate);

        ContentValues values = new ContentValues();
        values.put(entry.COLUMN_NAME, name);
        values.put(entry.COLUMN_CONTENT, content);
        values.put(entry.COLUMN_CREATEDATE, createDateText);
        values.put(entry.COLUMN_UPDATEDATE, updateDateText);

        long insertedId = database.insert(entry.TABLE_NAME, null, values);

        return insertedId;
    }

    public Note getNoteById(int id) {

        String[] columns = new String[]{entry.COLUMN_ID, entry.COLUMN_NAME, entry.COLUMN_CONTENT, entry.COLUMN_CREATEDATE, entry.COLUMN_UPDATEDATE, entry.COLUMN_ISDELETED};

        String[] ids = {String.valueOf(id)};
        Cursor cursor = database.query(entry.TABLE_NAME, columns, "id = ?", ids, null, null, null);

        if(cursor.moveToNext()){

            Note retrievedNote = retrieveNoteInfo(cursor);

            return retrievedNote;
        }

        return null;
    }

    public void updateNote(int id, String name, String content) {

        ContentValues values = new ContentValues();
        values.put(entry.COLUMN_NAME, name);
        values.put(entry.COLUMN_CONTENT, content);

        database.update(entry.TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteNote(int id) {

        int isDeleted = 1;

        ContentValues values = new ContentValues();
        values.put(entry.COLUMN_ISDELETED, isDeleted);

        database.update(entry.TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Note> getAllNotes(boolean loadDeletedNotes) {

        List<Note> notes = new ArrayList<>();
        String[] columns = new String[]{entry.COLUMN_ID, entry.COLUMN_NAME, entry.COLUMN_CONTENT, entry.COLUMN_CREATEDATE, entry.COLUMN_UPDATEDATE, entry.COLUMN_ISDELETED};
        Cursor cursor = database.query(entry.TABLE_NAME, columns, null , null, null, null, null);

        while(cursor.moveToNext()){

            Note retrievedNote = retrieveNoteInfo(cursor);

            if(loadDeletedNotes || !retrievedNote.isDeleted()){
                notes.add(retrievedNote);
            }
        }

        return notes;
    }

    private Note retrieveNoteInfo(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(entry.COLUMN_ID);
        int noteId = cursor.getInt(idIndex);

        int nameIndex = cursor.getColumnIndex(entry.COLUMN_NAME);
        String name = cursor.getString(nameIndex);

        int contentIndex = cursor.getColumnIndex(entry.COLUMN_CONTENT);
        String content = cursor.getString(contentIndex);

        int createDateIndex = cursor.getColumnIndex(entry.COLUMN_CREATEDATE);
        String createDateText = cursor.getString(createDateIndex);

        int updateDateIndex = cursor.getColumnIndex(entry.COLUMN_UPDATEDATE);
        String updateDateText = cursor.getString(updateDateIndex);

        LocalDateTime createDate = FormatHelper.convertToDatetime(createDateText);
        LocalDateTime updateDate = FormatHelper.convertToDatetime(updateDateText);

        int isDeletedIndex = cursor.getColumnIndex(entry.COLUMN_ISDELETED);
        int value = cursor.getInt(isDeletedIndex);
        boolean isDeleted = convertToBoolean(value);

        return new Note(noteId, name, content, createDate, updateDate, isDeleted);
    }

    private boolean convertToBoolean(int value) {

        return value == 1;
    }
}
