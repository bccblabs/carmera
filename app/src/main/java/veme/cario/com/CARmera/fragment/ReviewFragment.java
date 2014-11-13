package veme.cario.com.CARmera.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import veme.cario.com.CARmera.R;

/**
 * Created by bski on 11/10/14.
 */
public class ReviewFragment extends Fragment {
/* api call
https://api.edmunds.com/api/vehicle/v2/grade/honda/accord/2013?submodel=sedan&fmt=json&api_key=d442cka8a6mvgfnjcdt5fbns
https://api.edmunds.com/api/vehiclereviews/v2/audi/a4/2013?sortby=thumbsUp%3AASC&pagenum=1&pagesize=10&fmt=json&api_key=d442cka8a6mvgfnjcdt5fbns
*/

    private LinearLayout ed_review_layout;
    private LinearLayout cust_review_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

}
