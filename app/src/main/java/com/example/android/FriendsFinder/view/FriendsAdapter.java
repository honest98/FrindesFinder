package com.example.android.FriendsFinder.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.android.FriendsFinder.MapsActivity;
import com.example.android.FriendsFinder.R;
import com.example.android.FriendsFinder.model.FriendInfo;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.DataViewHolder> {


    private static final String TAG = FriendsAdapter.class.getSimpleName();

    private ArrayList<FriendInfo> mArrayList;
    private Context mContext;


    /**
     * Constructor for GreenAdapter that accepts a number of items to display and the specification
     * for the ListItemClickListener.
     *
     * @param arrayList Number of items to display in list
     */

    public FriendsAdapter(Context context, ArrayList<FriendInfo> arrayList){
        mArrayList = arrayList;
        mContext = context;
    }

    /**
     *
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  for more details.
     * @return A new NumberViewHolder that holds the View for each list item
     */

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_items, viewGroup, false);

        return new DataViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder,final int position) {
        Log.d(TAG, "#" + position);
        holder.name.setText(mArrayList.get(position).getName());
        holder.email.setText(mArrayList.get(position).getEmail());
        holder.lat.setText(mArrayList.get(position).getLat().toString());
        holder.lng.setText(mArrayList.get(position).getLng().toString());

        Glide
                .with(mContext)
                .load(mArrayList.get(position).getImageUrl())
                .placeholder(R.drawable.userprofile)
                .into(holder.circleImageView);


        // RecyclerView onItemClickListener
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, MapsActivity.class);
                intent.putExtra("friend", mArrayList.get(position).getName());
                intent.putExtra("lat", mArrayList.get(position).getLat());
                intent.putExtra("lng", mArrayList.get(position).getLng());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                try {
                    mContext.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(mContext, "Exception: " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);


    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    /**
     * Cache of the children views for a list item.
     */
    public class DataViewHolder extends RecyclerView.ViewHolder {

        // Will display the position in the list, ie 0 through getItemCount() - 1
        TextView name, lat, lng, email;
        CircleImageView circleImageView;
        LinearLayout parentLayout;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            lat = itemView.findViewById(R.id.tv_lat);
            lng = itemView.findViewById(R.id.tv_lng);
            email = itemView.findViewById(R.id.tv_email);
            circleImageView = itemView.findViewById(R.id.profile_image);

            parentLayout = itemView.findViewById(R.id.parent_layout);



        }


    }


}
