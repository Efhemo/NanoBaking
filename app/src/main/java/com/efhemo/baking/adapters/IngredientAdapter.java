package com.efhemo.baking.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.efhemo.baking.R;
import com.efhemo.baking.model.Ingredients;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private Context mContext;
    List<Ingredients> ingredientsList;

    public IngredientAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ingredients_layout, parent, false);


        return new IngredientViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {

        Ingredients ingredients = ingredientsList.get(holder.getAdapterPosition());

        holder.tvQuantity.setText(String.valueOf(ingredients.getQuantity()));
        holder.tvMeasure.setText(ingredients.getMeasure());
        holder.tvIngredient.setText(ingredients.getIngredient());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(ingredientsList == null){
            return 0;
        }
        return ingredientsList.size();
    }

    public void setIngredientsList(List<Ingredients> ingredientsList){
        this.ingredientsList = ingredientsList;
        notifyDataSetChanged();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder{

        TextView tvQuantity;
        TextView tvMeasure;
        TextView tvIngredient;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            tvQuantity = itemView.findViewById(R.id.tv_quantity_value);
            tvMeasure = itemView.findViewById(R.id.tv_measure_value);
            tvIngredient = itemView.findViewById(R.id.tv_ingredient_value);
        }
    }
}
