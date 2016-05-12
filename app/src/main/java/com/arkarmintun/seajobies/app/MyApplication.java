package com.arkarmintun.seajobies.app;

import android.app.Application;

import com.arkarmintun.seajobies.R;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by arkar on 5/2/16.
 */
public class MyApplication extends Application {

    private static final String APP_ID = "eVSp95Y5NKRNyszEm1bbFzkhBsZRE1mmGTkmI3JG";
    private static final String CLIENT_KEY = "KiuKPhA5YN0hQ8lEfOeUHU0gpqSlwPtClqLGghYd";

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

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
