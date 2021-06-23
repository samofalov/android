package lt.vcs.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import lt.vcs.notes.database.DatabaseDataWorker;
import lt.vcs.notes.database.OpenHelper;
import lt.vcs.notes.helper.FormatHelper;

import static lt.vcs.notes.MainActivity.EXTRA_ID;

public class EditNoteActivity extends AppCompatActivity {

    private int editableNoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_note);

        Intent intent = getIntent();

        editableNoteId = intent.getIntExtra(EXTRA_ID, 0);
        String idText = String.valueOf(editableNoteId);

        TextView textView = findViewById(R.id.note_id);
        textView.setText(idText);

        // TODO: move to a single place
        OpenHelper helper = new OpenHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        DatabaseDataWorker worker = new DatabaseDataWorker(database);

        Note editableNote = worker.getNoteById(editableNoteId);

        EditText nameEditText = findViewById(R.id.note_name);
        nameEditText.setText(editableNote.getName());

        EditText contentEditText = findViewById(R.id.note_content);
        contentEditText.setText(editableNote.getContent());

        String createDateText = FormatHelper.convertToText(editableNote.getCreateDate());
        TextView createDateTextView = findViewById(R.id.note_create_date);
        createDateTextView.setText(createDateText);

        String updateDateText = FormatHelper.convertToText(editableNote.getUpdateDate());
        TextView updateDateTextView = findViewById(R.id.note_update_date);
        updateDateTextView.setText(updateDateText);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 1. Read a value from the text field
        EditText nameEditText = findViewById(R.id.note_name);
        String name = nameEditText.getText().toString();

        EditText contentEditText = findViewById(R.id.note_content);
        String content = contentEditText.getText().toString();

        // 2. Add to DB
        // TODO: move to a single place
        OpenHelper helper = new OpenHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        DatabaseDataWorker worker = new DatabaseDataWorker(database);

        worker.updateNote(editableNoteId, name, content);
    }
}