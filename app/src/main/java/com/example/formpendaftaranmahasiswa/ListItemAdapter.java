package com.example.formpendaftaranmahasiswa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;

public class ListItemAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> items;
    private final ArrayList<Integer> itemIds;
    private final DatabaseHelper dbHelper;
    private final int layoutResource;

    public ListItemAdapter(Context context, ArrayList<String> items, ArrayList<Integer> itemIds, int layoutResource) {
        super(context, layoutResource, items);
        this.context = context;
        this.items = items;
        this.itemIds = itemIds;
        this.dbHelper = new DatabaseHelper(context);
        this.layoutResource = layoutResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResource, parent, false);

            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.itemText);
            holder.deleteButton = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String item = items.get(position);
        holder.textView.setText(item);

        holder.deleteButton.setOnClickListener(v -> {
            int itemId = itemIds.get(position);
            dbHelper.deleteItem(itemId);
            items.remove(position);
            itemIds.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
        ImageButton deleteButton;
    }
}
