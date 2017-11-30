package edu.ecu.cs.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.UUID;

/**
 * Created by RabindraK on 10/31/2017.
 */

public class UpdatePasswordActivity extends SingleFragmentActivity {

    private final static String EXTRA_USER_ID = "user_id";

    @Override
    protected Fragment createFragment() {
        return UpdatePasswordFragment.newInstance((UUID)getIntent().getSerializableExtra(EXTRA_USER_ID));
    }

    public static Intent newIntent(Context context, UUID id) {
        Intent intent = new Intent(context, UpdatePasswordActivity.class);
        intent.putExtra(EXTRA_USER_ID, id);
        return intent;
    }

}
