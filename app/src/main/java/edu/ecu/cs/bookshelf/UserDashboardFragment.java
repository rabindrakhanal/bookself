package edu.ecu.cs.bookshelf;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jennifer on 10/23/2017.
 */

public class UserDashboardFragment extends Fragment {

    private static final String EXTRA_USERID = "userid";

    private Button mFindBookButton;
    private RecyclerView mRecyclerView;
    private UserBookAdapter mUserBookAdapter;
    private UUID mUserId;

    public static UserDashboardFragment newInstance() {
        UserDashboardFragment fragment = new UserDashboardFragment();
        return fragment;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_user_dashboard, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting_update_password:
                Intent intent = UpdatePasswordActivity.newIntent(getActivity(), mUserId);
                startActivity(intent);
                return true;
            case R.id.launch_map:
                Intent i = new Intent(getActivity(), BookMapsActivity.class);
                i.putExtra(EXTRA_USERID, mUserId);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = LoggedInUser.getLoggedInUser(getActivity()).getUserId();
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_dashboard, container, false);

        mFindBookButton = (Button) view.findViewById(R.id.find_books);
        mFindBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = BookListActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.user_book_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        List<Book> books = new ArrayList<>();

        if (mUserId != null) {
            // Get ids for all books on user's shelf
            UserBookBase userBookBase = UserBookBase.getUserBookBase(getActivity());
            List<UserBook> usersBooks = userBookBase.getUserBooks(mUserId);
            // Get book info for each book on shelf
            BookBase bookBase = BookBase.getBookBase(getActivity());

            for (int i = 0; i < usersBooks.size(); i++) {
                books.add(bookBase.getBook(usersBooks.get(i).getBookId()));
            }

            if (mUserBookAdapter == null) {
                mUserBookAdapter = new UserBookAdapter(books);
                mRecyclerView.setAdapter(mUserBookAdapter);
            } else {
                mUserBookAdapter.setBooks(books);
                mUserBookAdapter.notifyDataSetChanged();
            }
        }
    }

    private class UserBookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Book mBook;
        private TextView mTitleTextView;
        private TextView mAuthorTextView;

        public UserBookHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_book, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.book_title);
            mAuthorTextView = (TextView) itemView.findViewById(R.id.book_author);
        }

        public void bind(Book book) {
            mBook = book;
            mTitleTextView.setText(mBook.getTitle());
            mAuthorTextView.setText(mBook.getAuthor());

            // Update text color of book title based on user data; favorite books get priority
            UserBook userBook = UserBookBase.getUserBookBase(getActivity()).getUserBook(mBook.getId(), mUserId);
            if(userBook.getRead()){
                mTitleTextView.setTextColor(Color.parseColor("#5A11D9"));
            } else if(userBook.getBorrowed()){
                mTitleTextView.setTextColor(Color.parseColor("#E01511"));
            } else if(userBook.getFavorite()){
                mTitleTextView.setTextColor(Color.parseColor("#11E059"));
            } else{
                mTitleTextView.setTextColor(Color.BLACK);
            }
        }

        @Override
        public void onClick(View view) {
            Intent intent = BookActivity.newIntent(getActivity(), mBook.getId());
            startActivity(intent);
        }
    }

    private class UserBookAdapter extends RecyclerView.Adapter<UserBookHolder> {
        private List<Book> mBooks;
        public UserBookAdapter(List<Book> books) {
            mBooks = books;
        }

        @Override
        public UserBookHolder
        onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new UserBookHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(UserBookHolder holder, int position) {
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
}
