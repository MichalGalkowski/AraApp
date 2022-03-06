package pl.ara.araapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lessonTable")
public class LessonEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String question;
    public String answer;
    public Boolean bool;
    public int day;
    public int month;
    public int year;
    public int fiboLvl;

    public LessonEntity(String question, String answer, Boolean bool, int day, int month, int year, int fiboLvl) {
        this.question = question;
        this.answer = answer;
        this.bool = bool;
        this.day = day;
        this.month = month;
        this.year = year;
        this.fiboLvl = fiboLvl;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public Boolean getBool() {
        return bool;
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

    public int getFiboLvl() {
        return fiboLvl;
    }

    public void setId(int id) {
        this.id = id;
    }
}
