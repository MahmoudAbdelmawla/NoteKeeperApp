package com.example.notekeeperapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    public static final String TAG = "ttt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteListActivity.this ,NoteActivity.class));
            }
        });
        
        initializeDisplayContent();
    }

    private void initializeDisplayContent() {
        final ListView listNote = findViewById(R.id.list_note);
        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        ArrayAdapter<NoteInfo> notesAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,notes);
        listNote.setAdapter(notesAdapter);
        listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoteListActivity.this , NoteActivity.class);
                NoteInfo note = (NoteInfo) listNote.getItemAtPosition(position);
                Log.i(TAG, "onItemClick: " + note.getCourse()+" "+note.getTitle()+" "+note.getText());
//                intent.putExtra(NoteActivity.NOTE_POSITION, note);
                intent.putExtra(NoteActivity.NOTE_POSITION, position);
                startActivity(intent);

            }
        });
    }

}
