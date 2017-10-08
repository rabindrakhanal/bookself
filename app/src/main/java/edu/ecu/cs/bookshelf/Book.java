package edu.ecu.cs.bookshelf;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Jennifer on 10/8/2017.
 */

public class Book implements Serializable {
    private UUID mId;
    private String mTitle;
    private String mAuthor;
    private Date mDatePublished;
    private String mEdition;
    private int mPageCount;
    private String mFormat;
    private Date mDateCreated;
    private Date mDateModified;

    public Book() {
        this(UUID.randomUUID());
    }

    public Book(UUID id) {
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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public Date getDatePublished() {
        return mDatePublished;
    }

    public void setDatePublished(Date datePublished) {
        mDatePublished = datePublished;
    }

    public String getEdition() {
        return mEdition;
    }

    public void setEdition(String edition) {
        mEdition = edition;
    }

    public int getPageCount() {
        return mPageCount;
    }

    public void setPageCount(int pageCount) {
        mPageCount = pageCount;
    }

    public String getFormat() {
        return mFormat;
    }

    public void setFormat(String format) {
        mFormat = format;
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
