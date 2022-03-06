package pl.ara.araapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class UpdateLesson extends AppCompatActivity {

    private EditText question, answer;
    private String lessonQuestion, lessonAnswer;
    private int lessonLvl, lessonDay, lessonMonth, lessonYear;
    private Boolean lessonBool;
    private ExtendedFloatingActionButton cancelBtn, updateLessonBtn;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_lesson);
        getSupportActionBar().setTitle("Update Lesson");

        //        FIND VIEW

        question = findViewById(R.id.updateQuestionET);
        answer = findViewById(R.id.updateAnswerET);
        cancelBtn = findViewById(R.id.cancelUpdateLessonFAB);
        updateLessonBtn = findViewById(R.id.updateLessonFAB);

        getData();

        //        ONCLICK

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(UpdateLesson.this, "Nothing Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        updateLessonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateLesson();
            }
        });
    }

    public void updateLesson() {

        lessonQuestion = question.getText().toString();
        lessonAnswer = answer.getText().toString();

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
            intent.putExtra("updateQuestion", lessonQuestion);
            intent.putExtra("updateAnswer", lessonAnswer);
            intent.putExtra("updateDay", lessonDay);
            intent.putExtra("updateMonth", lessonMonth);
            intent.putExtra("updateYear", lessonYear);
            intent.putExtra("updateBool", lessonBool);
            intent.putExtra("updateLvl", lessonLvl);

            if(id!=-1) {
                intent.putExtra("updateId", id);
                setResult(RESULT_OK, intent);
                finish();
            }
        }


    }

    public void getData() {
        Intent i = getIntent();
        id = i.getIntExtra("updateId", -1);
        lessonQuestion = i.getStringExtra("updateQuestion");
        lessonAnswer = i.getStringExtra("updateAnswer");
        lessonBool = i.getBooleanExtra("updateBool", false);
        lessonDay = i.getIntExtra("updateDay",-1);
        lessonMonth = i.getIntExtra("updateMonth", -1);
        lessonYear = i.getIntExtra("updateYear", -1);
        lessonLvl = i.getIntExtra("updateLvl", -1);

        question.setText(lessonQuestion);
        answer.setText(lessonAnswer);
    }
}