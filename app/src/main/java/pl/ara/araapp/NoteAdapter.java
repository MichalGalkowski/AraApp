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

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<NoteEntity> notes = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        NoteEntity currentNote = notes.get(position);
        Calendar c = Calendar.getInstance();
        c.set(currentNote.getYear(), currentNote.getMonth(), currentNote.getDay());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
        String date = sdf.format(c.getTime());
        holder.note.setText(currentNote.getNote());
        holder.date.setText(date);

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

    class NoteHolder extends RecyclerView.ViewHolder {

        TextView note, date;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            note = itemView.findViewById(R.id.noteItem);
            date = itemView.findViewById(R.id.noteDateItem);

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
