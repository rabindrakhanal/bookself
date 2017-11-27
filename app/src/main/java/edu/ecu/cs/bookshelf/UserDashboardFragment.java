package edu.ecu.cs.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by Jennifer on 10/23/2017.
 */

public class UserDashboardFragment extends Fragment {

    private Button mFindBookButton;
    private UUID mUserId;

    public static UserDashboardFragment newInstance() {
        UserDashboardFragment fragment = new UserDashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = (UUID) getActivity().getIntent().getSerializableExtra(BookActivity.EXTRA_USER_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_dashboard, container, false);

        mFindBookButton = (Button) view.findViewById(R.id.find_books);
        mFindBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = BookListActivity.newIntent(getActivity(), mUserId);
                startActivity(intent);
            }
        });

        return view;
    }
}
