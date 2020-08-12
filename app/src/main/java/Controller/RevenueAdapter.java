package Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cliffdevops.alpha.dufleet.R;

import java.util.List;

import Model.RevenueDetails;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.RevenueViewHolder> {
    private List<RevenueDetails> RevenueDetailsList;
    private OnRevenueListener OnRevenueListener;

    public RevenueAdapter(List<RevenueDetails> RevenueDetailsList, OnRevenueListener onRevenueListener) {
        this.RevenueDetailsList = RevenueDetailsList;
        this.OnRevenueListener = onRevenueListener;

    }

    public class RevenueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView fleet, ref, date, amount, conductor_name;
        OnRevenueListener mOnRevenueListener;

        RevenueViewHolder(View view, OnRevenueListener onRevenueListener) {
            super(view);

            fleet = view.findViewById(R.id.txtFleet);
            ref = view.findViewById(R.id.txtRef);
            amount = view.findViewById(R.id.txtAmount);
            date = view.findViewById(R.id.txtDate);
            conductor_name = view.findViewById(R.id.txtDriver);
            mOnRevenueListener = onRevenueListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final RevenueDetails item = RevenueDetailsList.get(getAdapterPosition());
            mOnRevenueListener.onItemClick(item.getFleet());
        }
    }

    @NonNull
    @Override
    public RevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.revenue_cardview, parent, false);
        return new RevenueViewHolder(itemView, OnRevenueListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueViewHolder holder, int position) {

        final RevenueDetails item = RevenueDetailsList.get(position);
        holder.fleet.setText(item.getFleet());
        holder.ref.setText(item.getRef());
        holder.amount.setText(item.getAmount());
        holder.date.setText(item.getDate());
        holder.conductor_name.setText(item.getConductorName());
    }

    @Override
    public int getItemCount() {
        return RevenueDetailsList.size();
    }

    public interface OnRevenueListener {
        void onItemClick(String name);
    }

}
