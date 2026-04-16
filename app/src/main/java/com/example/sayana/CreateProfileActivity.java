package com.example.sayana;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sayana.models.AuthResponse;
import com.example.sayana.models.RegisterRequest;
import com.example.sayana.network.AuthApi;
import com.example.sayana.network.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProfileActivity extends AppCompatActivity {

    private TextInputEditText editFirstName, editLastName, editPatronymic, editBirthDate;
    private RadioGroup radioGroupGender;
    private Button btnNext;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editPatronymic = findViewById(R.id.editPatronymic);
        editBirthDate = findViewById(R.id.editBirthDate);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        btnNext = findViewById(R.id.btnNext);

        editBirthDate.setOnClickListener(v -> showDatePickerDialog());

        btnNext.setOnClickListener(v -> saveProfile());
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    // Формат: YYYY-MM-DD с ведущими нулями для месяца и дня
                    String selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                    editBirthDate.setText(selectedDate);
                },
                2000, 0, 1);
        datePickerDialog.show();
    }

    private void saveProfile() {
        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String patronymic = editPatronymic.getText().toString().trim();
        String birthDate = editBirthDate.getText().toString().trim();
        String gender = "";
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        if (selectedId == R.id.radioMale) {
            gender = "MALE";
        } else if (selectedId == R.id.radioFemale) {
            gender = "FEMALE";
        }

        if (firstName.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(this, "Имя и фамилия обязательны", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest request = new RegisterRequest(); // пустой конструктор
        request.setEmail(email);
        request.setPassword(password);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setPatronymic(patronymic);
        request.setBirthDate(birthDate);
        request.setGender(gender);

        AuthApi api = RetrofitClient.getClient().create(AuthApi.class);
        api.registerUser(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateProfileActivity.this, "Профиль создан!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateProfileActivity.this, DashboardActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CreateProfileActivity.this, "Ошибка: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(CreateProfileActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}