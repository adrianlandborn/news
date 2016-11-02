package se.landborn.news;

import java.io.Serializable;

/**
 * Created by Adrian on 11/2/2016.
 */

public class NewsListItem implements Serializable {

    private String mTitle;
    private String mContent;
    private String mImageUrl;


    public NewsListItem(String title, String content, String imageUrl) {
        mTitle = title;
        mContent = content;
        mImageUrl = imageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
