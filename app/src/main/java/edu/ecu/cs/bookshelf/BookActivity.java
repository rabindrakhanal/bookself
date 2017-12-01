package edu.ecu.cs.bookshelf;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.UUID;

/**
 * Created by Jennifer on 10/29/2017.
 */

public class BookActivity extends SingleFragmentActivity {

    public static final String EXTRA_BOOK_ID = "edu.ecu.cs.bookshelf.book_id";
    private static final int REQUEST_ERROR = 0;

    public static Intent newIntent(Context packageContext, UUID bookId) {
        Intent intent = new Intent(packageContext, BookActivity.class);
        intent.putExtra(EXTRA_BOOK_ID, bookId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return BookFragment.newInstance();
    }

    @Override
    protected void onResume()
    {
      super.onResume();

      GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
      int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);
      //checkPlayServices();
      if (errorCode != ConnectionResult.SUCCESS)
      {
        Dialog errorDialog = apiAvailability.getErrorDialog(this,
        errorCode,
        REQUEST_ERROR,
        new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
        // Leave if services are unavailable.
        finish();
                                       }
                    });

        errorDialog.show();
      }
    }

    private boolean checkPlayServices() {
    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    if (resultCode != ConnectionResult.SUCCESS)
    {
       if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
       {
       GooglePlayServicesUtil.getErrorDialog(resultCode, this, REQUEST_ERROR).show();
       }
       else
       {
       finish();
       }
       return false;
       }
       return true;
       }
}
