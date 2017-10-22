package edu.ecu.cs.bookshelf;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Jennifer on 10/8/2017.
 */

public class User implements Serializable {
    private UUID mId;
    private String mEmailAddress;
    private String mFirstName;
    private String mLastName;
    private String mEncryptedPassword;
    private Date mDateCreated;
    private Date mDateModified;

    public User() {
        this(UUID.randomUUID());
    }

    public User(UUID id) {
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

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        mEmailAddress = emailAddress;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEncryptedPassword() {
        return mEncryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        //TODO: encrypt password here
        mEncryptedPassword = encryptedPassword;
    }

    public Date getDateCreated() {
        return mDateCreated;
    }

    public Date setDateCreated(Date dateCreated) {
        return dateCreated;
    }

    public Date getDateModified() {
        return mDateModified;
    }

    public void setDateModified(Date dateModified) {
        mDateModified = dateModified;
    }
}
