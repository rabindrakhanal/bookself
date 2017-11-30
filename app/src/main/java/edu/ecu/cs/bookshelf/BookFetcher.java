package edu.ecu.cs.bookshelf;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jennifer on 11/28/2017.
 */

public class BookFetcher {

    private static final String TAG = "BookFetcher";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Book> fetchItems() {
        // Example = http://openlibrary.org/subjects/overdrive.json?limit=10

        List<Book> items = new ArrayList<>();

        try {
            String url = Uri.parse("http://openlibrary.org/subjects/overdrive.json").buildUpon()
                    .appendQueryParameter("limit", "25")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return items;
    }

    private void parseItems(List<Book> items, JSONObject jsonBody) throws IOException, JSONException {
        JSONArray bookJsonArray = jsonBody.getJSONArray("works");
        for (int i = 0; i < bookJsonArray.length(); i++) {
            JSONObject bookJsonObject = bookJsonArray.getJSONObject(i);
            Book item = new Book();
            item.setTitle(bookJsonObject.getString("title"));
            item.setAuthor(bookJsonObject.getJSONArray("authors").getJSONObject(0).getString("name"));
            item.setDatePublished(new Date());
            item.setEdition("Kindle Edition");
            item.setPageCount(150);
            item.setFormat("epub");
            if (bookJsonObject.has("cover_i")) {
                item.setCoverUrl("http://covers.openlibrary.org/b/id/"+bookJsonObject.getString("cover_i")+"-S.jpg");
            }
            items.add(item);
        }
    }
}
