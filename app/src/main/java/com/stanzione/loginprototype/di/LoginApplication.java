package com.stanzione.loginprototype.di;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by lstanzione on 26/02/2018.
 */

public class LoginApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());

        // Dagger%COMPONENT_NAME%
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .loginModule(new LoginModule())
                .build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
