package Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cliffdevops.alpha.dufleet.R;

import java.util.List;

import Model.BookingDetails;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private List<BookingDetails> BookingList;
    private OnBookingListener OnBookingListener;

    public BookingAdapter(List<BookingDetails> BookingList, OnBookingListener onBookingListener) {
        this.BookingList = BookingList;
        this.OnBookingListener = onBookingListener;

    }

    public class BookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView bookingDate, name, department, note, mobile, email, timestamp;
        OnBookingListener mOnBookingListener;

        BookingViewHolder(View view, OnBookingListener onBookingListener) {
            super(view);

            bookingDate = view.findViewById(R.id.txtBookingDate);
            name = view.findViewById(R.id.txtBookingName);
            mobile = view.findViewById(R.id.txtBookingMobile);
            email = view.findViewById(R.id.txtBookingEmail);
            department = view.findViewById(R.id.txtBookingDepartment);
            note = view.findViewById(R.id.txtBookingNote);
            timestamp = view.findViewById(R.id.txtBookingTmeStamp);
            mOnBookingListener = onBookingListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            final BookingDetails item = BookingList.get(getAdapterPosition());
            mOnBookingListener.onItemClick(item.getBooking_id());
        }
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_cardview, parent, false);
        return new BookingViewHolder(itemView, OnBookingListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {

        final BookingDetails item = BookingList.get(position);
        holder.bookingDate.setText(item.getBooking_date());
        holder.name.setText(item.getName());
        holder.mobile.setText(item.getMobile());
        holder.email.setText(item.getEmail());
        holder.department.setText(item.getDepartment());
        holder.note.setText(item.getNote());
        holder.timestamp.setText(item.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return BookingList.size();
    }

    public interface OnBookingListener {
        void onItemClick(String name);
    }

}
