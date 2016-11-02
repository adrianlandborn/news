package se.landborn.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by Adrian on 11/2/2016.
 */

public class NewsListFragment extends ListFragment implements AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static String TAG = NewsListFragment.class.getSimpleName();

    private MainActivity mActivity;
    private View mProgressView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_list_frame, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (MainActivity) getActivity();
        mSwipeRefreshLayout = (SwipeRefreshLayout) mActivity.findViewById(R.id.swipe_to_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mProgressView = mActivity.findViewById(R.id.progress_container_id);

        showProgress(true);

        if (savedInstanceState == null) {
            // Fetch data from server
            loadData();
        } else {
            // TODO Reload data locally
            updateViews();
            showProgress(false);

        }

    }

    private void loadData() {
        // TODO Load data from server

        new AsyncTask<Object, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Object... params) {

                try {
                    // TODO implement real stuff
                    Thread.sleep(3000);
                    return true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                updateViews();
            }
        }.execute();
    }

    private void updateViews() {
        if (mActivity != null && !mActivity.isFinishing()) {
            // TODO Use the data to update views and adapter

            Toast.makeText(mActivity, "Views updates", Toast.LENGTH_SHORT).show();

            showProgress(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Handle item clicks
    }

    @Override
    public void onRefresh() {
        mProgressView.setVisibility(View.INVISIBLE);
        loadData();
    }

    protected void showProgress(boolean visible) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (visible) {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mProgressView.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                mProgressView.setVisibility(View.INVISIBLE);
            }
        }
    }
}
