package com.stanzione.loginprototype;

import com.stanzione.loginprototype.entity.AppUser;
import com.stanzione.loginprototype.login.LoginContract;
import com.stanzione.loginprototype.login.LoginPresenter;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by lstanzione on 01/03/2018.
 */

public class LoginPresenterTests {

    LoginContract.View mockView;
    LoginContract.Model mockModel;
    LoginPresenter loginPresenter;

    @Before
    public void setup(){

        mockView = mock(LoginContract.View.class);
        mockModel = mock(LoginContract.Model.class);

        loginPresenter = new LoginPresenter(mockModel);
        loginPresenter.attachView(mockView);

    }

    @Test
    public void testHasNoAppUserSaved(){

        when(mockModel.getAppUser()).thenReturn(null);

        loginPresenter.getAppUser();

        verify(mockModel, times(1)).getAppUser();
        verify(mockView, never()).showSignInDialog();
        verify(mockView, never()).setSignInUsername(anyString());

    }

    @Test
    public void testHasAppUserSaved(){

        AppUser appUser = new AppUser();
        appUser.setUsername("Leandro Stanzione");

        when(mockModel.getAppUser()).thenReturn(appUser);

        loginPresenter.getAppUser();

        verify(mockModel, times(1)).getAppUser();
        verify(mockView, times(1)).showSignInDialog();
        verify(mockView, times(1)).setSignInUsername(appUser.getUsername());

    }

    @Test
    public void testDoLoginNoUsername(){

        when(mockView.getSignInUsername()).thenReturn("");

        loginPresenter.doLogin();

        verify(mockModel, never()).doLogin(anyString(), anyString());
        verify(mockView, times(1)).getSignInUsername();
        verify(mockView, never()).getSignInPassword();
        verify(mockView, times(1)).showMessage("Username must have at least 5 characters");

    }

    @Test
    public void testDoLoginNoPassword(){

        when(mockView.getSignInUsername()).thenReturn("Leandro Stanzione");
        when(mockView.getSignInPassword()).thenReturn("");

        loginPresenter.doLogin();

        verify(mockModel, never()).doLogin(anyString(), anyString());
        verify(mockView, times(1)).getSignInUsername();
        verify(mockView, times(1)).getSignInPassword();
        verify(mockView, times(1)).showMessage("Enter a password");

    }

    @Test
    public void testDoLoginUsernameTooShort(){

        when(mockView.getSignInUsername()).thenReturn("Le");

        loginPresenter.doLogin();

        verify(mockModel, never()).doLogin(anyString(), anyString());
        verify(mockView, times(1)).getSignInUsername();
        verify(mockView, never()).getSignInPassword();
        verify(mockView, times(1)).showMessage("Username must have at least 5 characters");

    }

    @Test
    public void testDoLoginPass(){

        AppUser appUser = new AppUser();
        appUser.setUsername("Leandro Stanzione");

        when(mockView.getSignInUsername()).thenReturn("Leandro Stanzione");
        when(mockView.getSignInPassword()).thenReturn("Password");

        when(mockModel.doLogin(anyString(), anyString())).thenReturn(appUser);

        loginPresenter.doLogin();

        verify(mockModel, times(1)).doLogin(anyString(), anyString());
        verify(mockView, times(2)).getSignInUsername();
        verify(mockView, times(2)).getSignInPassword();
        verify(mockView, times(1)).showMessage("Logged successfully!");

    }

    @Test
    public void testDoLoginFail(){

        when(mockView.getSignInUsername()).thenReturn("Leandro Stanzione");
        when(mockView.getSignInPassword()).thenReturn("Password");

        when(mockModel.doLogin(anyString(), anyString())).thenReturn(null);

        loginPresenter.doLogin();

        verify(mockModel, times(1)).doLogin(anyString(), anyString());
        verify(mockView, times(2)).getSignInUsername();
        verify(mockView, times(2)).getSignInPassword();
        verify(mockView, times(1)).showMessage("Wrong username or password!");

    }

    @Test
    public void testDoSignUpNoUsername(){

        when(mockView.getSignUpUsername()).thenReturn("");

        loginPresenter.doSignUp();

        verify(mockModel, never()).doSignUp(any(AppUser.class));
        verify(mockView, times(1)).getSignUpUsername();
        verify(mockView, never()).getSignUpEmail();
        verify(mockView, never()).getSignUpPassword();
        verify(mockView, times(1)).showMessage("Username must have at least 5 characters");

    }

