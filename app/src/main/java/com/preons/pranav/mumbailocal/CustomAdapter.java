package com.preons.pranav.mumbailocal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

class CustomAdapter extends BaseAdapter {
    private String[] stationList;
    private static LayoutInflater inflater = null;
    @Nullable
    private ClickListener clickListener;

    CustomAdapter(Context context) {
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return stationList.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public CustomAdapter setStationList(String[] stationList) {
        this.stationList = stationList;
        return this;
    }

    private class Holder {
        TextView textView;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.row_elements, null);
        holder.textView = (TextView) rowView.findViewById(R.id.name);
        holder.textView.setText(Html.fromHtml(stationList[position]));
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) clickListener.clickEvent(position);
            }
        });
        return rowView;

    }

    void setClickListener(@Nullable ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    interface ClickListener{
        void clickEvent(int position);
    }
}

