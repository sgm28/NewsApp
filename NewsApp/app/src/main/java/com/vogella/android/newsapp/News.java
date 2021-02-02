package com.vogella.android.newsapp;

import android.graphics.Bitmap;

public class News {


    private String title;
    private String section;
    private String author;
    private String date;
    private Bitmap imageBitmap;
    private String webUrl;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    /**
     * @param title   the tile of the news article
     * @param section the section such as potics, technology, etc
     * @param author  The author of the tile
     * @param date    The date of the news article
     * @param image   The url of the image to be downloaded
     * @param webUrl  The url of the news article
     */

    public News(String title, String section, String author, String date, Bitmap image, String webUrl) {
        this.title = title;
        this.section = section;
        this.author = author;
        this.date = date;
        this.imageBitmap = image;

        this.webUrl = webUrl;
    }

    /**
     * @return tile
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return return section category
     */
    public String getSection() {
        return section;
    }

    /**
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return ImageView
     */
    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    /**
     * @return Date
     */
    public String getDate() {
        return date;
    }


}
