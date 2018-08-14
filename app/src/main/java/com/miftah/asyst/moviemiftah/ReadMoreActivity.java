package com.miftah.asyst.moviemiftah;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miftah.asyst.moviemiftah.model.Movie;
import com.miftah.asyst.moviemiftah.utility.Constant;
import com.miftah.asyst.moviemiftah.utility.DateUtils;

public class ReadMoreActivity extends AppCompatActivity {

    TextView tvTitle, tvDate, tvOverView;
    ImageView iVMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_more);

        tvTitle = findViewById(R.id.tv_title);
        tvDate = findViewById(R.id.tv_date);
        tvOverView = findViewById(R.id.tv_overview);
        iVMovie = findViewById(R.id.image_view_backdrop);

        if (getIntent().getExtras() != null) {
            Movie movie = getIntent().getExtras().getParcelable("movie");
            Glide.with(this).load(Constant.BACKDROP_PATH + movie.getPoster_path()).into(iVMovie);
            tvTitle.setText(movie.getTitle());
            tvDate.setText(DateUtils.formatDate("yyyy-MM-dd", "dd MMMM yyyy", movie.getRelease_date()));
            tvOverView.setText(movie.getOverview());
        }
    }
}
