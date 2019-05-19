package app.kuwas.android.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.kuwas.android.R;
import io.brookmg.soccerethiopiaapi.data.LeagueItemStatus;
import io.brookmg.soccerethiopiaapi.data.LeagueScheduleItem;
import io.brookmg.soccerethiopiaapi.data.Team;

/**
 * Created by BrookMG on 5/5/2019 in app.kuwas.android.ui.adapters
 * inside the project Kuwas .
 */
public class ScoresRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LeagueScheduleItem> leagueScheduleItems;

    private final int HEADER = 0;
    private final int SCORE = 1;

    public ScoresRecyclerAdapter(List<LeagueScheduleItem> leagueScheduleItems) {
        Collections.reverse(leagueScheduleItems);
        this.leagueScheduleItems = leagueScheduleItems;
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

            ((ViewHolder) holder).team_1_name.setText(team_result.keySet().toArray(new Team[0])[0].getTeamFullName());
            ((ViewHolder) holder).team_2_name.setText(team_result.keySet().toArray(new Team[0])[1].getTeamFullName());

            if (leagueScheduleItems.get(holder.getAdapterPosition() -1).getGameStatus() == LeagueItemStatus.STATUS_TOOK_PLACE) {
                ((ViewHolder) holder).team_1_score.setText(String.format(Locale.US, "%d", team_result.get(team_result.keySet().toArray(new Team[0])[0])));
                ((ViewHolder) holder).team_2_score.setText(String.format(Locale.US, "%d", team_result.get(team_result.keySet().toArray(new Team[0])[1])));
            } else {
                ((ViewHolder) holder).team_1_score.setText("-");
                ((ViewHolder) holder).team_2_score.setText("-");
            }

            Glide.with(((ViewHolder) holder).team_1_image).load(team_result.keySet().toArray(new Team[0])[0].getTeamLogo()).into(((ViewHolder) holder).team_1_image);
            Glide.with(((ViewHolder) holder).team_2_image).load(team_result.keySet().toArray(new Team[0])[1].getTeamLogo()).into(((ViewHolder) holder).team_2_image);
        }
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
        AppCompatTextView team_1_name, team_1_score;
        AppCompatTextView team_2_name, team_2_score;
        AppCompatTextView datetime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            team_1_image = itemView.findViewById(R.id.team_1_image);
            team_2_image = itemView.findViewById(R.id.team_2_image);

            team_1_score = itemView.findViewById(R.id.team_1_score);
            team_2_score = itemView.findViewById(R.id.team_2_score);

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
