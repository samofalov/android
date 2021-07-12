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

        if(editableNoteId > 0) {

            String idText = String.valueOf(editableNoteId);

            TextView textView = findViewById(R.id.note_id);
            textView.setText(idText);

            Note editableNote = DatabaseWorker.worker.getNoteById(editableNoteId);

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
    }

    @Override
    protected void onPause() {
        super.onPause();

        EditText nameEditText = findViewById(R.id.note_name);
        String name = nameEditText.getText().toString();

        EditText contentEditText = findViewById(R.id.note_content);
        String content = contentEditText.getText().toString();

        if(editableNoteId > 0) {
            DatabaseWorker.worker.updateNote(editableNoteId, name, content);
        } else {
            Note newNote = new Note(name, content);

            DatabaseWorker.worker.insertNote(newNote);
        }
    }
}