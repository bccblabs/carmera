package veme.cario.com.CARmera.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import veme.cario.com.CARmera.R;

/**
 * Created by bski on 11/10/14.
 */
public class DealershipFragment extends Fragment {

/* api call:
http://api.edmunds.com/api/dealer/v2/dealers/?zipcode=90404&radius=100&make=audi&state=new&pageNum=1&pageSize=10&sortby=distance%3AASC&view=basic&api_key=d442cka8a6mvgfnjcdt5fbns
http://api.edmunds.com/v1/api/drrrepository/getdrrbyzipcodeandmake?zipcode=90019&make=bmw&limit=0%2C5&fmt=json&api_key=d442cka8a6mvgfnjcdt5fbns

 */
    LinearLayout dls_overlay;
    LinearLayout dls_details_overlay;

    TextView dls_name;
    TextView dls_avg_rating; //
    TextView dls_hrs;

    /* address */
    TextView dls_addr; // street + city + stateCode + zipcode

    /* contactInfo */
    TextView dls_phone; // phone
    TextView dls_website; // website


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dealership, container, false);
    }

}
