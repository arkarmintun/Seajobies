package com.arkarmintun.seajobies.app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by arkar on 5/2/16.
 */
public class MyApplication extends Application {

    private static final String APP_ID = "o7dlk0ejprNfOtcpz1pXylZhupITJJKz3t1zzn04";
    private static final String CLIENT_KEY = "BEIn1pEqPPggAGcUORPJFBnrlhN3eZOFw4qnMvuq";

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(APP_ID)
                .clientKey(CLIENT_KEY)
                .server("https://parseapi.back4app.com")
                .enableLocalDataStore()
                .build()
        );

        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
