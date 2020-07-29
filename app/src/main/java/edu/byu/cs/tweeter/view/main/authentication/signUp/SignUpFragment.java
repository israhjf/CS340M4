package edu.byu.cs.tweeter.view.main.authentication.signUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import edu.byu.cs.tweeter.R;

public class SignUpFragment extends Fragment {
    private static final String TAG = "SingUpFragment";
    private Button btn_singUp;
    private EditText editText_alias;
    private EditText editText_password;
    private View view;
    private ProgressDialog loadingBar;
    private String userAlias = "";
    private String password = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        editText_alias = view.findViewById(R.id.edtext_alias);
        editText_password = view.findViewById(R.id.edtext_signup_password);
        btn_singUp = view.findViewById(R.id.btn_singup);
        loadingBar = new ProgressDialog(getActivity());
        retrieveUserInput();
        return view;
    }

    private void retrieveUserInput(){
        btn_singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAlias = String.format("@%s", editText_alias.getText().toString());
                password = editText_password.getText().toString();
                createNewUserAccount();
            }
        });
    }

    private void createNewUserAccount(){
        if (!userAlias.isEmpty() && !password.isEmpty()){
            Toast.makeText(getActivity(), "Starting User Authentication!",
                    Toast.LENGTH_SHORT).show();
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait while authenticating!!");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            OpenRegisterActivity();
            Toast.makeText(getActivity(), "Authentication completed!",
                Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
        }
        else{
            Toast.makeText(getActivity(), "Please fill in both userAlias and password",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void OpenRegisterActivity(){
        Intent intent = new Intent(getActivity(), RegisterUser.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        bundle.putString("userAlias", userAlias);
        bundle.putString("password", password);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
