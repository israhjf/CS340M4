package edu.byu.cs.tweeter.view.main.authentication.signUp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.SignUpPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.SignUpTask;
import edu.byu.cs.tweeter.view.main.MainActivity;
import edu.byu.model.services.request.SignUpRequest;

public class RegisterUser extends AppCompatActivity implements SignUpPresenter.View {

    private static final String TAG = "RegisterActivity";
    private EditText editText_firstName;
    private EditText editText_lastName;
    private Button btn_confirm;
    private Button btn_uploadImage;
    private String alias;
    private String password;
    private String firstName;
    private String lastName;
    private SignUpPresenter presenter;
    private ProgressDialog progressDialog;
    private CircleImageView profileImage;
    final static int REQUEST_CODE = 1;
    private boolean hasProfilePicture = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            alias = bundle.getString("userAlias");
            password = bundle.getString("password");
        }

        presenter = new SignUpPresenter(this);
        editText_firstName = findViewById(R.id.edtext_account_firstname);
        editText_lastName = findViewById(R.id.edtext_account_lastname);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_uploadImage = findViewById(R.id.btn_upload_picture);
        progressDialog = new ProgressDialog(RegisterUser.this);
        profileImage = findViewById(R.id.imageview_profile_picture);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                retrieveInput();
                signUpUser();
            }
        });

        btn_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageFromPhoneGallery();
            }
        });
    }

    private void retrieveInput(){
        firstName = editText_firstName.getText().toString();
        lastName = editText_lastName.getText().toString();
    }

    private void signUpUser(){
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("Please Wait!!");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(true);

        if(!firstName.isEmpty() && !lastName.isEmpty()) {
            SignUpRequest request = new SignUpRequest(alias, password, firstName, lastName,
                    "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
            SignUpTask task = new SignUpTask(presenter);
            task.execute(request);
        }
        else{
            System.out.println("Invalid register fields!!");
            Toast.makeText(this, "Invalid Input!!",
                    Toast.LENGTH_SHORT).show();
        }
        if(!hasProfilePicture){
            Uri imageUri = Uri.parse("android.resource://" + getPackageName() + "/"+ R.drawable.random_user);
            //UploadProfilePicture(imageUri);
        }
    }

    private void openHomePageActivity() {
        Intent intent = new Intent(RegisterUser.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void uploadImageFromPhoneGallery(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null){
            progressDialog.setTitle("Updating Profile Picture");
            progressDialog.setMessage("Please Wait!!");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(true);
            Uri imageUri = data.getData();
            //Add Profile Image to Firebase Storage
            //UploadProfilePicture(imageUri);
        }
    }

    @Override
    public void checkSignUpResponse(boolean isSignUpSuccessful) {
        if(isSignUpSuccessful){
            Toast.makeText(this, "Success Registration!!", Toast.LENGTH_SHORT).show();
            openHomePageActivity();
        }
        else{
            System.out.println("Invalid input fields!");
            Toast.makeText(this, "Invalid Input!!",
                    Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }
}
