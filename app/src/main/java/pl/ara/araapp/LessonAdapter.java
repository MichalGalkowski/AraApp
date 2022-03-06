package pl.ara.araapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonHolder> {

    private List<LessonEntity> lessons = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public LessonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lesson_item, parent, false);

        return new LessonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonHolder holder, int position) {



        LessonEntity currentLesson = lessons.get(position);
        Calendar c = Calendar.getInstance();
        c.set(currentLesson.getYear(), currentLesson.getMonth(), currentLesson.getDay());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
        String date = sdf.format(c.getTime());
        holder.question.setText(currentLesson.getQuestion());
        holder.date.setText("Test date: " + date);
        holder.lvl.setText("Lesson level: " + currentLesson.getFiboLvl());

    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public void setLessons(List<LessonEntity> lessons) {

        this.lessons = lessons;
        notifyDataSetChanged();
    }

    public LessonEntity getLessons(int position) {

        return lessons.get(position);
    }

    class LessonHolder extends RecyclerView.ViewHolder {

        TextView question, date, lvl;

        public LessonHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.questionItem);
            date = itemView.findViewById(R.id.dateItem);
            lvl = itemView.findViewById(R.id.fiboItem);

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
