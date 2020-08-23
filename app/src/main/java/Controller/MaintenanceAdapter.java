package Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cliffdevops.alpha.dufleet.R;

public class MaintenanceAdapter extends ArrayAdapter<String> {
    int groupId;
    java.lang.String[] item_list;
    Context context;

    public MaintenanceAdapter(Context context, int num, int id, java.lang.String[] item_list) {
        super(context, num, id, item_list);
        this.context = context;
        groupId = num;
        this.item_list = item_list;

    }

    static class ViewHolder {
        public TextView fleet;
        public TextView vehicle;
        public TextView description;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(groupId, parent, false);
            MaintenanceAdapter.ViewHolder viewHolder = new MaintenanceAdapter.ViewHolder();
            viewHolder.fleet = rowView.findViewById(R.id.txtFleetId);
            viewHolder.vehicle = rowView.findViewById(R.id.txtVehicle);
            viewHolder.description = rowView.findViewById(R.id.txtDescription);
            rowView.setTag(viewHolder);

        }

        java.lang.String[] items = item_list[position].split(",");
        MaintenanceAdapter.ViewHolder holder = (MaintenanceAdapter.ViewHolder) rowView.getTag();
        holder.fleet.setText(items[0]);
        holder.vehicle.setText(items[1]);
        holder.description.setText(items[2]);
        return rowView;
    }


}
