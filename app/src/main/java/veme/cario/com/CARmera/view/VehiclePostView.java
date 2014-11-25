package veme.cario.com.CARmera.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import veme.cario.com.CARmera.R;

import com.octo.android.robospice.spicelist.SpiceListItemView;
import com.parse.ParseImageView;

import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;

/**
 * Created by bski on 11/24/14.
 */
public class VehiclePostView extends RelativeLayout implements SpiceListItemView<TaggedVehicle> {

    //     Todo: Implement the fragment for swipable view

    private TextView vehicleDistView;
    private TextView posterNameView;
    private TextView vehicleLikesCnt;
    private TextView vehicleCommentsCnt;
    private ParseImageView posterImageView;
    private ParseImageView vehicleImageView;
    private TaggedVehicle taggedVehicle;


    public VehiclePostView(Context context) {
        super(context);
        inflateView(context);
    }

    private void inflateView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_cell_post, this);
        this.vehicleDistView = (TextView) this.findViewById(R.id.vehicle_dist_textview);
        this.posterNameView = (TextView) this.findViewById(R.id.poster_name_textview);
        this.vehicleLikesCnt = (TextView) this.findViewById(R.id.vehicle_likes_textview);
        this.vehicleCommentsCnt = (TextView) this.findViewById(R.id.vehicle_comments_textview);
        this.posterImageView = (ParseImageView) this.findViewById(R.id.poster_image_imageview);
        this.vehicleImageView = (ParseImageView) this.findViewById(R.id.vehicle_image_imageview);
    }

    @Override
    public void update(TaggedVehicle taggedVehicle) {
        this.taggedVehicle = taggedVehicle;
        vehicleDistView.setText(taggedVehicle.toString());
        posterNameView.setText(taggedVehicle.getUserName());
        vehicleLikesCnt.setText(taggedVehicle.getLikesCnt());
        vehicleCommentsCnt.setText(taggedVehicle.getCommentsCnt());
        posterImageView.setParseFile(taggedVehicle.getUserPhoto());
        vehicleImageView.setParseFile(taggedVehicle.getTagPhoto());

    }

    @Override
    public TaggedVehicle getData() {
        return taggedVehicle;
    }

    @Override
    public ImageView getImageView(int imageIndex) {
        return vehicleImageView;
    }

    @Override
    public int getImageViewCount() {
        return 2;
    }
}
