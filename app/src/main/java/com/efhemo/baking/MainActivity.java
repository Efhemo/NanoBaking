package com.efhemo.baking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.efhemo.baking.adapters.BakerRCAdapter;
import com.efhemo.baking.api.ApiClient;
import com.efhemo.baking.api.Service;
import com.efhemo.baking.model.BakeResponse;
import com.efhemo.baking.model.Ingredients;
import com.efhemo.baking.model.SimpleIdlingResource;
import com.efhemo.baking.model.Steps;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BakerRCAdapter.BakerClickListener {

    private TextView textView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView rc;
    private BakerRCAdapter bakerRCAdapter;
    List<BakeResponse> bakeResponse;


    @Nullable
    private SimpleIdlingResource mIdlingResource;
    private SimpleIdlingResource idlingResource;
    private SimpleIdlingResource idlingResource1;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the IdlingResource instance
        getIdlingResource();

        if (idlingResource1 != null) {
            idlingResource1.setIdleState(false);
        }
        initRecyclerView();
        if (savedInstanceState == null) {

            loadBake();

        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        bakeResponse = savedInstanceState.getParcelableArrayList("Recipe");
        bakerRCAdapter.setBakeResponses(bakeResponse);
    }

    void initRecyclerView(){

        rc = findViewById(R.id.rc_view);
        rc.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rc.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        rc.setLayoutManager(linearLayoutManager);
        bakerRCAdapter = new BakerRCAdapter(this, this);
        rc.setAdapter(bakerRCAdapter);

    }

    void loadBake(){

        Service service = ApiClient.getApiClient().create(Service.class);

        Call<List<BakeResponse>> bakeResponseCall =  service.getBaker("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");

        bakeResponseCall.enqueue(new Callback<List<BakeResponse>>() {
            @Override
            public void onResponse(Call<List<BakeResponse>> call, Response<List<BakeResponse>> response) {
                 bakeResponse = response.body();

                /*String result = bakeResponse.get(1).getId() + bakeResponse.get(1).getName() + bakeResponse.get(1).getIngredients().get(1).getIngredient()
                        +bakeResponse.get(1).getSteps().get(1).getShortDescription();

                Log.d(TAG, "this values are"+ result);*/
                bakerRCAdapter.setBakeResponses(bakeResponse);


                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }
            }

            @Override
            public void onFailure(Call<List<BakeResponse>> call, Throwable t) {
                Log.i(TAG, "error not getting data" +t.getMessage());
            }
        });
    }


    @Override
    public void onBakerClickListener(List<Ingredients> ingredientsList, List<Steps> stepsList) {

        String response = ingredientsList.get(0).getIngredient() + stepsList.get(3).getShortDescription();

        Log.d("DetailsActivity", "our values here are " + response);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putParcelableArrayListExtra(DetailActivity.INGREDIENT_EXTRA, (ArrayList<? extends Parcelable>) ingredientsList);
        intent.putParcelableArrayListExtra(DetailActivity.STEPS_EXTRA, (ArrayList<? extends Parcelable>) stepsList);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("Recipe", (ArrayList<? extends Parcelable>) bakeResponse);
    }
}
