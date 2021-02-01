package com.vogella.android.newsapp;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.AsyncTaskLoader;
import android.net.Uri;
import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>>  {


    //Fields
    String url;
    List<News> news;


    // Query Parameter
    private static final String QUERY_PARAM = "q";
    private static final String SHOW_TAGS= "show-tags";
    private static final String SHOW_FIELDS= "show-fields";
    private static final String API_KEY="api-key";
    private static String query_param_value = null;



    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        this.url = url;
    }



    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    @Nullable
    @Override
    public List<News> loadInBackground() {

        //Checking for empty url
        if(this.url.isEmpty())
        {

            //News is empty because I did not initialize it.
            return news;
        }

        else
        {






            String newsJSONData = NetworkUtils.getNewsData(this.url);

            news = QueryUtils.extractFeatureFromJson(newsJSONData);

            return news;
        }



    }

    @Override
    protected void onReset() {
        super.onReset();
    }



    };

