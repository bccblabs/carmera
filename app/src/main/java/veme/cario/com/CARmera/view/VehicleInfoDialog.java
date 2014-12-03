package veme.cario.com.CARmera.view;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.media.Image;
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
import android.widget.Button;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.fragment.DealershipFragment;
import veme.cario.com.CARmera.fragment.ImageFragment;
import veme.cario.com.CARmera.fragment.OwnershipCostFragment;
import veme.cario.com.CARmera.fragment.ReviewFragment;
import veme.cario.com.CARmera.fragment.SpecsFragment;

public class VehicleInfoDialog extends DialogFragment {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private PreviewPagerAdapter previewPagerAdapter;
    private ViewPager viewPager;
    private final static int NUM_FRAG = 5;

    private ImageFragment imageFragment = null;
    private ReviewFragment reviewFragment = null;
    private SpecsFragment specsFragment = null;
    private OwnershipCostFragment ownershipCostFragment = null;
    private DealershipFragment dealershipFragment = null;


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
        viewPager = (ViewPager)view.findViewById(R.id.pager);
        if (getArguments().getString("vehicle_year") == null) {
            previewPagerAdapter = new PreviewPagerAdapter(getChildFragmentManager());
            viewPager.setAdapter(previewPagerAdapter);
        } else {
            sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
            viewPager.setAdapter(sectionsPagerAdapter);
        }

        return view;
    }

    /* Tab paging */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Bundle args;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            args = getArguments();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    imageFragment = new ImageFragment();
                    imageFragment.setArguments(args);
                    return imageFragment;
                }
                case 1: {
                    reviewFragment = new ReviewFragment();
                    reviewFragment.setArguments(args);
                    return reviewFragment;
                }
                case 2: {
                    specsFragment = new SpecsFragment();
                    specsFragment.setArguments(args);
                    return specsFragment;
                }
                case 3: {
                    ownershipCostFragment = new OwnershipCostFragment();
                    ownershipCostFragment.setArguments(args);
                    return ownershipCostFragment;
                }
                case 4: {
                    dealershipFragment = new DealershipFragment();
                    dealershipFragment.setArguments(args);
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

    public class PreviewPagerAdapter extends FragmentPagerAdapter {
        public PreviewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    imageFragment = new ImageFragment();
                    Bundle args = getArguments();
                    imageFragment.setArguments(args);
                    return imageFragment;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Figure this out";
            }
            return null;
        }

    }

}