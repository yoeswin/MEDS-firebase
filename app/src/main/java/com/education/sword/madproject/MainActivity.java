package com.education.sword.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    EditText inputemail, inputpassword, check, ename;
    TextView signuptv,logi;
    Button btnlogin, btnsignup;
    ScrollView Root;
    InputMethodManager imm;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Root = findViewById(R.id.root);
        inputemail = findViewById(R.id.input_email);
        inputpassword = findViewById(R.id.input_password);
        check = findViewById(R.id.check_password);
        ename = findViewById(R.id.name);
        logi = findViewById(R.id.log);

        signuptv = findViewById(R.id.signup);
        btnlogin = findViewById(R.id.btn_login);
        btnsignup = findViewById(R.id.btn_signup);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this,details.class));
            finish();

        }

        signuptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnlogin.setVisibility(View.GONE);
                signuptv.setVisibility(View.GONE);
                inputemail.setVisibility(View.GONE);
                inputpassword.setVisibility(View.GONE);
                inputemail.setVisibility(View.VISIBLE);
                inputpassword.setVisibility(View.VISIBLE);
                check.setVisibility(View.VISIBLE);
                ename.setVisibility(View.VISIBLE);
                btnsignup.setVisibility(View.VISIBLE);
                logi.setVisibility(View.VISIBLE);
            }
        });

        logi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnlogin.setVisibility(View.VISIBLE);
                signuptv.setVisibility(View.VISIBLE);
                check.setVisibility(View.GONE);
                ename.setVisibility(View.GONE);
                btnsignup.setVisibility(View.GONE);
                logi.setVisibility(View.GONE);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputemail.getText().toString().trim();
                String password = inputpassword.getText().toString().trim();

                if ((TextUtils.isEmpty(email))) {
                    Toast.makeText(getBaseContext(), "Enter valid email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getBaseContext(), "Enter valid email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getBaseContext(), "welcome", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(MainActivity.this,details.class));
                                    finish();
//                                    savepref();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputemail.getText().toString().trim();
                final String password = inputpassword.getText().toString().trim();
                String Check = check.getText().toString().trim();
                final String sname = ename.getText().toString().trim();


                if ((TextUtils.isEmpty(email)) | (TextUtils.isEmpty(sname)) ) {
                    Toast.makeText(getBaseContext(), "Enter valid details!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ((TextUtils.isEmpty(password)) || (password.length() < 8)) {
                    Toast.makeText(getBaseContext(), "Enter valid password!Atleast 8 letters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!(Check.equals(password))) {
                    Toast.makeText(getBaseContext(), "Passwords mismatch", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Snackbar.make(Root, "Authenticated",
                                            Snackbar.LENGTH_SHORT).show();
                                    final FirebaseUser us = mAuth.getCurrentUser();
                                    writeNewUser(us.getUid(), sname, email);

                                    mAuth.signOut();

                                    check.setText("");
                                    ename.setText("");
                                    inputpassword.setText("");
                                    btnlogin.setVisibility(View.VISIBLE);
                                    signuptv.setVisibility(View.VISIBLE);
                                    check.setVisibility(View.GONE);
                                    ename.setVisibility(View.GONE);
                                    btnsignup.setVisibility(View.GONE);
                                    logi.setVisibility(View.GONE);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void writeNewUser(String userId, String nae, String emal) {
        User user = new User(nae, emal);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).setValue(user);
    }
}
