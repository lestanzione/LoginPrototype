package com.stanzione.loginprototype.login;

import android.util.Patterns;

import com.stanzione.loginprototype.entity.AppUser;

/**
 * Created by lstanzione on 26/02/2018.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private LoginContract.Model model;

    public LoginPresenter(LoginContract.Model model){
        this.model = model;
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void getAppUser() {

        AppUser appUser = model.getAppUser();

        if(appUser != null){
            view.showSignInDialog();
            view.setSignInUsername(appUser.getUsername());
        }

    }

    @Override
    public void signInButtonClicked() {
        view.showSignInDialog();
    }

    @Override
    public void signUpButtonClicked() {
        view.showSignUpDialog();
    }

    @Override
    public void doLogin() {

        if(!isSignInUsernameValid()){
            view.showMessage("Username must have at least 5 characters");
        }
        else if(!isSignInPasswordValid()){
            view.showMessage("Enter a password");
        }
        else{

            AppUser appUser = model.doLogin(view.getSignInUsername(), view.getSignInPassword());

            if(appUser != null){
                view.showMessage("Logged successfully!");
            }
            else{
                view.showMessage("Wrong username or password!");
            }

        }

    }

    @Override
    public void doSignUp() {

        if(!isUsernameValid()){
            view.showMessage("Username must have at least 5 characters");
        }
        else if(!isEmailValid()){
            view.showMessage("Enter a valid email");
        }
        else if(!isPasswordValid()){
            view.showMessage("Enter a password");
        }
        else{

            AppUser appUserToCreate = new AppUser();
            appUserToCreate.setUsername(view.getSignUpUsername());
            appUserToCreate.setPassword(view.getSignUpPassword());
            appUserToCreate.setEmail(view.getSignUpEmail());

            AppUser appUser = model.getAppUserByUsername(view.getSignUpUsername());

            if(appUser != null){
                view.showMessage("This username already exists");
            }
            else{
                boolean userCreated = model.doSignUp(appUserToCreate);

                if(userCreated){
                    view.showMessage("Account created successfully!");
                }
                else{
                    view.showMessage("Error creating the account");
                }

            }

        }

    }

    private boolean isUsernameValid(){
        return view.getSignUpUsername().length() >= 5;
    }

    private boolean isEmailValid(){
        //return Patterns.EMAIL_ADDRESS.matcher(view.getSignUpEmail()).matches();
        return view.getSignUpEmail().contains("@");
    }

    private boolean isPasswordValid(){
        return !view.getSignUpPassword().isEmpty();
    }

    private boolean isSignInUsernameValid(){
        return view.getSignInUsername().length() >= 5;
    }

    private boolean isSignInPasswordValid(){
        return !view.getSignInPassword().isEmpty();
    }

}
