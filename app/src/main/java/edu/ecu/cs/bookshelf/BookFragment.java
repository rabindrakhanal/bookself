package edu.ecu.cs.bookshelf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    public static BookFragment newInstance() {
        BookFragment fragment = new BookFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID bookId = (UUID) getActivity().getIntent().getSerializableExtra(BookActivity.EXTRA_BOOK_ID);
        mBook = BookBase.getBookBase(getActivity()).getBook(bookId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        mBookTitleTextView = (TextView) view.findViewById(R.id.book_title);
        mBookTitleTextView.setText(mBook.getTitle());

        mBookAuthorTextView = (TextView) view.findViewById(R.id.book_author);
        mBookAuthorTextView.setText(mBook.getAuthor());

        mBookDateTextView = (TextView) view.findViewById(R.id.book_publication_date);
        mBookDateTextView.setText(mBook.getDatePublished().toString());

        mAddToShelfButton = (Button) view.findViewById(R.id.add_to_shelf_button);
        mAddToShelfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.added_to_shelf, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
