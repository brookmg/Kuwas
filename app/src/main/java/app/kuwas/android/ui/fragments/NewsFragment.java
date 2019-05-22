package app.kuwas.android.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import app.kuwas.android.App;
import app.kuwas.android.R;
import app.kuwas.android.ui.activities.MainActivity;
import app.kuwas.android.ui.adapters.NewsRecyclerAdapter;
import app.kuwas.android.ui.adapters.OnItemActionListener;
import app.kuwas.android.utils.Constants;
import app.kuwas.android.utils.FabStates;

/**
 * Created by BrookMG on 4/9/2019 in app.kuwas.android.ui.fragments
 * inside the project Kuwas .
 */
public class NewsFragment extends BaseFragment {

    private RecyclerView mainRecycler;

    static NewsFragment newInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void refresh() {
        super.refresh();
        if (mainRecycler != null) {
            App.getInstance().getApi().getLatestNews(
                    news -> {
                        mainRecycler.setLayoutManager(new LinearLayoutManager(getActivity() , RecyclerView.VERTICAL, false));
                        NewsRecyclerAdapter adapter = new NewsRecyclerAdapter(news, new OnItemActionListener() {
                            @Override
                            public void onItemClicked(View view, int position) {
                                Bundle newsArgs = new Bundle();
                                newsArgs.putSerializable("news", news.get(position));

                                ViewCompat.setTransitionName(view, "news_item_" + position);
                                newsArgs.putString("news_transition" , ViewCompat.getTransitionName(view));
                                NewsFragment.this.setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.default_transition));

                                if (Build.VERSION.SDK_INT >= 21)
                                    NewsFragment.this.setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.no_transition));

                                changeFragment(Constants.TAG_NEWS_PREVIEW, newsArgs, view);
                            }

                            @Override
                            public void onItemLongClicked(View view, int position) {

                            }
                        });
                        mainRecycler.setAdapter(adapter);
                    },
                    error -> Log.e("NewsFragment:", error != null ? error : "Unknown")
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.news_fragment, container, false);
        mainRecycler = mainView.findViewById(R.id.mainNewsRecyclerView);

        refresh();

        mainRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (getActivity() instanceof MainActivity) {
                    if (dy < 0) ((MainActivity) getActivity()).changeFabState(FabStates.STATE_EXPAND);    //scrolling upward so expand fab
                    else if (dy > 0) ((MainActivity) getActivity()).changeFabState(FabStates.STATE_COLLAPSE); //scrolling downward so shrink fab
                }
            }
        });

        return mainView;
    }
}
