package Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cliffdevops.alpha.dufleet.R;

import java.util.List;

import Model.SubContractDetails;

public class SubContractAdapter extends RecyclerView.Adapter<SubContractAdapter.SubContractViewHolder> {
    private List<SubContractDetails> subContractDetailsList;
    private SubContractAdapter.OnSubContractListener OnSubContractListener;

    public SubContractAdapter(List<SubContractDetails> subContractDetailsList, SubContractAdapter.OnSubContractListener onSubContractListener) {
        this.subContractDetailsList = subContractDetailsList;
        this.OnSubContractListener = onSubContractListener;

    }

    public class SubContractViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView company, contactPerson, mobile, contractDetails, contractDuration, contractExpireDate;
        SubContractAdapter.OnSubContractListener mOnSubContractListener;

        SubContractViewHolder(View view, SubContractAdapter.OnSubContractListener onSubContractListener) {
            super(view);

            company = view.findViewById(R.id.txtCompany);
            contactPerson = view.findViewById(R.id.txtContactPerson);
            mobile = view.findViewById(R.id.txtContactMobile);
            contractDetails = view.findViewById(R.id.txtContractDetails);
            contractDuration = view.findViewById(R.id.txtContractDuration);
            contractExpireDate = view.findViewById(R.id.txtExpireDate);
            mOnSubContractListener = onSubContractListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final SubContractDetails item = subContractDetailsList.get(getAdapterPosition());
            mOnSubContractListener.onItemClick(item.getContract_id());
        }
    }

    @NonNull
    @Override
    public SubContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subcontract_cardview, parent, false);
        return new SubContractViewHolder(itemView, OnSubContractListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubContractViewHolder holder, int position) {

        final SubContractDetails item = subContractDetailsList.get(position);
        holder.company.setText(item.getCompany());
        holder.contactPerson.setText(item.getContact_person());
        holder.mobile.setText(item.getMobile());
        holder.contractDetails.setText(item.getContract_details());
        holder.contractDuration.setText(item.getContract_duration());
        holder.contractExpireDate.setText(item.getContract_expire_date());
    }

    @Override
    public int getItemCount() {
        return subContractDetailsList.size();
    }

    public interface OnSubContractListener {
        void onItemClick(String name);
    }

}
