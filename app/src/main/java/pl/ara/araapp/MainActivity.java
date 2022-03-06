package pl.ara.araapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button lessonsBtn, testBtn, notesBtn, scheduleBtn;
    private ExtendedFloatingActionButton addNote, addLesson;
    private LessonVM lessonVM;
    private NoteVM noteVM;
    private List<LessonEntity> lessonsList = new ArrayList<>();
    private List<NoteEntity> notesList = new ArrayList<>();
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        FIND VIEW

        lessonsBtn = findViewById(R.id.lessonsBtn);
        testBtn = findViewById(R.id.testBtn);
        notesBtn = findViewById(R.id.notesBtn);
        scheduleBtn = findViewById(R.id.dayScheduleBtn);
        addNote = findViewById(R.id.cancelNoteFAB);
        addLesson = findViewById(R.id.addNoteFAB);


        //        VIEW MODEL

        lessonVM = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(LessonVM.class);

        lessonVM.getAllLessons().observe(this, new Observer<List<LessonEntity>>() {
            @Override
            public void onChanged(List<LessonEntity> lessons) {
                lessonsList = lessons;

            }
        });

        noteVM = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteVM.class);

        noteVM.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> notes) {
                notesList = notes;

            }
        });

        //        ONCLICK


        lessonsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Lessons.class);
                startActivity(intent);
            }
        });

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLesson();
                Intent intent = new Intent(MainActivity.this, Test.class);
                startActivity(intent);
            }
        });

        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deleteNotes();
                Intent intent = new Intent(MainActivity.this, Notes.class);
                startActivity(intent);
            }
        });

        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotes();
                Intent intent = new Intent(MainActivity.this, DaySchedule.class);
                startActivity(intent);
            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNote.class);
                startActivityForResult(intent, 4);

            }
        });

        addLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddLesson.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK) {

            String question = data.getStringExtra("lessonQuestion");
            String answer = data.getStringExtra("lessonAnswer");
            int day = data.getIntExtra("lessonDay", -1);
            int month = data.getIntExtra("lessonMonth", -1);
            int year = data.getIntExtra("lessonYear", -1);

            LessonEntity lesson =  new LessonEntity(question, answer, false, day, month, year, 1);
            lessonVM.insert(lesson);
            Toast.makeText(MainActivity.this, "Lesson added", Toast.LENGTH_SHORT).show();
        }

        if(requestCode == 4 && resultCode == RESULT_OK) {

            String noteText = data.getStringExtra("noteText");
            int day = data.getIntExtra("noteDay",-1);
            int month = data.getIntExtra("noteMonth",-1);
            int year = data.getIntExtra("noteYear",-1);

            NoteEntity note = new NoteEntity(noteText, day, month, year, false);
            noteVM.insert(note);
            Toast.makeText(MainActivity.this, "Note added", Toast.LENGTH_SHORT).show();
        }

    }

    public void switchLesson() {

        int size = lessonsList.size();


        for (int i = 0; i < size; i++) {

            if (lessonsList.get(i).getBool().equals(false)) {
                LessonEntity currentLesson = lessonsList.get(i);
                int lessonDay = currentLesson.getDay();
                int lessonMonth = currentLesson.getMonth();
                int lessonYear = currentLesson.getYear();

                Calendar c = Calendar.getInstance();
                Calendar lessonDate = Calendar.getInstance();
                lessonDate.set(lessonYear, lessonMonth, lessonDay);

                if(c.getTime().compareTo(lessonDate.getTime())>=0) {
                    String question = currentLesson.getQuestion();
                    String answer = currentLesson.getAnswer();
                    int id = currentLesson.getId();
                    int day = currentLesson.getDay();
                    int month = currentLesson.getMonth();
                    int year = currentLesson.getYear();
                    int lvl = currentLesson.getFiboLvl();
                    LessonEntity lesson = new LessonEntity(question, answer, true, day, month, year, lvl);
                    lesson.setId(id);
                    lessonVM.update(lesson);
                }

            }
        }
    }

    public void deleteNotes() {

        int size = notesList.size();

        Calendar c = Calendar.getInstance();
        Calendar noteDate = Calendar.getInstance();

        for(int i=0; i<size; i++) {

            NoteEntity currentNote = notesList.get(i);
            int noteDay = currentNote.getDay();
            int noteMonth = currentNote.getMonth();
            int noteYear = currentNote.getYear();
            noteDate.set(noteYear,noteMonth, noteDay);

            if(c.getTime().compareTo(noteDate.getTime())>0) {

                noteVM.delete(currentNote);
            }

        }
    }
}