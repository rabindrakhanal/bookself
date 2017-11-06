package edu.ecu.cs.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Jennifer on 10/23/2017.
 */

public class UserDashboardActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, UserDashboardActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return UserDashboardFragment.newInstance();
    }
}
