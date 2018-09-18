package com.example.amittiwari.epilux.services;

/**
 * Created by Amit Tiwari on 14-09-2018.
 */
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    Session session;

    @Override
    public void onTokenRefresh() {
   session =  new Session(getApplicationContext());
        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        //calling the method store token and passing token
        storeToken(refreshedToken);


    }

    private void storeToken(String token) {
        //we will save the token in sharedpreferences later
     session.saveDeviceToken(token);

    }


}
