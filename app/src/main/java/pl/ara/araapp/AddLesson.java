package pl.ara.araapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddLesson extends AppCompatActivity {

    private EditText question, answer;
    private String lessonQuestion, lessonAnswer;
    private ExtendedFloatingActionButton cancelBtn, addLessonBtn;
    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);
        getSupportActionBar().setTitle("Add lesson");

        //        FIND VIEW

        question = findViewById(R.id.questionET);
        answer = findViewById(R.id.answerET);
        cancelBtn = findViewById(R.id.cancelLessonFAB);
        addLessonBtn = findViewById(R.id.addLessonFAB);

        //        ONCLICK

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AddLesson.this, "Nothing Added", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        addLessonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addLesson();
            }
        });
    }

    public void addLesson() {

        lessonQuestion = question.getText().toString().trim();
        lessonAnswer = answer.getText().toString().trim();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatD = new SimpleDateFormat("dd");
        SimpleDateFormat formatM = new SimpleDateFormat("MM");
        SimpleDateFormat formatY = new SimpleDateFormat("yyyy");
        c.add(Calendar.DAY_OF_MONTH, 1);
        day = Integer.parseInt(formatD.format(c.getTime()));
        month = Integer.parseInt(formatM.format(c.getTime()))-1;
        year = Integer.parseInt(formatY.format(c.getTime()));

        if(lessonQuestion.length()<3 &&lessonAnswer.length()<3) {
            Toast.makeText(this, "Lesson must have at least 3 marks!", Toast.LENGTH_SHORT).show();
        }
        else if(lessonQuestion.length()<3) {
            Toast.makeText(this, "Question must have at least 3 marks!", Toast.LENGTH_SHORT).show();
        }
        else if(lessonAnswer.length()<3) {
            Toast.makeText(this, "Answer must have at least 3 marks!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent();
            intent.putExtra("lessonQuestion", lessonQuestion);
            intent.putExtra("lessonAnswer", lessonAnswer);
            intent.putExtra("lessonDay", day);
            intent.putExtra("lessonMonth", month);
            intent.putExtra("lessonYear", year);
            setResult(RESULT_OK, intent);
            finish();
        }


    }
}