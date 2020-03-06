package net.smallacademy.authenticatorapp;

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

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    TextView fullName,email,phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    Button add;
    Spinner pref1,pref2,pref3;

    FirebaseDatabase database;
    DatabaseReference databasePreferences;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fullName = findViewById(R.id.profileName);
        email    = findViewById(R.id.profileEmail);
        add = findViewById(R.id.button2);
        pref1 = findViewById(R.id.spinner);
        pref2 = findViewById(R.id.spinner2);
        pref3 = findViewById(R.id.spinner3);

//        readname();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              checkpref();
            }
        });

        databasePreferences = FirebaseDatabase.getInstance().getReference("data/faculties/preference");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                phone.setText(documentSnapshot.getString("phone"));
                fullName.setText(documentSnapshot.getString("fName"));
                email.setText(documentSnapshot.getString("email"));
            }
        });


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

    private void checkpref()
    {
        String preference1 = pref1.getSelectedItem().toString();
        String preference2 = pref2.getSelectedItem().toString();
        String preference3 = pref3.getSelectedItem().toString();

        if(!TextUtils.isEmpty(preference1) || !TextUtils.isEmpty(preference2) || !TextUtils.isEmpty(preference3))
        {
            String id = databasePreferences.push().getKey();
            Preferences Preferences = new Preferences(preference1,preference2,preference3);
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
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}
