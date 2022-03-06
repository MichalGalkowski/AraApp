package pl.ara.araapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void Insert(NoteEntity note);
    @Update
    void Update(NoteEntity note);
    @Delete
    void Delete(NoteEntity note);

    @Query("SELECT * FROM noteTable ORDER BY id ASC ")
    LiveData<List<NoteEntity>> GetAllNotes();

    @Query("SELECT COUNT(id) FROM noteTable")
    LiveData<Integer> getCount();
}
