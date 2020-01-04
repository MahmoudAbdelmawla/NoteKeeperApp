package com.example.notekeeperapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private NoteInfo mNote;
    private boolean mIsNewNote;
    private static final int POSITION_NOT_SET = -1;
    private static final String TAG = "mNote";
//    public static final String NOTE_INFO = "NOTE_INFO";
    public static final String NOTE_POSITION = "com.example.notekeeperapp.NOTE_POSITION";
    private Spinner mSpinnerCourses;
    private EditText mTextNoteTitle;
    private EditText mTextNoteBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSpinnerCourses = findViewById(R.id.spinner_courses);
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        ArrayAdapter<CourseInfo> adapterCourses =
                new ArrayAdapter<>(this , android.R.layout.simple_spinner_item , courses);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCourses.setAdapter(adapterCourses);
        
        readDisplayStateValues();

        mTextNoteTitle = findViewById(R.id.edit_text_note_title);
        mTextNoteBody = findViewById(R.id.edit_text_note_body);

        if (!mIsNewNote)
        displayNote(mSpinnerCourses, mTextNoteTitle, mTextNoteBody);

    }

    private void displayNote(Spinner spinnerCourses , EditText textNoteTitle , EditText textNoteBody) {
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        int courseIndex = courses.indexOf(mNote.getCourse());
        spinnerCourses.setSelection(courseIndex);
        textNoteTitle.setText(mNote.getTitle());
        textNoteBody.setText(mNote.getText());
    }

    private void readDisplayStateValues() {
        Intent intent = getIntent();
        int position = intent.getIntExtra(NOTE_POSITION,POSITION_NOT_SET);
        mIsNewNote = position == POSITION_NOT_SET;
        if (!mIsNewNote)
            mNote = DataManager.getInstance().getNotes().get(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_mail) {
            sendEmail();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendEmail() {
        CourseInfo course = (CourseInfo) mSpinnerCourses.getSelectedItem();
        String subject = mTextNoteTitle.getText().toString();
        String text = course.getTitle()+"\"\n"+mTextNoteBody.getText().toString();
        Intent sendEmailIntent = new Intent(Intent.ACTION_SEND);
        sendEmailIntent.setType("message/rcf2822");
        sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT , subject);
        sendEmailIntent.putExtra(Intent.EXTRA_TEXT , text);
        startActivity(sendEmailIntent);
    }
}
