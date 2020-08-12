package Controller;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cliffdevops.alpha.dufleet.R;

import java.util.List;

import Model.StaffDetails;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {
    private List<StaffDetails> StaffList;
    private OnStaffListener OnStaffListener;
    private Context context;

    public StaffAdapter(List<StaffDetails> StaffList, StaffAdapter.OnStaffListener onStaffListener) {
        this.StaffList = StaffList;
        this.OnStaffListener = onStaffListener;
    }

    public class StaffViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, staff_id, role, mobile, email, tax_pin, national_id,
                license, insuranceStatus, insurancePolicy, bloodGroup,
                medicalCondition;
        ImageView imageView;
        OnStaffListener mOnStaffListener;

        StaffViewHolder(View view, StaffAdapter.OnStaffListener onStaffListener) {
            super(view);
            imageView = view.findViewById(R.id.staffProfile);
            name = view.findViewById(R.id.txtStaffName);
            staff_id = view.findViewById(R.id.txtStaffId);
            role = view.findViewById(R.id.txtRole);
            mobile = view.findViewById(R.id.txtStaffMobile);
            email = view.findViewById(R.id.txtStaffEmail);
            tax_pin = view.findViewById(R.id.txtTaxPin);
            national_id = view.findViewById(R.id.txtNationalID);
            license = view.findViewById(R.id.txtStaffLicense);
            insuranceStatus = view.findViewById(R.id.txtStaffInsuranceStatus);
            insurancePolicy = view.findViewById(R.id.txtStaffInsurance);
            bloodGroup = view.findViewById(R.id.txtBloodGroup);
            medicalCondition = view.findViewById(R.id.txtStaffMedicalCondition);
            mOnStaffListener = OnStaffListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final StaffDetails item = StaffList.get(getAdapterPosition());
            mOnStaffListener.onNoteClick(item.getName());

            ImageView viewDetails = view.findViewById(R.id.viewMore);
            ConstraintLayout constraintLayout = view.findViewById(R.id.staffProfileData);

            TransitionManager.beginDelayedTransition(constraintLayout, new AutoTransition());
            constraintLayout.setVisibility(View.VISIBLE);

            if (constraintLayout.getVisibility() == View.VISIBLE) {
                viewDetails.setImageResource(R.drawable.ic_arrow_up);

            } else {
                viewDetails.setImageResource(R.drawable.ic_arrow_down);
                //.beginDelayedTransition(constraintLayout, new AutoTransition());
                constraintLayout.setVisibility(View.GONE);

            }

        }

    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.staff_cardview, parent, false);
        return new StaffAdapter.StaffViewHolder(itemView, OnStaffListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        final StaffDetails item = StaffList.get(position);
        holder.name.setText(item.getName());
        holder.staff_id.setText(item.getStaff_id());
        holder.role.setText(item.getRole());
        holder.mobile.setText(item.getMobile());
        holder.email.setText(item.getEmail());
        holder.tax_pin.setText(item.getTax_pin());
        holder.national_id.setText(item.getNational_id());
        holder.license.setText(item.getLicense());
        holder.insuranceStatus.setText(item.getInsuranceStatus());
        holder.insurancePolicy.setText(item.getInsurancePolicy());
        holder.bloodGroup.setText(item.getBloodGroup());
        holder.medicalCondition.setText(item.getMedicalCondition());
        //Picasso.get().load(item.getProfilePic()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return StaffList.size();
    }

    public interface OnStaffListener {
        void onNoteClick(String name);
    }

}
