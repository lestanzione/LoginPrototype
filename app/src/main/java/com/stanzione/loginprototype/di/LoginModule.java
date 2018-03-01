package com.stanzione.loginprototype.di;

import com.stanzione.loginprototype.login.LoginContract;
import com.stanzione.loginprototype.login.LoginModel;
import com.stanzione.loginprototype.login.LoginPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by lstanzione on 26/02/2018.
 */

@Module
public class LoginModule {

    @Singleton
    @Provides
    LoginContract.Presenter providesLoginPresenter(LoginContract.Model loginModel){
        LoginPresenter loginPresenter = new LoginPresenter(loginModel);
        return loginPresenter;
    }

    @Singleton
    @Provides
    LoginContract.Model providesLoginModel(Realm realm){
        LoginModel loginModel = new LoginModel(realm);
        return loginModel;
    }

}
