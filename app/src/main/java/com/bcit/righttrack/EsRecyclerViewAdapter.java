package com.bcit.righttrack;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Recycler View Adapter. The adapter is the piece that will connect our data to our RecyclerView.
 * @author Alireza Sattari
 */
class EsRecyclerViewAdapter extends RecyclerView.Adapter<EsRecyclerViewAdapter.ViewHolder> {

    private HashMap<String, String> nameDEsc;
    private ArrayList<String> website;
    private ArrayList<String> latY;
    private ArrayList<String> longX;


    /*protected EsRecyclerViewAdapter(HashMap<String, String> nameDesc, ArrayList<String> website,
                                    ArrayList<String> latY, ArrayList<String> longX) {
        nameDesc = new HashMap<>();
        website = new ArrayList<>();
        latY = new ArrayList<>();
        longX = new ArrayList<>();

        this.nameDEsc = nameDesc;
        this.website = website;
        this.latY = latY;
        this.longX = longX;
    }*/

    @Override
    public EsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        // Getting the card view template created in the es_cardView.xml
        View cardView = lf.inflate(R.layout.es_cardview, parent,false);

        // Returns an instance of the View Holder class
        return new ViewHolder(cardView);
    }

    @Override
    /*
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the cardView to reflect the item at the given position.
     * Basically, we can initialize the element and views that we declared in the view holder.
     * @position: It is the position of the thing you are showing, in our case it is the cards
     */
    public void onBindViewHolder(EsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.orgName.setText("Aware Society - Job Options BC");
        // The MAX length can be shown properly
        holder.description.setText("Job training opportunities are available for work in restaurants, retail stores, and landscaping.  Training includes work experience, help with resumes.");
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    /**
     * View Holder class. I will be used along the Recycler View Adapter to to display data in the
     * Recycler View. Basically we declare the elements of the view that will be placed in our recycler
     * view. In our case it is the Card View.
     */
    protected class ViewHolder extends RecyclerView.ViewHolder{
        private TextView orgName;
        private TextView description;

        /**
         * Constructor.
         * @param cardView the view that contains the cards
         */
        public ViewHolder(View cardView) {
            super(cardView);

            orgName = cardView.findViewById(R.id.orgName);
            description = cardView.findViewById(R.id.description);
        }
    }
}
