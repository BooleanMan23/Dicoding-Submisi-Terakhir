package com.example.dicoding_submisi2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by dicoding on 1/9/2017.
 */


class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {



    private static ArrayList<Movie> favorit = new ArrayList<Movie>();
    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;


    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        downloadFavorit();
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return favorit.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
         Movie movie = favorit.get(position);
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext).asBitmap()
                    .load(movie.getPosterLink())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        remoteViews.setImageViewBitmap(R.id.imageView,bitmap);

        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public static void downloadFavorit() {

        Cursor c = MainActivity.myDatabase.rawQuery("SELECT * FROM favorite", null);
        //mengambil index kolum
        int titleIndex = c.getColumnIndex("title");
        int overviewIndex = c.getColumnIndex("overview");
        int posterLinkIndex = c.getColumnIndex("posterLink");
        int releaseDateIndex = c.getColumnIndex("releaseDate");
        int idIndex = c.getColumnIndex("id");
        int isMovieIndex = c.getColumnIndex("isMovie");
        Log.i("hasil", "===============");
        c.moveToFirst();
        do {
            String title = c.getString(titleIndex);
            String overview = c.getString(overviewIndex);
            String posterLink = c.getString(posterLinkIndex);
            String releaseDate = c.getString(releaseDateIndex);
            double id = c.getDouble(idIndex);
            Boolean isMovie;
            if (c.getString(isMovieIndex).equals("1")){
                isMovie = true;
            }else {isMovie = false;}
            Movie movie = new Movie(id, title, overview, posterLink, releaseDate, isMovie);
            favorit.add(movie);
        } while (c.moveToNext());

    }


}


