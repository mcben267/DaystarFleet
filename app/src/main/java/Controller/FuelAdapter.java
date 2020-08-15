package Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cliffdevops.alpha.dufleet.R;

import java.util.List;

import Model.FuelDetails;

public class FuelAdapter extends RecyclerView.Adapter<FuelAdapter.FuelViewHolder> {
    private List<FuelDetails> FuelDetailsList;
    private FuelAdapter.OnFuelListener OnFuelListener;

    public FuelAdapter(List<FuelDetails> FuelDetailsList, FuelAdapter.OnFuelListener onFuelListener) {
        this.FuelDetailsList = FuelDetailsList;
        this.OnFuelListener = onFuelListener;

    }

    public class FuelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView fleet, station, date, amount, driver_name;
        FuelAdapter.OnFuelListener mOnFuelListener;

        FuelViewHolder(View view, FuelAdapter.OnFuelListener onFuelListener) {
            super(view);

            fleet = view.findViewById(R.id.txtFleet);
            station = view.findViewById(R.id.txtStation);
            amount = view.findViewById(R.id.txtAmount);
            date = view.findViewById(R.id.txtDate);
            driver_name = view.findViewById(R.id.txtDriver);
            mOnFuelListener = onFuelListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final FuelDetails item = FuelDetailsList.get(getAdapterPosition());
            mOnFuelListener.onItemClick(item.getFleet());
        }
    }

    @NonNull
    @Override
    public FuelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fuel_cardview, parent, false);
        return new FuelViewHolder(itemView, OnFuelListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FuelViewHolder holder, int position) {

        final FuelDetails item = FuelDetailsList.get(position);
        holder.fleet.setText(item.getFleet());
        holder.station.setText(item.getStation());
        holder.amount.setText(item.getAmount());
        holder.date.setText(item.getDate());
        holder.driver_name.setText(item.getDriverName());
    }

    @Override
    public int getItemCount() {
        return FuelDetailsList.size();
    }

    public interface OnFuelListener {
        void onItemClick(String name);
    }

}