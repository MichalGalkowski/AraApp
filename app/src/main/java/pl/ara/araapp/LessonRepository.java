package pl.ara.araapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LessonRepository {

    private LessonDAO lessonDAO;
    private LiveData<List<LessonEntity>> lessons;
    public LessonRepository(Application app) {
        LessonDB db = LessonDB.getInstance(app);
        lessonDAO = db.lessonDAO();
        lessons = lessonDAO.GetAllLessons();
    }

    public void insert(LessonEntity lesson) {
        new InsertLessonAsyncTask(lessonDAO).execute(lesson);
    }

    public void update(LessonEntity lesson) {
        new UpdateLessonAsyncTask(lessonDAO).execute(lesson);
    }

    public void delete(LessonEntity lesson) {
        new DeleteLessonAsyncTask(lessonDAO).execute(lesson);
    }

    public LiveData<List<LessonEntity>> getAllLessons() {
        return lessons;
    }
    public LiveData<Integer> getCount() {
        return lessonDAO.getCount();
    }

    private static class InsertLessonAsyncTask extends AsyncTask<LessonEntity, Void, Void> {

        private LessonDAO lessonDAO;

        private InsertLessonAsyncTask(LessonDAO lessonDAO) {
            this.lessonDAO = lessonDAO;
        }
        @Override
        protected Void doInBackground(LessonEntity... lessons) {
            lessonDAO.Insert(lessons[0]);

            return null;
        }
    }

    private static class UpdateLessonAsyncTask extends AsyncTask<LessonEntity, Void, Void> {

        private LessonDAO lessonDAO;

        private UpdateLessonAsyncTask(LessonDAO lessonDAO) {
            this.lessonDAO = lessonDAO;
        }
        @Override
        protected Void doInBackground(LessonEntity... lessons) {
            lessonDAO.Update(lessons[0]);

            return null;
        }
    }

    private static class DeleteLessonAsyncTask extends AsyncTask<LessonEntity, Void, Void> {

        private LessonDAO lessonDAO;

        private DeleteLessonAsyncTask(LessonDAO lessonDAO) {
            this.lessonDAO = lessonDAO;
        }
        @Override
        protected Void doInBackground(LessonEntity... lessons) {
            lessonDAO.Delete(lessons[0]);

            return null;
        }
    }
}
