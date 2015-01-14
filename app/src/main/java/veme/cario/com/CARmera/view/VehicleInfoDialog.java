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
import veme.cario.com.CARmera.fragment.ActivityFragment.CreateSearchFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.SavedListingsFragment;
import veme.cario.com.CARmera.fragment.SavedSearchFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.CarInfoFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.CustomerReviewFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.EdmundsReviewFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.ImageFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.IncentivesFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.OwnershipCostFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.RecallFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.SelectStyleFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.SpecsFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.TaggedPostFragment;

public class VehicleInfoDialog extends DialogFragment {

    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter = null;
    private static String TAG = "VEHICLE_INFO_DIALOG";

    private class AnimatedPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }



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
        viewPager.setPageTransformer(true, new AnimatedPageTransformer());

        String dialog_type = getArguments().getString("dialog_type");
        if (dialog_type.equals("preview")) {
            fragmentPagerAdapter = new PreviewPagerAdapter(getChildFragmentManager());
        } else if (dialog_type.equals("choose_style")) {
            fragmentPagerAdapter = new StylePagerAdapter(getChildFragmentManager());
        } else if (dialog_type.equals("vehicle_info")) {
            fragmentPagerAdapter = new InfoPagerAdapter(getChildFragmentManager());
        } else if (dialog_type.equals("post_details")) {
            fragmentPagerAdapter = new PostDetailsAdapter(getChildFragmentManager());
        } else if (dialog_type.equals("create_search")) {
            fragmentPagerAdapter = new CreateSearchAdapter(getChildFragmentManager());
        } else if (dialog_type.equals("saved_listings")) {
            fragmentPagerAdapter = new SavedListingsAdapter(getChildFragmentManager());
        } else if (dialog_type.equals("saved_search")) {
            fragmentPagerAdapter = new SavedSearchesAdapter(getChildFragmentManager());
        } else if (dialog_type.equals("incentive_rebate")) {
            fragmentPagerAdapter = new IncentivesAdapter(getChildFragmentManager());
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

    public class PostDetailsAdapter extends FragmentPagerAdapter {
        public PostDetailsAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment taggedPostFragment = new TaggedPostFragment();
            Bundle args = getArguments();
            taggedPostFragment.setArguments(args);
            return taggedPostFragment;
        }
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Post Details";
            }
            return null;
        }
    }


    public class CreateSearchAdapter extends FragmentPagerAdapter {
        public CreateSearchAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment createSearchFragment = new CreateSearchFragment();
            Bundle args = getArguments();
            createSearchFragment.setArguments(args);
            return createSearchFragment;
        }
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Create Search Criteria";
            }
            return null;
        }
    }

    public class SavedListingsAdapter extends FragmentPagerAdapter {
        public SavedListingsAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment savedListingsFragment = new SavedListingsFragment();
            Bundle args = getArguments();
            savedListingsFragment.setArguments(args);
            return savedListingsFragment;
        }
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Saved Listings";
            }
            return null;
        }
    }

    public class SavedSearchesAdapter extends FragmentPagerAdapter {
        public SavedSearchesAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment savedSearchesFragment = new SavedSearchFragment();
            Bundle args = getArguments();
            if (position == 1) {
                args.putBoolean("show_history", true);
            } else {
                args.putBoolean("show_history", false);
            }
            savedSearchesFragment.setArguments(args);
            return savedSearchesFragment;
        }
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Favorite Searches";
                case 1:
                    return "All Search History";
            }
            return null;
        }
    }

    public class IncentivesAdapter extends FragmentPagerAdapter {
        public IncentivesAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment incentivesFragment = new IncentivesFragment();
            Bundle args = getArguments();
            incentivesFragment.setArguments(args);
            return incentivesFragment;
        }
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Incentives & Rebates";
            }
            return null;
        }
    }
}