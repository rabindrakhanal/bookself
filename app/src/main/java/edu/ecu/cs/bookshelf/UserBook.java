package edu.ecu.cs.bookshelf;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Jennifer on 10/8/2017.
 */

public class UserBook implements Serializable {
    private UUID mId;
    private UUID mUserId;
    private UUID mBookId;
    private Boolean mRead;
    private Boolean mFavorite;
    private Boolean mBorrowed;
    private Date mDateCreated;
    private Date mDateModified;

    public UserBook() {
        this(UUID.randomUUID());
    }

    public UserBook(UUID id) {
        mId = id;
        mDateCreated = new Date();
        mDateModified = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public UUID getUserId() {
        return mUserId;
    }

    public void setUserId(UUID userId) {
        mUserId = userId;
    }

    public UUID getBookId() {
        return mBookId;
    }

    public void setBookId(UUID bookId) {
        mBookId = bookId;
    }

    public Boolean getRead() {
        return mRead;
    }

    public void setRead(Boolean read) {
        mRead = read;
    }

    public Boolean getFavorite() {
        return mFavorite;
    }

    public void setFavorite(Boolean favorite) {
        mFavorite = favorite;
    }

    public Boolean getBorrowed() {
        return mBorrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        mBorrowed = borrowed;
    }

    public Date getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        mDateCreated = dateCreated;
    }

    public Date getDateModified() {
        return mDateModified;
    }

    public void setDateModified(Date dateModified) {
        mDateModified = dateModified;
    }
}
