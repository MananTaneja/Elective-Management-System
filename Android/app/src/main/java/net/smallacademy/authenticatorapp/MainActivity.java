package net.smallacademy.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    TextView fullName, email1;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    Button add, curprefbutton;
    Spinner pref1;

    TextView curpref;

    FirebaseDatabase database;
    DatabaseReference databasePreferences;
    DatabaseReference Ref;
    DatabaseReference databaseReference;

    AddFaculty addFaculty = new AddFaculty();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fullName = findViewById(R.id.profileName);
        email1 = findViewById(R.id.profileEmail);
        add = findViewById(R.id.button2);
        pref1 = findViewById(R.id.spinner);
        curpref = findViewById(R.id.textView10);
        curprefbutton = findViewById(R.id.button3);

        addFaculty = new AddFaculty();

//        databaseReference = FirebaseDatabase.getInstance().getReference().child("2").child("data").child("faculties");


//        readname();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        Ref = FirebaseDatabase.getInstance().getReference("2");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("2").child("data").child("faculties").child("7");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                fullName.setText(name);
                email1.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        curprefbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("2").child("data").child("faculties").child("7");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String pref = dataSnapshot.child("preference").getValue().toString();
                        curpref.setText(pref);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                fullName.setText(documentSnapshot.getString("fName"));
//                email.setText(documentSnapshot.getString("email"));
            }
        });


//        databasePreferences = database.getInstance().getReference("2/data/faculties/6");
    }


//    public void readname()
//    {
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.v("Name", "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.v("Name", "Failed to read value.", error.toException());
//            }
//        });
//    }

    private void checkpref() {
        String pref1 = this.pref1.getSelectedItem().toString();

        if (!TextUtils.isEmpty(pref1)) {
//            AddFaculty addFaculty = new AddFaculty(id,name,email,pref1);
//            databasePreferences.setValue(addFaculty);
//            Preferences Preferences = new Preferences(pref1);
//            databasePreferences.setValue(Preferences);
            addFaculty.setPreference(pref1);
            databaseReference.child("7").setValue(addFaculty);

            Toast.makeText(this, "Preferences Added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You should enter all the preferences", Toast.LENGTH_LONG).show();
        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void update() {
        if (isPrefChanged()) {
            Toast.makeText(this,"Preference added/changed",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPrefChanged() {
        String pref = this.pref1.getSelectedItem().toString();
        if(!pref1.equals(pref))
        {
            Ref.child("data").child("faculties").child("7").child("preference").setValue(pref);
            return true;
        }
        else
        {
            return false;
        }
    }

}
