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

package app.kuwas.android.ui.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Locale;

import app.kuwas.android.R;
import io.brookmg.soccerethiopiaapi.data.RankItem;
import io.brookmg.soccerethiopiaapi.data.Team;

import static app.kuwas.android.utils.Utils.dpToPx;
import static app.kuwas.android.utils.Utils.getCurrentTheme;

/**
 * Created by BrookMG on 5/19/2019 in app.kuwas.android.ui.widgets
 * inside the project Kuwas .
 */
public class StandingTable extends TableLayout {

    private interface ApplyProperty {
        void apply(View item);
    }

    public interface OnTableRowClicked {
        void onClick(Team team);
    }

    OnTableRowClicked onTableRowClickedCallback;

    public StandingTable(Context context) {
        super(context);
    }

    public StandingTable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void populateTable(List<RankItem> ranks) {
        setStretchAllColumns(true);
        bringToFront();
        addRows(null, generateTableRow(0, null));
        for (RankItem rank : ranks) addRows(rank, generateTableRow(1, rank));
    }

    public void setOnTableRowClickedCallback(OnTableRowClicked onTableRowClicked) {
        this.onTableRowClickedCallback = onTableRowClicked;
    }

    private void addRows(RankItem rankItem, TableRow ...rows) {
        for (TableRow row : rows) {
            row.setOnClickListener(v -> {
                if (onTableRowClickedCallback != null && rankItem != null)
                    onTableRowClickedCallback.onClick(rankItem.getTeam());
            });
            addView(row);
        }
    }

    private void addMultipleViews(ViewGroup viewGroup, View... items){
        for (View item : items) viewGroup.addView(item);
    }

    private void applyToAllViews(ApplyProperty property, AppCompatTextView ...onto) {
        for(AppCompatTextView textview: onto) {
            property.apply(textview);
        }
    }

    public void clearTable() {
        removeAllViews();
    }

    private TableRow generateTableRow (int position, RankItem item) {
        String[] headers = {"Pos" , "" , "Club", "P", "GD", "Point"};

        if (position == 0) {
            TableRow headerRow = new TableRow(getContext());
            for (String header : headers) {
                AppCompatTextView textView = new AppCompatTextView(getContext());
                applyToAllViews((textview) -> {
                    ((AppCompatTextView) textview).setGravity(Gravity.CENTER);
                    ((AppCompatTextView) textview).setTextColor(ContextCompat.getColor(getContext(), getCurrentTheme(getContext()) == 0 ? R.color.black_0 : R.color.white_0));
                    ((AppCompatTextView) textview).setTypeface(null, Typeface.BOLD);
                    textview.setPadding(dpToPx(getContext(), 16) , dpToPx(getContext(), 12),
                            dpToPx(getContext(), 16), dpToPx(getContext(), 12));
                }, textView);
                textView.setText(header);
                headerRow.addView(textView);
            }
            headerRow.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey_1x));
            return headerRow;
        } else {
            TableRow itemRow = new TableRow(getContext());
            AppCompatTextView rank = new AppCompatTextView(getContext());
            AppCompatTextView club = new AppCompatTextView(getContext());
            AppCompatTextView played = new AppCompatTextView(getContext());
            AppCompatTextView goaldiff = new AppCompatTextView(getContext());
            AppCompatTextView point = new AppCompatTextView(getContext());

            rank.setText(String.format(Locale.US,"%d", item.getRank()));
            played.setText(String.format(Locale.US,"%d", item.getPlayedGames()));
            goaldiff.setText(String.format(Locale.US,"%d", item.getGoalDifference()));
            point.setText(String.format(Locale.US,"%d", item.getPoints()));
            club.setText(item.getTeam().getTeamFullName());

            applyToAllViews(
                    textview -> {
                        ((AppCompatTextView) textview).setGravity(Gravity.CENTER);
                        ((AppCompatTextView) textview).setTextColor(
                                getCurrentTheme(getContext()) == 0 ?
                                        ContextCompat.getColor(getContext(), R.color.black_0):
                                        ContextCompat.getColor(getContext(), R.color.white_0)
                        );
                        textview.setPadding(dpToPx(getContext(), 16) , dpToPx(getContext(), 16),
                                dpToPx(getContext(), 16), dpToPx(getContext(), 16));
                    },
                    rank, club, played, goaldiff, point);

            AppCompatImageView clubpic = new AppCompatImageView(getContext());
            Glide.with(clubpic).load(item.getTeam().getTeamLogo()).apply(RequestOptions.circleCropTransform()).into(clubpic);

            addMultipleViews(itemRow, rank, clubpic, club, played, goaldiff, point);
            TableRow.LayoutParams layoutParams = (TableRow.LayoutParams) clubpic.getLayoutParams();
            layoutParams.width = LayoutParams.MATCH_PARENT;
            layoutParams.height = LayoutParams.MATCH_PARENT;

           // clubpic.setLayoutParams(layoutParams);

            return itemRow;
        }
    }
}
