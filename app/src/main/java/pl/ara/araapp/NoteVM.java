package pl.ara.araapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteVM extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<NoteEntity>> notes;

    public NoteVM(@NonNull Application application) {
        super(application);

        repository = new NoteRepository(application);
        notes = repository.getAllNotes();
    }

    public void insert(NoteEntity note) {
        repository.insert(note);
    }

    public void update(NoteEntity note) {
        repository.update(note);
    }

    public void delete(NoteEntity note) {
        repository.delete(note);
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return notes;
    }

    public LiveData<Integer> getCount() {return repository.getCount();}
}
