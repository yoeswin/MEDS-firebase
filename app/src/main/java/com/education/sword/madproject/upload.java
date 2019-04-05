package com.education.sword.madproject;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class upload {

    public String symptoms;
    public String remedy;

    public upload() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public upload(String username, String email) {
        this.symptoms = username;
        this.remedy = email;
    }

    public String getSymptoms() {
        return symptoms;
    }
}