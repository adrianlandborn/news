package se.landborn.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Adrian on 11/2/2016.
 */

public class NewsItemActivity extends AppCompatActivity {

    public final static String TITLE = "title";
    public final static String CONTENT = "content";
    public final static String URL = "url";

    private String mTitle;
    private String mContent;
    private String mImageUrl;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_news);

        ImageView imageView = (ImageView) findViewById(R.id.item_image);
        TextView titleView = (TextView) findViewById(R.id.item_title);
        TextView contentView = (TextView) findViewById(R.id.item_content);

        Bundle bundle = getIntent().getExtras();

        mTitle = bundle.getString(TITLE);
        mContent = bundle.getString(CONTENT);
        mImageUrl = bundle.getString(URL);

        titleView.setText(mTitle);
        contentView.setText(mContent);
        Picasso.with(this).load(mImageUrl).into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.search:
                searchItem();
                return true;
            case R.id.share:
                shareItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void searchItem() {
        Uri uri = Uri.parse("http://www.google.com/#q=" + mTitle);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void shareItem() {
        String message = "Hey! You have to read this!\n\n" +
                mTitle + " \n\n" +
                mContent + " \n\n" +
                mImageUrl;
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Share..."));
    }
}
