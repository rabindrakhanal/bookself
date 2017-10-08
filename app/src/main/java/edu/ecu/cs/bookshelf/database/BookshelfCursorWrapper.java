package edu.ecu.cs.bookshelf.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import edu.ecu.cs.bookshelf.User;
import edu.ecu.cs.bookshelf.database.BookshelfDbSchema.UserTable;

/**
 * Created by Jennifer on 10/8/2017.
 */

public class BookshelfCursorWrapper extends CursorWrapper {
    public BookshelfCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        String uuidString = getString(getColumnIndex(UserTable.Cols.UUID));
        String emailAddress = getString(getColumnIndex(UserTable.Cols.EMAIL_ADDRESS));
        String firstName = getString(getColumnIndex(UserTable.Cols.FIRST_NAME));
        String lastName = getString(getColumnIndex(UserTable.Cols.LAST_NAME));
        String encrytpedPassword = getString(getColumnIndex(UserTable.Cols.ENCRYPTED_PASSWORD));
        long dateCreated = getLong(getColumnIndex(UserTable.Cols.DATE_CREATED));
        long dateModified = getLong(getColumnIndex(UserTable.Cols.DATE_MODIFIED));

        User user = new User(UUID.fromString(uuidString));
        user.setEmailAddress(emailAddress);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEncryptedPassword(encrytpedPassword);
        user.setDateCreated(new Date(dateCreated));
        user.setDateModified(new Date(dateModified));

        return user;
    }
}
