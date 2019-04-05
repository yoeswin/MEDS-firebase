package com.education.sword.madproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class details extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseUser mUser;
    ProgressDialog progressDialog;
    EditText dis;
    TextView tv1, tv2;
    LinearLayout tb;
    ArrayList<String> uploadList;
    ListView listView;
    int i;
    Button b;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            tb = findViewById(R.id.tabl);
            b = findViewById(R.id.add);
            uploadList = new ArrayList<>();
            tv1 = findViewById(R.id.textView2);
            tv2 = findViewById(R.id.textView3);
            progressDialog = new ProgressDialog(this);
            listView = findViewById(R.id.listview);
            dis = findViewById(R.id.disease);
//            Toast.makeText(getBaseContext(), user.getEmail(), Toast.LENGTH_SHORT).show();
            if (!(user.getEmail().equals("admin@gmail.com"))) {
                b.setVisibility(View.GONE);
            }
        } else startActivity(new Intent(details.this, MainActivity.class));

    }

    void searc(String req) {
        if (TextUtils.isEmpty(dis.getText().toString().trim())) {
            dis.setError("is empty");
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(dis.getWindowToken(), 0);
            progressDialog.show();
            progressDialog.setMessage("Searching In...");
            progressDialog.setCancelable(false);
            DatabaseReference mDatabaseReference;
            mDatabaseReference = FirebaseDatabase.getInstance().getReference("diseases");
            //retrieving upload data from firebase database
            mDatabaseReference.child(req).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String ne = postSnapshot.getValue(String.class);
                        Toast.makeText(getBaseContext(), ne, Toast.LENGTH_SHORT).show();
                        uploadList.add(ne);
                    }
                    if (uploadList.isEmpty()) {
                        progressDialog.setMessage("No data found");
                        progressDialog.setCancelable(true);
                        tb.setVisibility(View.INVISIBLE);
                    } else {
                        progressDialog.cancel();
                        tb.setVisibility(View.VISIBLE);
                        tv1.setText(uploadList.get(1));
                        tv2.setText(uploadList.get(0));
                        uploadList.clear();
                    }
//                  ArrayAdapter<String> adapter;
//                adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, uploadList);
//                listView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getBaseContext(), "Error retriving data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void getdet(View view) {
        Toast.makeText(getBaseContext(), "Getting det", Toast.LENGTH_SHORT).show();
        searc(dis.getText().toString());

    }

    public void addet(View view) {
        startActivity(new Intent(this, addDetail.class));
    }


    public void signout(View view) {
        mAuth.signOut();
        Toast.makeText(getBaseContext(),"Signed out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
