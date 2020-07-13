package com.arcticfox.cyberpro.ui.home.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.arcticfox.cyberpro.R;
import com.arcticfox.cyberpro.databinding.ActivityHomeBinding;
import com.arcticfox.cyberpro.ui.expenses.fragments.ExpensesFragment;
import com.arcticfox.cyberpro.ui.home.viewmodels.HomeViewModel;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    //private Toolbar toolbar;
    //private DrawerLayout mDrawerLayout;
    //private NavigationView navigationView;
    private HomeViewModel mHomeVM;
    private ActivityHomeBinding mActivityHomeBinding;
    private boolean isFragmentInBackStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_home);
        mActivityHomeBinding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
        //mHomeVM = ViewModelProviders.of(HomeActivity.this).get(HomeViewModel.class);
        mHomeVM = new ViewModelProvider(this).get(HomeViewModel.class);
        mActivityHomeBinding.setHomeVM(mHomeVM);
        mActivityHomeBinding.setLifecycleOwner(HomeActivity.this);

        /*SpannableStringBuilder longDescription = new SpannableStringBuilder();
        longDescription.append(getResources().getString(R.string.app_name));
        int start = longDescription.length();
        longDescription.append(getResources().getString(R.string.menu_home));
        //longDescription.setSpan(new ForegroundColorSpan(0xFFCC5500), start, longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        longDescription.setSpan(CustomTypefaceSpan(ResourcesCompat.getFont(this, R.font.radio_stars)), start, longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/

        mHomeVM.setPageTitle(getResources().getString(R.string.menu_home));
        //mActivityHomeBinding.toolbar.tvTitle.setText("abcdef");
        //initializeViews();
        setupToolbar();
        setupDrawerContent();
    }

    /*private void initializeViews(){
        toolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
    }*/

    private void setupToolbar() {
        setSupportActionBar(mActivityHomeBinding.toolbar.toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, mActivityHomeBinding.drawerLayout, mActivityHomeBinding.toolbar.toolbar,
                R.string.menu_home, R.string.menu_home
        );

        mActivityHomeBinding.drawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorNextToWhite));
        mDrawerToggle.syncState();
    }

    private void setupDrawerContent() {
        mActivityHomeBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String sTag = null;
                Fragment newFragment = null;
                final Handler mUIHandler = new Handler();
                switch (item.getItemId()) {
                    case R.id.nav_expenses:
                        sTag = getResources().getString(R.string.expenses);
                        newFragment = getSupportFragmentManager().findFragmentByTag(sTag);
                        if (newFragment == null) {
                            newFragment = ExpensesFragment.Companion.newInstance();
                            isFragmentInBackStack = false;
                        } else
                            isFragmentInBackStack = true;
                        mHomeVM.setPageTitle(sTag);
                        break;
                    default:
                        mUIHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                mHomeVM.setPageTitle(getResources().getString(R.string.menu_home));
                                mActivityHomeBinding.toolbar.tvTitle.setVisibility(View.GONE);
                                mActivityHomeBinding.toolbar.tvAppName.setVisibility(View.VISIBLE);
                            }
                        }, 0);
                        break;
                }
                if (isFragmentInBackStack) {
                    if (newFragment != null) {
                        getSupportFragmentManager().beginTransaction().show(newFragment).commit();
                        mActivityHomeBinding.toolbar.tvAppName.setVisibility(View.VISIBLE);
                        mActivityHomeBinding.toolbar.tvTitle.setVisibility(View.VISIBLE);
                    }
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.flContent, ExpensesFragment.Companion.newInstance(), sTag)
                            .addToBackStack(null)
                            .commit();
                    mActivityHomeBinding.toolbar.tvAppName.setVisibility(View.VISIBLE);
                    mActivityHomeBinding.toolbar.tvTitle.setVisibility(View.VISIBLE);
                }
                mActivityHomeBinding.drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //handles open and close of home button of actionbar/toolbar
            case android.R.id.home:
                mActivityHomeBinding.drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mActivityHomeBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mActivityHomeBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mActivityHomeBinding.toolbar.tvAppName.setVisibility(View.VISIBLE);
            mActivityHomeBinding.toolbar.tvTitle.setVisibility(View.GONE);
            mHomeVM.setPageTitle(getResources().getString(R.string.menu_home));
            super.onBackPressed();
            /*int nBackStackCount = getSupportFragmentManager().getBackStackEntryCount();
            if (nBackStackCount == 1) {
                mHomeVM.setPageTitle(getResources().getString(R.string.app_name));
            }
            if(nBackStackCount > 1){
                String sTag = getSupportFragmentManager().getFragments().get(nBackStackCount - 1).getTag();
                mHomeVM.setPageTitle(sTag);
            }
            super.onBackPressed();*/
        }
    }
}
