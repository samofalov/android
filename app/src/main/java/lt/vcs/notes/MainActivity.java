package lt.vcs.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import lt.vcs.notes.database.DatabaseDataWorker;
import lt.vcs.notes.database.OpenHelper;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "lt.vcs.notes.mainactivity.noteId";
    private ArrayAdapter<Note> adapter;
    private List<Note> notes;
    private DatabaseDataWorker worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: move to a single place
        OpenHelper helper = new OpenHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        worker = new DatabaseDataWorker(database);

        notes = worker.getAllNotes(true);

        setupListView(notes);

        View view = findViewById(android.R.id.content).getRootView();
        Snackbar.make(view, "All notes were loaded", Snackbar.LENGTH_LONG).show();

        setupNewNoteButton();
    }

    @Override
    protected void onResume() {
        super.onResume();

        notes.clear();
        notes.addAll(worker.getAllNotes(true));
        adapter.notifyDataSetChanged();
    }

    private void setupListView(List<Note> notes) {
        ListView noteList = findViewById(R.id.note_listview);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        noteList.setAdapter(adapter);

        AdapterView.OnItemLongClickListener listener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showAlertDialog(position, notes, adapter);

                return true;
            }
        };

        noteList.setOnItemLongClickListener(listener);

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);

                Note clickedNote = notes.get(position);

                intent.putExtra(EXTRA_ID, clickedNote.getId());

                startActivity(intent);
            }
        };

        noteList.setOnItemClickListener(clickListener);
    }

    private void showAlertDialog(int position, List<Note> notes, ArrayAdapter<Note> adapter) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this?");

        DialogInterface.OnClickListener yesListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Note clickedNote = notes.get(position);
                worker.deleteNote(clickedNote.getId());

                notes.remove(position);

                adapter.notifyDataSetChanged();
            }
        };

        alertDialogBuilder.setPositiveButton("Yes", yesListener);
        alertDialogBuilder.setNegativeButton("No", null);

        alertDialogBuilder.show();
    }

    private void setupNewNoteButton() {
        Button button = findViewById(R.id.btn_create_note);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Click");
                // TODO: Ability to add new note
            }
        };
        button.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}