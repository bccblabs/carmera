//package carmera.io.carmera.discroll_layout;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.RelativeLayout;
//
//import com.flavienlaurent.discrollview.lib.Discrollvable;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import carmera.io.carmera.R;
//
///**
// * Created by bski on 7/17/15.
// */
//public class White extends RelativeLayout implements Discrollvable {
//    public final String TAG = this.getClass().getCanonicalName();
//
////    @Bind(R.id.rating_chart)
////    public View mLastView1;
////    @Bind(R.id.price_chart)
////    public View mLastView2;
////    @Bind(R.id.tco_chart)
////    public View mLastView3;
////
////    private float mLastView1TranslationX;
////    private float mLastView2TranslationX;
////    private float mLastView3TranslationX;
////
////    private float mLastView1TranslationY;
////    private float mLastView2TranslationY;
////    private float mLastView3TranslationY;
////
////    public White (Context context) {
////        super (context);
////    }
////
////    public White(Context context, AttributeSet attrs) {
////        super(context, attrs);
////    }
////
////    public White(Context context, AttributeSet attrs, int defStyle) {
////        super(context, attrs, defStyle);
////    }
////
////    @Override
////    protected void onFinishInflate () {
////        super.onFinishInflate();
////        ButterKnife.bind(this);
////
////        mLastView1TranslationX = mLastView1.getTranslationX();
////        mLastView2TranslationX = mLastView2.getTranslationX();
////        mLastView3TranslationX = mLastView3.getTranslationX();
////
////        mLastView1TranslationY = mLastView1.getTranslationY();
////        mLastView2TranslationY = mLastView2.getTranslationY();
////        mLastView3TranslationY = mLastView3.getTranslationY();
////    }
////    @Override
////    public void onResetDiscrollve() {
////        mLastView1.setAlpha(0);
////        mLastView1.setTranslationX(mLastView1TranslationX);
////        mLastView1.setTranslationY(mLastView1TranslationY);
////        mLastView2.setAlpha(0);
////        mLastView2.setTranslationX(mLastView2TranslationX);
////        mLastView2.setTranslationY(mLastView2TranslationY);
////        mLastView3.setAlpha(0);
////        mLastView3.setTranslationX(mLastView3TranslationX);
////        mLastView3.setTranslationY(mLastView3TranslationY);
////    }
////
////    @Override
////    public void onDiscrollve(float ratio) {
////        mLastView1.setTranslationX(mLastView1TranslationX * (1 - ratio));
////        mLastView1.setTranslationY(mLastView1TranslationY * (1 - ratio));
////        mLastView1.setAlpha(ratio);
////
////        mLastView2.setTranslationX(mLastView2TranslationX * (1 - ratio));
////        mLastView2.setTranslationY(mLastView2TranslationY * (1 - ratio));
////        mLastView2.setAlpha(ratio);
////
////        mLastView3.setTranslationX(mLastView3TranslationX * (1 - ratio));
////        mLastView3.setTranslationY(mLastView3TranslationY * (1 - ratio));
////        mLastView3.setAlpha(ratio);
////    }
//
//}
