package com.miftah.asyst.moviemiftah.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.miftah.asyst.moviemiftah.R;
import com.miftah.asyst.moviemiftah.model.Movie;
import com.miftah.asyst.moviemiftah.utility.Constant;
import com.miftah.asyst.moviemiftah.utility.DateUtils;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolderMovie> {

    Context mContext;
    ArrayList<Movie> mListMovie;
    OnItemClickListener mlistener;

    public MovieAdapter(Context context, ArrayList<Movie> listMovie, OnItemClickListener listener) {
        this.mContext = context;
        this.mListMovie = listMovie;
        this.mlistener = listener;

    }

    @NonNull
    @Override
    public MyViewHolderMovie onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);

        return new MovieAdapter.MyViewHolderMovie(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderMovie holder, int position) {

        final Movie movie = mListMovie.get(position);
        holder.tvTitle.setText(movie.getTitle());
        if (!movie.getRelease_date().isEmpty()) {
            holder.tvDate.setText(DateUtils.formatDate("yyyy-MM-dd", "dd MMMM yyyy", movie.getRelease_date()));
        } else {
            holder.tvDate.setText("xxx");
        }
        holder.tvOverview.setText(movie.getOverview());
        holder.tvReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onItemClick(movie);
            }
        });

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.image).error(R.drawable.ic_tag_faces);

        Glide.with(mContext).load(Constant.BASE_IMAGE + movie.getPoster_path()).apply(requestOptions).into(holder.ivMovie);


    }

    @Override
    public int getItemCount() {
        return mListMovie.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public class MyViewHolderMovie extends RecyclerView.ViewHolder {

        ImageView ivMovie;
        TextView tvTitle;
        TextView tvDate;
        TextView tvOverview;
        TextView tvReadMore;
        CardView cardView;

        public MyViewHolderMovie(View itemView) {
            super(itemView);

            ivMovie = itemView.findViewById(R.id.image_view_movie);
            tvTitle = itemView.findViewById(R.id.tv_title_movie);
            tvDate = itemView.findViewById(R.id.tv_date_movie);
            tvOverview = itemView.findViewById(R.id.tv_overview_movie);
            tvReadMore = itemView.findViewById(R.id.tv_read_more);

            cardView = itemView.findViewById(R.id.cardview);

        }
    }
}
