package edu.byu.cs.tweeter.view.main.authentication;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.view.main.authentication.signIn.SignInFragment;
import edu.byu.cs.tweeter.view.main.authentication.signUp.SignUpFragment;

public class AuthenticationActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        Log.d(TAG, "onCreate: Starting");
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignInFragment(), "Sing In");
        adapter.addFragment(new SignUpFragment(), "Sign Up");
        viewPager.setAdapter(adapter);
    }
}