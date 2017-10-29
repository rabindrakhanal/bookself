package edu.ecu.cs.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Jennifer on 10/23/2017.
 */

public class LoginFragment extends Fragment {

    private EditText mEmailAddress;
    private EditText mPassword;
    private Button mSubmitButton;
    private Button mSignUpButton;

    private HashMap<String, String> mUser = new HashMap<String, String>();

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mEmailAddress = (EditText) view.findViewById(R.id.email_address_text_input);
        mEmailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // TODO
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mUser.put("emailAddress", charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //TODO
            }
        });

        mPassword = (EditText) view.findViewById(R.id.password_text_input);
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // TODO
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mUser.put("encryptedPassword", charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //TODO
            }
        });

        mSubmitButton = (Button) view.findViewById(R.id.login_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (UserBase.getUserBase(getActivity()).exists(mUser.get("emailAddress"))) {
                User user = UserBase.getUserBase(getActivity()).getUserByEmail(mUser.get("emailAddress"));
                if (user.getEncryptedPassword().equals(mUser.get("encryptedPassword"))) {
                    Intent intent = UserDashboardActivity.newIntent(getActivity());
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), R.string.incorrect_password_toast, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), R.string.no_existing_account_toast, Toast.LENGTH_SHORT).show();
            }
            }
        });

        mSignUpButton = (Button) view.findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        return view;
    }
}
