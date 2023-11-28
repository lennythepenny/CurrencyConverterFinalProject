package com.zybooks.currencyconverter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private ArrayList<String> favoritesList;
    private OnItemClickListener onItemClickListener;
    public FavoritesAdapter(ArrayList<String> favoritesList) {
        this.favoritesList = favoritesList;
    }
    public interface OnItemClickListener {
        void onItemClick(String selectedCurrency);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.favoriteTextView.setText(favoritesList.get(position));
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }
    public void updateData(ArrayList<String> newFavoritesList) {
        favoritesList.clear();
        favoritesList.addAll(newFavoritesList);
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView favoriteTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favoriteTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}

