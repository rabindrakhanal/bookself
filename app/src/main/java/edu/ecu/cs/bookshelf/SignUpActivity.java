package edu.ecu.cs.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class SignUpActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext, UUID id) {
        Intent intent = new Intent(packageContext, SignUpActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return SignUpFragment.newInstance();
    }
}
