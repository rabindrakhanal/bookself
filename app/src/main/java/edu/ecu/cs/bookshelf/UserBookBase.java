package edu.ecu.cs.bookshelf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.ecu.cs.bookshelf.UserBook;
import edu.ecu.cs.bookshelf.database.BookshelfCursorWrapper;
import edu.ecu.cs.bookshelf.database.BookshelfDbSchema.UserBookTable;
import edu.ecu.cs.bookshelf.database.BookshelfHelper;

/**
 * Created by Jennifer on 10/8/2017.
 */

public class UserBookBase {
    private static UserBookBase sUserBookBase;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static UserBookBase getUserBookBase(Context context) {
        if (sUserBookBase == null) {
            sUserBookBase = new UserBookBase(context);
        }
        return sUserBookBase;
    }

    private UserBookBase(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new BookshelfHelper(mContext).getWritableDatabase();
    }

    public void addUserBook(UserBook userBook) {
        ContentValues values = getContentValues(userBook);
        mDatabase.insert(UserBookTable.NAME, null, values);
    }

    public List<UserBook> getUserBooks(UUID userId) {
        List<UserBook> userBooks = new ArrayList<>();

        BookshelfCursorWrapper cursor = queryUserBooks(UserBookTable.Cols.USER_ID + " = ?",
                new String[] { userId.toString() });

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                userBooks.add(cursor.getUserBook());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return userBooks;
    }

    public UserBook getUserBook(UUID bookId, UUID userId) {
        BookshelfCursorWrapper cursor = queryUserBooks(UserBookTable.Cols.BOOK_ID + " = ? AND " +
                UserBookTable.Cols.USER_ID + " = ?", new String[] { bookId.toString(), userId.toString() });

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getUserBook();
        } finally {
            cursor.close();
        }
    }

    public void updateUserBook(UserBook userBook) {
        String uuidString = userBook.getId().toString();
        ContentValues values = getContentValues(userBook);

        mDatabase.update(UserBookTable.NAME, values, UserBookTable.Cols.UUID + " = ?", new String[] { uuidString });
    }

    private BookshelfCursorWrapper queryUserBooks(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(UserBookTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new BookshelfCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(UserBook userBook) {
        ContentValues values = new ContentValues();
        values.put(UserBookTable.Cols.UUID, userBook.getId().toString());
        values.put(UserBookTable.Cols.USER_ID, userBook.getUserId().toString());
        values.put(UserBookTable.Cols.BOOK_ID, userBook.getBookId().toString());
        values.put(UserBookTable.Cols.READ, userBook.getRead() ? 1 : 0);
        values.put(UserBookTable.Cols.FAVORITE, userBook.getFavorite() ? 1 : 0);
        values.put(UserBookTable.Cols.BORROWED, userBook.getBorrowed() ? 1 : 0);
        values.put(UserBookTable.Cols.DATE_CREATED, userBook.getDateCreated().getTime());
        values.put(UserBookTable.Cols.DATE_MODIFIED, userBook.getDateModified().getTime());

        return values;
    }
}
