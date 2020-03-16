package net.smallacademy.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "TAG";
    EditText mPref;
    EditText mFullName,mEmail,mPassword,mID;
    Button mRegisterBtn,mRegisterBtn2;
    TextView mLoginBtn,mLoginBtn2;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;
    DatabaseReference databaseUsers;
    String curuserid;
    int countid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName   = findViewById(R.id.fullName);
        mPref = findViewById(R.id.Preference);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.password);
        mID      = findViewById(R.id.ID);
        mRegisterBtn= findViewById(R.id.registerBtn);
        mRegisterBtn2= findViewById(R.id.registerBtn2);
        mLoginBtn   = findViewById(R.id.createText);
        mLoginBtn2   = findViewById(R.id.createText2);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        databaseUsers = FirebaseDatabase.getInstance().getReference("2").child("data").child("faculties");

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                countid = (int) dataSnapshot.getChildrenCount();
                curuserid = String.valueOf(countid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
                final String email = mEmail.getText().toString().trim();
                final String pref = mPref.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String id    = mID.getText().toString();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("id",id);
                            user.put("preference",pref);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }

            private void addUser() {
                String email = mEmail.getText().toString().trim();
                String id = mID.getText().toString().trim();
                String name = mFullName.getText().toString().trim();
                String pref = mPref.getText().toString().trim();
                String curid = curuserid;

                if(!TextUtils.isEmpty(name)||!TextUtils.isEmpty(id)||!TextUtils.isEmpty(email))
                {
                    AddFaculty addFaculty = new AddFaculty(id,name,email,pref);
                    databaseUsers.child(curid).setValue(addFaculty);
                    Toast.makeText(Register.this,"User Added",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Register.this, "You Should Enter the Name or ID", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mRegisterBtn2.setOnClickListener(this);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        mLoginBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginStudent.class));
            }
        });

    }

    @Override
    public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(),RegisterStudent.class));
    }
}
