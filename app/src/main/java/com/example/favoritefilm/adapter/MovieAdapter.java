package com.example.favoritefilm.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.favoritefilm.BuildConfig;
import com.example.favoritefilm.R;
import com.example.favoritefilm.model.movie.Result;
import com.example.favoritefilm.utils.MovieConverter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<Result> mData;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public List<Result> getmData() {
        return mData;
    }

    public void setmData(List<Result> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_view_movie, viewGroup, false);

        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textViewTitle.setText(getmData().get(i).getTitle());
        viewHolder.textViewOverview.setText(MovieConverter.textSubStr(getmData().get(i).getOverview(), 105));
        Picasso.get()
                .load(BuildConfig.POSTER_URL + BuildConfig.POSTER_WIDTH + "/" + getmData().get(i).getPosterPath())
                .into(viewHolder.imageViewPoster);
    }

    @Override
    public int getItemCount() {
        return getmData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;

        TextView textViewOverview;

        ImageView imageViewPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.txt_movie_title);
            textViewOverview = (TextView) itemView.findViewById(R.id.txt_movie_overview);
            imageViewPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
        }
    }
}
