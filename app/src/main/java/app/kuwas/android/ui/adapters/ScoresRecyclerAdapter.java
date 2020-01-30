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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.kuwas.android.R;
import app.kuwas.android.bridge.data.LeagueItemStatus;
import app.kuwas.android.bridge.data.LeagueScheduleItem;
import app.kuwas.android.bridge.data.Team;

import static app.kuwas.android.utils.Utils.run;

/**
 * Created by BrookMG on 5/5/2019 in app.kuwas.android.ui.adapters
 * inside the project Kuwas .
 */
public class ScoresRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Boolean> leagueScheduleItemsShouldShowDate;
    private List<LeagueScheduleItem> leagueScheduleItems;

    public interface OnTeamClicked {
        void onClick(Team team);
    }

    private OnTeamClicked teamClicked;

    private final int HEADER = 0;
    private final int SCORE = 1;

    public ScoresRecyclerAdapter(List<LeagueScheduleItem> leagueScheduleItems) {
        this(leagueScheduleItems, null);
    }

    public ScoresRecyclerAdapter(List<LeagueScheduleItem> leagueScheduleItems, OnTeamClicked teamClicked) {
        Collections.reverse(leagueScheduleItems);
        this.teamClicked = teamClicked;
        this.leagueScheduleItems = leagueScheduleItems;
        setupLeagueScheduleShouldShowDateBooleanList();
    }

    private void setupLeagueScheduleShouldShowDateBooleanList () {
        List<LeagueScheduleItem> copy = new ArrayList<>();
        for (LeagueScheduleItem item : leagueScheduleItems)
            // Clean up fields with null team detail values
            if (item.getGameDetail().keySet().toArray(new Team[0]).length > 1) copy.add(item);

        leagueScheduleItems = copy;

        leagueScheduleItemsShouldShowDate = new ArrayList<>(leagueScheduleItems.size());
        run(() -> leagueScheduleItemsShouldShowDate.add(false), leagueScheduleItems.size());

        // starting from the top, toggle the values on the list to `true` for positions that
        // correspond to league items with different date from what comes before them

        for (LeagueScheduleItem item : leagueScheduleItems) {
            if (leagueScheduleItems.indexOf(item) == 0) {
                leagueScheduleItemsShouldShowDate.remove(0);
                leagueScheduleItemsShouldShowDate.add(0, true);
                continue;
            }

            if (!item.getGameDate().equals(
                    leagueScheduleItems.get(leagueScheduleItems.indexOf(item) -1).getGameDate()
            )) replaceItem(leagueScheduleItemsShouldShowDate, leagueScheduleItems.indexOf(item), true);
        }
    }

    private <T> void replaceItem ( List<T> fromList, int atPosition, T withItem ) {
        fromList.remove(atPosition);
        fromList.add(atPosition, withItem);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER:
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.blank_header, parent, false));
            case SCORE:
            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.score_element, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == SCORE) {
            position -= 1;  // the first view is taken by the header
            Map<Team, Integer> team_result = leagueScheduleItems.get(position).getGameDetail();
            ((ViewHolder) holder).datetime.setText(leagueScheduleItems.get(position).getGameDate());
            ((ViewHolder) holder).datetime.setVisibility(leagueScheduleItemsShouldShowDate.get(position) ? View.VISIBLE : View.GONE);

            if (team_result.keySet().toArray(new Team[0])[0] == null || team_result.keySet().toArray(new Team[0])[1] == null) return;

            ((ViewHolder) holder).team_1_name.setText(team_result.keySet().toArray(new Team[0])[0].getTeamFullName());
            ((ViewHolder) holder).team_2_name.setText(team_result.keySet().toArray(new Team[0])[1].getTeamFullName());

            if (leagueScheduleItems.get(holder.getAdapterPosition() -1).getGameStatus() == LeagueItemStatus.STATUS_TOOK_PLACE) {
                ((ViewHolder) holder).teams_score.setText(
                        String.format(Locale.US, "%d : %d",
                                team_result.get(team_result.keySet().toArray(new Team[0])[0]),
                                team_result.get(team_result.keySet().toArray(new Team[0])[1])
                        )
                );
            } else {
                ((ViewHolder) holder).teams_score.setText("- : -");
            }

            Glide.with(((ViewHolder) holder).team_1_image).load(team_result.keySet().toArray(new Team[0])[0].getTeamLogo()).into(((ViewHolder) holder).team_1_image);
            Glide.with(((ViewHolder) holder).team_2_image).load(team_result.keySet().toArray(new Team[0])[1].getTeamLogo()).into(((ViewHolder) holder).team_2_image);

            ((ViewHolder) holder).team_1_image.setOnClickListener(v -> fireTeamClicked(team_result.keySet().toArray(new Team[0])[0]));
            ((ViewHolder) holder).team_2_image.setOnClickListener(v -> fireTeamClicked(team_result.keySet().toArray(new Team[0])[1]));
        }
    }

    private void fireTeamClicked(Team team) {
        if (teamClicked != null) teamClicked.onClick(team);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : SCORE;
    }

    @Override
    public int getItemCount() {
        //we should count the header too
        return leagueScheduleItems.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView team_1_image, team_2_image;
        AppCompatTextView team_1_name;
        AppCompatTextView team_2_name;
        AppCompatTextView teams_score;
        AppCompatTextView datetime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            team_1_image = itemView.findViewById(R.id.team_1_image);
            team_2_image = itemView.findViewById(R.id.team_2_image);

            teams_score = itemView.findViewById(R.id.teams_score);
//            team_1_score = itemView.findViewById(R.id.team_1_score);
//            team_2_score = itemView.findViewById(R.id.team_2_score);

            team_1_name = itemView.findViewById(R.id.team_1_name);
            team_2_name = itemView.findViewById(R.id.team_2_name);

            datetime = itemView.findViewById(R.id.game_datetime);
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
