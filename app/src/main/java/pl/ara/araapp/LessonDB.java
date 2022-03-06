package pl.ara.araapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = LessonEntity.class, version = 1)
public abstract class LessonDB extends RoomDatabase {

    private static LessonDB instance;
    public abstract LessonDAO lessonDAO();
    public static synchronized LessonDB getInstance(Context ctx) {
        if(instance == null) {
            instance = Room.databaseBuilder(ctx.getApplicationContext(), LessonDB.class, "lessonDB")
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

            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private LessonDAO lessonDAO;
        private PopulateDbAsyncTask(LessonDB db) {
            lessonDAO = db.lessonDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
