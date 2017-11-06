package edu.ecu.cs.bookshelf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import edu.ecu.cs.bookshelf.database.BookshelfCursorWrapper;
import edu.ecu.cs.bookshelf.database.BookshelfDbSchema.BookTable;
import edu.ecu.cs.bookshelf.database.BookshelfHelper;

/**
 * Created by Jennifer on 10/8/2017.
 */

public class BookBase {
    private static BookBase sBookBase;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static BookBase getBookBase(Context context) {
        if (sBookBase == null) {
            sBookBase = new BookBase(context);
        }
        return sBookBase;
    }

    private BookBase(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new BookshelfHelper(mContext).getWritableDatabase();
    }

    public void addBook(Book book) {
        ContentValues values = getContentValues(book);
        mDatabase.insert(BookTable.NAME, null, values);
    }

    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();

        BookshelfCursorWrapper cursor = queryBooks(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                books.add(cursor.getBook());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return books;
    }

    public Book getBook(UUID id) {
        BookshelfCursorWrapper cursor = queryBooks(BookTable.Cols.UUID + " = ?", new String[] { id.toString() });

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getBook();
        } finally {
            cursor.close();
        }
    }

    public void updateBook(Book book) {
        String uuidString = book.getId().toString();
        ContentValues values = getContentValues(book);

        mDatabase.update(BookTable.NAME, values, BookTable.Cols.UUID + " = ?", new String[] { uuidString });
    }

    private BookshelfCursorWrapper queryBooks(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(BookTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new BookshelfCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Book book) {
        ContentValues values = new ContentValues();
        values.put(BookTable.Cols.UUID, book.getId().toString());
        values.put(BookTable.Cols.TITLE, book.getTitle());
        values.put(BookTable.Cols.AUTHOR, book.getAuthor());
        values.put(BookTable.Cols.DATE_PUBLISHED, book.getDatePublished().getTime());
        values.put(BookTable.Cols.EDITION, book.getEdition());
        values.put(BookTable.Cols.PAGE_COUNT, book.getPageCount());
        values.put(BookTable.Cols.FORMAT, book.getEdition());
        values.put(BookTable.Cols.DATE_CREATED, book.getDateCreated().getTime());
        values.put(BookTable.Cols.DATE_MODIFIED, book.getDateModified().getTime());

        return values;
    }

    // Remove once book API is in place
    public void addSampleData() {
        Book book1 = new Book();
        book1.setTitle("Great Expectations");
        book1.setAuthor("Charles Dickens");
        book1.setDatePublished(new Date());
        book1.setEdition("Kindle edition");
        book1.setPageCount(380);
        book1.setFormat("epub");
        addBook(book1);

        Book book2 = new Book();
        book2.setTitle("War of the Worlds");
        book2.setAuthor("H. G. Wells");
        book2.setDatePublished(new Date());
        book2.setEdition("Kindle edition");
        book2.setPageCount(180);
        book2.setFormat("epub");
        addBook(book2);

        Book book3 = new Book();
        book3.setTitle("The Jungle Book");
        book3.setAuthor("Rudyard Kipling");
        book3.setDatePublished(new Date());
        book3.setEdition("Kindle edition");
        book3.setPageCount(150);
        book3.setFormat("epub");
        addBook(book3);
    }
}
