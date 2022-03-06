package pl.ara.araapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class Notes extends AppCompatActivity {
    private RecyclerView notesRV;
    private NoteVM noteVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getSupportActionBar().setTitle("Your notes");

        //        FIND VIEW

        notesRV = findViewById(R.id.notesRV);

//        RECYCLER VIEW

        notesRV.setLayoutManager(new LinearLayoutManager(this));
        NoteAdapter adapter = new NoteAdapter();
        notesRV.setAdapter(adapter);

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
                Toast.makeText(Notes.this, "Note deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(notesRV);

        adapter.setOnItemClickListener(new NoteAdapter.onItemClickListener() {
            @Override
            public void onItemClick(NoteEntity note) {

                Intent intent = new Intent(Notes.this, UpdateNote.class);
                intent.putExtra("updateNoteId", note.getId());
                intent.putExtra("updateNote", note.getNote());
                intent.putExtra("updateNoteDay", note.getDay());
                intent.putExtra("updateNoteMonth", note.getMonth());
                intent.putExtra("updateNoteYear", note.getYear());
                intent.putExtra("updateNoteBool", note.getBool());

                startActivityForResult(intent, 5);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 5 && resultCode == RESULT_OK) {

            int id = data.getIntExtra("updateNoteId", -1);
            String noteText = data.getStringExtra("updateNote");
            int day = data.getIntExtra("updateNoteDay", -1);
            int month = data.getIntExtra("updateNoteMonth", -1);
            int year = data.getIntExtra("updateNoteYear", -1);
            Boolean bool = data.getBooleanExtra("updateNoteBool", false);

            NoteEntity note = new NoteEntity(noteText, day, month, year, bool);
            note.setId(id);
            noteVM.update(note);


        }
    }
}