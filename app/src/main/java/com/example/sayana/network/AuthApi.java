package com.example.sayana.network;

import com.example.sayana.models.AuthResponse;
import com.example.sayana.models.LoginRequest;
import com.example.sayana.models.RegisterRequest;
import com.example.sayana.models.UserProfile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthApi {
    @POST("api/auth/register")
    Call<AuthResponse> registerUser(@Body RegisterRequest request);

    @POST("api/auth/login")
    Call<AuthResponse> loginUser(@Body LoginRequest request);

    @GET("api/auth/profile/{email}")
    Call<UserProfile> getProfile(@Path("email") String email);


}