/*
 * Copyright (C) 2019 Brook Mezgebu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.kuwas.android.ui.adapters;

import android.os.Build;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import app.kuwas.android.R;
import io.brookmg.soccerethiopiaapi.data.NewsItem;

import static app.kuwas.android.utils.Utils.getTimeGap;

/**
 * Created by BrookMG on 5/5/2019 in app.kuwas.android.ui.adapters
 * inside the project Kuwas .
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsItem> news;
    private OnItemActionListener onItemActionListener;

    private final int HEADER = 0;
    private final int NEWS = 1;

    public NewsRecyclerAdapter(List<NewsItem> news) {
        this(news, null);
    }

    public NewsRecyclerAdapter(List<NewsItem> news, OnItemActionListener listener) {
        this.news = news;
        this.onItemActionListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER:
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.blank_header, parent, false));
            case NEWS:
            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_element, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : NEWS;   //the first item should be the header
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // decrease one from position because the header is also counted
        if (getItemViewType(position) == NEWS) {
            ((ViewHolder) holder).news_title.setText(news.get(position - 1).getNewsTitle());
            ((ViewHolder) holder).news_author.setText(news.get(position - 1).getNewsAuthorName());
            ((ViewHolder) holder).news_published.setText(getTimeGap(news.get(position - 1).getNewsPublishedOn().getTime()));
            Glide.with(((ViewHolder) holder).news_image).load(news.get(position - 1).getNewsImage()).into(((ViewHolder) holder).news_image);
            if (onItemActionListener != null) {
                ((ViewHolder) holder).itemView.setOnClickListener(v -> onItemActionListener.onItemClicked(holder.itemView, holder.getAdapterPosition()-1));
                ((ViewHolder) holder).itemView.setOnLongClickListener(v -> onItemActionListener.onItemLongClicked(v, holder.getAdapterPosition() - 1));
            }
        }
    }

    @Override
    public int getItemCount() {
        //we should count the header too
        return news.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView news_image;
        AppCompatTextView news_title, news_author, news_published;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            news_image = itemView.findViewById(R.id.news_image);
            news_author = itemView.findViewById(R.id.news_author);
            news_published = itemView.findViewById(R.id.news_published);
            news_title = itemView.findViewById(R.id.news_title);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        View blankView;

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            blankView = itemView.findViewById(R.id.blank_view);
        }
    }

}
