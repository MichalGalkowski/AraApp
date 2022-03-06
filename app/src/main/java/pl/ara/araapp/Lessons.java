package pl.ara.araapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class Lessons extends AppCompatActivity {

    private RecyclerView lessonsRV;
    private LessonVM lessonVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        getSupportActionBar().setTitle("Your lessons");

        //        FIND VIEW

        lessonsRV = findViewById(R.id.lessonsRV);

//        RECYCLER VIEW

        lessonsRV.setLayoutManager(new LinearLayoutManager(this));
        LessonAdapter adapter = new LessonAdapter();
        lessonsRV.setAdapter(adapter);

        //        VIEW MODEL


        lessonVM = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(LessonVM.class);
        lessonVM.getAllLessons().observe(this, new Observer<List<LessonEntity>>() {
            @Override
            public void onChanged(List<LessonEntity> lessons) {
                adapter.setLessons(lessons);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                lessonVM.delete(adapter.getLessons(viewHolder.getAdapterPosition()));
                Toast.makeText(Lessons.this, "Lesson deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(lessonsRV);

        adapter.setOnItemClickListener(new LessonAdapter.onItemClickListener() {
            @Override
            public void onItemClick(LessonEntity lesson) {

                Intent intent = new Intent(Lessons.this, UpdateLesson.class);
                intent.putExtra("updateId", lesson.getId());
                intent.putExtra("updateQuestion", lesson.getQuestion());
                intent.putExtra("updateAnswer", lesson.getAnswer());
                intent.putExtra("updateBool", lesson.getBool());
                intent.putExtra("updateDay", lesson.getDay());
                intent.putExtra("updateMonth", lesson.getMonth());
                intent.putExtra("updateYear", lesson.getYear());
                intent.putExtra("updateLvl", lesson.getFiboLvl());

                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK) {

            int id = data.getIntExtra("updateId", -1);
            String question = data.getStringExtra("updateQuestion");
            String answer = data.getStringExtra("updateAnswer");
            Boolean bool = data.getBooleanExtra("updateBool", false);
            int day = data.getIntExtra("updateDay", -1);
            int month = data.getIntExtra("updateMonth", -1);
            int year = data.getIntExtra("updateYear", -1);
            int lvl = data.getIntExtra("updateLvl", -1);

            LessonEntity lesson = new LessonEntity(question, answer, bool, day, month, year, lvl);
            lesson.setId(id);
            lessonVM.update(lesson);


        }
    }
}