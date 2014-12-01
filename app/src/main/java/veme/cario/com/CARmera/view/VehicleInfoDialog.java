package veme.cario.com.CARmera.view;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.io.ByteArrayOutputStream;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.fragment.DealershipFragment;
import veme.cario.com.CARmera.fragment.ImageFragment;
import veme.cario.com.CARmera.fragment.OwnershipCostFragment;
import veme.cario.com.CARmera.fragment.ReviewFragment;
import veme.cario.com.CARmera.fragment.SpecsFragment;

public class VehicleInfoDialog extends DialogFragment {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private final static int NUM_FRAG = 5;

    private Bitmap capturedBitmap = null;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog, container);
        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager = (ViewPager)view.findViewById(R.id.pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        return view;
    }

    public void setVehicleBitmap (Bitmap bitmap) {
        this.capturedBitmap = bitmap;
        Bundle args = new Bundle();
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
        args.putByteArray("previewImage", bs.toByteArray());
    }

    /* Tab paging */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    ImageFragment imageFragment = new ImageFragment();
                    return imageFragment;
                }
                case 1: {
                    ReviewFragment reviewFragment = new ReviewFragment();
                    return reviewFragment;
                }
                case 2: {
                    SpecsFragment specsFragment = new SpecsFragment();
                    return specsFragment;
                }
                case 3: {
                    OwnershipCostFragment ownershipCostFragment = new OwnershipCostFragment();
                    return ownershipCostFragment;
                }
                case 4: {
                    DealershipFragment dealershipFragment = new DealershipFragment();
                    return dealershipFragment;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_FRAG;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Figure this out";
                case 1:
                    return "Customer Reviews";
                case 2:
                    return "Detailed Specifications";
                case 3:
                    return "Cost-To-Own";
                case 4:
                    return "Nearby Dealerships";
            }
            return null;
        }
    }
}