package com.bcit.righttrack;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Recycler View Adapter. The adapter is the piece that will connect our data to our RecyclerView.
 * @author Alireza Sattari
 */
class EsRecyclerViewAdapter extends RecyclerView.Adapter<EsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> test;

    public EsRecyclerViewAdapter(ArrayList<String> test) {
        this.test = test;
    }

    @Override
    public EsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View cardView = lf.inflate(R.layout.es_cardview, parent,false);
        return new ViewHolder(cardView); // Returns an instance of the View Holder class
    }

    @Override
    /*
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the cardView to reflect the item at the given position.
     */
    public void onBindViewHolder(EsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.t1.setText(test.get(position));
        holder.t1.setTextColor(Color.RED);
        holder.t1.setTextSize(35);
    }

    @Override
    public int getItemCount() {
        return test.size();
    }

    /**
     * View Holder class. I will be used along the Recycler View Adapter to to display data in the
     * Recycler View.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView t1;
        /**
         * Constructor.
         * @param cardView the view that contains the cards
         */
        public ViewHolder(View cardView) {
            super(cardView);

            t1 = cardView.findViewById(R.id.test);


        }
    }
}
