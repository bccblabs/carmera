package veme.cario.com.CARmera.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import veme.cario.com.CARmera.ListingsActivity;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.SavedSearch;
import veme.cario.com.CARmera.util.SavedSearchAdapter;

public class SavedSearchFragment extends Fragment {

    private SavedSearchAdapter savedSearchAdapter;
    private ListView saved_search_listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_search, container, false);
        saved_search_listview = (ListView) view.findViewById(R.id.saved_search_list_view);
        savedSearchAdapter = new SavedSearchAdapter(inflater.getContext());
        saved_search_listview.setAdapter(savedSearchAdapter);

        /* load data from parse query */
        ParseQuery<SavedSearch> query = ParseQuery.getQuery("SavedSearch");
        query.findInBackground(new FindCallback<SavedSearch>() {
            @Override
            public void done(List<SavedSearch> savedSearches, ParseException e) {
                for (SavedSearch savedSearch : savedSearches) {
                    savedSearchAdapter.add (savedSearch);
                }
            }
        });
        savedSearchAdapter.notifyDataSetChanged();

        saved_search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final SavedSearch savedSearch = (SavedSearch) saved_search_listview.getItemAtPosition(position);
                Bundle args = new Bundle();
                args.putString("saved_search_id", savedSearch.getObjectId());
                Intent listings_intent = new Intent(getActivity(), ListingsActivity.class);
                listings_intent.putExtras(args);
                startActivity(listings_intent);
            }
        });

        return view;
    }
}
