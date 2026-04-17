package com.example.sayana.network;

import com.example.sayana.models.News;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsApi {
    @GET("api/news")
    Call<List<News>> getNews();
}