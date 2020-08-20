package Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cliffdevops.alpha.dufleet.R;

import java.util.List;

import Model.TaskDetails;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<TaskDetails> TaskDetailsList;
    private OnTaskClickListener OnTaskClickedListener;

    public TaskAdapter(List<TaskDetails> TaskDetailsList, OnTaskClickListener onTaskClickedListener) {
        this.TaskDetailsList = TaskDetailsList;
        this.OnTaskClickedListener = onTaskClickedListener;

    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, status, details, timestamp;
        OnTaskClickListener mOnTaskClickListener;

        TaskViewHolder(View view, OnTaskClickListener onTaskClickListener) {
            super(view);

            title = view.findViewById(R.id.txtTitle);
            status = view.findViewById(R.id.txtStatus);
            details = view.findViewById(R.id.txtDetails);
            timestamp = view.findViewById(R.id.txtTimestamp);
            mOnTaskClickListener = onTaskClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final TaskDetails item = TaskDetailsList.get(getAdapterPosition());
            mOnTaskClickListener.onItemClick(item.getTitle());
        }
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_cardview, parent, false);
        return new TaskAdapter.TaskViewHolder(itemView, OnTaskClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {

        final TaskDetails item = TaskDetailsList.get(position);
        holder.title.setText(item.getTitle());
        holder.status.setText(item.getStatus());
        holder.details.setText(item.getDetails());
        holder.timestamp.setText(item.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return TaskDetailsList.size();
    }

    public interface OnTaskClickListener {
        void onItemClick(String name);
    }

}