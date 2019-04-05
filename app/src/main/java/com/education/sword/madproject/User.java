package com.education.sword.madproject;
import com.google.firebase.database.IgnoreExtraProperties;



// [START blog_user_class]

@IgnoreExtraProperties

public class User {

    public static String NAME_PREF,EMAIL_PREF;


    public String username;
    public String email;




    public User() {

        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }



    public User(String username, String email ) {
        this.username = username;
        this.email = email;
    }



}
