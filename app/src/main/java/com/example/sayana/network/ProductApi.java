package com.example.sayana.network;

import com.example.sayana.models.Product;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductApi {
    @GET("api/products")
    Call<List<Product>> getProducts(@Query("gender") String gender);
}