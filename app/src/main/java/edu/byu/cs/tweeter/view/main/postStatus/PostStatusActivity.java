package edu.byu.cs.tweeter.view.main.postStatus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.SignInServiceProxy;
import edu.byu.cs.tweeter.presenter.StatusPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.PostStatusTask;
import edu.byu.cs.tweeter.view.main.MainActivity;
import edu.byu.model.domain.User;
import edu.byu.model.services.SignInService;
import edu.byu.model.services.request.StatusRequest;

public class PostStatusActivity extends AppCompatActivity implements StatusPresenter.View{
    private Toolbar toolbar;
    private Button btn_sharePost;
    private EditText editText_postText;
    private String message;
    private String postTimeStamp;
    private User author;
    private String dateStamp;
    private String timeStamp;
    private ProgressDialog progressDialog;
    private StatusPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_status);

        presenter = new StatusPresenter(this);
        author = SignInServiceProxy.getInstance().getCurrentUser();
        toolbar = findViewById(R.id.toolbar_create_post);
        btn_sharePost = findViewById(R.id.btn_share_new_post);
        editText_postText = findViewById(R.id.edittext_post_text);
        progressDialog = new ProgressDialog(PostStatusActivity.this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Create New Post!");

        btn_sharePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePost();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home)
            openHomePageActivity();
        return super.onOptionsItemSelected(item);
    }

    public void openHomePageActivity(){
        Intent intent = new Intent(PostStatusActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void sharePost(){
        if(!TextUtils.isEmpty(editText_postText.getText())){
            message = editText_postText.getText().toString();
            postStatus();
        }
        else{
            message = "";
            Toast.makeText(this, "No post text", Toast.LENGTH_SHORT).show();
        }
    }

    public void postStatus(){
        progressDialog.setTitle("Adding New Post");
        progressDialog.setMessage("Please Wait!!");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(true);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
        dateStamp = simpleDateFormat.format(calendar.getTime());
        timeStamp = simpleTimeFormat.format(calendar.getTime());
        postTimeStamp = dateStamp + "_" + timeStamp;

        StatusRequest request = new StatusRequest(author, message, postTimeStamp);
        PostStatusTask task = new PostStatusTask(presenter);
        task.execute(request);
    }

    @Override
    public void checkPostStatusResponse(boolean isSuccesfullyUploaded) {
        if(isSuccesfullyUploaded){
            Toast.makeText(PostStatusActivity.this,
                    "Success: Post updated in database", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(PostStatusActivity.this,
                    "Error: while uploading to Database", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
        goToMainActivity();
    }

    private void goToMainActivity(){
        Intent intent = new Intent(PostStatusActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
