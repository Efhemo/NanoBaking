package com.efhemo.baking;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class DetailActivity extends AppCompatActivity {

    boolean mTwoPane = false;

    private static final String TAG = DetailActivity.class.getSimpleName();
    public final static String TAG_FRAGMENT = "TAG_FRAGMENT";

    public static final String INGREDIENT_EXTRA = "bake_extra";

    public static final String STEPS_EXTRA = "bake_extra_step";
    private FragmentSteps fragmentSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager();


            fragmentSteps = new FragmentSteps();
            Bundle bundle = new Bundle();


            Log.d("Detail", "general " + mTwoPane);

            showFragment(fragmentManager, fragmentSteps);

            //check if big screen or extra view is available
            if (findViewById(R.id.media_container) != null) {

                mTwoPane = true; //big screen
                fragmentManager.beginTransaction().add(R.id.media_container, new FragmentMediaPlayer())
                        .commit();
                Log.d("Detail", "general big screen " + mTwoPane);
            }

            bundle.putBoolean("twopane", mTwoPane);
            fragmentSteps.setArguments(bundle);

        }
    }

    private void showFragment(FragmentManager fragmentManager, FragmentSteps fragmentSteps) {
        fragmentManager.beginTransaction().replace(R.id.steps_container, fragmentSteps )
                /*.addToBackStack(TAG_FRAGMENT)*/.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(!mTwoPane){

            getSupportFragmentManager().popBackStack(TAG_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else {
            super.onBackPressed();
        }
        /*if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack(TAG_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Log.d(TAG,"on back fragment happened");
        } else {
            super.onBackPressed();
            Log.d(TAG,"on back fragment happened to Activity");

        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();

        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }
}
