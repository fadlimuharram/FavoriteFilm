package com.example.favoritefilm;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.favoritefilm.adapter.MovieAdapter;
import com.example.favoritefilm.model.movie.Result;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;

import static com.example.favoritefilm.db.DatabaseContract.FavoriteMovie._ID;
import static com.example.favoritefilm.db.DatabaseContract.FavoriteMovie.CONTENT_URI;
import static com.example.favoritefilm.db.DatabaseContract.FavoriteMovie.OVERVIEW;
import static com.example.favoritefilm.db.DatabaseContract.FavoriteMovie.TITLE;
import static com.example.favoritefilm.db.DatabaseContract.FavoriteMovie.POSTER_PATH;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFavoriteFragment extends Fragment implements NowPlayingCallback{

    ArrayList<Result> movieFavorite;
    private MovieAdapter adapter;

    RecyclerView rvMovie;

    public NowPlayingFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new LoadFavoriteMovieAsync(getActivity(),this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_now_playing_favorite, container, false);
        rvMovie = view.findViewById(R.id.rv_movie);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void preExecuteFavorites() {

    }

    @Override
    public void postExecuteFavorites(Cursor result) {
        if (result.getCount() > 0){
            movieFavorite = mapCursorToArrayListMovie(result);
            initAdapter();
        }

    }

    private void initAdapter(){
        adapter = new MovieAdapter(getActivity());
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setAdapter(adapter);
        adapter.setmData(movieFavorite);

    }



    public static class LoadFavoriteMovieAsync extends AsyncTask<Void, Void, Cursor>{
        private WeakReference<Context> weakContext;
        private WeakReference<NowPlayingCallback> weakCallback;

        public LoadFavoriteMovieAsync(Context context, NowPlayingCallback view) {
            this.weakContext = new WeakReference<>(context);
            this.weakCallback = new WeakReference<>(view);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().showLoading();
            weakCallback.get().preExecuteFavorites();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();

            return context.getContentResolver().query(CONTENT_URI, null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecuteFavorites(cursor);
            weakCallback.get().hideLoading();
        }
    }


    public static ArrayList<Result> mapCursorToArrayListMovie(Cursor resultCursor){
        ArrayList<Result> results = new ArrayList<>();
        while (resultCursor.moveToNext()){
            long id = resultCursor.getInt(resultCursor.getColumnIndexOrThrow(_ID));
            String overview = resultCursor.getString(resultCursor.getColumnIndexOrThrow(OVERVIEW));
            String poster_path = resultCursor.getString(resultCursor.getColumnIndexOrThrow(POSTER_PATH));
            String title = resultCursor.getString(resultCursor.getColumnIndexOrThrow(TITLE));
            results.add(new Result(id, overview, poster_path, title));
        }
        return results;
    }

}
