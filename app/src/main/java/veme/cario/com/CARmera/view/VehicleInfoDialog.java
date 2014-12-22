package veme.cario.com.CARmera.view;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.CarInfoFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.CustomerReviewFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.EdmundsReviewFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.ImageFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.OwnershipCostFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.RecallFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.SelectStyleFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.SpecsFragment;

public class VehicleInfoDialog extends DialogFragment {

    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter = null;
    private static String TAG = "VEHICLE_INFO_DIALOG";



    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        Drawable semi_transparent = new ColorDrawable(Color.BLACK);
        semi_transparent.setAlpha(180);

        dialog.getWindow().setBackgroundDrawable(semi_transparent);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog, container);
        viewPager = (ViewPager) view.findViewById(R.id.pager);


        String dialog_type = getArguments().getString("dialog_type");
        if (dialog_type == "preview") {
            fragmentPagerAdapter = new PreviewPagerAdapter(getChildFragmentManager());
        } else if (dialog_type == "choose_style") {
            fragmentPagerAdapter = new StylePagerAdapter(getChildFragmentManager());
        } else if (dialog_type == "vehicle_info") {
            fragmentPagerAdapter = new InfoPagerAdapter(getChildFragmentManager());
        } else {
            Log.d(TAG, " - no such type, default to info dialog");
            fragmentPagerAdapter = new InfoPagerAdapter(getChildFragmentManager());
        }
        viewPager.setAdapter(fragmentPagerAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        getDialog().getWindow().setLayout(width, height);
    }
    /* Tab paging */
    public class InfoPagerAdapter extends FragmentPagerAdapter {

        private final static int NUM_FRAG = 6;
        public InfoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0: {
                    fragment = new CarInfoFragment();
                    break;
                }
                case 1: {
                    fragment = new SpecsFragment();
                    break;
                }
                case 2: {
                    fragment = new OwnershipCostFragment();
                    break;
                }
                case 3: {
                    fragment = new CustomerReviewFragment();
                    break;
                }
                case 4: {
                    fragment = new EdmundsReviewFragment();
                    break;
                }
                case 5: {
                    fragment = new RecallFragment();
                    break;
                }
            }
            if (fragment != null)
                fragment.setArguments(getArguments());
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_FRAG;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Snap Shot";
                case 1:
                    return "Powertrain";
                case 2:
                    return "Cost-To-Own";
                case 3:
                    return "Drivers Review";
                case 4:
                    return "Edmunds Review";
                case 5:
                    return "Recalls";
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
            Fragment imageFragment = new ImageFragment();
            Bundle args = getArguments();
            imageFragment.setArguments(args);
            return imageFragment;
        }
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Preview Photo";
            }
            return null;
        }
    }

    public class StylePagerAdapter extends FragmentPagerAdapter {
        public StylePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment selectStyleFragment = new SelectStyleFragment();
            Bundle args = getArguments();
            selectStyleFragment.setArguments(args);
            return selectStyleFragment;
        }
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Select Car Style";
            }
            return null;
        }
    }

}