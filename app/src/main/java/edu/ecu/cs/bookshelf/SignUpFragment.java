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
 * Created by Jennifer on 10/8/2017.
 */

public class SignUpFragment extends Fragment {

    private EditText mFirstname;
    private EditText mLastname;
    private EditText mEmailAddress;
    private EditText mPassword;
    private EditText mPasswordConfirmation;
    private Button mSubmitButton;

    private HashMap<String, String> mUser = new HashMap<String, String>();

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        mFirstname = (EditText) view.findViewById(R.id.first_name_text_input);
        mFirstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // TODO
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mUser.put("firstName", charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //TODO
            }
        });

        mLastname = (EditText) view.findViewById(R.id.last_name_text_input);
        mLastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // TODO
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mUser.put("lastName", charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //TODO
            }
        });

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

        mPasswordConfirmation = (EditText) view.findViewById(R.id.password_confirmation_text_input);
        mPasswordConfirmation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // TODO
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // TODO
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //TODO
            }
        });

        mSubmitButton = (Button) view.findViewById(R.id.sign_up_submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFirstname.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), R.string.invalid_first_name_toast, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mLastname.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), R.string.invalid_last_name_toast, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEmailAddress.getText().toString().equals("") ||
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(mEmailAddress.getText().toString()).matches()) {
                    Toast.makeText(getActivity(), R.string.invalid_email_toast, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mPassword.getText().toString().equals("") || mPassword.getText().toString().length() < 6) {
                    Toast.makeText(getActivity(), R.string.invalid_password_toast, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mPasswordConfirmation.getText().toString().equals("") || mPasswordConfirmation.getText().toString().length() < 6 ||
                        !mPassword.getText().toString().equals(mPasswordConfirmation.getText().toString())) {
                    Toast.makeText(getActivity(), R.string.invalid_password_confirmation_toast, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Check if user has already registered
                    if (UserBase.getUserBase(getActivity()).exists(mUser.get("emailAddress"))) {
                        Toast.makeText(getActivity(), R.string.existing_account_toast, Toast.LENGTH_SHORT).show();
                    } else {
                        User user = new User();
                        user.setEmailAddress(mUser.get("emailAddress"));
                        user.setFirstName(mUser.get("firstName"));
                        user.setLastName(mUser.get("lastName"));
                        user.setEncryptedPassword(mUser.get("encryptedPassword"));
                        UserBase.getUserBase(getActivity()).addUser(user);

                        String newUserFirstName = UserBase.getUserBase(getActivity()).getUser(user.getId()).getFirstName();
                        String newUserLastName = UserBase.getUserBase(getActivity()).getUser(user.getId()).getLastName();

                        Toast.makeText(getActivity(), "Account created for " + newUserFirstName + " " + newUserLastName + "!",
                                Toast.LENGTH_SHORT).show();

                        LoggedInUser.getLoggedInUser(getActivity()).setUserId(user.getId());
                        Intent intent = NavigationDrawerActivity.newIntent(getActivity());
                        startActivity(intent);
                    }
                }
            }
        });

        return view;
    }
}
