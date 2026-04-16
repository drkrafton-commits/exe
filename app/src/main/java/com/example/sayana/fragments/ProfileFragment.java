package com.example.sayana.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.sayana.LoginActivity;
import com.example.sayana.R;
import com.example.sayana.models.UserProfile;
import com.example.sayana.network.AuthApi;
import com.example.sayana.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvEmail;
    private Button btnLogout;
    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnLogout = view.findViewById(R.id.btnLogout);

        if (getArguments() != null) {
            email = getArguments().getString("email");
        }
        if (email != null) {
            loadProfile(email);
        }

        btnLogout.setOnClickListener(v -> logout());
        return view;
    }

    private void loadProfile(String email) {
        AuthApi api = RetrofitClient.getClient().create(AuthApi.class);
        Call<UserProfile> call = api.getProfile(email);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserProfile profile = response.body();
                    String fullName = (profile.getFirstName() != null ? profile.getFirstName() : "") + " " +
                            (profile.getLastName() != null ? profile.getLastName() : "");
                    tvName.setText(fullName.trim());
                    tvEmail.setText(profile.getEmail());
                } else {
                    Toast.makeText(getContext(), "Не удалось загрузить профиль", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        if (getActivity() != null) getActivity().finish();
    }
}