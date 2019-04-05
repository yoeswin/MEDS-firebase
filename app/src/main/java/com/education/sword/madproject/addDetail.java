package com.education.sword.madproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addDetail extends AppCompatActivity {
    DatabaseReference mDatabase;
    EditText e1, e2, e3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);

        e1 = findViewById(R.id.editText);
        e2 = findViewById(R.id.editText2);
        e3 = findViewById(R.id.editText3);
    }

    private void writeNewdisease(String diseasename, String symptoms, String medicine) {

        if (e1.getText().toString().trim().isEmpty()) {
            e1.setError("Empty field");
            if (e2.getText().toString().trim().isEmpty())
                e2.setError("Empty field");
            if (e3.getText().toString().trim().isEmpty())
                e3.setError("Empty field");

        } else {

            upload upload = new upload(symptoms, medicine);
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("diseases").child(diseasename).setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getBaseContext(), "successfully added", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void add(View view) {
        writeNewdisease(e1.getText().toString().trim().toLowerCase(), e2.getText().toString().trim(), e3.getText().toString().trim());
    }

    public void remove(View view) {
        if (!(e1.getText().toString().trim().isEmpty())) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("diseases").child(e1.getText().toString().trim()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getBaseContext(), "successfully removed", Toast.LENGTH_SHORT).show();
                }
            });
        } else e1.setError("Empty field");
    }

    public void update(View view) {
        if ((e1.getText().toString().trim().isEmpty())) {
            e1.setError("Empty field");
            if (e2.getText().toString().trim().isEmpty())
                e2.setError("Empty field");

        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("diseases").child(e1.getText().toString().trim()).child("symptoms").setValue(e2.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getBaseContext(), "successfully updated symptom", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void medicine(View view) {
        if ((e1.getText().toString().trim().isEmpty())) {
            e1.setError("Empty field");
            if (e3.getText().toString().trim().isEmpty())
                e3.setError("Empty field");

        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("diseases").child(e1.getText().toString().trim()).child("remedy").setValue(e3.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getBaseContext(), "successfully updated remedy", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
