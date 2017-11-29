package edu.ecu.cs.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;




public class LoginFragment extends Fragment {

    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "SignInActivity";
    private EditText mEmailAddress;
    private EditText mPassword;
    private Button mSubmitButton;
    private Button mSignUpButton;

    private HashMap<String, String> mUser = new HashMap<String, String>();
    private GoogleSignInClient mGoogleSignInClient;

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
                        LoggedInUser.getLoggedInUser(getActivity()).setUserId(user.getId());
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
//                Intent intent = SignUpActivity.newIntent(getActivity());
//                startActivity(intent);
                signIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this.getActivity(), gso);


        return view;


    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this.getActivity());
        if (account != null) {
            Toast.makeText(this.getActivity(), "Already Signed In!!", Toast.LENGTH_SHORT).show();
       updateUI(account);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }


    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            //account.getDisplayName();
            //account.getEmail();


        } else {
            mEmailAddress.setText(R.string.no_email_found);

        }
    }
}
