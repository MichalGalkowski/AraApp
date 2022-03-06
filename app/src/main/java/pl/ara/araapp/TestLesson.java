package pl.ara.araapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestLesson extends AppCompatActivity {
    private TextView question;
    private EditText answer;
    private ExtendedFloatingActionButton cancelBtn, answerBtn;
    private String lessonQuestion, lessonAnswer, yourAnswer;
    private int lessonLvl, lessonDay, lessonMonth, lessonYear;
    private Boolean lessonBool;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_lesson);

        getSupportActionBar().setTitle("Answer your question");

        //        FIND VIEW

        question = findViewById(R.id.testQuestion);
        answer = findViewById(R.id.testAnswer);
        cancelBtn = findViewById(R.id.cancelTestLessonFAB);
        answerBtn = findViewById(R.id.answerLessonFAB);

        getData();

        //        ONCLICK

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(TestLesson.this, "Test canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        answerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                testLesson();
            }
        });

    }

    public void testLesson() {

        yourAnswer = answer.getText().toString();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatD = new SimpleDateFormat("dd");
        SimpleDateFormat formatM = new SimpleDateFormat("MM");
        SimpleDateFormat formatY = new SimpleDateFormat("yyyy");

        if(yourAnswer.toLowerCase().trim().equals(lessonAnswer.toLowerCase().trim())) {

            lessonLvl++;
        }


        if(lessonLvl==1) {
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        else if(lessonLvl==2) {
            c.add(Calendar.DAY_OF_MONTH, 2);
        }
        else if(lessonLvl==3) {
            c.add(Calendar.DAY_OF_MONTH, 3);
        }
        else if(lessonLvl==4) {
            c.add(Calendar.DAY_OF_MONTH, 5);
        }
        else if(lessonLvl==5) {
            c.add(Calendar.DAY_OF_MONTH, 8);
        }
        else if(lessonLvl==6) {
            c.add(Calendar.DAY_OF_MONTH, 13);
        }
        else if(lessonLvl==7) {
            c.add(Calendar.DAY_OF_MONTH, 21);
        }
        else if(lessonLvl==8) {
            c.add(Calendar.DAY_OF_MONTH, 34);
        }
        else if(lessonLvl==9) {
            c.add(Calendar.DAY_OF_MONTH, 55);
        }
        else if(lessonLvl==10) {
            c.add(Calendar.DAY_OF_MONTH, 89);
        }
        else {
            c.add(Calendar.DAY_OF_MONTH, 1);
        }

        lessonDay = Integer.parseInt(formatD.format(c.getTime()));
        lessonMonth = Integer.parseInt(formatM.format(c.getTime()))-1;
        lessonYear = Integer.parseInt(formatY.format(c.getTime()));
        lessonBool = false;

        if(yourAnswer.length()<3) {
            Toast.makeText(this, "Answer must have at least 3 marks!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent();
            intent.putExtra("testQuestion", lessonQuestion);
            intent.putExtra("testAnswer", lessonAnswer);
            intent.putExtra("testBool", lessonBool);
            intent.putExtra("testDay", lessonDay);
            intent.putExtra("testMonth", lessonMonth);
            intent.putExtra("testYear", lessonYear);
            intent.putExtra("testLvl", lessonLvl);

            if(id!=-1) {
                intent.putExtra("testId", id);
                setResult(RESULT_OK, intent);
                finish();
            }
        }

    }

    public void getData() {
        Intent i = getIntent();
        id = i.getIntExtra("testId", -1);
        lessonQuestion = i.getStringExtra("testQuestion");
        lessonAnswer = i.getStringExtra("testAnswer");
        lessonBool = i.getBooleanExtra("testBool", false);
        lessonDay = i.getIntExtra("testDay",-1);
        lessonMonth = i.getIntExtra("testMonth", -1);
        lessonYear = i.getIntExtra("testYear", -1);
        lessonLvl = i.getIntExtra("testLvl", -1);

        question.setText(lessonQuestion);
    }
}