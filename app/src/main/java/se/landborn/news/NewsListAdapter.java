package se.landborn.news;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Adrian on 11/2/2016.
 */

public class NewsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<NewsListItem> mItems;

    public NewsListAdapter(Context context, List<NewsListItem> items) {
        mContext = context;
        mItems = items;
    }
    @Override
    public int getCount() {
        return mItems == null? 0 : mItems.size();
    }

    @Override
    public NewsListItem getItem(int position) {
        return  mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = View.inflate(mContext, R.layout.news_row, null);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
        TextView textView = (TextView) view.findViewById(R.id.item_title);

        NewsListItem item = mItems.get(position);

        Picasso.with(mContext).load(item.getImageUrl()).into(imageView);
        textView.setText(item.getTitle());

        return view;
    }

    public void setItems(List<NewsListItem> items) {
        mItems = items;
    }
}
