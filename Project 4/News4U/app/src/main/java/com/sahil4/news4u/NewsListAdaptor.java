package com.sahil4.news4u;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsListAdaptor extends RecyclerView.Adapter<NewsViewHolder> {

    private final ArrayList<NewsItem> localDataSet = new ArrayList<>();
    private final NewsItemClick listener;

    public NewsListAdaptor(ArrayList<NewsItem> Items, NewsItemClick listener) {
        this.listener = listener;
        localDataSet.addAll(Items);
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_news, viewGroup, false);

        NewsViewHolder newsViewHolder = new NewsViewHolder(view);

        view.setOnClickListener(view1 -> listener.OnItemClicked(localDataSet.get(newsViewHolder.getAdapterPosition())));

        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder newsViewHolder, final int position) {
        newsViewHolder.getTitleView().setText(localDataSet.get(position).title);
        newsViewHolder.getDescriptionView().setText(localDataSet.get(position).description);
        Glide.with(newsViewHolder.itemView.getContext()).load(localDataSet.get(position).urlToImage).into(newsViewHolder.getImageView());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addNewsItems(ArrayList<NewsItem> newsList) {
        localDataSet.clear();
        localDataSet.addAll(newsList);
        notifyDataSetChanged();
    }

}

class NewsViewHolder extends RecyclerView.ViewHolder {
    private final TextView title;
    private final TextView description;
    private final ImageView image;

    public NewsViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        image = view.findViewById(R.id.image);
    }

    public TextView getTitleView() {
        return title;
    }

    public TextView getDescriptionView() {
        return description;
    }

    public ImageView getImageView() {
        return image;
    }

}

interface NewsItemClick {
    void OnItemClicked(NewsItem item);
}
