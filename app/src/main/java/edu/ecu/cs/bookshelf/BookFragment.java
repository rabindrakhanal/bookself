package edu.ecu.cs.bookshelf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Jennifer on 10/29/2017.
 */

public class BookFragment extends Fragment {

    private Book mBook;
    private TextView mBookTitleTextView;
    private TextView mBookAuthorTextView;
    private TextView mBookDateTextView;
    private Button mAddToShelfButton;
    private CheckBox mReadCheckbox;
    private CheckBox mFavoriteCheckbox;
    private CheckBox mBorrowedCheckbox;
    private ImageView mBookCover;

    private UUID mUserId;

    public static BookFragment newInstance() {
        BookFragment fragment = new BookFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID bookId = (UUID) getActivity().getIntent().getSerializableExtra(BookActivity.EXTRA_BOOK_ID);
        mUserId = LoggedInUser.getLoggedInUser(getActivity()).getUserId();
        mBook = BookBase.getBookBase(getActivity()).getBook(bookId);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_book, menu);

        final UserBookBase userBookBase = UserBookBase.getUserBookBase(getActivity());
        final UserBook userBook = userBookBase.getUserBook(mBook.getId(), mUserId);
        menu.getItem(0).setEnabled(false);
        if (userBook != null) {
            menu.getItem(0).setEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_book:
                UserBookBase.getUserBookBase(getActivity()).deleteUserBook(mBook.getId());
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_book, container, false);

        if (mBook.getCoverUrl() != null && mBook.getCoverUrl() != "") {
            mBookCover = (ImageView) view.findViewById(R.id.book_cover);
            Picasso.with(getActivity()).load(mBook.getCoverUrl())
                    .placeholder(R.drawable.ic_book_cover)
                    .into(mBookCover);
        }

        mBookTitleTextView = (TextView) view.findViewById(R.id.book_title);
        mBookTitleTextView.setText(mBook.getTitle());

        mBookAuthorTextView = (TextView) view.findViewById(R.id.book_author);
        mBookAuthorTextView.setText(mBook.getAuthor());

        mBookDateTextView = (TextView) view.findViewById(R.id.book_publication_date);
        mBookDateTextView.setText(mBook.getDatePublished().toString());

        mAddToShelfButton = (Button) view.findViewById(R.id.add_to_shelf_button);
        final UserBookBase userBookBase = UserBookBase.getUserBookBase(getActivity());
        final UserBook userBook = userBookBase.getUserBook(mBook.getId(), mUserId);
        if (userBook != null) {
            enableCheckboxes(view);
        }
        mAddToShelfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserId != null) {
                    UserBook userBook = new UserBook();
                    userBook.setBookId(mBook.getId());
                    userBook.setUserId(mUserId);
                    userBook.setRead(false);
                    userBook.setFavorite(false);
                    userBook.setBorrowed(false);
                    userBook.setDateCreated(new Date());
                    userBook.setDateModified(new Date());
                    UserBookBase.getUserBookBase(getActivity()).addUserBook(userBook);
                    Toast.makeText(getActivity(), R.string.added_to_shelf, Toast.LENGTH_SHORT).show();
                    enableCheckboxes(view);
                } else {
                    Toast.makeText(getActivity(), R.string.error_adding_to_shelf, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void enableCheckboxes(View view) {
        final UserBookBase userBookBase = UserBookBase.getUserBookBase(getActivity());
        final UserBook userBook = userBookBase.getUserBook(mBook.getId(), mUserId);

        mAddToShelfButton.setEnabled(false);
        mAddToShelfButton.setText(R.string.book_added);

        mReadCheckbox = (CheckBox) view.findViewById(R.id.read_checkbox);
        mReadCheckbox.setChecked(userBook.getRead());
        mReadCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                userBook.setRead(b);
                userBookBase.updateUserBook(userBook);
            }
        });

        mFavoriteCheckbox = (CheckBox) view.findViewById(R.id.favorite_checkbox);
        mFavoriteCheckbox.setChecked(userBook.getFavorite());
        mFavoriteCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                userBook.setFavorite(b);
                userBookBase.updateUserBook(userBook);
            }
        });

        mBorrowedCheckbox = (CheckBox) view.findViewById(R.id.borrowed_checkbox);
        mBorrowedCheckbox.setChecked(userBook.getBorrowed());
        mBorrowedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                userBook.setBorrowed(b);
                userBookBase.updateUserBook(userBook);
            }
        });

        mReadCheckbox.setVisibility(View.VISIBLE);
        mFavoriteCheckbox.setVisibility(View.VISIBLE);
        mBorrowedCheckbox.setVisibility(View.VISIBLE);
    }
}
