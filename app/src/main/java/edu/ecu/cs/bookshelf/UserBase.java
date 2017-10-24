package edu.ecu.cs.bookshelf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.ecu.cs.bookshelf.database.BookshelfCursorWrapper;
import edu.ecu.cs.bookshelf.database.BookshelfDbSchema;
import edu.ecu.cs.bookshelf.database.BookshelfDbSchema.UserTable;
import edu.ecu.cs.bookshelf.database.BookshelfHelper;

/**
 * Created by Jennifer on 10/8/2017.
 */

public class UserBase {
    private static UserBase sUserBase;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static UserBase getUserBase(Context context) {
        if (sUserBase == null) {
            sUserBase = new UserBase(context);
        }
        return sUserBase;
    }
    
    private UserBase(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new BookshelfHelper(mContext).getWritableDatabase();
    }

    public void addUser(User user) {
        ContentValues values = getContentValues(user);
        mDatabase.insert(UserTable.NAME, null, values);
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        BookshelfCursorWrapper cursor = queryUsers(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                users.add(cursor.getUser());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return users;
    }

    public User getUser(UUID id) {
        BookshelfCursorWrapper cursor = queryUsers(UserTable.Cols.UUID + " = ?", new String[] { id.toString() });

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }

    public User getUserByEmail(String emailAddress) {
        BookshelfCursorWrapper cursor = queryUsers(UserTable.Cols.EMAIL_ADDRESS + " = ?", new String[] { emailAddress.toString() });

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }

    public boolean exists(String emailAddress) {
        BookshelfCursorWrapper cursor = queryUsers(UserTable.Cols.EMAIL_ADDRESS + " = ?", new String[] { emailAddress });

        try {
            if (cursor.getCount() == 0) {
                return false;
            }

            return true;
        } finally {
            cursor.close();
        }
    }

    public void updateUser(User user) {
        String uuidString = user.getId().toString();
        ContentValues values = getContentValues(user);

        mDatabase.update(UserTable.NAME, values, UserTable.Cols.UUID + " = ?", new String[] { uuidString });
    }

    private BookshelfCursorWrapper queryUsers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(UserTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new BookshelfCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UserTable.Cols.UUID, user.getId().toString());
        values.put(UserTable.Cols.EMAIL_ADDRESS, user.getEmailAddress());
        values.put(UserTable.Cols.FIRST_NAME, user.getFirstName());
        values.put(UserTable.Cols.LAST_NAME, user.getLastName());
        values.put(UserTable.Cols.ENCRYPTED_PASSWORD, user.getEncryptedPassword());
        values.put(UserTable.Cols.DATE_CREATED, user.getDateCreated().getTime());
        values.put(UserTable.Cols.DATE_MODIFIED, user.getDateModified().getTime());

        return values;
    }
}
