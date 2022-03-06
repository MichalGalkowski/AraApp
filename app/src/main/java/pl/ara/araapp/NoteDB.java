package pl.ara.araapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = NoteEntity.class, version = 1)
public abstract class NoteDB extends RoomDatabase {

    private static NoteDB instance;
    public abstract NoteDAO noteDAO();
    public static synchronized NoteDB getInstance(Context ctx) {
        if(instance == null) {
            instance = Room.databaseBuilder(ctx.getApplicationContext(), NoteDB.class, "noteDB")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new NoteDB.PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDAO noteDAO;
        private PopulateDbAsyncTask(NoteDB db) {
            noteDAO = db.noteDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
