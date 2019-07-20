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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.kuwas.android.R;

import static app.kuwas.android.utils.Utils.openUrlInCustomTab;

/**
 * Created by BrookMG on 7/20/2019 in app.kuwas.android.ui.adapters
 * inside the project Kuwas .
 */
public class UsedLibrariesRecyclerAdapter extends RecyclerView.Adapter<UsedLibrariesRecyclerAdapter.ViewHolder> {

    private List<LibItem> librariesUsed;

    public UsedLibrariesRecyclerAdapter(List<LibItem> librariesUsed) {
        this.librariesUsed = librariesUsed;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.used_libs_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(librariesUsed.get(position).getTitle());
        holder.owner.setText(librariesUsed.get(position).getOwner());
        holder.itemView.setOnClickListener(librariesUsed.get(position).getClickListener());
    }

    @Override
    public int getItemCount() {
        return librariesUsed.size();
    }

    public static class LibItem {
        private String title;
        private String owner;
        private View.OnClickListener clickListener;

        public LibItem(String title, String owner, View.OnClickListener clickListener) {
            this.title = title;
            this.owner = owner;
            this.clickListener = clickListener;
        }

        public String getTitle() {
            return title;
        }

        public String getOwner() {
            return owner;
        }

        public View.OnClickListener getClickListener() {
            return clickListener;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView title, owner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_text_view);
            owner = itemView.findViewById(R.id.desc_text_view);
        }
    }
}
