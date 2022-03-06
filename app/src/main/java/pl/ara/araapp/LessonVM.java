package pl.ara.araapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LessonVM extends AndroidViewModel {

    private LessonRepository repository;
    private LiveData<List<LessonEntity>> lessons;

    public LessonVM(@NonNull Application application) {
        super(application);

        repository = new LessonRepository(application);
        lessons = repository.getAllLessons();
    }

    public void insert(LessonEntity lesson) {
        repository.insert(lesson);
    }

    public void update(LessonEntity lesson) {
        repository.update(lesson);
    }

    public void delete(LessonEntity lesson) {
        repository.delete(lesson);
    }

    public LiveData<List<LessonEntity>> getAllLessons() {
        return lessons;
    }

    public LiveData<Integer> getCount() {return repository.getCount();}
}
