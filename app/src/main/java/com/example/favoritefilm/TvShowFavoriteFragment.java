package com.example.favoritefilm;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.favoritefilm.adapter.TvShowAdapter;
import com.example.favoritefilm.model.tv.Result;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.favoritefilm.db.DatabaseContract.FavoriteTv.CONTENT_URI;
import static com.example.favoritefilm.db.DatabaseContract.FavoriteTv._ID;;
import static com.example.favoritefilm.db.DatabaseContract.FavoriteTv.OVERVIEW;
import static com.example.favoritefilm.db.DatabaseContract.FavoriteTv.TITLE;
import static com.example.favoritefilm.db.DatabaseContract.FavoriteTv.POSTER_PATH;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment implements NowPlayingCallback {


    ArrayList<Result> tvFavorite;
    private TvShowAdapter adapter;
    RecyclerView rvTv;

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new LoadFavoriteTvAsync(getActivity(),this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);
        rvTv = view.findViewById(R.id.rv_tv);
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
            tvFavorite = mapCursorToArrayListTv(result);
            initAdapter();
        }
    }

    private void initAdapter(){
        adapter = new TvShowAdapter(getActivity());
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTv.setAdapter(adapter);
        adapter.setmData(tvFavorite);
    }


    public static class LoadFavoriteTvAsync extends AsyncTask<Void, Void, Cursor> {
        private WeakReference<Context> weakContext;
        private WeakReference<NowPlayingCallback> weakCallback;

        public LoadFavoriteTvAsync(Context context, NowPlayingCallback view) {
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

    public static ArrayList<Result> mapCursorToArrayListTv(Cursor resultCursor){
        ArrayList<Result> results = new ArrayList<>();
        while (resultCursor.moveToNext()){
            long id = resultCursor.getInt(resultCursor.getColumnIndexOrThrow(_ID));
            String overview = resultCursor.getString(resultCursor.getColumnIndexOrThrow(OVERVIEW));
            String poster_path = resultCursor.getString(resultCursor.getColumnIndexOrThrow(POSTER_PATH));
            String title = resultCursor.getString(resultCursor.getColumnIndexOrThrow(TITLE));
            results.add(new Result(id, title, overview, poster_path));
        }

        return results;
    }

}
