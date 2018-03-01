package com.stanzione.loginprototype.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by lstanzione on 26/02/2018.
 */

@Module
public class NetModule {

    @Provides
    @Singleton
    Realm providesRealm() {
        return Realm.getDefaultInstance();
    }

}
