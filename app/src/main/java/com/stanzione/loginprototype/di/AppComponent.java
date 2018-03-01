package com.stanzione.loginprototype.di;

import com.stanzione.loginprototype.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lstanzione on 26/02/2018.
 */

@Singleton
@Component(modules={AppModule.class, NetModule.class, LoginModule.class})
public interface AppComponent {
    void inject(LoginActivity activity);
}
