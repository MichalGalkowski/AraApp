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

import java.util.Calendar;

public class UpdateNote extends AppCompatActivity {

    private EditText noteET;
    private String noteText;
    private int noteDay, noteMonth, noteYear;
    private Boolean noteBool;
    private CalendarView calendar;
    private ExtendedFloatingActionButton cancelBtn, updateNoteBtn;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        getSupportActionBar().setTitle("Update Note");

        //        FIND VIEW

        noteET = findViewById(R.id.updateNoteET);
        calendar = findViewById(R.id.updateNoteCV);
        cancelBtn = findViewById(R.id.cancelNoteUpdateFAB);
        updateNoteBtn = findViewById(R.id.updateNoteFAB);

        getData();

//        CALENDAR

        Calendar c = Calendar.getInstance();
        c.set(noteYear, noteMonth, noteDay);
        calendar.setDate(c.getTimeInMillis());

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

                Toast.makeText(UpdateNote.this, "Nothing Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        updateNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateNote();
            }
        });

    }

    public void updateNote() {

        noteText = noteET.getText().toString();

        if(noteText.length()<3) {
            Toast.makeText(this, "Note must have at least 3 marks!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent();
            intent.putExtra("updateNote", noteText);
            intent.putExtra("updateNoteDay", noteDay);
            intent.putExtra("updateNoteMonth", noteMonth);
            intent.putExtra("updateNoteYear", noteYear);
            intent.putExtra("updateNoteBool", noteBool);

            if(id!=-1) {
                intent.putExtra("updateNoteId", id);
                setResult(RESULT_OK, intent);
                finish();
            }
        }

    }

    public void getData() {
        Intent i = getIntent();
        id = i.getIntExtra("updateNoteId", -1);
        noteText = i.getStringExtra("updateNote");
        noteDay = i.getIntExtra("updateNoteDay",-1);
        noteMonth = i.getIntExtra("updateNoteMonth", -1);
        noteYear = i.getIntExtra("updateNoteYear", -1);
        noteBool = i.getBooleanExtra("updateNoteBool", false);

        noteET.setText(noteText);

    }
}