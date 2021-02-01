package com.vogella.android.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
                                                                                                         //Implementing the  OnNewsClick
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>, NewsAdapter.OnNewsListener, SearchView.OnQueryTextListener {
//changing the save date
                                                                                                             RecyclerView rvNews;
                                                                                                             NewsAdapter newsAdapter;
                                                                                                             List<News> data;
                                                                                                             TextView emptyView;
                                                                                                             //Endpoint for News
                                                                                                             private static String NEWS_BASE_URL = "https://content.guardianapis.com/search?";
                                                                                                             // Query Parameter
                                                                                                             private static final String QUERY_PARAM = "q";
                                                                                                             private static final String SHOW_TAGS = "show-tags";
                                                                                                             private static final String SHOW_FIELDS = "show-fields";
                                                                                                             private static final String API_KEY = "api-key";
                                                                                                             private static String query_param_value = null;

                                                                                                             @Override
                                                                                                             protected void onCreate(Bundle savedInstanceState) {
                                                                                                                 super.onCreate(savedInstanceState);
                                                                                                                 setContentView(R.layout.recycler_view_layout);

                                                                                                                 emptyView = (TextView) findViewById(R.id.empty_view);
                                                                                                                 emptyView.setVisibility(View.GONE);
                                                                                                                 rvNews = (RecyclerView) findViewById(R.id.rvNews);
                                                                                                                 newsAdapter = new NewsAdapter(new ArrayList<News>(), this);
                                                                                                                 rvNews.setAdapter(newsAdapter);
                                                                                                                 rvNews.setLayoutManager(new LinearLayoutManager(this));


//Checking the internet status
                                                                                                                 ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                                                                                                                 NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                                                                                                                 boolean isConnected = activeNetwork != null &&
                                                                                                                         activeNetwork.isConnectedOrConnecting();

                                                                                                                 if (isConnected) {
                                                                                                                     LoaderManager loaderManager = getLoaderManager();

                                                                                                                     loaderManager.initLoader(0, null, this);

                                                                                                                 } else {
                                                                                                                     ProgressBar progressBar = findViewById(R.id.indeterminateBar);
                                                                                                                     progressBar.setVisibility(View.GONE);
                                                                                                                     rvNews.setVisibility(View.GONE);
                                                                                                                     emptyView.setVisibility(View.VISIBLE);
                                                                                                                     emptyView.setText("No internet connection");

                                                                                                                 }


                                                                                                             }


                                                                                                             //Loader Stuff
                                                                                                             @Override
                                                                                                             public Loader<List<News>> onCreateLoader(int id, Bundle args) {
                                                                                                                 Uri builtURI;
//Building the
                                                                                                                 if (query_param_value == null)
                                                                                                                 {
                                                                                                                      builtURI = Uri.parse(NEWS_BASE_URL).buildUpon()
                                                                                                                            // .appendQueryParameter(QUERY_PARAM, query_param_value)
                                                                                                                             .appendQueryParameter(SHOW_TAGS, "contributor")
                                                                                                                             .appendQueryParameter(SHOW_FIELDS, "thumbnail")
                                                                                                                             .appendQueryParameter(API_KEY, "test")
                                                                                                                             .build();
                                                                                                                 }

                                                                                                                 else
                                                                                                                 {
                                                                                                                      builtURI = Uri.parse(NEWS_BASE_URL).buildUpon()
                                                                                                                              .appendQueryParameter(QUERY_PARAM, query_param_value)
                                                                                                                             .appendQueryParameter(SHOW_TAGS, "contributor")
                                                                                                                             .appendQueryParameter(SHOW_FIELDS, "thumbnail")
                                                                                                                             .appendQueryParameter(API_KEY, "test")
                                                                                                                             .build();
                                                                                                                 }



                                                                                                                 return new NewsLoader(this, builtURI.toString());
                                                                                                             }

                                                                                                             @Override
                                                                                                             public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
//Setting the progress bar to invisible after data loaded
                                                                                                                 ProgressBar progressBar = findViewById(R.id.indeterminateBar);
                                                                                                                 progressBar.setVisibility(View.GONE);

//TESTING PURPOSES - testing if my text view will display if the list data is empty
// data.clear();


//Deciding which view to display based on whether or not the data is empty
                                                                                                                 if (data.isEmpty()) {
                                                                                                                     rvNews.setVisibility(View.GONE);
                                                                                                                     emptyView.setVisibility(View.VISIBLE);
                                                                                                                     emptyView.setText("No News to display");

                                                                                                                 } else {

//Update the UI

                                                                                                                     newsAdapter.setData(data);
                                                                                                                     newsAdapter.notifyDataSetChanged();
                                                                                                                     this.data = data;

                                                                                                                 }


                                                                                                             }

                                                                                                             @Override
                                                                                                             public void onLoaderReset(Loader<List<News>> loader) {
//Clean up
                                                                                                             }

                                                                                                             @Override
                                                                                                             public void OnNewsClick(int position) {

                                                                                                                 String url = data.get(position).getWebUrl();


                                                                                                                 Intent i = new Intent(Intent.ACTION_VIEW);
                                                                                                                 i.setData(Uri.parse(url));
                                                                                                                 startActivity(i);
                                                                                                             }

                                                                                                             @Override
                                                                                                             public boolean onCreateOptionsMenu(Menu menu) {
                                                                                                                 getMenuInflater().inflate(R.menu.main, menu);
                                                                                                                 MenuItem item = menu.findItem(R.id.search_view);
                                                                                                                 SearchView searchView = (SearchView) item.getActionView();
                                                                                                                 searchView.setOnQueryTextListener(this);
                                                                                                                 return true;
                                                                                                             }

                                                                                                             @Override
                                                                                                             public boolean onQueryTextSubmit(String query) {
                                                                                                                 query_param_value=query;
                                                                                                                Log.d("Main activity", query_param_value);
                                                                                                                 getLoaderManager().restartLoader(0, null, this);
                                                                                                                 return true;
                                                                                                             }

                                                                                                             @Override
                                                                                                             public boolean onQueryTextChange(String newText) {

                                                                                                                 query_param_value=newText;
                                                                                                                 Log.d("Main activity", query_param_value);
                                                                                                                 getLoaderManager().restartLoader(0, null, this);

                                                                                                                 return true;

                                                                                                             }
}





