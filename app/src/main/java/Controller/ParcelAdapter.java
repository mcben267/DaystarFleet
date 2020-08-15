package Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cliffdevops.alpha.dufleet.R;

import java.util.List;

import Model.ParcelDetails;

public class ParcelAdapter extends RecyclerView.Adapter<ParcelAdapter.ParcelViewHolder> {
    private List<ParcelDetails> ParcelList;
    private ParcelAdapter.OnParcelListener OnParcelListener;

    public ParcelAdapter(List<ParcelDetails> ParcelList, ParcelAdapter.OnParcelListener onParcelListener) {
        this.ParcelList = ParcelList;
        this.OnParcelListener = onParcelListener;

    }

    public class ParcelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView receivers_name, receiver_address, parcel_category;
        ParcelAdapter.OnParcelListener mOnParcelListener;

        ParcelViewHolder(View view, ParcelAdapter.OnParcelListener onParcelListener) {
            super(view);

            receivers_name = view.findViewById(R.id.txtReceiverName);
            receiver_address = view.findViewById(R.id.txtParcelAddress);
            parcel_category = view.findViewById(R.id.txtParcelCategory);
            mOnParcelListener = onParcelListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            final ParcelDetails item = ParcelList.get(getAdapterPosition());
            mOnParcelListener.onItemClick(item.getParcel_id(), item.getParcel_category(), item.getParcel_status(),
                    item.getReceiver_name(), item.getReceiver_mobile(), item.getReceiver_address(),
                    item.getParcel_destination(), item.getParcel_origin(), item.getParcel_image());
        }
    }

    @NonNull
    @Override
    public ParcelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parcel_cardview, parent, false);
        return new ParcelViewHolder(itemView, OnParcelListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ParcelViewHolder holder, int position) {

        final ParcelDetails item = ParcelList.get(position);
        holder.receivers_name.setText(item.getReceiver_name());
        holder.receiver_address.setText(item.getReceiver_address());
        holder.parcel_category.setText(item.getParcel_category());

    }

    @Override
    public int getItemCount() {
        return ParcelList.size();
    }

    public interface OnParcelListener {
        void onItemClick(String id, String category, String status, String name, String mobile,
                         String address, String destination, String origin, String image);
    }

}
