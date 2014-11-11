package veme.cario.com.CARmera.fragment;
import android.app.Dialog;
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

import veme.cario.com.CARmera.R;

public class VehicleInfoDialog extends DialogFragment {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private final static int NUM_FRAG = 6;

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
        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager)view.findViewById(R.id.pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        return view;
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
                    SpecsFragment specsFragment = new SpecsFragment();
                    return specsFragment;
                }
                case 1: {
                    DealershipFragment dealershipFragment = new DealershipFragment();
                    return dealershipFragment;
                }
                case 2: {
                    OwnershipCostFragment ownershipCostFragment = new OwnershipCostFragment();
                    return ownershipCostFragment;
                }
                case 3: {
                    RentalFragment rentalFragment = new RentalFragment();
                    return rentalFragment;
                }
                case 4: {
                    NewsFragment newsFragment = new NewsFragment();
                    return newsFragment;
                }
                case 5: {
                    RecommendedFragment recommendedFragment = new RecommendedFragment();
                    return recommendedFragment;
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
                    return "SPECS & REVIEWS";
                case 1:
                    return "NEARBY LISTINGS";
                case 2:
                    return "COST TO OWN";
                case 3:
                    return "RENTALS";
                case 4:
                    return "NEWS";
                case 5:
                    return "SIMILAR VEHICLES";
            }
            return null;
        }
    }
}