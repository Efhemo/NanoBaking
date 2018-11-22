package com.efhemo.baking.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.efhemo.baking.R;
import com.efhemo.baking.model.Steps;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {

    private Context mContext;

    private List<Steps> stepsList;

    private OnClickItemBakerListener onClickItemBakerListener;

    public interface OnClickItemBakerListener{

        void onClickItemBaker(Steps steps);
    }

    public StepsAdapter(Context mContext, OnClickItemBakerListener onClickItemBakerListener) {
        this.mContext = mContext;
        this.onClickItemBakerListener = onClickItemBakerListener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_steps_layout, parent, false);


        return new StepViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {

        holder.tvNumber.setText("Steps "+ stepsList.get(holder.getAdapterPosition()).getId());
        holder.tvDescription.setText(stepsList.get(holder.getAdapterPosition()).getShortDescription());

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(stepsList == null){
            return 0;
        }else return stepsList.size();
    }

    public void setStepsList(List<Steps> stepsList){
        this.stepsList = stepsList;

        notifyDataSetChanged();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener{

        TextView tvNumber;
        TextView tvDescription;

        public StepViewHolder(View itemView) {
            super(itemView);

            tvNumber = itemView.findViewById(R.id.step_number);
            tvDescription  = itemView.findViewById(R.id.step_short_description);

            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            Steps steps = stepsList.get(getAdapterPosition());

            onClickItemBakerListener.onClickItemBaker(steps);
        }
    }
}
