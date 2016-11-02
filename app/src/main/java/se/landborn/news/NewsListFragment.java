package se.landborn.news;

import android.content.Context;
import android.net.ConnectivityManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 11/2/2016.
 */

public class NewsListFragment extends ListFragment implements AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static String TAG = NewsListFragment.class.getSimpleName();

    private MainActivity mActivity;
    private View mProgressView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsListAdapter mAdapter;

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
            updateViews(null);
            showProgress(false);

        }

    }

    private void loadData() {
        if (!hasNetworkConnection()) {
            //TODO Show No network view
            Toast.makeText(mActivity, "Please check your connection", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO Extract task to own class
        // TODO Retain task during rotation
        new AsyncTask<Void, Void, List<NewsListItem>>() {

            @Override
            protected List<NewsListItem> doInBackground(Void... params) {

                try {
                    URL url = new URL("https://dl.dropboxusercontent.com/u/277040683/payload.json");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder builder = new StringBuilder();

                    String inputString;
                    while ((inputString = reader.readLine()) != null) {
                        builder.append(inputString);
                    }

                    JSONArray jsonArray = new JSONArray(builder.toString());

                    JSONObject object;

                    List<NewsListItem> items = new ArrayList<>();
                    String title;
                    String content;
                    String imageUrl;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        object = jsonArray.getJSONObject(i);

                        title = object.getString("news_title");
                        content = object.getString("news_content");
                        imageUrl = object.getString("news_image");
                        items.add(new NewsListItem(title, content, imageUrl));
                    }

                    urlConnection.disconnect();
                    return items;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<NewsListItem> items) {
                updateViews(items);
            }
        }.execute();
    }

    private void updateViews(List<NewsListItem> items) {
        if (mActivity != null && !mActivity.isFinishing()) {

            if (items != null) {
                if (mAdapter != null) {
                    mAdapter.setItems(items);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter = new NewsListAdapter(mActivity, items);
                    setListAdapter(mAdapter);
                }
            } else {
                Toast.makeText(mActivity, "Could not fetch data", Toast.LENGTH_SHORT).show();
            }
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

    private boolean hasNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
