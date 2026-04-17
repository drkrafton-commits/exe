package com.example.sayana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sayana.R;
import com.example.sayana.adapters.NewsAdapter;
import com.example.sayana.adapters.ProductAdapter;
import com.example.sayana.models.News;
import com.example.sayana.models.Product;
import com.example.sayana.network.NewsApi;
import com.example.sayana.network.ProductApi;
import com.example.sayana.network.RetrofitClient;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView newsRecycler, productsRecycler;
    private ChipGroup chipGroup;
    private ProgressBar progressBar;
    private TextView emptyText;
    private NewsAdapter newsAdapter;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        newsRecycler = view.findViewById(R.id.newsRecycler);
        productsRecycler = view.findViewById(R.id.productsRecycler);
        chipGroup = view.findViewById(R.id.chipGroup);
        progressBar = view.findViewById(R.id.progressBar);
        emptyText = view.findViewById(R.id.emptyText);

        // Настройка горизонтального списка новостей
        newsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // Настройка вертикального списка товаров
        productsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadNews();
        loadProducts(null); // загружаем все товары

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int selectedId = checkedIds.get(0);
            String gender = null;
            if (selectedId == R.id.chipAll) gender = null;
            else if (selectedId == R.id.chipWomen) gender = "FEMALE";
            else if (selectedId == R.id.chipMen) gender = "MALE";
            loadProducts(gender);
        });

        return view;
    }

    private void loadNews() {
        NewsApi api = RetrofitClient.getClient().create(NewsApi.class);
        api.getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    newsAdapter = new NewsAdapter(response.body());
                    newsRecycler.setAdapter(newsAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {}
        });
    }

    private void loadProducts(String gender) {
        progressBar.setVisibility(View.VISIBLE);
        ProductApi api = RetrofitClient.getClient().create(ProductApi.class);
        api.getProducts(gender).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    productList = response.body();
                    productAdapter = new ProductAdapter(productList);
                    productsRecycler.setAdapter(productAdapter);
                    emptyText.setVisibility(productList.isEmpty() ? View.VISIBLE : View.GONE);
                } else {
                    emptyText.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                emptyText.setVisibility(View.VISIBLE);
            }
        });
    }
}