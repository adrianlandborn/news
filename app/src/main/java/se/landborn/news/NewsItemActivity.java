package se.landborn.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news);

        ImageView imageView = (ImageView) findViewById(R.id.item_image);
        TextView titleView = (TextView) findViewById(R.id.item_title);
        TextView contentView = (TextView) findViewById(R.id.item_content);

        Bundle bundle = getIntent().getExtras();

        titleView.setText(bundle.getString(TITLE));
        contentView.setText(bundle.getString(CONTENT));
        Picasso.with(this).load(bundle.getString(URL)).into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
