package edu.ecu.cs.bookshelf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;


/**
 * Created by RabindraK on 10/31/2017.
 */

public class UpdatePasswordFragment extends Fragment {
    private Button updateButton;
    private EditText old_password;
    private EditText new_password;
    private EditText confirm_password;
    private User user;
    private HashMap<String, String> updatepassword=new HashMap<>();

    private static final String ARG_USER_ID = "user_id";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.activity_update_password_fragment,container,false);

        user = UserBase.getUserBase(getActivity()).getUser((UUID)getArguments().getSerializable(ARG_USER_ID));

        old_password=(EditText) view.findViewById(R.id.old_password);
        old_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updatepassword.put("old_password",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        new_password=(EditText) view.findViewById(R.id.new_password);
        new_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            updatepassword.put("new_password",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        confirm_password=(EditText) view.findViewById(R.id.confirm_password);
        confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updatepassword.put("confirm_password",charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        updateButton=(Button) view.findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(old_password.getText().toString().equals(UserBase.getUserBase(getActivity()).getUser(user.getId()).getEncryptedPassword())){
                    if(new_password.getText().toString().equals(confirm_password.getText().toString())|| new_password.getText().toString()!= null ) {
                        user.setEncryptedPassword(new_password.getText().toString());
                        UserBase.getUserBase(getActivity()).updateUser(user);
                        Toast.makeText(getActivity(),"updateSuccessfull", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }

                }

            }
        });
        return view;
    }

    public static UpdatePasswordFragment newInstance(UUID id) {
        UpdatePasswordFragment fragment = new UpdatePasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_USER_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

}
