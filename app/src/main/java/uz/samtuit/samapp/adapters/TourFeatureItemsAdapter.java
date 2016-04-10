package uz.samtuit.samapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

import uz.samtuit.samapp.helpers.IntentHelper;
import uz.samtuit.samapp.main.ItemsListActivity;

import uz.samtuit.samapp.main.R;
import uz.samtuit.samapp.main.TourFeatureActivity;
import uz.samtuit.samapp.util.BitmapUtil;

import uz.samtuit.samapp.util.GlobalsClass;
import uz.samtuit.samapp.util.TourFeature;
import uz.samtuit.samapp.util.TourFeatureList;

/**
 * Created by Bakha on 25.02.2016.
 */
public class TourFeatureItemsAdapter extends RecyclerView.Adapter<TourFeatureItemsAdapter.ViewHolder> {

    Context context;
    ArrayList<TourFeature> data;
    private GlobalsClass.FeatureType featureType;
    private int layoutID;
    private boolean fromItinerary;
    private int selectedDay;
    private int indexToAssign;

    public TourFeatureItemsAdapter(Context context, GlobalsClass.FeatureType featureType, ArrayList<TourFeature> data, int LayoutID, boolean fromItinerary, int selectedDay, int indexToAssign){
        this.context = context;
        this.featureType = featureType;
        this.data = data;
        this.layoutID = LayoutID;
        this.fromItinerary = fromItinerary;
        this.selectedDay = selectedDay;
        this.indexToAssign = indexToAssign; // if fromitinerary its index to assign feature in itineraries in selected day;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(layoutID, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        holder.TF_RATING.setRating(data.get(position).getRating());
        holder.TF_TITLE.setText(data.get(position).getString("name"));
        String fileName = data.get(position).getPhoto();
        if(layoutID==R.layout.items_list_adapter){

        } else {
            holder.TF_TITLE.setTextSize(13);
        }

        try{
            BitmapUtil.setRoundImageFromFileToView(context, fileName, holder.TF_IMAGE, AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
        }catch (Exception ex){
            ex.printStackTrace();
        }

        holder.TF_HOLDER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TourFeatureActivity.class);

                if (featureType == GlobalsClass.FeatureType.ITINERARY) {
                    featureType = TourFeatureList.findFeatureTypeByName(context, data.get(position).getString("name"));
                }
                intent.putExtra("featureType", featureType.toString());
                intent.putExtra("photo", data.get(position).getPhoto());
                intent.putExtra("rating", data.get(position).getRating());
                intent.putExtra("name", data.get(position).getString("name"));
                intent.putExtra("desc", data.get(position).getString("desc"));
                intent.putExtra("type", data.get(position).getString("type"));
                intent.putExtra("price", data.get(position).getString("price"));
                intent.putExtra("wifi", data.get(position).getString("wifi"));
                intent.putExtra("open", data.get(position).getString("open"));
                intent.putExtra("addr", data.get(position).getString("addr"));
                intent.putExtra("from_itinerary", fromItinerary);
                intent.putExtra("selected_day", selectedDay);
                intent.putExtra("index", indexToAssign);
                intent.putExtra("tel", data.get(position).getString("tel"));
                intent.putExtra("url", data.get(position).getString("url"));
                intent.putExtra("long", data.get(position).getLongitude());
                intent.putExtra("lat", data.get(position).getLatitude());
                intent.putExtra("primaryColorId", GlobalsClass.getPrimaryColorId(featureType));
                intent.putExtra("toolbarColorId", GlobalsClass.getToolbarColorId(featureType));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView TF_TITLE;
        public ImageView TF_IMAGE;
        public RatingBar TF_RATING;
        public View TF_HOLDER;

        public ViewHolder(View v) {
            super(v);
            TF_TITLE = (TextView)v.findViewById(R.id.tf_title);
            TF_IMAGE = (ImageView)v.findViewById(R.id.listViewThumbnail);
            TF_RATING = (RatingBar)v.findViewById(R.id.ratingBar);
            TF_HOLDER = v.findViewById(R.id.holder_layout);
        }
    }
}
