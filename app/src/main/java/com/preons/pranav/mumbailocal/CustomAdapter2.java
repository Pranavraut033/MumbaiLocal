package com.preons.pranav.mumbailocal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.preons.pranav.util.Constants;

/**
 * Created on 23-03-17 at 17:09 by Pranav Raut.
 * For MumbaiLocal
 */

class CustomAdapter2 extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private String[][] strings;
    private int[] ints = new int[]{
            R.id.p_id, R.id.from, R.id.to,
            R.id.for_s, R.id.amount, R.id.method,
            R.id.no_of_passenger, R.id.date
    };

    CustomAdapter2(Context context, String[][] strings) {
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings[1].length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.transcation_element, null);
        holder.linearLayout = (LinearLayout) rowView.findViewById(R.id.ticket_content);
        for (int i = 0; i < ints.length; i++) {
            holder.textViews[i] = (TextView) rowView.findViewById(ints[i]);
            holder.textViews[i].setText(strings[i][getCount() - position - 1]);
        }
        if (strings[2][getCount()-position-1].isEmpty()) {
            holder.linearLayout.setVisibility(View.GONE);
            holder.textViews[3].setVisibility(View.VISIBLE);
        }
        holder.textView = (TextView) rowView.findViewById(R.id.unique_id);
        holder.textView.setText(Constants.randomAlphaNumeric(16));
        holder.textViews[0].append(")");
        return rowView;
    }


    private class Holder {
        TextView[] textViews = new TextView[ints.length];
        LinearLayout linearLayout;
        TextView textView;
    }
}
