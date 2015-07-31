package carmera.io.carmera;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import carmera.io.carmera.fragments.TrimDetailsFragment;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 7/25/15.
 */
public class TrimsViewerContainer extends ActionBarActivity{
    private String TAG = getClass().getCanonicalName();
    private FragmentManager fragmentManager;
    private TrimDetailsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicles_display_container);
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null)
            toolbar.hide();

        SlidrConfig config = new SlidrConfig.Builder()
                .position(SlidrPosition.LEFT)
                .touchSize(Util.dpToPx(this, 32))
                .build();

        Slidr.attach(this, config);
        fragmentManager = getSupportFragmentManager();
        fragment = new TrimDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(TrimDetailsFragment.EXTRA_TRIM_DATA, getIntent().getExtras().getParcelable(TrimDetailsFragment.EXTRA_TRIM_DATA));
        fragment.setArguments(args);
        addFragment(fragment, false, R.id.fragment_container);
    }


    protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }


}
