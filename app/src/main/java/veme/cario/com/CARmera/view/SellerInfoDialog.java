package veme.cario.com.CARmera.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import veme.cario.com.CARmera.R;

/**
 * Created by bski on 12/3/14.
 */
public class SellerInfoDialog extends DialogFragment {

    private TextView seller_phone_tv;
    private TextView seller_info_tv;

    @Override
    public Dialog onCreateDialog (Bundle savedBundleInst) {
        super.onCreateDialog(savedBundleInst);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.seller_info_dialog, null))
                .setPositiveButton(R.string.str_create_new_contact, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* sends an intent to create contact */
                    }
                })
                .setNegativeButton(R.string.str_close_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SellerInfoDialog.this.getDialog().cancel();
                    }
                });
        Bundle args = getArguments();

        String seller_info = args.getString("seller_info");
        seller_info_tv = (TextView) getView().findViewById((R.id.seller_info_textview));
        seller_info_tv.setText(seller_info);

        byte [] seller_img_arr = args.getByteArray("seller_thumbnail");

        String phone = args.getString("seller_phone");
        if (phone != null) {
            seller_phone_tv = (TextView) getView().findViewById(R.id.seller_phone_textview);
            seller_phone_tv.setText(phone);
            seller_phone_tv.setClickable(true);
            seller_phone_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* make phone call here */
                }
            });
        } else {
            seller_phone_tv.setVisibility(View.GONE);
        }

        return builder.create();
    }

}
