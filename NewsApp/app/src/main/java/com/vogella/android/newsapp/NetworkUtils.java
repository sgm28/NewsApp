package com.vogella.android.newsapp;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.preference.PreferenceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.provider.Settings.Secure.getString;

public class NetworkUtils {


    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();






    //thumbnail

    public static String getNewsData(String url)
    {

        //Slowing the data process so the progress bar loader displays.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String newsJSONString = null;

        try{


            //Convert to a URL
            URL requestURL = new URL(url.toString());


            //Start Network connection
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();

            //Create a buffer reader
            reader = new BufferedReader(new InputStreamReader(inputStream));

            //String builder to hold incoming data
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line);

                builder.append("\n"); //Makes debugging easier

            }

            if (builder.length() == 0)
            {
                return null;
            }

            newsJSONString = builder.toString();


        } catch (MalformedURLException e) {
           Log.e(LOG_TAG, "Error building url");
        } catch (ProtocolException e) {
            Log.e(LOG_TAG, "Problem with setRequestMethod");
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with openConnection and connect");
        }

        finally {
            //Close the connection
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Problem closing reader");
                }
            }

            Log.d(LOG_TAG, newsJSONString);
            return newsJSONString;
        }
    }

    //Image data
    public static Bitmap getNewsImagePerArticle(String urlImage)
    {
        Bitmap bmp = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String newsJSONString = null;

        try{
            //Building the URL
            Uri builtURI = Uri.parse(urlImage).buildUpon().build();

            //Convert to a URL
            URL requestURL = new URL(builtURI.toString());


            //Start Network connection
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();

            bmp = BitmapFactory.decodeStream(inputStream);




        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error building url");
        } catch (ProtocolException e) {
            Log.e(LOG_TAG, "Problem with setRequestMethod");
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with openConnection and connect");
        }

        finally {
            //Close the connection
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Problem closing reader");
                }
            }

            Log.d(LOG_TAG, String.valueOf(bmp));
            return bmp;

        }
    }



}
