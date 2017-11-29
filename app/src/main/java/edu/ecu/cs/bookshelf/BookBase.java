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
        values.put(BookTable.Cols.COVER_URL, book.getCoverUrl());

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

        Book book4 = new Book();
        book4.setTitle("A Christmas Carol");
        book4.setAuthor("Charles Dickens");
        book4.setDatePublished(new Date());
        book4.setEdition("Kindle edition");
        book4.setPageCount(150);
        book4.setFormat("epub");
        addBook(book4);

        Book book5 = new Book();
        book5.setTitle("Frankenstein");
        book5.setAuthor("Mary Shelley");
        book5.setDatePublished(new Date());
        book5.setEdition("Kindle edition");
        book5.setPageCount(150);
        book5.setFormat("epub");
        addBook(book5);
        
        Book book6 = new Book();
        book6.setTitle("Moby Dick");
        book6.setAuthor("Herman Melville");
        book6.setDatePublished(new Date());
        book6.setEdition("Kindle edition");
        book6.setPageCount(150);
        book6.setFormat("epub");
        addBook(book6);
        
        Book book7 = new Book();
        book7.setTitle("Leviathan");
        book7.setAuthor("Thomas Hobbes");
        book7.setDatePublished(new Date());
        book7.setEdition("Kindle edition");
        book7.setPageCount(150);
        book7.setFormat("epub");
        addBook(book7);
        
        Book book8 = new Book();
        book8.setTitle("War and Peace");
        book8.setAuthor("Leo Tolstoy");
        book8.setDatePublished(new Date());
        book8.setEdition("Kindle edition");
        book8.setPageCount(150);
        book8.setFormat("epub");
        addBook(book8);
        
        Book book9 = new Book();
        book9.setTitle("Dracula");
        book9.setAuthor("Bram Stoker");
        book9.setDatePublished(new Date());
        book9.setEdition("Kindle edition");
        book9.setPageCount(150);
        book9.setFormat("epub");
        addBook(book9);

        Book book10 = new Book();
        book10.setTitle("The Iliad");
        book10.setAuthor("Homer");
        book10.setDatePublished(new Date());
        book10.setEdition("Kindle edition");
        book10.setPageCount(150);
        book10.setFormat("epub");
        addBook(book10);
        
        Book book11 = new Book();
        book11.setTitle("Treasure Island");
        book11.setAuthor("Robert Louis Stevenson");
        book11.setDatePublished(new Date());
        book11.setEdition("Kindle edition");
        book11.setPageCount(150);
        book11.setFormat("epub");
        addBook(book11);
        
        Book book12 = new Book();
        book12.setTitle("Peter Pan");
        book12.setAuthor("J. M. Barrie");
        book12.setDatePublished(new Date());
        book12.setEdition("Kindle edition");
        book12.setPageCount(150);
        book12.setFormat("epub");
        addBook(book12);
        
        Book book13 = new Book();
        book13.setTitle("The Republic");
        book13.setAuthor("Plato");
        book13.setDatePublished(new Date());
        book13.setEdition("Kindle edition");
        book13.setPageCount(150);
        book13.setFormat("epub");
        addBook(book13);
        
        Book book14 = new Book();
        book14.setTitle("Common Sense");
        book14.setAuthor("Thomas Paine");
        book14.setDatePublished(new Date());
        book14.setEdition("Kindle edition");
        book14.setPageCount(150);
        book14.setFormat("epub");
        addBook(book14);
        
        Book book15 = new Book();
        book15.setTitle("Candide");
        book15.setAuthor("Voltaire");
        book15.setDatePublished(new Date());
        book15.setEdition("Kindle edition");
        book15.setPageCount(150);
        book15.setFormat("epub");
        addBook(book15);
        
        Book book16 = new Book();
        book16.setTitle("The History of the Peloponnesian War");
        book16.setAuthor("Thucydides");
        book16.setDatePublished(new Date());
        book16.setEdition("Kindle edition");
        book16.setPageCount(150);
        book16.setFormat("epub");
        addBook(book16);
        
        Book book17 = new Book();
        book17.setTitle("The Wonderful Wizard of Oz");
        book17.setAuthor("L. Frank Baum");
        book17.setDatePublished(new Date());
        book17.setEdition("Kindle edition");
        book17.setPageCount(150);
        book17.setFormat("epub");
        addBook(book17);
        
        Book book18 = new Book();
        book18.setTitle("The Call of the Wild");
        book18.setAuthor("Jack London");
        book18.setDatePublished(new Date());
        book18.setEdition("Kindle edition");
        book18.setPageCount(150);
        book18.setFormat("epub");
        addBook(book18);
        
        Book book19 = new Book();
        book19.setTitle("Meditations");
        book19.setAuthor("Marcus Aurelius");
        book19.setDatePublished(new Date());
        book19.setEdition("Kindle edition");
        book19.setPageCount(150);
        book19.setFormat("epub");
        addBook(book19);

        Book book20 = new Book();
        book20.setTitle("The Secret Adversary");
        book20.setAuthor("Agatha Christie");
        book20.setDatePublished(new Date());
        book20.setEdition("Kindle edition");
        book20.setPageCount(150);
        book20.setFormat("epub");
        addBook(book20);
    }
}
