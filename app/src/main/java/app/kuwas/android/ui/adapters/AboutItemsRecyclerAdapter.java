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

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.kuwas.android.R;

import static android.view.View.GONE;

/**
 * Created by BrookMG on 7/20/2019 in app.kuwas.android.ui.adapters
 * inside the project Kuwas .
 */
public class AboutItemsRecyclerAdapter extends RecyclerView.Adapter<AboutItemsRecyclerAdapter.ViewHolder> {

    private List<AboutItem> items;

    public AboutItemsRecyclerAdapter(List<AboutItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.about_recycler_item_normi, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTextView.setText(items.get(position).getTitle());
        holder.contentTextView.setText(items.get(position).getContent());
        holder.actionButton.setText(items.get(position).getActionText());

        if (items.get(position).getActionOnClickListener() == null) holder.actionButton.setVisibility(GONE);
        holder.actionButton.setOnClickListener(items.get(position).getActionOnClickListener());

        if (items.get(position).getItemBackgroundColor() != null) {
            holder.itemView.setBackgroundColor(items.get(position).getItemBackgroundColor());
        }

        if (items.get(position).getItemTextColor() != null) {
            holder.titleTextView.setTextColor(items.get(position).getItemTextColor());
            holder.contentTextView.setTextColor(items.get(position).getItemTextColor());
            holder.actionButton.setTextColor(items.get(position).getItemTextColor());
        }

        if (items.get(position).getCornerBadge() == null && items.get(position).getCornerBadgeLink() == null) {
            holder.cornerBadge.setVisibility(GONE);
        }

        if (items.get(position).getCornerBadge() != null) {
            Glide.with(holder.cornerBadge).load(items.get(position).getCornerBadge()).into(holder.cornerBadge);
        } else if (items.get(position).getCornerBadgeLink() != null) {
            Glide.with(holder.cornerBadge).load(items.get(position).getCornerBadgeLink()).into(holder.cornerBadge);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class AboutItem {
        private String title;
        private String content;
        private String actionText;
        private View.OnClickListener actionOnClickListener;

        // Optional
        private Integer itemBackgroundColor;
        private Integer itemTextColor;
        private @DrawableRes Integer cornerBadge;   // This will be given priority over network image
        private String cornerBadgeLink;

        public AboutItem(String title, String content, String actionText, View.OnClickListener actionOnClickListener) {
            this(title, content, actionText, actionOnClickListener, null, null);
        }

        public AboutItem(String title, String content, String actionText, View.OnClickListener actionOnClickListener,@DrawableRes Integer cornerBadge) {
            this(title, content, actionText, actionOnClickListener, null, null, cornerBadge);
        }

        public AboutItem(String title, String content, String actionText, View.OnClickListener actionOnClickListener, String cornerBadgeLink) {
            this(title, content, actionText, actionOnClickListener, null, null, cornerBadgeLink);
        }

        public AboutItem(String title, String content, String actionText, View.OnClickListener actionOnClickListener,
                          Integer itemBackgroundColor, Integer itemTextColor) {
            this(title, content, actionText, actionOnClickListener, itemBackgroundColor, itemTextColor, null, null);
        }

        public AboutItem(String title, String content, String actionText, View.OnClickListener actionOnClickListener,
                         Integer itemBackgroundColor, Integer itemTextColor, String cornerBadgeLink) {
            this(title, content, actionText, actionOnClickListener, itemBackgroundColor, itemTextColor, null, cornerBadgeLink);
        }

        public AboutItem(String title, String content, String actionText, View.OnClickListener actionOnClickListener,
                         Integer itemBackgroundColor, Integer itemTextColor,@DrawableRes Integer cornerBadge) {
            this(title, content, actionText, actionOnClickListener, itemBackgroundColor, itemTextColor, cornerBadge, null);
        }

        private AboutItem(String title, String content, String actionText, View.OnClickListener actionOnClickListener, Integer itemBackgroundColor,
                         Integer itemTextColor,@DrawableRes Integer cornerBadge, String cornerBadgeLink) {
            this.title = title;
            this.content = content;
            this.actionText = actionText;
            this.actionOnClickListener = actionOnClickListener;
            this.itemBackgroundColor = itemBackgroundColor;
            this.itemTextColor = itemTextColor;
            this.cornerBadge = cornerBadge;
            this.cornerBadgeLink = cornerBadgeLink;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getActionText() {
            return actionText;
        }

        public View.OnClickListener getActionOnClickListener() {
            return actionOnClickListener;
        }

        public Integer getItemBackgroundColor() {
            return itemBackgroundColor;
        }

        public Integer getItemTextColor() {
            return itemTextColor;
        }

        public Integer getCornerBadge() {
            return cornerBadge;
        }

        public String getCornerBadgeLink() {
            return cornerBadgeLink;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView titleTextView , contentTextView, actionButton;
        AppCompatImageView cornerBadge;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            contentTextView = itemView.findViewById(R.id.content_text_view);
            actionButton = itemView.findViewById(R.id.action_button);
            cornerBadge = itemView.findViewById(R.id.corner_badge);
        }
    }

}
