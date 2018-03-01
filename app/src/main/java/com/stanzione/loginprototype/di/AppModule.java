package com.stanzione.loginprototype.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lstanzione on 26/02/2018.
 */

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

}
