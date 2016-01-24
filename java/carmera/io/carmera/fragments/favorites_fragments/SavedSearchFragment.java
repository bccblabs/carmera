package carmera.io.carmera.fragments.favorites_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import java.util.ArrayList;
import java.util.List;
import carmera.io.carmera.R;
import carmera.io.carmera.models.ParseSavedSearch;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by bski on 1/22/16.
 */
public class SavedSearchFragment extends Fragment {

    private Context context;

    private CardRecyclerView cardRecyclerView;

    private ParseQueryAdapter<ParseSavedSearch> postsQueryAdapter;


    public static SavedSearchFragment newInstance () {
        return  new SavedSearchFragment();
    }

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cards_recycler, container, false);
        Context cxt = getActivity();
        cardRecyclerView = (CardRecyclerView) v.findViewById(R.id.cards_recycler);
        cardRecyclerView.setHasFixedSize(true);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(cxt));
        List<Card> cards = new ArrayList<>();
        cardRecyclerView.setAdapter(new CardArrayRecyclerViewAdapter(cxt, cards));

        return v;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();

        ParseQueryAdapter.QueryFactory<ParseSavedSearch> factory =
                new ParseQueryAdapter.QueryFactory<ParseSavedSearch>() {
                    @Override
                    public ParseQuery<ParseSavedSearch> create() {
                        ParseQuery<ParseSavedSearch> query = ParseSavedSearch.getQuery();
//                        query.include("user");
                        return query;
                    }
                };
        postsQueryAdapter = new ParseQueryAdapter<ParseSavedSearch>(context, factory) {
            @Override
            public View getItemView(ParseSavedSearch post, View view, ViewGroup parent) {
                if (view == null) {
                    view = View.inflate(context, R.layout.tags_card_content, null);
                }
                super.getItemView (post, view, parent);
                TextView title = (TextView) view.findViewById(R.id.desc_line0);
                title.setText(post.getSavedName());
                return view;
            }
        };


        postsQueryAdapter.loadObjects();
        postsQueryAdapter.setPaginationEnabled(false);
        postsQueryAdapter.notifyDataSetChanged();
        Toast.makeText(context, postsQueryAdapter.getCount() + " searches loaded", Toast.LENGTH_SHORT).show();

    }

}
