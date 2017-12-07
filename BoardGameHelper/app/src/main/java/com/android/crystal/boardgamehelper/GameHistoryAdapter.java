package com.android.crystal.boardgamehelper;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huiyu on 11/7/17.
 */

public class GameHistoryAdapter extends ArrayAdapter {
    LayoutInflater li;
    ArrayList<GameHistoryModel> gameHistoryModelArrayList;
    int resource;
    ViewHolder holder;

    public GameHistoryAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        li = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        gameHistoryModelArrayList = objects;
        this.resource = resource;
    }

    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            holder = new GameHistoryAdapter.ViewHolder();
            v = li.inflate(resource, null);
            holder.imageView = (ImageView) v.findViewById(R.id.ghRoleImage);
            holder.ghName = (TextView) v.findViewById(R.id.ghName);
            holder.ghTime = (TextView) v.findViewById(R.id.ghTime);
            holder.ghResult = (TextView) v.findViewById(R.id.ghResult);
            v.setTag(holder);
        } else {
            holder = (ViewHolder)v.getTag();
        }
        holder.ghName.setText(gameHistoryModelArrayList.get(position).getGameName());
        holder.ghTime.setText(gameHistoryModelArrayList.get(position).getGameTime());
        holder.ghResult.setText(gameHistoryModelArrayList.get(position).getGameResult());
        if (gameHistoryModelArrayList.get(position).getGameRole().equals("Guard")) {
            holder.imageView.setImageDrawable(v.getResources().getDrawable(R.drawable.defender));
        } else if (gameHistoryModelArrayList.get(position).getGameRole().equals("wolf")) {
            holder.imageView.setImageDrawable(v.getResources().getDrawable(R.drawable.wifi_reconnect));
        } else if (gameHistoryModelArrayList.get(position).getGameRole().equals("prophet")) {
            holder.imageView.setImageDrawable(v.getResources().getDrawable(R.drawable.fortune_teller));
        }
        //holder.imageView.setText(gameHistoryModelArrayList.get(position).getGameRole());
        return v;
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView ghName;
        public TextView ghTime;
        public TextView ghResult;
    }

}
