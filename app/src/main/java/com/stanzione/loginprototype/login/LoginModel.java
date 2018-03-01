package com.stanzione.loginprototype.login;

import com.stanzione.loginprototype.entity.AppUser;

import io.realm.Realm;

/**
 * Created by lstanzione on 26/02/2018.
 */

public class LoginModel implements LoginContract.Model {

    private Realm realm;

    boolean hasError = true;

    public LoginModel(Realm realm){
        this.realm = realm;
    }

    @Override
    public AppUser getAppUser() {
        AppUser appUser = realm.where(AppUser.class).findFirst();
        return appUser;
    }

    @Override
    public AppUser getAppUserByUsername(String username) {
        AppUser appUser = realm.where(AppUser.class).equalTo("username", username).findFirst();
        return appUser;
    }

    @Override
    public boolean doSignUp(final AppUser appUser) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(appUser);
                hasError = false;
            }
        });

        return !hasError;

    }

    @Override
    public AppUser doLogin(String username, String password) {
        AppUser appUser = realm.where(AppUser.class)
                                .equalTo("username", username)
                                .equalTo("password", password)
                                .findFirst();

        return appUser;
    }

}
