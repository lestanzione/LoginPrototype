package com.stanzione.loginprototype.login;

import com.stanzione.loginprototype.entity.AppUser;

/**
 * Created by lstanzione on 26/02/2018.
 */

public interface LoginContract {

    interface View {
        void showSignInDialog();
        void showSignUpDialog();
        String getSignInUsername();
        String getSignInPassword();
        String getSignUpUsername();
        String getSignUpEmail();
        String getSignUpPassword();
        void setSignInUsername(String signInUsername);
        void showMessage(String message);
    }

    interface Presenter {
        void attachView(LoginContract.View view);
        void getAppUser();
        void signInButtonClicked();
        void signUpButtonClicked();
        void doLogin();
        void doSignUp();
    }

    interface Model {
        AppUser getAppUser();
        AppUser getAppUserByUsername(String username);
        boolean doSignUp(AppUser appUser);
        AppUser doLogin(String username, String password);
    }

}
