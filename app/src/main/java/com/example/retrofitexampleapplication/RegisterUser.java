package com.example.retrofitexampleapplication;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitexampleapplication.data.model.User;
import com.example.retrofitexampleapplication.network.ApiCall;
import com.example.retrofitexampleapplication.network.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView registerUser;
    private EditText userEmail, userPass;
    private String TAG = "RegisterUser";
    private String firebaseUserId;
    private String userId;
    private RetrofitClient apiCall;
    private final static String SERVER_URL = "https://us-central1-testapis-320804.cloudfunctions.net/";
    private final RetrofitClient retrofitClient = new RetrofitClient(SERVER_URL, ApiCall.class);
    private final ApiCall service = (ApiCall) retrofitClient.getService();

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        registerUser = (Button) findViewById(R.id.registerButton);
        registerUser.setOnClickListener(this);

        userEmail = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        userPass = (EditText) findViewById(R.id.editTextTextPassword);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerButton:
                registerUser();
                break;
        }
    }
    private void createUser (String userId){
        Log.i(TAG,"In Create User");
        final String user = new String(userEmail.getText().toString().trim());
//        apiCall = retrofitClient.create(ApiCall.class);
//        ApiCall apiCall = retrofitClient.createService(ApiCall.class, SERVER_URL);

        Call<User> call = service.createUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Log.i(TAG,"User creation successful");
                }else {
                    Log.i(TAG,"User creation failed. Act of GOD");
                    Log.i(TAG, response.errorBody().toString());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(TAG,"User creation call failed");

            }
        });

    }

    private void registerUser() {
        String email = userEmail.getText().toString().trim();
        String password = userPass.getText().toString().trim();

        if (email.isEmpty()){
            userEmail.setError("Email is required");
            userEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            userPass.setError("Password is required");
            userPass.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Please enter a valid email");
            userEmail.requestFocus();
            return;
        }

        if (password.length() <6){
            userPass.setError("Please enter a password longer than 6 characters");
            userPass.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterUser.this, "User registered successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterUser.this, LoggedInUserActivity.class);
                            startActivity(intent);
                            Log.i(TAG, "intent activity started");
                            Log.i(TAG,"skipping client side user creation");
                            // trying to do this with Retrofit
                            createUser(email);

                            // Replaced below with Firebase function to add a user
//                            firebaseUserId = mAuth.getCurrentUser().getUid();
//                            Log.i(TAG, "user: " + firebaseUserId);
//                            DocumentReference documentReference = db.collection("userdb").document(firebaseUserId);
//                            Map<String, Object> userObj = new HashMap<>();
//                            userObj.put("email", email);
//                            Log.i(TAG, "userObj: " + userObj.toString());
//                            documentReference.set(userObj)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            Log.i(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                                            Toast.makeText(RegisterUser.this, "User registered to the database successfully", Toast.LENGTH_LONG).show();
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull @NotNull Exception e) {
//                                            Log.e(TAG, "Error adding document" + e.toString(), e);
//                                            Toast.makeText(RegisterUser.this, "User Database registration failed", Toast.LENGTH_LONG).show();
//                                        }
//                                    });
                        }
                        else{
                            Toast.makeText(RegisterUser.this, "User registration failed. Try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}