package edu.ecu.cs.bookshelf.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.ecu.cs.bookshelf.database.BookshelfDbSchema.BookTable;
import edu.ecu.cs.bookshelf.database.BookshelfDbSchema.UserBookTable;
import edu.ecu.cs.bookshelf.database.BookshelfDbSchema.UserTable;

/**
 * Created by Jennifer on 10/8/2017.
 */

public class BookshelfHelper extends SQLiteOpenHelper {
    private static int VERSION = 1;
    private static final String DATABASE_NAME = "bookshelf.db";

    public BookshelfHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + UserTable.NAME +
                "(_id integer primary key autoincrement, " +
                UserTable.Cols.UUID + ", " +
                UserTable.Cols.EMAIL_ADDRESS + ", " +
                UserTable.Cols.FIRST_NAME + ", " +
                UserTable.Cols.LAST_NAME + ", " +
                UserTable.Cols.ENCRYPTED_PASSWORD + ", " +
                UserTable.Cols.DATE_CREATED + ", " +
                UserTable.Cols.DATE_MODIFIED + ")"
        );
//        db.execSQL("create table " + BookTable.NAME);
//        db.execSQL("create table " + UserBookTable.NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
