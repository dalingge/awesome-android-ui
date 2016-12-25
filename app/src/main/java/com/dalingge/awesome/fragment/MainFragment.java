package com.dalingge.awesome.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dalingge.awesome.R;
import com.dalingge.awesome.widget.SearchView;
import com.dalingge.awesome.widget.ShareButtonView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.search_view)
    SearchView searchView;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    int count = 1;


    @OnClick({R.id.share_button, R.id.start, R.id.reset})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_button:
                if (count % 2 == 0)
                    ((ShareButtonView) v).reset();
                else
                    ((ShareButtonView) v).startAnimation();
                count++;
                break;
            case R.id.start:
                searchView.startAnim();

                break;
            case R.id.reset:
                searchView.resetAnim();
                break;
        }
    }



}
