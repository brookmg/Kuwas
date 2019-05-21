package app.kuwas.android.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.ViewUtils;
import androidx.coordinatorlayout.widget.ViewGroupUtils;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;

import app.kuwas.android.App;
import app.kuwas.android.R;
import app.kuwas.android.ui.adapters.TagsChipRecyclerAdapter;
import io.brookmg.soccerethiopiaapi.data.NewsItem;

import static app.kuwas.android.utils.Utils.dpToPx;
import static app.kuwas.android.utils.Utils.setMargins;

/**
 * Created by BrookMG on 4/9/2019 in app.kuwas.android.ui.fragments
 * inside the project Kuwas .
 */
public class NewsPreviewFragment extends BaseFragment {

    private AppCompatImageView newsImage;
    private RecyclerView newsTagsHolder;
    private AppCompatTextView newsContent;
    private NewsItem item;

    public static NewsPreviewFragment newInstance() {
        return newInstance(new Bundle());
    }

    public static NewsPreviewFragment newInstance(Bundle bundle) {
        NewsPreviewFragment fragment = new NewsPreviewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        item = (NewsItem) getArguments().getSerializable("news");
        refresh();
    }

    @Override
    public void refresh() {
        super.refresh();
        if (item != null && getActivity() != null) {
            Glide.with(newsImage).load(item.getNewsImage()).into(newsImage);
            newsContent.setText(item.getNewsContent());

            newsTagsHolder.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL , false));
            newsTagsHolder.setAdapter(new TagsChipRecyclerAdapter(item.getNewsTags()));

            App.getInstance().getApi().getNewsItemContent(item,
                    newItem -> newsContent.setText(newItem.getNewsContent()),
                    error -> Log.v("NewsItem" , error != null ? error : "Unknown"));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.news_preview_fragment, container, false);
        newsImage = mainView.findViewById(R.id.news_image);
        newsContent = mainView.findViewById(R.id.news_content);
        newsTagsHolder = mainView.findViewById(R.id.news_tags_holder);

        return mainView;
    }
}
