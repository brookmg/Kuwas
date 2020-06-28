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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import app.kuwas.android.App;
import app.kuwas.android.R;
import app.kuwas.android.bridge.data.Player;
import app.kuwas.android.bridge.data.Team;

/**
 * Created by BrookMG on 5/5/2019 in app.kuwas.android.ui.adapters
 * inside the project Kuwas .
 */
public class TopPlayersRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int UNSELECTED = -1;

    private List<Player> players;
    private int selectedPosition = UNSELECTED;
    private OnItemActionListener onItemActionListener;
    private RecyclerView recyclerView;

    private final int HEADER = 0;
    private final int PLAYERS = 1;

    public TopPlayersRecyclerAdapter(RecyclerView recyclerView, List<Player> players) {
        this(recyclerView, players, null);
    }

    public TopPlayersRecyclerAdapter(RecyclerView recyclerView, List<Player> players, OnItemActionListener listener) {
        this.players = players;
        this.onItemActionListener = listener;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER:
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.blank_header, parent, false));
            case PLAYERS:
            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.top_player_element, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : PLAYERS;   //the first item should be the header
    }

    private List<String> filterOutStringFromTeams(List<Team> teams) {
        List<String> names = new ArrayList<>();
        for (Team team : teams)
            if (team != null) names.add(team.getTeamFullName());
        return names;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // decrease one from position because the header is also counted
        if (getItemViewType(position) == PLAYERS) {
            boolean isSelected = selectedPosition == holder.getAdapterPosition();

            ((ViewHolder) holder).setItemSelected(isSelected);
            ((ViewHolder) holder).moreDetails.setExpanded(isSelected, false);

            ((ViewHolder) holder).playerName.setText(players.get(position - 1).getFullName());
            ((ViewHolder) holder).playerRank.setText(String.format(Locale.US, "%d", players.get(position - 1).getPlayerRank()));
            ((ViewHolder) holder).playerGoals.setText(String.format(Locale.US, "Goals: %d" , players.get(position - 1).getGoalsInThisSession()));
            Log.e("TeamPosition" , "" + (position-1));

            if (players.get(position - 1).getCurrentTeam() != null)
                Glide.with(((ViewHolder) holder).playerCurrentTeam).load(
                        players.get(position - 1).getCurrentTeam().getTeamLogo()
                ).apply(RequestOptions.circleCropTransform()).into(((ViewHolder) holder).playerCurrentTeam);

            ((ViewHolder) holder).moreDetails.setOnExpansionUpdateListener((expansionFraction, state) -> {
                if (state == ExpandableLayout.State.EXPANDED) {
                    App.getInstance().getApiBridge().getPlayerDetail(players.get(position - 1), player -> {
                        ((ViewHolder) holder).playerNumberContent.setText(String.format(Locale.US, "%d", player.getNumber()));
                        ((ViewHolder) holder).playerPosition.setText(player.getPlayerPosition());
                        ((ViewHolder) holder).previousTeamsRecycler.setLayoutManager(new LinearLayoutManager(
                                ((ViewHolder) holder).previousTeamsRecycler.getContext(),
                                RecyclerView.HORIZONTAL,
                                false
                        ));

                        if (!player.getPreviousTeams().isEmpty())
                            ((ViewHolder) holder).previousTeamsRecycler.setAdapter(
                                    new TagsChipRecyclerAdapter(filterOutStringFromTeams(player.getPreviousTeams()))
                            );
                        else
                            ((ViewHolder) holder).playerPreviousTeamsTitle.setVisibility(View.GONE);
                    }, error -> {

                    });
                }
            });

            new Thread(() -> {
                try {

                    Drawable drawable = Glide.with(holder.itemView).asDrawable().load(
                            "https://www.countryflags.io/" + players.get(position - 1)
                                    .getAlpha2CountryCode(((ViewHolder) holder)
                                            .playerCountry.getContext())
                                    + "/flat/64.png")
                            .submit(40,40).get();

                    ((ViewHolder) holder).playerCountry.post(() ->
                            ((ViewHolder) holder).playerName
                                    .setCompoundDrawablesWithIntrinsicBounds(
                                            null, null,
                                            drawable, null));

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            if (onItemActionListener != null) {
                holder.itemView.setOnLongClickListener(view -> onItemActionListener.onItemLongClicked(view, holder.getAdapterPosition()));
            }

        }
    }

    @Override
    public int getItemCount() {
        //we should count the header too
        return players.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatTextView playerRank, playerName, playerGoals, playerNumberContent, playerPosition, playerPreviousTeamsTitle;
        AppCompatImageView playerCurrentTeam, playerCountry;
        ExpandableLayout moreDetails;
        RecyclerView previousTeamsRecycler;
        FrameLayout selectedIndicator;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            playerRank = itemView.findViewById(R.id.player_rank);
            playerName = itemView.findViewById(R.id.player_name);
            playerGoals = itemView.findViewById(R.id.player_goals);
            playerCurrentTeam = itemView.findViewById(R.id.player_team_icon);
            playerCountry = itemView.findViewById(R.id.player_country_flag);
            moreDetails = itemView.findViewById(R.id.main_expandable_layout);
            playerNumberContent = itemView.findViewById(R.id.playing_as_number_content);
            playerPosition = itemView.findViewById(R.id.player_position_content);
            previousTeamsRecycler = itemView.findViewById(R.id.player_previous_teams_content);
            playerPreviousTeamsTitle = itemView.findViewById(R.id.player_previous_teams_title);
            selectedIndicator = itemView.findViewById(R.id.selected_indicator);

            itemView.setOnClickListener(this);
        }

        void setItemSelected(boolean selected) {
            selectedIndicator.setVisibility(selected ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedPosition);
            if (holder != null) {
                holder.moreDetails.collapse();
                holder.setItemSelected(false);
            }

            int position = getAdapterPosition();
            if (position == selectedPosition) {
                selectedPosition = UNSELECTED;
            } else {
                moreDetails.expand();
                selectedPosition = position;
                setItemSelected(true);
            }

            onItemActionListener.onItemClicked(v, getAdapterPosition());
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
