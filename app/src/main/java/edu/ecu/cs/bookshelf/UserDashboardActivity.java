package edu.ecu.cs.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Jennifer on 10/23/2017.
 */

public class UserDashboardActivity extends SingleFragmentActivity {

    private final static String EXTRA_USER_ID = "user_id";

    public static Intent newIntent(Context packageContext, UUID id) {
        Intent intent = new Intent(packageContext, UserDashboardActivity.class);
        intent.putExtra(EXTRA_USER_ID, id);
        return intent;
    }

    @Override
    protected Fragment createFragment() {

        return UserDashboardFragment.newInstance((UUID)getIntent().getSerializableExtra(EXTRA_USER_ID));
    }
}
