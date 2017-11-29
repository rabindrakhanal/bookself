package edu.ecu.cs.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private final static String ARG_USER_ID = "user_id";

    private User mUser;

    public static UserDashboardFragment newInstance(UUID userId) {
        UserDashboardFragment fragment = new UserDashboardFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_USER_ID, userId);
        fragment.setArguments(bundle);
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
                Intent intent = UpdatePasswordActivity.newIntent(getActivity(), mUser.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_dashboard, container, false);

        mUser = UserBase.getUserBase(getActivity()).getUser((UUID)getArguments().getSerializable(ARG_USER_ID));

        mFindBookButton = (Button) view.findViewById(R.id.find_books);
        mFindBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = BookListActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        return view;
    }
}
