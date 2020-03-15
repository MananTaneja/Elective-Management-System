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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterStudent extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "TAG";
    EditText mPref1,mPref2,mPref3;
    EditText mFullName,mEmail,mPassword,mRoll;
    Button mRegisterBtn,mRegisterBtn2;
    TextView mLoginBtn,mLoginBtn2;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        mFullName   = findViewById(R.id.fullName1);
        mPref1 = findViewById(R.id.Preference1);
        mPref2 = findViewById(R.id.Preference2);
        mPref3 = findViewById(R.id.Preference3);
        mEmail      = findViewById(R.id.Email1);
        mPassword   = findViewById(R.id.password1);
        mRoll     = findViewById(R.id.ID1);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mRegisterBtn2 = findViewById(R.id.registerBtn3);
        mLoginBtn   = findViewById(R.id.createTextstud);
        mLoginBtn2   = findViewById(R.id.createText2stud);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        databaseUsers = FirebaseDatabase.getInstance().getReference("3/data/students/20");


        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Student_main.class));
            finish();
        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
                final String email = mEmail.getText().toString().trim();
                final String pref1 = mPref1.getText().toString().trim();
                final String pref2 = mPref2.getText().toString().trim();
                final String pref3 = mPref3.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String roll    = mRoll.getText().toString();

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
                            Toast.makeText(RegisterStudent.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("roll",roll);
                            user.put("preference1",pref1);
                            user.put("preference2",pref2);
                            user.put("preference3",pref3);

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
                            startActivity(new Intent(getApplicationContext(),Student_main.class));

                        }else {
                            Toast.makeText(RegisterStudent.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }

            private void addUser() {
                String email = mEmail.getText().toString().trim();
                String name = mFullName.getText().toString().trim();
                String pref1 = mPref1.getText().toString().trim();
                String pref2 = mPref2.getText().toString().trim();
                String pref3 = mPref3.getText().toString().trim();
                String roll = mRoll.getText().toString().trim();

                if(!TextUtils.isEmpty(name)||!TextUtils.isEmpty(roll))
                {
                    AddStudent addStudent = new AddStudent(roll,name,email,pref1,pref2,pref3);
                    databaseUsers.setValue(addStudent);
                    Toast.makeText(RegisterStudent.this,"User Added",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(RegisterStudent.this, "You Should Enter the Name or Roll no.", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(getApplicationContext(),Register.class));
    }
}
