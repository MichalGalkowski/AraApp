package pl.ara.araapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDAO noteDAO;
    private LiveData<List<NoteEntity>> notes;

    public NoteRepository(Application app) {
        NoteDB db = NoteDB.getInstance(app);
        noteDAO = db.noteDAO();
        notes = noteDAO.GetAllNotes();
    }

    public void insert(NoteEntity note) {
        new NoteRepository.InsertNoteAsyncTask(noteDAO).execute(note);
    }

    public void update(NoteEntity note) {
        new NoteRepository.UpdateNoteAsyncTask(noteDAO).execute(note);
    }

    public void delete(NoteEntity note) {
        new NoteRepository.DeleteNoteAsyncTask(noteDAO).execute(note);
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return notes;
    }
    public LiveData<Integer> getCount() {
        return noteDAO.getCount();
    }

    private static class InsertNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void> {

        private NoteDAO noteDAO;

        private InsertNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }
        @Override
        protected Void doInBackground(NoteEntity... notes) {
            noteDAO.Insert(notes[0]);

            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void> {

        private NoteDAO noteDAO;

        private UpdateNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }
        @Override
        protected Void doInBackground(NoteEntity... notes) {
            noteDAO.Update(notes[0]);

            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void> {

        private NoteDAO noteDAO;

        private DeleteNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }
        @Override
        protected Void doInBackground(NoteEntity... notes) {
            noteDAO.Delete(notes[0]);

            return null;
        }
    }
}
