package edu.ecu.cs.bookshelf;

import android.content.Context;

import java.util.UUID;

/**
 * Created by Jennifer on 11/27/2017.
 */

public class LoggedInUser {

    private static LoggedInUser sLoggedInUser;
    private Context mContext;
    private UUID mUserId;

    public static LoggedInUser getLoggedInUser(Context context) {
        if (sLoggedInUser == null) {
            sLoggedInUser = new LoggedInUser(context);
        }
        return sLoggedInUser;
    }

    private LoggedInUser(Context context) {
        mContext = context.getApplicationContext();
    }

    public UUID getUserId() {
        return mUserId;
    }

    public void setUserId(UUID userId) {
        mUserId = userId;
    }
}
