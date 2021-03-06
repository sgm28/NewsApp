package com.vogella.android.newsapp;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class QueryUtils {

    private static String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     *
     */

    public static List<News> extractFeatureFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<News> news = new ArrayList();

        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(newsJSON);

            // Get the JSONArray of data items.
            // The name of the Array is result.
            JSONObject itemsArray = baseJsonResponse.getJSONObject("response");
            JSONArray result = itemsArray.getJSONArray("results");

            // Initialize iterator, and variables to store the data.

            int i = 0;
            String title = null;
            String section = null;
            StringBuilder author = null;
            String date = null;
            Date correctDate = null;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Bitmap downloadedImage = null;
            String webUrl = null;
            JSONObject currentNewsData = null;
            // Look for results in the items array, exiting when items are found.

            while (i < itemsArray.length()) {
                currentNewsData = result.getJSONObject(i);

                section = currentNewsData.optString("sectionName");
                title = String.valueOf(currentNewsData.optString("webTitle"));
                date = String.valueOf(currentNewsData.optString("webPublicationDate"));
                webUrl = currentNewsData.optString("webUrl");
                //Tags array contains and author and contributor
                JSONArray tagsArray = currentNewsData.optJSONArray("tags");

                int j = 0;
                author = new StringBuilder();
                //Sometimes articles have more than one author

                if (tagsArray != null) {

                    while (j < tagsArray.length()) {
                        author.append(tagsArray.getJSONObject(j).getString("webTitle") + " ");
                        j++;
                    }

                }

                if (currentNewsData.optJSONObject("fields") == null) {
                    //skip
                    downloadedImage = null;
                } else {
                    //The fields object can the image of the current article.
                    String urlImage = currentNewsData.optJSONObject("fields").getString("thumbnail");
                    //Downloading the image
                    downloadedImage = NetworkUtils.getNewsImagePerArticle(urlImage);
                }


                correctDate = formatter.parse(date.replaceAll("Z$", "+0000"));



////////////////////////////////////Not my code the original code is here:
// https://stackoverflow.com/questions/51234171/parsing-datetime-in-java-for-previous-os-versions/////////////////////
                // Define a new SimpleDateFormat object to reconstruct the date into the desired format.
                DateFormat newDateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
                // Convert the Date object into a String.
                String formattedDate = newDateFormat.format(correctDate);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //Creating the News object
                News currentNewsDate = new News(title, section, author.toString(), formattedDate, downloadedImage, webUrl);

                //Logging the information
                Log.d(LOG_TAG, currentNewsData.toString());
                news.add(currentNewsDate);


                i++;
            }
        } catch (JSONException e) {

            Log.d(LOG_TAG, "Fields does not exits ");
            e.printStackTrace();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Return the News Array.
        return news;
    }
}
