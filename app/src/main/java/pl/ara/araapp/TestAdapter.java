package pl.ara.araapp;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {

    private List<LessonEntity> lessons = new ArrayList<>();
    private onItemClickListener listener;


    @NonNull
    @Override
    public TestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_item, parent, false);

        return new TestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestHolder holder, int position) {

        LessonEntity currentLesson = lessons.get(position);

        if(currentLesson.getBool().equals(false)) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        holder.question.setText(currentLesson.getQuestion());
        holder.lvl.setText("Level: " + currentLesson.getFiboLvl());

    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public void setLessons(List<LessonEntity> lessons) {

        this.lessons = lessons;
        notifyDataSetChanged();
    }

    public class TestHolder extends RecyclerView.ViewHolder {

        TextView question, lvl;

        public TestHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.questionTestItem);
            lvl = itemView.findViewById(R.id.lvlItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if(listener!=null && position!=RecyclerView.NO_POSITION) {
                        listener.onItemClick(lessons.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(LessonEntity lesson);
    }

    public void setOnItemClickListener(onItemClickListener listener) {

        this.listener = listener;
    }
}
