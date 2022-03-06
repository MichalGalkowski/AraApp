package pl.ara.araapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "noteTable")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String note;
    public int day;
    public int month;
    public int year;
    public Boolean bool;

    public NoteEntity(String note, int day, int month, int year, Boolean bool) {
        this.note = note;
        this.day = day;
        this.month = month;
        this.year = year;
        this.bool = bool;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public Boolean getBool() {
        return bool;
    }

    public void setId(int id) {
        this.id = id;
    }

}
