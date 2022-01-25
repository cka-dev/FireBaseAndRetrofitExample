package com.example.retrofitexampleapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LoggedInUserActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView userEmailToReturn;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_user);

        Button logout = (Button) findViewById(R.id.logoutButton);
        logout.setOnClickListener(this);
        userEmailToReturn = (TextView) findViewById(R.id.greetingTextView);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String firebaseUserId = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("userdb").document(firebaseUserId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                Log.d("LoggedInUserClass", "Something");
                userEmailToReturn.setText("Your email is " + value.getString("email"));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logoutButton) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
