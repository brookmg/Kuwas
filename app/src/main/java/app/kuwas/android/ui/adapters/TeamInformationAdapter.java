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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import app.kuwas.android.R;
import io.brookmg.soccerethiopiaapi.data.Team;

import static app.kuwas.android.utils.Utils.dpToPx;

/**
 * Created by BrookMG on 6/28/2019 in app.kuwas.android.ui.adapters
 * inside the project Kuwas .
 */
public class TeamInformationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int HEADER = 0;
    private final int BODY = 1;

    private final List<String> detailTitleStrings = Arrays.asList(
            "Since",
            "From City",
            "Previous Names",
            "Stadium",
            "President",
            "V.President",
            "Manager",
            "Coach",
            "V.Coach",
            "Technique Director",
            "Goal Keeper",
            "Team Alpha",
            "Nurse",
            "Keywords"
    );

    private List<List<String>> detailContentStrings;

    public TeamInformationAdapter(Team team) {
        detailContentStrings = feedTeamDetailsIntoArray(team);
    }

    private List<List<String>> feedTeamDetailsIntoArray(Team team) {
        List<List<String>> returnable = new ArrayList<>();

        returnable.add(Collections.singletonList(Integer.toString(team.getInitYear())));
        returnable.add(Collections.singletonList(team.getFromCity()));
        returnable.add(team.getPreviousNames());
        returnable.add(Collections.singletonList(team.getStadium()));
        returnable.add(Collections.singletonList(team.getPresident()));
        returnable.add(Collections.singletonList(team.getVicePresident()));
        returnable.add(Collections.singletonList(team.getManager()));
        returnable.add(Collections.singletonList(team.getMainCoach()));
        returnable.add(Collections.singletonList(team.getViceCoach()));
        returnable.add(Collections.singletonList(team.getTechniqueDirector()));
        returnable.add(Collections.singletonList(team.getGoalKeeper()));
        returnable.add(Collections.singletonList(team.getTeamAlpha()));
        returnable.add(Collections.singletonList(team.getTeamNurse()));
        returnable.add(team.getKeywords());

        return returnable;
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
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.team_information_element, parent, false)
                );
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : BODY;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BODY) {
            position = position -1;
            ((ViewHolder) holder).detailTitle.setText(detailTitleStrings.get(position));
            if (detailContentStrings.get(position).size() == 1) {
                ((ViewHolder) holder).detailContent.setText(detailContentStrings.get(position).get(0));
            } else {
                ((ViewHolder) holder).detailRecyclerContent.setLayoutManager(
                        new LinearLayoutManager(
                                ((ViewHolder) holder).detailRecyclerContent.getContext(),
                                RecyclerView.HORIZONTAL,
                                false
                        )
                );

                ((ViewHolder) holder).detailRecyclerContent.setAdapter(
                        new TagsChipRecyclerAdapter(
                                detailContentStrings.get(position)
                        )
                );

                ((ViewHolder) holder).detailRecyclerContent.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).detailContent.setVisibility(View.GONE);
            }
        } else {
            ViewGroup.LayoutParams params = ((HeaderViewHolder) holder).blankView.getLayoutParams();
            params.height = dpToPx(holder.itemView.getContext(), 84);
            ((HeaderViewHolder) holder).blankView.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return detailContentStrings.size() +1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView detailTitle , detailContent;
        RecyclerView detailRecyclerContent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailContent = itemView.findViewById(R.id.detail_content);
            detailRecyclerContent = itemView.findViewById(R.id.detail_recycler);
            detailTitle = itemView.findViewById(R.id.detail_title);
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
