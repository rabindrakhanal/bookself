package edu.ecu.cs.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Jennifer on 10/29/2017.
 */


import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class BookListActivity extends AppCompatActivity {
    private ListView lvBooks;
    private BookAdapter bookAdapter;
    private BookClient client;
    private ArrayList<Book> aBooks;
    private ProgressBar mProgressBar;

    //@Bind(R.id.lvBooks) ListView lvBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        ButterKnife.bind(this);

        lvBooks = (ListView) findViewById(R.id.lvBooks);
        aBooks = new ArrayList<>();
        // initialize the adapter
        bookAdapter = new BookAdapter(this, aBooks);

        //add progress footer
        setupListWithFooter();
        // attach the adapter to the ListView
        //lvBooks.setAdapter(bookAdapter);

        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //editItemPosition = position;
                launchBookDetailsActivity(position);
            }
        });

        // Fetch the data remotely
        //fetchBooks("Oscar Wilde");
    }

    public void setupListWithFooter() {
        View footerView = getLayoutInflater().inflate(R.layout.footer_progress, null);
        mProgressBar = (ProgressBar) footerView.findViewById(R.id.pbFooterLoading);
        lvBooks.addFooterView(footerView);
        lvBooks.setAdapter(bookAdapter);

    }

    public void launchBookDetailsActivity(int position) {
        Book book = aBooks.get(position);

        Intent i = new Intent(this, BookDetailActivity.class);
        i.putExtra("book", book);
        startActivity(i);

//        Toast.makeText(getApplicationContext(), "Item clicked: " + book.getAuthor() + " "
//                        + book.getTitle(),
//                Toast.LENGTH_LONG).show();
    }

    // Executes an API call to the OpenLibrary search endpoint, parses the results
    // Converts them into an array of book objects and adds them to the adapter
    private void fetchBooks(String query) {
        client = new BookClient();
        client.getBooks(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs;
                    if(response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("docs");
                        // Parse json array into array of model objects
                        final ArrayList<Book> books = Book.fromJson(docs);
                        // Remove all books from the adapter
                        bookAdapter.clear();
                        // Load model objects into the adapter
                        for (Book book : books) {
                            bookAdapter.add(book); // add book through the adapter
                        }
                        bookAdapter.notifyDataSetChanged();
                        hideProgress();
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_list, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599

//                Toast.makeText(getApplicationContext(), "Search entered: " + query,
//                        Toast.LENGTH_LONG).show();
                showProgress();
                fetchBooks(query);
                //hideProgress();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    //Show progress for progressbar
    void showProgress() {
        Toast.makeText(getApplicationContext(), "Starting progress", Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    //hide progress for progressbar
    void hideProgress() {
        Toast.makeText(getApplicationContext(), "Stopping progress", Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
