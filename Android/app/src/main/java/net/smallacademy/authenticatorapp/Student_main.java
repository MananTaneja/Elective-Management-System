package net.smallacademy.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    TextView fullName,email1,curpref1,curpref2,curpref3;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    Spinner pref1,pref2,pref3;

    ListView listview;

    Button add,curprefbutton;

    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<students> list;
    ArrayAdapter<students> adapter;

    DatabaseReference databasePreferences;
    DatabaseReference myRef;
    DatabaseReference databaseReference;
    DatabaseReference Ref;

    String curuserid;
    int countid = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        fullName = findViewById(R.id.profileName1);
        email1    = findViewById(R.id.profileEmail2);
        add = findViewById(R.id.button2);
        curprefbutton = findViewById(R.id.button5);
        curpref1 = findViewById(R.id.textView12);
        curpref2 = findViewById(R.id.textView13);
        curpref3 = findViewById(R.id.textView14);

        Ref = FirebaseDatabase.getInstance().getReference("3");

        database = FirebaseDatabase.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("3").child("data").child("students").child("21");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                countid = (int) dataSnapshot.getChildrenCount();
                curuserid = String.valueOf(countid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        curprefbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curid = curuserid;
                databaseReference = FirebaseDatabase.getInstance().getReference().child("3").child("data").child("students").child("21");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String pref1 = dataSnapshot.child("pref1").getValue().toString();
                        String pref2 = dataSnapshot.child("pref2").getValue().toString();
                        String pref3 = dataSnapshot.child("pref3").getValue().toString();
                        curpref1.setText(pref1);
                        curpref2.setText(pref2);
                        curpref3.setText(pref3);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
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

        databasePreferences = database.getInstance().getReference("3/data/students/21");

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

        public void update() {
        if (isPref1Changed()) {
            Toast.makeText(this,"Preferences added/changed",Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isPref1Changed() {
        String pref = this.pref1.getSelectedItem().toString();
        String prefe = this.pref2.getSelectedItem().toString();
        String prefer = this.pref3.getSelectedItem().toString();
        String curid = curuserid;
        if(!pref1.equals(pref))
        {
            Ref.child("data").child("students").child("21").child("pref1").setValue(pref);
            Ref.child("data").child("students").child("21").child("pref2").setValue(prefe);
            Ref.child("data").child("students").child("21").child("pref3").setValue(prefer);
            return true;
        }
        else
        {
            return false;
        }
    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),LoginStudent.class));
        finish();
    }
}
