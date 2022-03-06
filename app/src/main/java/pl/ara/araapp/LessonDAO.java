package pl.ara.araapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LessonDAO {

    @Insert
    void Insert(LessonEntity lesson);
    @Update
    void Update(LessonEntity lesson);
    @Delete
    void Delete(LessonEntity lesson);

    @Query("SELECT * FROM lessonTable ORDER BY id ASC ")
    LiveData<List<LessonEntity>> GetAllLessons();

    @Query("SELECT COUNT(id) FROM lessonTable")
    LiveData<Integer> getCount();
}
