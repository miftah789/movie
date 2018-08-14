package com.miftah.asyst.moviemiftah;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miftah.asyst.moviemiftah.adapter.MovieAdapter;
import com.miftah.asyst.moviemiftah.fragment.FilterFragment;
import com.miftah.asyst.moviemiftah.fragment.SearchFragment;
import com.miftah.asyst.moviemiftah.model.Movie;
import com.miftah.asyst.moviemiftah.retrofit.ApiClient;
import com.miftah.asyst.moviemiftah.retrofit.ApiServices;
import com.miftah.asyst.moviemiftah.retrofit.Response.MovieResponse;
import com.miftah.asyst.moviemiftah.utility.Constant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements FilterFragment.OnSubmitButtonListener, SearchFragment.OnSubmitButtonListener {

    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    ArrayList<Movie> listMovie = new ArrayList<>();
    String year = "";
    int pages = 1;
    String sortBy = "";
    int totalPages;
    String query = "";
    ProgressBar progressBar;
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress_bar);

        movieAdapter = new MovieAdapter(this, listMovie, new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                Toast.makeText(getApplicationContext(), movie.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, ReadMoreActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        if (totalPages >= pages) {
                            progressBar.setVisibility(View.VISIBLE);
                            isLoading = true;
                            if (query.equalsIgnoreCase("")) {
                                getDataWithRetrofit();
                            } else {
                                getDataSearch();
                            }
                        }
                    }
                }
            }
        });
        recyclerView.setAdapter(movieAdapter);
        getDataWithRetrofit();
    }

    public void getDataWithRetrofit() {

        ApiServices apiServices = ApiClient.newInstance(getApplicationContext()).create(ApiServices.class);

        Call<MovieResponse> call = apiServices.getMovies(Constant.KEY_API, year, pages, sortBy);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, retrofit2.Response<MovieResponse> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response.body() != null) {
                    if (response.body().getResults().size() > 0) {
                        totalPages = response.body().getTotalPages();
                        pages = response.body().getPage() + 1;
                        listMovie.addAll(response.body().getResults());
                        movieAdapter.notifyDataSetChanged();
                        isLoading = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getDataSearch() {

        ApiServices apiServices = ApiClient.newInstance(getApplicationContext()).create(ApiServices.class);

        Call<MovieResponse> call = apiServices.getMovies(Constant.KEY_API, query, pages);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, retrofit2.Response<MovieResponse> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response.body() != null) {
                    if (response.body().getResults().size() > 0) {
                        totalPages = response.body().getTotalPages();
                        pages = response.body().getPage() + 1;
                        listMovie.addAll(response.body().getResults());
                        movieAdapter.notifyDataSetChanged();
                        isLoading = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.main_select:
                FilterFragment filterFragment = FilterFragment.newInstance(year, sortBy);
                filterFragment.show(getSupportFragmentManager(), "");
                break;
            case R.id.main_search:
                SearchFragment searchFragment = SearchFragment.newInstance(query);
                searchFragment.show(getSupportFragmentManager(), "");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSubmitButton(String year, String sort) {
        this.year = year;
        this.sortBy = sort;
        Log.d("year", year);
        Log.d("sort", sort);
        listMovie.clear();
        movieAdapter.notifyDataSetChanged();

        pages = 1;

        getDataWithRetrofit();
    }

    @Override
    public void onSubmitButton(String query) {
        this.query = query;
        listMovie.clear();
        movieAdapter.notifyDataSetChanged();

        pages = 1;

        getDataSearch();

    }
}
