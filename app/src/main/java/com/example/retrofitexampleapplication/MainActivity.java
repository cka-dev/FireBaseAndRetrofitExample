package com.example.retrofitexampleapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitexampleapplication.data.UserDataModel;
import com.example.retrofitexampleapplication.data.model.User;
import com.example.retrofitexampleapplication.data.model.UserData;
import com.example.retrofitexampleapplication.network.ApiCall;
import com.example.retrofitexampleapplication.network.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView responseText;
    private ApiCall apiCall;
    private String TAG = "Retrofit555";
    private TextView register;
    private FirebaseAuth mAuth;
    private EditText emailText;
    private EditText passwordText;
    private Button login;
    private TextView userEmailToReturn;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String firebaseUserId;
    private final static String SERVER_URL = "https://us-central1-testapis-320804.cloudfunctions.net/";
    private final RetrofitClient retrofitClient = new RetrofitClient(SERVER_URL, ApiCall.class);
    private final ApiCall service = (ApiCall) retrofitClient.getService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "in OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(this);


        emailText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        passwordText = (EditText) findViewById(R.id.editTextNumberPassword);

        userEmailToReturn = (TextView) findViewById(R.id.greetingTextView);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.loginButton:
                Log.i(TAG, "Login Button clicked");
                Log.e(TAG, "Login Button clicked");
                signUserIn();
//                setEmail();
                break;

            case R.id.register:
                Log.i(TAG, "Register Button clicked");
                startActivity(new Intent(this, RegisterUser.class));
                break;
        }

    }
    private void triggerOnRequest(){
        String email = emailText.getText().toString().trim();
//        apiCall = RetrofitClient.getRetrofit().create(ApiCall.class);
//        apiCall = retrofitClient.createService(ApiCall.class, SERVER_URL);
        firebaseUserId = mAuth.getCurrentUser().getUid();
        Call<User> call  = service.createUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Log.i(TAG, "trigger on Request successful");
                } else {
                    Log.i(TAG, "error: " + response.message());
                    Log.i(TAG, "trigger on Request failed. Act of GOD");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "call failed" + t.getMessage());
            }
        });


    }

    private void signUserIn(){
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        Log.i(TAG, "In SignIN method and the user is " + email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        Log.i(TAG, "In OnComplete");
                        if (task.isSuccessful()){
                            Log.i(TAG, "Sign in successfull");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "User logged in successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, LoggedInUserActivity.class);
                            startActivity(intent);
                            triggerOnRequest();

                        }else {
                            Log.i(TAG, "Sign in failed");
                            Toast.makeText(MainActivity.this, "Sign in Failed. Try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    private void setEmail(){
        // Get the database record from Firestore for the signed in user based on User id.
        Log.i(TAG, "In setEmail");
        firebaseUserId = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("userdb").document(firebaseUserId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                userEmailToReturn.setText("Your email is " + value.getString("email"));
            }

        });
        Log.i(TAG, "Out of setEmail");
    }
}


//        responseText =  findViewById(R.id.text_view_result);
//        apiCall = RetrofitClient.getRetrofit().create(ApiCall.class);
//        Call<List<UserDataModel>> listCall = apiCall.getUserInfo();
//        listCall.enqueue(new Callback<List<UserDataModel>>() {
//            @Override
//            public void onResponse(Call<List<UserDataModel>> call, Response<List<UserDataModel>> response) {
//                Log.i(TAG, "in OnResponse");
//                if (!response.isSuccessful()){
//                    responseText.setText("Code: " + response.code());
//                    Log.i(TAG, "An act of GOD");
//                    return;
//                }
//                List<UserDataModel> results = (List<UserDataModel>) response.body();
//                for (UserDataModel userData: results){
//                    String content = "";
//                    content += "id: " + userData.getId() + "\n"
//                    + "name: " + userData.getName() +"\n"
//                    + "address: " + "\n"
//                            .concat("  " +userData.getAddress().getStreet()) +"\n"
//                            .concat("  " +userData.getAddress().getSuite() + "\n")
//                            .concat("  " +userData.getAddress().getCity() + "\n")
//                            .concat("  " +userData.getAddress().getZipcode() + "\n")
//                            .concat("  geo: " + "\n")
//                            .concat("    lat: " +userData.getAddress().getGeo().getLat() + "\n")
//                            .concat("    lon: " +userData.getAddress().getGeo().getLng()) + "\n"
//                                    + "phone: " + userData.getPhone() + "\n"
//                                    + "website: " + userData.getWebsite() + "\n"
//                                    + "company"+"\n\n";
//
//                    responseText.append(content);
//                    Log.i(TAG,content);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<UserDataModel>> call, Throwable t) {
//                Log.i(TAG, "Retrofit call failed");
//                Log.i(TAG, String.valueOf(t.fillInStackTrace()));
//                Log.i(TAG, String.valueOf(t.getStackTrace()));
//                responseText.setText("Code: " + t.getMessage());
//            }
//        });

