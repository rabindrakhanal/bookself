package edu.ecu.cs.bookshelf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText mFirstname;
    private EditText mLastname;
    private EditText mEmailAddress;
    private EditText mPassword;
    private EditText mPasswordConfirmation;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirstname = (EditText) findViewById(R.id.first_name_text_input);
        mFirstname.addTextChangedListener(new TextWatcher() {
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

        mLastname = (EditText) findViewById(R.id.last_name_text_input);
        mLastname.addTextChangedListener(new TextWatcher() {
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

        mEmailAddress = (EditText) findViewById(R.id.email_address_text_input);
        mEmailAddress.addTextChangedListener(new TextWatcher() {
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

        mPassword = (EditText) findViewById(R.id.password_text_input);
        mPassword.addTextChangedListener(new TextWatcher() {
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

        mPasswordConfirmation = (EditText) findViewById(R.id.password_confirmation_text_input);
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

        mSubmitButton = (Button) findViewById(R.id.sign_up_submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFirstname.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, R.string.invalid_first_name_toast, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mLastname.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, R.string.invalid_last_name_toast, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEmailAddress.getText().toString().equals("") ||
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(mEmailAddress.getText().toString()).matches()) {
                    Toast.makeText(SignUpActivity.this, R.string.invalid_email_toast, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mPassword.getText().toString().equals("") || mPassword.getText().toString().length() < 6) {
                    Toast.makeText(SignUpActivity.this, R.string.invalid_password_toast, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mPasswordConfirmation.getText().toString().equals("") || mPasswordConfirmation.getText().toString().length() < 6 ||
                        !mPassword.getText().toString().equals(mPasswordConfirmation.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, R.string.invalid_password_confirmation_toast, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(SignUpActivity.this, R.string.submit_toast, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
