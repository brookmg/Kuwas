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

package app.kuwas.android.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import app.kuwas.android.App;
import app.kuwas.android.R;
import app.kuwas.android.bridge.data.NewsItem;
import app.kuwas.android.ui.adapters.TagsChipRecyclerAdapter;
import app.kuwas.android.utils.Constants;
import app.kuwas.android.utils.GlideImageGetter;

import static app.kuwas.android.utils.Utils.dpToPx;
import static app.kuwas.android.utils.Utils.getTimeGap;

/**
 * Created by BrookMG on 4/9/2019 in app.kuwas.android.ui.fragments
 * inside the project Kuwas .
 */
public class NewsPreviewFragment extends BaseFragment {

    private AppCompatImageView newsImage;
    private RecyclerView newsTagsHolder;
    private AppCompatTextView newsContent, newsTitle, newsDateTimePlusAuthor;
    private NewsItem item;
    private String transitionName;

    public static NewsPreviewFragment newInstance() {
        return newInstance(new Bundle());
    }

    public static NewsPreviewFragment newInstance(Bundle bundle) {
        NewsPreviewFragment fragment = new NewsPreviewFragment();
        fragment.setArguments(bundle);
        fragment.setNewsItem((NewsItem) bundle.getSerializable("news"));
        fragment.setNewsTransitionName(bundle.getString("news_transition"));
        return fragment;
    }

    private void setNewsTransitionName(String transitionName) {
        this.transitionName = transitionName;
    }

    private void setNewsItem(NewsItem item) {
        this.item = item;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    private void handleTopMarginOnFAB(FloatingActionButton button) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // the sdk is greater than lollipop; the app is being drawn under the status bar
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
            marginParams.setMargins(getActivity() != null ? dpToPx(getActivity(), 8) : 0, getActivity() != null ? dpToPx(getActivity(), 32) : 0, 0, 0);
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        if (item != null && getActivity() != null) {
            Glide.with(newsImage).load(item.getNewsImage()).into(newsImage);
            newsContent.setText(item.getNewsContent());

            newsTagsHolder.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL , false));
            newsTagsHolder.setAdapter(new TagsChipRecyclerAdapter(item.getNewsTags()));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                newsContent.setText(
                        Html.fromHtml(item.getNewsContent() ,
                                Html.FROM_HTML_OPTION_USE_CSS_COLORS,
                                new GlideImageGetter(newsContent), null)
                );
            } else {
                newsContent.setText(
                        Html.fromHtml(item.getNewsContent() , new GlideImageGetter(newsContent), null)
                );
            }
            newsTitle.setText(item.getNewsTitle());

            newsDateTimePlusAuthor.setText(String.format("%s by %s", getTimeGap(item.getNewsPublishedOn().getTime()), item.getNewsAuthorName()));
            startPostponedEnterTransition();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.setTransitionName(newsImage, transitionName);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.news_preview_fragment, container, false);
        newsImage = mainView.findViewById(R.id.news_image);
        newsContent = mainView.findViewById(R.id.news_content);
        newsTagsHolder = mainView.findViewById(R.id.news_tags_holder);
        newsTitle = mainView.findViewById(R.id.news_title);
        newsDateTimePlusAuthor = mainView.findViewById(R.id.news_date_time_plus_author);
        handleTopMarginOnFAB(mainView.findViewById(R.id.back_button));

        if (App.getInstance().getRemoteConfig().getBoolean(Constants.FRC_SHOW_ADS)) {
            AdView banner = mainView.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("2F3A40AA575193DE16F63722988863C8").build();
            banner.loadAd(adRequest);
        }

        mainView.findViewById(R.id.back_button).setOnClickListener(v -> getActivity().onBackPressed());

        return mainView;
    }
}
