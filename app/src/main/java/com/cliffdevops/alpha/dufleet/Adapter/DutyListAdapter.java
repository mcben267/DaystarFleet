package com.cliffdevops.alpha.dufleet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cliffdevops.alpha.dufleet.R;

public class DutyListAdapter extends ArrayAdapter<String> {
    int groupId;
    String[] item_list;
    Context context;

    public DutyListAdapter(Context context, int num, int id, String[] item_list) {
        super(context, num, id, item_list);
        this.context = context;
        groupId = num;
        this.item_list = item_list;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        // Inflate the table_row.xml file if convertView is null
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(groupId, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.fleet = (TextView) rowView.findViewById(R.id.txtFleetId);
            viewHolder.vehicle = (TextView) rowView.findViewById(R.id.txtVehicle);
            viewHolder.driver = (TextView) rowView.findViewById(R.id.txtDriver);
            rowView.setTag(viewHolder);

        }

        String[] items = item_list[position].split(",");
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.fleet.setText(items[0]);
        holder.vehicle.setText(items[1]);
        holder.driver.setText(items[2]);
        return rowView;
    }

    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView fleet;
        public TextView vehicle;
        public TextView driver;

    }

}
