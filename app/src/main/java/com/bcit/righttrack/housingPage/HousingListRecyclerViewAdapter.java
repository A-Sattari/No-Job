package com.bcit.righttrack.housingPage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bcit.righttrack.R;

/**
 * Custom Recycler View Adapter for housing list. The adapter is the piece that will
 * connect our data to our RecyclerView.
 * @author Alireza Sattari
 */
public class HousingListRecyclerViewAdapter extends RecyclerView.Adapter<HousingListRecyclerViewAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Getting the root view in housing_item.xml
        View housingItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.housing_item, parent, false);
        // Returns an instance of the View Holder class
        return new ViewHolder(housingItem);
    }

    /*
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of each linea item in the list to reflect the item at the given position.
     * Basically, we can initialize the element and views that we declared in the view holder.
     * @position: It is the position of the thing you are showing, in our case it is the cards
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() { return 10; }


    /**
     * View Holder class. The views in the list are represented by view holder objects.
     * I will be used along the Recycler View Adapter to to display data in the
     * Recycler View. Basically we declare the elements of the view that will be placed in our recycler
     * view. In our case it is the linear item.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView address;
        private ImageView houseImg;

        /**
         * Constructor
         * @param item the view that contains the linear item
         */
        private ViewHolder(View item) {
            super(item);

            title = item.findViewById(R.id.title);
            address = item.findViewById(R.id.address);
            houseImg = item.findViewById(R.id.houseImage);
        }
    }
}
