package app.kuwas.android.ui.adapters;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import app.kuwas.android.R;
import io.brookmg.soccerethiopiaapi.data.NewsItem;

/**
 * Created by BrookMG on 5/5/2019 in app.kuwas.android.ui.adapters
 * inside the project Kuwas .
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    private List<NewsItem> news;

    public NewsRecyclerAdapter(List<NewsItem> news) {
        this.news = news;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_element, parent, false));
    }

    private static String _getTimeGap(long fromTimeMil) {

        long diff = (new Date().getTime()) - fromTimeMil;

        if (diff < DateUtils.MINUTE_IN_MILLIS)
        { return "just now"; }
        else if (diff < DateUtils.HOUR_IN_MILLIS)
        { return Math.round((float) diff/DateUtils.MINUTE_IN_MILLIS) + "m ago"; }
        else if (diff < DateUtils.DAY_IN_MILLIS)
        { return Math.round((float) diff/DateUtils.HOUR_IN_MILLIS) + "h ago"; }
        else if (diff < DateUtils.WEEK_IN_MILLIS)
        { return Math.round((float) diff/DateUtils.DAY_IN_MILLIS) + "d ago"; }
        else if (diff < DateUtils.YEAR_IN_MILLIS)
        { return Math.round((float) diff/DateUtils.WEEK_IN_MILLIS) + "w ago"; }
        else
        { return Math.round((float) diff/DateUtils.YEAR_IN_MILLIS) + "y ago"; }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.news_title.setText(news.get(position).getNewsTitle());
        holder.news_author.setText(news.get(position).getNewsAuthorName());
        holder.news_published.setText(_getTimeGap(news.get(position).getNewsPublishedOn().getTime()));
        Glide.with(holder.news_image).load(news.get(position).getNewsImage()).into(holder.news_image);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView news_image;
        AppCompatTextView news_title, news_author, news_published;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            news_image = itemView.findViewById(R.id.news_image);
            news_author = itemView.findViewById(R.id.news_author);
            news_published = itemView.findViewById(R.id.news_published);
            news_title = itemView.findViewById(R.id.news_title);
        }
    }

}
