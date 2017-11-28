package edu.ecu.cs.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Jennifer on 10/29/2017.
 */

public class BookListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, BookListActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return BookListFragment.newInstance();
    }
}