    @Test
    public void testDoSignUpNoEmail(){

        when(mockView.getSignUpUsername()).thenReturn("Leandro Stanzione");
        when(mockView.getSignUpEmail()).thenReturn("");

        loginPresenter.doSignUp();

        verify(mockModel, never()).doSignUp(any(AppUser.class));
        verify(mockView, times(1)).getSignUpUsername();
        verify(mockView, times(1)).getSignUpEmail();
        verify(mockView, never()).getSignUpPassword();
        verify(mockView, times(1)).showMessage("Enter a valid email");

    }

    @Test
    public void testDoSignUpWrongEmail(){

        when(mockView.getSignUpUsername()).thenReturn("Leandro Stanzione");
        when(mockView.getSignUpEmail()).thenReturn("test.com");

        loginPresenter.doSignUp();

        verify(mockModel, never()).doSignUp(any(AppUser.class));
        verify(mockView, times(1)).getSignUpUsername();
        verify(mockView, times(1)).getSignUpEmail();
        verify(mockView, never()).getSignUpPassword();
        verify(mockView, times(1)).showMessage("Enter a valid email");

    }

    @Test
    public void testDoSignUpNoPassword(){

        when(mockView.getSignUpUsername()).thenReturn("Leandro Stanzione");
        when(mockView.getSignUpEmail()).thenReturn("mail@mail.com");
        when(mockView.getSignUpPassword()).thenReturn("");

        loginPresenter.doSignUp();

        verify(mockModel, never()).doSignUp(any(AppUser.class));
        verify(mockView, times(1)).getSignUpUsername();
        verify(mockView, times(1)).getSignUpEmail();
        verify(mockView, times(1)).getSignUpPassword();
        verify(mockView, times(1)).showMessage("Enter a password");

    }

    @Test
    public void testDoSignUpUserAlreadyCreated(){

        when(mockModel.getAppUserByUsername(anyString())).thenReturn(new AppUser());
        when(mockView.getSignUpUsername()).thenReturn("Leandro Stanzione");
        when(mockView.getSignUpEmail()).thenReturn("mail@mail.com");
        when(mockView.getSignUpPassword()).thenReturn("Password");

        loginPresenter.doSignUp();

        verify(mockModel, never()).doSignUp(any(AppUser.class));
        verify(mockModel, times(1)).getAppUserByUsername(anyString());
        verify(mockView, times(3)).getSignUpUsername();
        verify(mockView, times(2)).getSignUpEmail();
        verify(mockView, times(2)).getSignUpPassword();
        verify(mockView, times(1)).showMessage("This username already exists");

    }

    @Test
    public void testDoSignUpPass(){

        when(mockModel.getAppUserByUsername(anyString())).thenReturn(null);
        when(mockModel.doSignUp(any(AppUser.class))).thenReturn(false);
        when(mockView.getSignUpUsername()).thenReturn("Leandro Stanzione");
        when(mockView.getSignUpEmail()).thenReturn("mail@mail.com");
        when(mockView.getSignUpPassword()).thenReturn("Password");

        loginPresenter.doSignUp();

        verify(mockModel, times(1)).doSignUp(any(AppUser.class));
        verify(mockModel, times(1)).getAppUserByUsername(anyString());
        verify(mockView, times(3)).getSignUpUsername();
        verify(mockView, times(2)).getSignUpEmail();
        verify(mockView, times(2)).getSignUpPassword();
        verify(mockView, times(1)).showMessage("Error creating the account");

    }

    @Test
    public void testDoSignUpFail(){

        when(mockModel.getAppUserByUsername(anyString())).thenReturn(null);
        when(mockModel.doSignUp(any(AppUser.class))).thenReturn(true);
        when(mockView.getSignUpUsername()).thenReturn("Leandro Stanzione");
        when(mockView.getSignUpEmail()).thenReturn("mail@mail.com");
        when(mockView.getSignUpPassword()).thenReturn("Password");

        loginPresenter.doSignUp();

        verify(mockModel, times(1)).doSignUp(any(AppUser.class));
        verify(mockModel, times(1)).getAppUserByUsername(anyString());
        verify(mockView, times(3)).getSignUpUsername();
        verify(mockView, times(2)).getSignUpEmail();
        verify(mockView, times(2)).getSignUpPassword();
        verify(mockView, times(1)).showMessage("Account created successfully!");

    }

    @Test
    public void testSignInButtonClicked(){

        loginPresenter.signInButtonClicked();

        verify(mockView, times(1)).showSignInDialog();

    }

    @Test
    public void testSignUpButtonClicked(){

        loginPresenter.signUpButtonClicked();

        verify(mockView, times(1)).showSignUpDialog();

    }

}
