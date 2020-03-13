package net.smallacademy.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import javax.annotation.Nullable;

public class Student_main extends AppCompatActivity {
    TextView fullName,email;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    Spinner pref1,pref2,pref3;

    ListView listview;

    Button add;

    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<students> list;
    ArrayAdapter<students> adapter;

    DatabaseReference databasePreferences;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        fullName = findViewById(R.id.profileName1);
        email    = findViewById(R.id.profileEmail1);
        add = findViewById(R.id.button2);
//        listview = findViewById(R.id.ListView);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("3/data/students");
//        list = new ArrayList<>();
//        adapter = new ArrayAdapter<students>(this,R.layout.student_info,R.layout.)
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds: dataSnapshot.getChildren())
//                {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        pref1 = findViewById(R.id.spinner);
        pref2 = findViewById(R.id.spinner2);
        pref3 = findViewById(R.id.spinner3);

//        readname();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkprefforstudent();
            }
        });

        databasePreferences = FirebaseDatabase.getInstance().getReference("data/students/preference");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                fullName.setText(documentSnapshot.getString("fName"));
//                email.setText(documentSnapshot.getString("email"));
            }
        });

        databasePreferences = database.getInstance().getReference("3/data/students/0");

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

    private void checkprefforstudent()
    {
        String pref1 = this.pref1.getSelectedItem().toString();
        String pref2 = this.pref2.getSelectedItem().toString();
        String pref3 = this.pref3.getSelectedItem().toString();

        if(!TextUtils.isEmpty(pref1) || !TextUtils.isEmpty(pref2) || !TextUtils.isEmpty(pref3))
        {
            String id = databasePreferences.push().getKey();
            PrefStudent Preferences = new PrefStudent(pref1,pref2,pref3);
            databasePreferences.child(id).setValue(Preferences);

            Toast.makeText(this,"Preferences Added",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "You should enter all the preferences", Toast.LENGTH_LONG).show();
        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),LoginStudent.class));
        finish();
    }
}
