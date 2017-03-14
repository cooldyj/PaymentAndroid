package com.jerry.payment.mobile.module.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.utils.IntentUtils;
import com.jerry.payment.mobile.widget.ListItemFragment;

/**
 * Discover Main Page
 * Created by jerry on 2016/7/5.
 */
public class DiscoverFragment extends ListItemFragment {

    public static DiscoverFragment newInstance() {
        Bundle args = new Bundle();
        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
            initItems();
        }
        return rootView;
    }

    private void initItems(){
        addItem(R.drawable.discover_help, getString(R.string.discover_help), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext, HelpActivity.class);
            }
        });
        addItem(R.drawable.discover_feedback, getString(R.string.discover_feedback), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext, FeedBackActivity.class);
            }
        });
        addItem(R.drawable.discover_about_us, getString(R.string.discover_about_us), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext, AboutUsActivity.class);
            }
        });
    }

}
