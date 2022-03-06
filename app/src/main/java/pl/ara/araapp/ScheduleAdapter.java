package pl.ara.araapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder> {

    private List<NoteEntity> notes = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_item, parent, false);

        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatD = new SimpleDateFormat("dd");
        SimpleDateFormat formatM = new SimpleDateFormat("MM");
        SimpleDateFormat formatY = new SimpleDateFormat("yyyy");
        int currentDay = Integer.parseInt(formatD.format(c.getTime()));
        int currentMonth = Integer.parseInt(formatM.format(c.getTime()))-1;
        int currentYear = Integer.parseInt(formatY.format(c.getTime()));

        NoteEntity currentNote = notes.get(position);
        holder.note.setText(currentNote.getNote());

        int noteDay = currentNote.getDay();
        int noteMonth = currentNote.getMonth();
        int noteYear = currentNote.getYear();

        if(currentDay==noteDay && currentMonth==noteMonth && currentYear==noteYear) {
            holder.itemView.setVisibility(View.VISIBLE);

        }
        else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<NoteEntity> notes) {

        this.notes = notes;
        notifyDataSetChanged();
    }

    public NoteEntity getNotes(int position) {

        return notes.get(position);
    }

    class ScheduleHolder extends RecyclerView.ViewHolder {

        TextView note;

        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);

            note = itemView.findViewById(R.id.noteItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if(listener!=null && position!=RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(NoteEntity note);
    }

    public void setOnItemClickListener(onItemClickListener listener) {

        this.listener = listener;
    }
}
