package com.steszyngagne.draw.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.steszyngagne.draw.Structs.ShapeListItem;

import java.util.LinkedList;

import ca.qc.johnabbott.cs603.R;

public class PicturesAdapter extends ArrayAdapter<ShapeListItem> {
    private final static int REGULAR_ROW_LAYOUT = R.layout.list_item_picture;
    private LinkedList<ShapeListItem> list;
    private final PicturesAdapter ad = this;
    // to load artwork, references the main activity
    Context activity;

    public PicturesAdapter(Context activity, LinkedList<ShapeListItem> list) {
        super(activity, REGULAR_ROW_LAYOUT, list);
        this.list = list;
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) { // first time: inflate a new View
            Context context = getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE
            );
            rowView = inflater.inflate(REGULAR_ROW_LAYOUT, null);
        }

        final TextView name = (TextView) rowView.findViewById(R.id.name);
        final TextView num = (TextView) rowView.findViewById(R.id.num);

        name.setText(list.get(position).name);
        num.setText(list.get(position).num_shapes + " " + activity.getString(R.string.num_shapes));

        return rowView;
    }

}