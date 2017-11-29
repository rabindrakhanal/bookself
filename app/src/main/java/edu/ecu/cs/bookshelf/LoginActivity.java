package edu.ecu.cs.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.UUID;

/**
 * Created by Jennifer on 10/23/2017.
 */

public class LoginActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, LoginActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance();
    }


    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build();

    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
}
