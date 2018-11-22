package com.efhemo.baking;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.efhemo.baking.adapters.IngredientAdapter;
import com.efhemo.baking.adapters.StepsAdapter;
import com.efhemo.baking.model.Ingredients;
import com.efhemo.baking.model.Steps;
import com.efhemo.baking.widget.UpdateService;

import java.util.ArrayList;
import java.util.List;

import static com.efhemo.baking.DetailActivity.TAG_FRAGMENT;

public class FragmentSteps extends Fragment implements StepsAdapter.OnClickItemBakerListener {

    private RecyclerView rc;
    private RecyclerView rcStep;

    IngredientAdapter ingredientAdapter;
    StepsAdapter stepsAdapter;
    private boolean boole;

    private static final String TAG = FragmentSteps.class.getSimpleName();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step_ingredients, container, false);

        initRecyclerViewIngredient(view);
        Log.d(TAG,"on fragment created");

        initRecyclerViewStep(view);

        //check if an intent was sent
        Intent intent = getActivity().getIntent();
        if(intent == null){
            closeOnError();
        }

        Bundle bundle = getArguments();
        boole = bundle.getBoolean("twopane");

        //if this type of intent exist here at all then, do
        if (intent.getExtras() !=null && intent.hasExtra(DetailActivity.INGREDIENT_EXTRA)){


            List<Ingredients> ingredientsList = getActivity().getIntent().getParcelableArrayListExtra(DetailActivity.INGREDIENT_EXTRA);

            //String response = ingredientsList.get(0).getIngredient() + ingredientsList.get(3).getMeasure();

            ArrayList<String> recipeIngredientsForWidgets= new ArrayList<>();


            for (Ingredients a: ingredientsList) {

                recipeIngredientsForWidgets.add(a.getIngredient()+"\n"+
                        "Quantity: "+a.getQuantity()+"\n"+
                        "Measure: "+a.getMeasure()+"\n");

                Log.d("whichValue", a.getIngredient()+"\n"+
                        "Quantity: "+a.getQuantity()+"\n"+
                        "Measure: "+a.getMeasure()+"\n" );

            }

            Log.d("DetailsActivity Second", "our values here are " + recipeIngredientsForWidgets.size());

            ingredientAdapter.setIngredientsList(ingredientsList);
            stepsAdapter.setStepsList(getActivity().getIntent().<Steps>getParcelableArrayListExtra(DetailActivity.STEPS_EXTRA));

            //update widget
            UpdateService.startBakingService(getContext(),recipeIngredientsForWidgets);
        }


        return view;

    }

    private void closeOnError(){
        getActivity().finish();
        Toast.makeText(getContext(), "Can't find Bake Intent", Toast.LENGTH_SHORT).show();
    }

    void initRecyclerViewIngredient(View v){

        rc = v.findViewById(R.id.rc_ingredient);
        rc.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);

        rc.setLayoutManager(linearLayoutManager);
        ingredientAdapter = new IngredientAdapter(getContext());
        rc.setAdapter(ingredientAdapter);

    }

    void initRecyclerViewStep(View v){

        rcStep = v.findViewById(R.id.rc_steps);
        rcStep.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL);
        rcStep.addItemDecoration(itemDecor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            itemDecor.setDrawable(getActivity().getDrawable(R.drawable.rc_line_decor));
        }

        rcStep.setLayoutManager(linearLayoutManager);
        stepsAdapter = new StepsAdapter(getContext(), this);
        rcStep.setAdapter(stepsAdapter);

    }

    @Override
    public void onClickItemBaker(Steps steps) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("steps", steps);

        FragmentMediaPlayer fragmentMediaPlayer = new FragmentMediaPlayer();
        fragmentMediaPlayer.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        Log.d("Detail", "general " +boole);

        if(boole == false){
            fragmentManager.beginTransaction().replace(R.id.steps_container, fragmentMediaPlayer)
                    .addToBackStack(TAG_FRAGMENT) .commit();
            return;
        }
        //if it is big screen
        fragmentManager.beginTransaction().add(R.id.media_container, fragmentMediaPlayer)
                .commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
