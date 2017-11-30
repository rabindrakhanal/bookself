package edu.ecu.cs.bookshelf;

/**
 * Created by venkateshpala on 11/29/17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;

public class BookView implements Parcelable {
    private String openLibraryId;
    private String author;
    private String title;
    private String publisher;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getOpenLibraryId() {
        return openLibraryId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    // Get book cover from covers API
    public String getCoverUrl() {
        return "http://covers.openlibrary.org/b/olid/" + openLibraryId + "-L.jpg?default=false";
    }

    // Returns a BookView given the expected JSON
    public static BookView fromJson(JSONObject jsonObject) {
        BookView bookView = new BookView();
        try {
            // Deserialize json into object fields
            // Check if a cover edition is available
            if (jsonObject.has("cover_edition_key")) {
                bookView.openLibraryId = jsonObject.getString("cover_edition_key");
            } else if(jsonObject.has("edition_key")) {
                final JSONArray ids = jsonObject.getJSONArray("edition_key");
                bookView.openLibraryId = ids.getString(0);
            }
            bookView.title = jsonObject.has("title_suggest") ? jsonObject.getString("title_suggest") : "";
            bookView.author = getAuthor(jsonObject);
            bookView.publisher = getPublisher(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return bookView;
    }

    // Return comma separated author list when there is more than one author
    private static String getAuthor(final JSONObject jsonObject) {
        try {
            final JSONArray authors = jsonObject.getJSONArray("author_name");
            int numAuthors = authors.length();
            final String[] authorStrings = new String[numAuthors];
            for (int i = 0; i < numAuthors; ++i) {
                authorStrings[i] = authors.getString(i);
            }
            return TextUtils.join(", ", authorStrings);
        } catch (JSONException e) {
            return "";
        }
    }    private static String getPublisher(final JSONObject jsonObject) {
        try {
            final JSONArray pubs = jsonObject.getJSONArray("publisher");
            int numPubs = pubs.length();
            final String[] pubStrings = new String[numPubs];
            for (int i = 0; i < numPubs; ++i) {
                pubStrings[i] = pubs.getString(i);
            }
            return TextUtils.join(", ", pubStrings);
        } catch (JSONException e) {
            return "";
        }
    }

    // Decodes array of book json results into business model objects
    public static ArrayList<BookView> fromJson(JSONArray jsonArray) {
        ArrayList<BookView> bookViews = new ArrayList<BookView>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject bookJson = null;
            try {
                bookJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            BookView bookView = BookView.fromJson(bookJson);
            if (bookView != null) {
                bookViews.add(bookView);
            }
        }
        return bookViews;
    }

    public BookView() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.openLibraryId);
        dest.writeString(this.author);
        dest.writeString(this.title);
        dest.writeString(this.publisher);
    }

    private BookView(Parcel in) {
        this.openLibraryId = in.readString();
        this.author = in.readString();
        this.title = in.readString();
        this.publisher = in.readString();
    }

    public static final Creator<BookView> CREATOR = new Creator<BookView>() {
        public BookView createFromParcel(Parcel source) {
            return new BookView(source);
        }

        public BookView[] newArray(int size) {
            return new BookView[size];
        }
    };
}
