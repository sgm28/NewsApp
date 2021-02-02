package com.vogella.android.newsapp;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    //Fields
    // Store a List of News objects
    private List<News> mNews;

    // OnNewsListener needed for NewsAdapter constructor and the ViewHolder constructor
    private OnNewsListener onNewsListener;


    /**
     * @param mNews A list of News objects
     */
    public NewsAdapter(List<News> mNews, OnNewsListener onNewsListener) {
        this.mNews = mNews;
        this.onNewsListener = onNewsListener;
    }

    public void setData(List<News> data) {
        mNews = data;
        notifyDataSetChanged();
    }


    /**
     * Need a ViewHolder class because the NewsAdapter needs a ViewHolder
     */                                                                 //implementing the OnClickListener
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageArticle;
        public TextView title;
        public TextView section;
        public TextView author;
        public TextView date;


        OnNewsListener onNewsListener;


        /**
         * @param itemView Accepts an item row.
         *                 Finds the views in the role
         *                 Store the location of each role in the correct variables.
         */
        public ViewHolder(@NonNull View itemView, OnNewsListener onNewsListener) {
            super(itemView);

            imageArticle = (ImageView) itemView.findViewById(R.id.imageArticle);
            title = (TextView) itemView.findViewById(R.id.title);
            section = (TextView) itemView.findViewById(R.id.section);
            author = (TextView) itemView.findViewById(R.id.author);
            date = (TextView) itemView.findViewById(R.id.date);


            this.onNewsListener = onNewsListener;
            //This refers to View.OnClickListener
            itemView.setOnClickListener(this);

        }

        //Implementing onClick method from the OnNewsListener
        @Override
        public void onClick(View view) {

            onNewsListener.OnNewsClick(getAdapterPosition());

        }
    }


    //Inflating the custom layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        // Inflate the custom Layout
        View NewsViewHolder = inflater.inflate(R.layout.news_item_template, parent, false);

        //Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(NewsViewHolder, onNewsListener);
        return viewHolder;
    }

    //Attaching the date to the custom layout
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Getting the position of the data in the list
        News news = mNews.get(position);

        // Attaching the itemsViews to the data in the news object
        TextView author = holder.author;
        TextView section = holder.section;
        TextView date = holder.date;
        TextView title = holder.title;
        ImageView imageView = holder.imageArticle;

        author.setText(news.getAuthor());
        section.setText(news.getSection());
        date.setText(news.getDate());
        title.setText(news.getTitle());

        //Sometimes News Articles don't have a picture.
        if (news.getImageBitmap() == null) {
            imageView.setImageResource(R.drawable.newspaper);
        } else {
            imageView.setImageBitmap(news.getImageBitmap());
        }


    }

    //Return the total number of items in the list
    @Override
    public int getItemCount() {
        return mNews.size();
    }


    //Interface for On Click Listener
    public interface OnNewsListener {
        void OnNewsClick(int position);

    }
}
