package com.efhemo.baking.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.efhemo.baking.R;
import com.efhemo.baking.model.BakeResponse;
import com.efhemo.baking.model.Ingredients;
import com.efhemo.baking.model.Steps;

import java.util.List;

public class BakerRCAdapter extends RecyclerView.Adapter<BakerRCAdapter.BakerViewHolder> {

    List<BakeResponse> bakeResponses;


    private Context context;

    private BakerClickListener bakerClickListener;

    public interface BakerClickListener{

        void onBakerClickListener(List<Ingredients> ingredientsList, List<Steps> stepsList);
    }


    public BakerRCAdapter(Context context, BakerClickListener bakerClickListener) {

        this.context = context;
        this.bakerClickListener = bakerClickListener;
    }



    @NonNull
    @Override
    public BakerRCAdapter.BakerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc_bake, parent, false);

        return new BakerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BakerRCAdapter.BakerViewHolder holder, int position) {

        String cakeName = bakeResponses.get(holder.getAdapterPosition()).getName();
        int serving  = bakeResponses.get(holder.getAdapterPosition()).getServings();

        holder.cake.setText(cakeName);
        holder.cakeText.setText(new StringBuilder().append("Serving: ").append(String.valueOf(serving)).toString());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {

        if(bakeResponses != null){
            return bakeResponses.size();
        }else
        return 0;
    }

    public void setBakeResponses(List<BakeResponse> bakeResponses){
        this.bakeResponses  = bakeResponses;
        notifyDataSetChanged();
    }



    public class BakerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView cake;
        TextView cakeText;

        public BakerViewHolder(View itemView) {
            super(itemView);

            cake = itemView.findViewById(R.id.cake);
            cakeText = itemView.findViewById(R.id.cake_text);

            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            bakerClickListener.onBakerClickListener(bakeResponses.get(getAdapterPosition()).getIngredients(),
                    bakeResponses.get(getAdapterPosition()).getSteps());
            /*Intent intent = new Intent(context, DetailActivity.class);
            context.startActivity(intent);*/
        }
    }
}

