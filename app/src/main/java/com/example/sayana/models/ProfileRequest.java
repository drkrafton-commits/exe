package com.example.sayana.models;

public class ProfileRequest {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String birthDate; // в формате "yyyy-MM-dd"
    private String gender;    // "MALE" или "FEMALE"

    public ProfileRequest() {}

    // геттеры и сеттеры
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }
    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}