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

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.kuwas.android.R;

/**
 * Created by BrookMG on 7/25/2019 in app.kuwas.android.ui.adapters
 * inside the project Kuwas .
 */
public class MenuSheetAdapter extends RecyclerView.Adapter<MenuSheetAdapter.ViewHolder> {

    public static class MenuItem {
        private Drawable menuIcon;
        private String menuTitle;
        private View.OnClickListener clickListener;

        public MenuItem(Drawable menuIcon, String menuTitle, View.OnClickListener clickListener) {
            this.menuIcon = menuIcon;
            this.menuTitle = menuTitle;
            this.clickListener = clickListener;
        }

        public Drawable getMenuIcon() {
            return menuIcon;
        }

        public String getMenuTitle() {
            return menuTitle;
        }

        public View.OnClickListener getClickListener() {
            return clickListener;
        }
    }

    private List<MenuItem> items;

    public MenuSheetAdapter(List<MenuItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_sheet_element, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.menuIcon.setImageDrawable(items.get(position).getMenuIcon());
        holder.menuText.setText(items.get(position).getMenuTitle());
        holder.itemView.setOnClickListener(items.get(position).getClickListener());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView menuIcon;
        AppCompatTextView menuText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuText = itemView.findViewById(R.id.menu_title);
            menuIcon = itemView.findViewById(R.id.menu_icon);
        }
    }

}
