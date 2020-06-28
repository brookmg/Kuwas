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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import app.kuwas.android.R;
import app.kuwas.android.bridge.data.Team;

/**
 * Created by BrookMG on 6/28/2019 in app.kuwas.android.ui.adapters
 * inside the project Kuwas .
 */
public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Team> teams;
    private ArrayList<Team> selectedTeams;
    private final int HEADER = 0;
    private final int BODY = 1;

    public interface OnSelectedTeamsChanged {
        void onChanged(List<Team> items);
    }

    OnSelectedTeamsChanged callback;

    public FavoriteRecyclerAdapter(ArrayList<Team> teams, OnSelectedTeamsChanged teamsChanged) {
        this.teams = teams;
        selectedTeams = new ArrayList<>();
        callback = teamsChanged;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER:
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.blank_header, parent, false));
            case BODY:
            default:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_recycler_item, parent, false)
                );
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position < 3 ? HEADER : BODY;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BODY) {
            Glide.with(((ViewHolder) holder).teamIcon)
                    .load(teams.get(position - 3).getTeamLogo())
                    .apply(RequestOptions.circleCropTransform())
                    .into(((ViewHolder) holder).teamIcon);
            holder.itemView.setOnClickListener(v -> {
                boolean currently = toggleSelectedItem(teams.get(position - 3));
                ((ViewHolder) holder).overlay.setVisibility(currently ? View.GONE : View.VISIBLE);
                callback.onChanged(selectedTeams);
            });
            ((ViewHolder) holder).teamName.setText(teams.get(position - 3).getTeamFullName());
        } else {
//            ViewGroup.LayoutParams params = ((HeaderViewHolder) holder).blankView.getLayoutParams();
//            params.height = dpToPx(holder.itemView.getContext(), 116);
//            ((HeaderViewHolder) holder).blankView.setLayoutParams(params);
        }
    }

    public boolean toggleSelectedItem(Team item) {
        if (selectedTeams.contains(item)) {
            selectedTeams.remove(item);
            return false;
        }
        else {
            selectedTeams.add(item);
            return true;
        }
    }

    @Override
    public int getItemCount() {
        return teams.size() + 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView teamIcon , checked;
        AppCompatTextView teamName;
        View overlay;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamIcon = itemView.findViewById(R.id.team_logo);
            checked = itemView.findViewById(R.id.check_sign);
            overlay = itemView.findViewById(R.id.overlay);
            teamName = itemView.findViewById(R.id.team_name);
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
