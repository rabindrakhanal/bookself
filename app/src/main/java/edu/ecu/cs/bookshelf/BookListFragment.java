package edu.ecu.cs.bookshelf;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jennifer on 10/29/2017.
 */

public class BookListFragment extends Fragment {

    private RecyclerView mBookRecyclerView;
    private BookAdapter mBookAdapter;
    private List<Book> mBookItems = new ArrayList<>();

    public static BookListFragment newInstance() {
        BookListFragment fragment = new BookListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        mBookRecyclerView = (RecyclerView) view.findViewById(R.id.book_recycler_view);
        mBookRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        setupAdapter();

        return view;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mBookRecyclerView.setAdapter(new BookAdapter(mBookItems));
        }
    }

    private void updateUI() {
        BookBase bookBase = BookBase.getBookBase(getActivity());
        List<Book> books = bookBase.getBooks();
        if (mBookAdapter == null) {
            mBookAdapter = new BookAdapter(books);
            mBookRecyclerView.setAdapter(mBookAdapter);
        } else {
            mBookAdapter.setBooks(books);
            mBookAdapter.notifyDataSetChanged();
        }
    }

    private class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Book mBook;
        private TextView mTitleTextView;
        private TextView mAuthorTextView;

        public BookHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_book, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.book_title);
            mAuthorTextView = (TextView) itemView.findViewById(R.id.book_author);
        }

        public void bind(Book book) {
            mBook = book;
            mTitleTextView.setText(mBook.getTitle());
            mAuthorTextView.setText(mBook.getAuthor());
        }

        @Override
        public void onClick(View view) {
            Book selectedBook = BookBase.getBookBase(getActivity()).findBook(mBook);
            if (selectedBook == null) {
                BookBase.getBookBase(getActivity()).addBook(mBook);
                selectedBook = BookBase.getBookBase(getActivity()).getBook(mBook.getId());
            }
            Intent intent = BookActivity.newIntent(getActivity(), selectedBook.getId());
            startActivity(intent);
        }
    }

    private class BookAdapter extends RecyclerView.Adapter<BookHolder> {
        private List<Book> mBooks;
        public BookAdapter(List<Book> books) {
            mBooks = books;
        }

        @Override
        public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new BookHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(BookHolder holder, int position) {
            Book book = mBooks.get(position);
            holder.bind(book);
        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }

        public void setBooks(List<Book> books) {
            mBooks = books;
        }
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,List<Book>> {
        @Override
        protected List<Book> doInBackground(Void... params) {
            return new BookFetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(List<Book> items) {
            mBookItems = items;
            setupAdapter();
        }
    }
}
