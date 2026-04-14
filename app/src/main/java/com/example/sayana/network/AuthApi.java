package com.example.sayana.network;

import com.example.sayana.models.AuthResponse;
import com.example.sayana.models.LoginRequest;
import com.example.sayana.models.RegisterRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("api/auth/register")
    Call<AuthResponse> registerUser(@Body RegisterRequest request);

    @POST("api/auth/login")
    Call<AuthResponse> loginUser(@Body LoginRequest request);
}