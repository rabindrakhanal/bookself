package edu.ecu.cs.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Jennifer on 10/29/2017.
 */

public class BookActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext, UUID id) {
        Intent intent = new Intent(packageContext, BookActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return BookFragment.newInstance();
    }
}
