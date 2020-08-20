package Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cliffdevops.alpha.dufleet.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.FleetDetails;

public class FleetAdapter extends RecyclerView.Adapter<FleetAdapter.FleetViewHolder> {
    private List<FleetDetails> FleetList;
    private OnFleetClickListener OnFleetClickListener;

    public FleetAdapter(List<FleetDetails> FleetList, OnFleetClickListener onFleetClickListener) {
        this.FleetList = FleetList;
        this.OnFleetClickListener = onFleetClickListener;

    }

    public class FleetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView fleetImage;
        TextView fleetId, type, capacity, regNum, commissionDate, chaise_no, mileage;
        OnFleetClickListener mOnFleetClickListener;

        FleetViewHolder(View view, OnFleetClickListener onFleetClickListener) {
            super(view);

            fleetId = view.findViewById(R.id.txtFleet_id);
            fleetImage = view.findViewById(R.id.fleet_Image);
            type = view.findViewById(R.id.txtFleet_type);
            capacity = view.findViewById(R.id.txtFleet_capacity);
            regNum = view.findViewById(R.id.txtFleet_reg);
            commissionDate = view.findViewById(R.id.txtFleet_CommissionDate);
            chaise_no = view.findViewById(R.id.txtFleet_chaise);
            mileage = view.findViewById(R.id.txtFleet_comMileage);
            mOnFleetClickListener = onFleetClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final FleetDetails item = FleetList.get(getAdapterPosition());
            mOnFleetClickListener.onItemClick(item.getRegNum());
        }
    }

    @NonNull
    @Override
    public FleetAdapter.FleetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fleet_cardview, parent, false);
        return new FleetAdapter.FleetViewHolder(itemView, OnFleetClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FleetAdapter.FleetViewHolder holder, int position) {
        final FleetDetails item = FleetList.get(position);
        holder.fleetId.setText(item.getFleetId());
        holder.type.setText(item.getType());
        holder.capacity.setText(item.getCapacity());
        holder.regNum.setText(item.getRegNum());
        holder.commissionDate.setText(item.getCommissionDate());
        holder.chaise_no.setText(item.getChaise_no());
        holder.mileage.setText(item.getMileage());
        Picasso.get().load(item.getImage()).into(holder.fleetImage);

    }

    @Override
    public int getItemCount() {
        return FleetList.size();
    }

    public interface OnFleetClickListener {
        void onItemClick(String name);
    }

}
