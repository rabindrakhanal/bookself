package edu.ecu.cs.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Jennifer on 10/23/2017.
 */

public class UserDashboardFragment extends Fragment {

    private Button mButton1;
    private Button mButton2;

    public static UserDashboardFragment newInstance() {
        UserDashboardFragment fragment = new UserDashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_dashboard, container, false);

        mButton1 = (Button) view.findViewById(R.id.activity_button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Started Activity #1", Toast.LENGTH_SHORT).show();
            }
        });

        mButton1 = (Button) view.findViewById(R.id.activity_button2);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Started Activity #2", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
