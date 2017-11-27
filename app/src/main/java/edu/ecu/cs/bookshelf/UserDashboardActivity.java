package edu.ecu.cs.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Jennifer on 10/23/2017.
 */

public class UserDashboardActivity extends SingleFragmentActivity {

    public static final String EXTRA_USER_ID = "edu.ecu.cs.bookshelf.user_id";

    public static Intent newIntent(Context packageContext, UUID userId) {
        Intent intent = new Intent(packageContext, UserDashboardActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return UserDashboardFragment.newInstance();
    }
}
