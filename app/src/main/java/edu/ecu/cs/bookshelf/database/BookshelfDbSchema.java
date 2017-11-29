package edu.ecu.cs.bookshelf.database;

/**
 * Created by Jennifer on 10/8/2017.
 */

public class BookshelfDbSchema {
    public static final class BookTable {
        public static final String NAME = "books";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String AUTHOR = "author";
            public static final String DATE_PUBLISHED = "date_published";
            public static final String EDITION = "edition";
            public static final String PAGE_COUNT = "page_count";
            public static final String FORMAT = "format";
            public static final String DATE_CREATED = "date_created";
            public static final String DATE_MODIFIED = "date_modified";
            public static final String COVER_URL = "cover_url";
        }
    }

    public static final class UserTable {
        public static final String NAME = "users";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String EMAIL_ADDRESS = "email_address";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String ENCRYPTED_PASSWORD = "encrypted_password";
            public static final String DATE_CREATED = "date_created";
            public static final String DATE_MODIFIED = "date_modified";
        }
    }

    public static final class UserBookTable {
        public static final String NAME = "user_books";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String USER_ID = "user_id";
            public static final String BOOK_ID = "book_id";
            public static final String READ = "read";
            public static final String FAVORITE = "favorite";
            public static final String BORROWED = "borrowed";
            public static final String DATE_CREATED = "date_created";
            public static final String DATE_MODIFIED = "date_modified";
        }
    }
}
