package edu.ecu.cs.bookshelf.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import edu.ecu.cs.bookshelf.Book;
import edu.ecu.cs.bookshelf.User;
import edu.ecu.cs.bookshelf.UserBook;
import edu.ecu.cs.bookshelf.database.BookshelfDbSchema.BookTable;
import edu.ecu.cs.bookshelf.database.BookshelfDbSchema.UserBookTable;
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

    public Book getBook() {
        String uuidString = getString(getColumnIndex(BookTable.Cols.UUID));
        String title = getString(getColumnIndex(BookTable.Cols.TITLE));
        String author = getString(getColumnIndex(BookTable.Cols.AUTHOR));
        long datePublished = getLong(getColumnIndex(BookTable.Cols.DATE_PUBLISHED));
        String edition = getString(getColumnIndex(BookTable.Cols.EDITION));
        int pageCount = getInt(getColumnIndex(BookTable.Cols.PAGE_COUNT));
        String format = getString(getColumnIndex(BookTable.Cols.FORMAT));
        long dateCreated = getLong(getColumnIndex(BookTable.Cols.DATE_CREATED));
        long dateModified = getLong(getColumnIndex(BookTable.Cols.DATE_MODIFIED));
        double latitude = getDouble(getColumnIndex(BookTable.Cols.LATITUDE));
        double longitude = getDouble(getColumnIndex(BookTable.Cols.LONGITUDE));

        Book book = new Book(UUID.fromString(uuidString));
        book.setTitle(title);
        book.setAuthor(author);
        book.setDatePublished(new Date(datePublished));
        book.setEdition(edition);
        book.setPageCount(pageCount);
        book.setFormat(format);
        book.setDateCreated(new Date(dateCreated));
        book.setDateModified(new Date(dateModified));
        book.setLatitude(latitude);
        book.setLongitude(longitude);

        return book;
    }

    public UserBook getUserBook() {
        String uuidString = getString(getColumnIndex(UserBookTable.Cols.UUID));
        String userIdString = getString(getColumnIndex(UserBookTable.Cols.USER_ID));
        String bookIdString = getString(getColumnIndex(UserBookTable.Cols.BOOK_ID));
        int read = getInt(getColumnIndex(UserBookTable.Cols.READ));
        int favorite = getInt(getColumnIndex(UserBookTable.Cols.FAVORITE));
        int borrowed = getInt(getColumnIndex(UserBookTable.Cols.BORROWED));
        long dateCreated = getLong(getColumnIndex(UserBookTable.Cols.DATE_CREATED));
        long dateModified = getLong(getColumnIndex(UserBookTable.Cols.DATE_MODIFIED));

        UserBook userBook = new UserBook(UUID.fromString(uuidString));
        userBook.setUserId(UUID.fromString(userIdString));
        userBook.setBookId(UUID.fromString(bookIdString));
        userBook.setRead(read != 0);
        userBook.setFavorite(favorite != 0);
        userBook.setBorrowed(borrowed != 0);
        userBook.setDateCreated(new Date(dateCreated));
        userBook.setDateModified(new Date(dateModified));

        return userBook;
    }
}
