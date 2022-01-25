package com.example.retrofitexampleapplication.network;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class UserIdToken implements Interceptor {
    // Custom header for passing ID token in the request.
    private static final String X_FIREBASE_ID_TOKEN = "X-FireIDToken";
    private static final String TAG = "Interceptor";

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) {
                Log.i(TAG, "user is null");
            } else {
                Task<GetTokenResult> task = user.getIdToken(true);
                GetTokenResult Result = Tasks.await(task);
                String token = Result.getToken();

                if (token == null) {
                    Log.i(TAG, "token is null");
                    throw new Exception("token is null");
                } else {
                    Request modifiedRequest = request.newBuilder()
                            .addHeader(X_FIREBASE_ID_TOKEN, token)
                            .build();
                    return chain.proceed(modifiedRequest);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
        return null;
    }
}
