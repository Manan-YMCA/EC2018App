package com.manan.dev.ec2018app.Fragments;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manan.dev.ec2018app.R;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.Arrays;

/**
 * Created by yatindhingra on 02/03/18.
 */

public class FragmentFbLogin extends DialogFragment {

    TextView skipLogin;
    LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProgressBar barLogin;
    private FirebaseAuth mAuth;

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fblogin_dialog_box, container, false);

        if(mContext == null){
            mContext = getActivity();
        }

        skipLogin = (TextView) rootView.findViewById(R.id.tv_skip_fb_login);
        loginButton = (LoginButton) rootView.findViewById(R.id.login_button);
        FacebookSdk.sdkInitialize(mContext);
        callbackManager = CallbackManager.Factory.create();
        barLogin = (ProgressBar) rootView.findViewById(R.id.pb_login);

        mAuth = FirebaseAuth.getInstance();

        skipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbLoginButton activity = (fbLoginButton) getActivity();
                activity.fbStatus(false, null);
                dismiss();
            }
        });


        final String EMAIL = "email";

        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        loginButton.setFragment(this);
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                barLogin.setVisibility(View.VISIBLE);
                skipLogin.setClickable(false);
                skipLogin.setLongClickable(false);
                skipLogin.setVisibility(View.INVISIBLE);
                getDialog().setCancelable(false);
                AccessToken accessToken = loginResult.getAccessToken();
                fbLoginButton activity = (fbLoginButton) mContext;
                handleFacebookAccessToken(loginResult.getAccessToken());
                activity.fbStatus(true, accessToken.getUserId());
            }

            @Override
            public void onCancel() {
                MDToast.makeText(mContext, "Facebook login cancelled", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });

        return rootView;
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            barLogin.setVisibility(View.GONE);
                            dismiss();
                        } else {
                            // If sign in fails, display a message to the user.

                            MDToast.makeText(mContext, "Authentication failed.", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                            dismiss();
                        }
                    }
                });
    }

    public interface fbLoginButton {
        void fbStatus(Boolean status, String userId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        fbLoginButton activity = (fbLoginButton) mContext;
        activity.fbStatus(false, null);
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
