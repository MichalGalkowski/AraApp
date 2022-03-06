package pl.ara.araapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Test extends AppCompatActivity {
    private RecyclerView testRV;
    private LessonVM lessonVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportActionBar().setTitle("Test your lesson");

        //        FIND VIEW

        testRV = findViewById(R.id.testRV);

        //        RECYCLER VIEW

        testRV.setLayoutManager(new LinearLayoutManager(this));
        TestAdapter adapter = new TestAdapter();
        testRV.setAdapter(adapter);

        //        VIEW MODEL

        lessonVM = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(LessonVM.class);
        lessonVM.getAllLessons().observe(this, new Observer<List<LessonEntity>>() {
            @Override
            public void onChanged(List<LessonEntity> lessons) {

                adapter.setLessons(lessons);

            }
        });

        adapter.setOnItemClickListener(new TestAdapter.onItemClickListener() {
            @Override
            public void onItemClick(LessonEntity lesson) {

                Intent intent = new Intent(Test.this, TestLesson.class);
                intent.putExtra("testId", lesson.getId());
                intent.putExtra("testQuestion", lesson.getQuestion());
                intent.putExtra("testAnswer", lesson.getAnswer());
                intent.putExtra("testBool", lesson.getBool());
                intent.putExtra("testDay", lesson.getDay());
                intent.putExtra("testMonth", lesson.getMonth());
                intent.putExtra("testYear", lesson.getYear());
                intent.putExtra("testLvl", lesson.getFiboLvl());

                startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 3 && resultCode == RESULT_OK) {

            int id = data.getIntExtra("testId", -1);
            String question = data.getStringExtra("testQuestion");
            String answer = data.getStringExtra("testAnswer");
            Boolean bool = data.getBooleanExtra("testBool", false);
            int day = data.getIntExtra("testDay", -1);
            int month = data.getIntExtra("testMonth", -1);
            int year = data.getIntExtra("testYear", -1);
            int lvl = data.getIntExtra("testLvl", -1);

            LessonEntity lesson = new LessonEntity(question, answer, bool, day, month, year, lvl);
            lesson.setId(id);
            if(lvl==11) {
                lessonVM.delete(lesson);
                Toast.makeText(Test.this, "Congratulations! Lesson completed", Toast.LENGTH_LONG).show();
            }
            else {
                lessonVM.update(lesson);
            }




        }
    }
}