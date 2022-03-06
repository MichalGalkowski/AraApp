package pl.ara.araapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNote extends AppCompatActivity {
    private EditText noteText;
    private CalendarView calendar;
    private ExtendedFloatingActionButton cancelBtn, addNoteBtn;
    private String note;
    private int noteDay, noteMonth, noteYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        getSupportActionBar().setTitle("Add note");

        //        FIND VIEW

        noteText = findViewById(R.id.noteET);
        calendar = findViewById(R.id.noteCV);
        cancelBtn = findViewById(R.id.cancelNoteFAB);
        addNoteBtn = findViewById(R.id.addNoteFAB);

//        CALENDAR

        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatD = new SimpleDateFormat("dd");
        SimpleDateFormat formatM = new SimpleDateFormat("MM");
        SimpleDateFormat formatY = new SimpleDateFormat("yyyy");

        noteDay = Integer.parseInt(formatD.format(c.getTime()));
        noteMonth = Integer.parseInt(formatM.format(c.getTime()))-1;
        noteYear = Integer.parseInt(formatY.format(c.getTime()));

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                noteDay = dayOfMonth;
                noteMonth = month;
                noteYear = year;
            }
        });

//        ONCLICK

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNote.this, "Nothing Added", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addNote();
            }
        });
    }

    public void addNote() {
        note = noteText.getText().toString().trim();

        if(note.length()<3) {
            Toast.makeText(this, "Note must have at least 3 marks!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent();
            intent.putExtra("noteText", note);
            intent.putExtra("noteDay", noteDay);
            intent.putExtra("noteMonth", noteMonth);
            intent.putExtra("noteYear", noteYear);
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}