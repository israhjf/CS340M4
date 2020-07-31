package edu.byu.cs.tweeter.view.main.authentication.signIn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.SignInPresenter;
import edu.byu.cs.tweeter.util.Pbkdf2;
import edu.byu.cs.tweeter.view.asyncTasks.SignInTask;
import edu.byu.cs.tweeter.view.main.MainActivity;
import edu.byu.model.services.request.SignInRequest;

public class SignInFragment extends Fragment implements SignInPresenter.View {
    private static final String TAG = "SingInFragment";
    private Button btn_singIn;
    private EditText editText_userAlias;
    private EditText editText_password;
    private String userAlias = "";
    private String password = "";
    private ProgressDialog loadingBar;
    private SignInPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        presenter = new SignInPresenter(this);
        btn_singIn = view.findViewById(R.id.btn_singin);
        editText_userAlias = view.findViewById(R.id.edtext_signin_useralias);
        editText_password = view.findViewById(R.id.edtext_signin_password);
        loadingBar = new ProgressDialog(getActivity());
        btn_singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveInput();
                signIn();
            }
        });
        return view;
    }

    public void retrieveInput(){
        userAlias = String.format("@%s", editText_userAlias.getText().toString());
        password = editText_password.getText().toString();
    }

    private void signIn(){
        loadingBar.setTitle("Signing In");
        loadingBar.setMessage("Please Wait!!");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(true);
        Pbkdf2 pbkdf2_hasher = new Pbkdf2();

        try {
            if (!userAlias.isEmpty() && !password.isEmpty()) {
                String hashedPassword = pbkdf2_hasher.hashPassword(password, "salt");
                SignInRequest signInRequest = new SignInRequest(userAlias, hashedPassword);
                SignInTask task = new SignInTask(presenter);
                task.execute(signInRequest);
            } else {
                System.out.println("Invalid input fields!");
                Toast.makeText(getActivity(), "Invalid Input!!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }

    public void openHomePageActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void checkSignInResponse(boolean isSignInSuccessful) {
        if(isSignInSuccessful){
            Toast.makeText(getActivity(), "Success Authentication!!", Toast.LENGTH_SHORT).show();
            openHomePageActivity();
        }
        else{
            System.out.println("Invalid input fields!");
            Toast.makeText(getActivity(), "Invalid Input!!",
                    Toast.LENGTH_SHORT).show();
        }
        loadingBar.dismiss();
    }
}
