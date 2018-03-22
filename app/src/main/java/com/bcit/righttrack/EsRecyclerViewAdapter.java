package com.bcit.righttrack;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Recycler View Adapter. The adapter is the piece that will connect our data to our RecyclerView.
 * @author Alireza Sattari
 */
class EsRecyclerViewAdapter extends RecyclerView.Adapter<EsRecyclerViewAdapter.ViewHolder> {

    //private HashMap<String, String> nameDEsc;
    private ArrayList<String> website;
    private ArrayList<String> latY;
    private ArrayList<String> longX;
    private Context context;

    /**
     * Constructor
     * @param context activity context
     * @param web list of website URLs
     * @param y list of latitudes
     * @param x list of longitudes
     */
    protected EsRecyclerViewAdapter(final Context context, /*final HashMap<String, String> nameDesc,*/
                                    final ArrayList<String> web, final ArrayList<String> y,
                                    final ArrayList<String> x) {
        this.context = context;
        //nameDesc = new HashMap<>();
        website = new ArrayList<>(web);
        latY = new ArrayList<>(y);
        longX = new ArrayList<>(x);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Uri geoUri = Uri.parse("geo:<" + latY.get(holder.getAdapterPosition()) + ">, <" +
                longX.get(holder.getAdapterPosition()) + "> ?q=<" + latY.get(holder.getAdapterPosition()) +
                ">, <" + longX.get(holder.getAdapterPosition()) + "> (Label)");
        final String url = website.get(holder.getAdapterPosition());
        final Uri webAddress = Uri.parse(url);


        holder.orgName.setText("Service Canada Centre " + holder.getAdapterPosition());
        // The MAX length can be shown properly
        holder.description.setText("Job training opportunities are available for work in restaurants, retail stores, and landscaping.");

        // Address button listener
        holder.addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent googleMap = new Intent(Intent.ACTION_VIEW, geoUri);
                googleMap.setPackage("com.google.android.apps.maps");

                // Checks whether user's device has an app that can respond to this intent
                if (googleMap.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(googleMap);
                } else {
                    Toast.makeText(context, "Google Map is not installed on your device.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Website button listener
        holder.websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webPage = new Intent(Intent.ACTION_VIEW, webAddress);
                if (webPage.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(webPage);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return website.size();
    }

    /**
     * View Holder class. I will be used along the Recycler View Adapter to to display data in the
     * Recycler View. Basically we declare the elements of the view that will be placed in our recycler
     * view. In our case it is the Card View.
     */
    protected class ViewHolder extends RecyclerView.ViewHolder{
        private TextView orgName;
        private TextView description;
        private Button addressButton;
        private Button websiteButton;

        /**
         * Constructor
         * @param cardView the view that contains the cards
         */
        private ViewHolder(View cardView) {
            super(cardView);

            orgName = cardView.findViewById(R.id.orgName);
            description = cardView.findViewById(R.id.description);
            addressButton = cardView.findViewById(R.id.address);
            websiteButton = cardView.findViewById(R.id.website);
        }
    }
}
