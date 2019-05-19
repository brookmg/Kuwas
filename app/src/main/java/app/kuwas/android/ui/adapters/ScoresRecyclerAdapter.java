package app.kuwas.android.ui.adapters;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.kuwas.android.R;
import io.brookmg.soccerethiopiaapi.data.LeagueItemStatus;
import io.brookmg.soccerethiopiaapi.data.LeagueScheduleItem;
import io.brookmg.soccerethiopiaapi.data.NewsItem;
import io.brookmg.soccerethiopiaapi.data.Team;

/**
 * Created by BrookMG on 5/5/2019 in app.kuwas.android.ui.adapters
 * inside the project Kuwas .
 */
public class ScoresRecyclerAdapter extends RecyclerView.Adapter<ScoresRecyclerAdapter.ViewHolder> {

    private List<LeagueScheduleItem> leagueScheduleItems;

    public ScoresRecyclerAdapter(List<LeagueScheduleItem> leagueScheduleItems) {
        this.leagueScheduleItems = leagueScheduleItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.score_element, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<Team, Integer> team_result = leagueScheduleItems.get(position).getGameDetail();
        holder.datetime.setText(leagueScheduleItems.get(position).getGameDate());

        holder.team_1_name.setText(team_result.keySet().toArray(new Team[0])[0].getTeamFullName());
        holder.team_2_name.setText(team_result.keySet().toArray(new Team[0])[1].getTeamFullName());

        if (leagueScheduleItems.get(holder.getAdapterPosition()).getGameStatus() == LeagueItemStatus.STATUS_TOOK_PLACE) {
            holder.team_1_score.setText(String.format(Locale.US, "%d", team_result.get(team_result.keySet().toArray(new Team[0])[0])));
            holder.team_2_score.setText(String.format(Locale.US, "%d", team_result.get(team_result.keySet().toArray(new Team[0])[1])));
        } else {
            holder.team_1_score.setText("-");
            holder.team_2_score.setText("-");
        }

        Glide.with(holder.team_1_image).load(team_result.keySet().toArray(new Team[0])[0].getTeamLogo()).into(holder.team_1_image);
        Glide.with(holder.team_2_image).load(team_result.keySet().toArray(new Team[0])[1].getTeamLogo()).into(holder.team_2_image);
    }

    @Override
    public int getItemCount() {
        return leagueScheduleItems.size();
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

}
