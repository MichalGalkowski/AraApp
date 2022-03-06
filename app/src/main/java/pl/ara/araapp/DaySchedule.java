package pl.ara.araapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DaySchedule extends AppCompatActivity {

    private TextView dateTV;
    private RecyclerView scheduleRV;
    private NoteVM noteVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_schedule);
        getSupportActionBar().setTitle("Your daily schedule");

//        FIND VIEW

        dateTV = findViewById(R.id.scheduleDate);
        scheduleRV = findViewById(R.id.scheduleRV);

//        CALENDAR


        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatD = new SimpleDateFormat("dd");
        SimpleDateFormat formatM = new SimpleDateFormat("MM");
        SimpleDateFormat formatY = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatFull = new SimpleDateFormat("dd-MM-YYYY");
        int currentDay = Integer.parseInt(formatD.format(c.getTime()));
        int currentMonth = Integer.parseInt(formatM.format(c.getTime()));
        int currentYear = Integer.parseInt(formatY.format(c.getTime()));
        dateTV.setText(formatFull.format(c.getTime()));

        //        RECYCLER VIEW

        scheduleRV.setLayoutManager(new LinearLayoutManager(this));
        ScheduleAdapter adapter = new ScheduleAdapter();
        scheduleRV.setAdapter(adapter);

        //        VIEW MODEL

        noteVM = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteVM.class);
        noteVM.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> notes) {
                adapter.setNotes(notes);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                noteVM.delete(adapter.getNotes(viewHolder.getAdapterPosition()));
                Toast.makeText(DaySchedule.this, "Task completed", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(scheduleRV);


    }
}