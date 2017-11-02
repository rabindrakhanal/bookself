package edu.ecu.cs.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Jennifer on 10/29/2017.
 */

public class BookActivity extends SingleFragmentActivity {

    public static final String EXTRA_BOOK_ID = "edu.ecu.cs.bookshelf.book_id";

    public static Intent newIntent(Context packageContext, UUID bookId) {
        Intent intent = new Intent(packageContext, BookActivity.class);
        intent.putExtra(EXTRA_BOOK_ID, bookId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return BookFragment.newInstance();
    }
}
