package mrblack.myapplication;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.realm.Realm;
import mrblack.myapplication.baseactivity.BaseActivity;
import mrblack.myapplication.fashiondetail.FashionDetailActivity;
import mrblack.myapplication.model.FashionInfor;
import mrblack.myapplication.view.TableRadioGroup;


public class MainActivity extends BaseActivity {

    @Bind(R.id.radioGroupAge)
    RelativeLayout radioGroupAge;
    @Bind(R.id.radioGroupHeight)
    LinearLayout radioGroupHeight;
    @Bind(R.id.radioGroupSex)
    LinearLayout radioGroupSex;
    @Bind(R.id.radioGroupWeight)
    LinearLayout radioGroupWeight;
    @Bind(R.id.txtTitleAge)
    TextView txtTitleAge;
    @Bind(R.id.txtTitleHeight)
    TextView txtTitleHeight;
    @Bind(R.id.txtTitleSex)
    TextView txtTitleSex;
    @Bind(R.id.txtTitleWeight)
    TextView txtTitleWeight;
    @Bind(R.id.viewAge)
    View viewAge;
    @Bind(R.id.viewHeight)
    View viewHeight;
    @Bind(R.id.viewSex)
    View viewSex;
    @Bind(R.id.viewWeight)
    View viewWeight;

    @Bind(R.id.rdAgeCheck1)
    RadioButton rdAgeCheck1;
    @Bind(R.id.rdAgeCheck2)
    RadioButton rdAgeCheck2;
    @Bind(R.id.rdAgeCheck3)
    RadioButton rdAgeCheck3;
    @Bind(R.id.rdAgeCheck4)
    RadioButton rdAgeCheck4;
    @Bind(R.id.rdAgeCheck5)
    RadioButton rdAgeCheck5;
    @Bind(R.id.rdAgeCheck6)
    RadioButton rdAgeCheck6;


    @Bind(R.id.rdContinentsCheck1)
    RadioButton rdEuroCheck;
    @Bind(R.id.rdContinentsCheck2)
    RadioButton rdAmerican;
    @Bind(R.id.rdContinentsCheck3)
    RadioButton rdAfrica;
    @Bind(R.id.rdContinentsCheck4)
    RadioButton rdAsia;
    @Bind(R.id.rdContinentsCheck5)
    RadioButton rdOther;


    @Bind(R.id.rdWeightCheck1)
    RadioButton rdWeightCheck1;
    @Bind(R.id.rdWeightCheck2)
    RadioButton rdWeightCheck2;
    @Bind(R.id.rdWeightCheck3)
    RadioButton rdWeightCheck3;
    @Bind(R.id.rdWeightCheck4)
    RadioButton rdWeightCheck4;
    @Bind(R.id.rdWeightCheck5)
    RadioButton rdWeightCheck5;
    @Bind(R.id.rdWeightCheck6)
    RadioButton rdWeightCheck6;

    @Bind(R.id.rdHeightCheck1)
    RadioButton rdHeightCheck1;
    @Bind(R.id.rdHeightCheck2)
    RadioButton rdHeightCheck2;
    @Bind(R.id.rdHeightCheck3)
    RadioButton rdHeightCheck3;

    @Bind(R.id.btnSex)
    Button btnSex;
    @Bind(R.id.btnAge)
    Button btnAge;
    @Bind(R.id.btnWeight)
    Button btnWeight;
    @Bind(R.id.btnHeight)
    Button btnHeight;

    @Bind(R.id.adView)
    AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private CountDownTimer mCountDownTimer;
    private Button mRetryButton;
    private boolean mGameIsInProgress;
    private long mTimerMilliseconds;


    FashionInfor fashionInfor=null;
    private Realm mRealm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this, "ca-app-pub-3142747330671419~8763967886");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3142747330671419/2717434285");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                //  startGame();
            }
        });
        startGame();


        mRealm = Realm.getInstance(this);
        mRealm.beginTransaction();
        fashionInfor = mRealm.createObject(FashionInfor.class);
        FashionInfor fashionInfor=mRealm.where(FashionInfor.class).findFirst();
        Log.d(TAG, "onStart111: "+fashionInfor.getWeight());
        if(!String.valueOf(fashionInfor.getHeight()).equals("0.0"))
            startActivityForResult(new Intent(this, FashionDetailActivity.class),3002);

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (!rdEuroCheck.isChecked() || !rdAsia.isChecked()||!rdAmerican.isChecked()||!rdAfrica.isChecked()||!rdOther.isChecked()) {
            btnSex.setEnabled(false);
        }
    }


    @OnClick(R.id.btnSex)
    void onGroupSex() {
        radioGroupSex.setVisibility(View.GONE);
        radioGroupAge.setVisibility(View.VISIBLE);
        btnAge.setEnabled(false);
        txtTitleSex.setTextColor(getResources().getColor(R.color.colorGrayWhite));
        txtTitleAge.setTextColor(getResources().getColor(R.color.colorWhite));
        viewSex.setBackground(getResources().getDrawable(R.drawable.bg_circle_gray_white));
        viewAge.setBackground(getResources().getDrawable(R.drawable.bg_circle_white));
        setFashionInforSex();
    }

    @OnCheckedChanged(R.id.rdContinentsCheck1)
    void onContinentsCheck1() {
        btnSex.setEnabled(true);
    }

    @OnCheckedChanged(R.id.rdContinentsCheck2)
    void onContinentsCheck2() {
        btnSex.setEnabled(true);

    }
    @OnCheckedChanged(R.id.rdContinentsCheck3)
    void onContinentsCheck3() {
        btnSex.setEnabled(true);

    }
    @OnCheckedChanged(R.id.rdContinentsCheck4)
    void onContinentsCheck4() {
        btnSex.setEnabled(true);

    }
    @OnCheckedChanged(R.id.rdContinentsCheck5)
    void onContinentsCheck5() {
        btnSex.setEnabled(true);

    }





    @OnCheckedChanged(R.id.rdAgeCheck1)
    void onAgeCheck1() {
        btnAge.setEnabled(true);
    }

    @OnCheckedChanged(R.id.rdAgeCheck2)
    void onAgeCheck2() {
        btnAge.setEnabled(true);
    }

    @OnCheckedChanged(R.id.rdAgeCheck3)
    void onAgeCheck3() {
        btnAge.setEnabled(true);
    }

    @OnCheckedChanged(R.id.rdAgeCheck4)
    void onAgeCheck4() {
        btnAge.setEnabled(true);
    }

    @OnCheckedChanged(R.id.rdAgeCheck5)
    void onAgeCheck5() {
        btnAge.setEnabled(true);
    }

    @OnCheckedChanged(R.id.rdAgeCheck6)
    void onAgeCheck6() {
        btnAge.setEnabled(true);
    }


    @OnClick(R.id.btnAge)
    void onGroupAge() {
        radioGroupAge.setVisibility(View.GONE);
        radioGroupHeight.setVisibility(View.VISIBLE);
        btnHeight.setEnabled(false);
        txtTitleAge.setTextColor(getResources().getColor(R.color.colorGrayWhite));
        txtTitleHeight.setTextColor(getResources().getColor(R.color.colorWhite));
        viewAge.setBackground(getResources().getDrawable(R.drawable.bg_circle_gray_white));
        viewHeight.setBackground(getResources().getDrawable(R.drawable.bg_circle_white));
        setFashionInforAge();
    }

    @OnClick(R.id.btnAgeCancel)
    void onAgeCancel() {
        radioGroupSex.setVisibility(View.VISIBLE);
        radioGroupAge.setVisibility(View.GONE);
        fashionInfor.setAge(0);
    }

    @OnCheckedChanged(R.id.rdHeightCheck1)
    void onHeightCheck1() {
        btnHeight.setEnabled(true);
    }

    @OnCheckedChanged(R.id.rdHeightCheck2)
    void onHeightCheck2() {
        btnHeight.setEnabled(true);
    }

    @OnCheckedChanged(R.id.rdHeightCheck3)
    void onHeightCheck3() {
        btnHeight.setEnabled(true);
    }

    @OnClick(R.id.btnHeight)
    void onGroupHeight() {
        radioGroupHeight.setVisibility(View.GONE);
        radioGroupWeight.setVisibility(View.VISIBLE);
        btnWeight.setEnabled(false);
        txtTitleHeight.setTextColor(getResources().getColor(R.color.colorGrayWhite));
        txtTitleWeight.setTextColor(getResources().getColor(R.color.colorWhite));
        viewHeight.setBackground(getResources().getDrawable(R.drawable.bg_circle_gray_white));
        viewWeight.setBackground(getResources().getDrawable(R.drawable.bg_circle_white));
        setFashionInforHeight();
    }

    @OnClick(R.id.btnHeightCancel)
    void onHeightCancel() {
        radioGroupAge.setVisibility(View.VISIBLE);
        radioGroupHeight.setVisibility(View.GONE);
        fashionInfor.setHeight((float) 0);
    }

    @OnCheckedChanged(R.id.rdWeightCheck1)
    void onWeightCheck1() {
        btnWeight.setEnabled(true);
    }

    @OnCheckedChanged(R.id.rdWeightCheck2)
    void onWeightCheck2() {
        btnWeight.setEnabled(true);
    }

    @OnCheckedChanged(R.id.rdWeightCheck3)
    void onWeightCheck3() {
        btnWeight.setEnabled(true);
    }

    @OnCheckedChanged(R.id.rdWeightCheck4)
    void onWeightCheck4() {
        btnWeight.setEnabled(true);
    }

    @OnCheckedChanged(R.id.rdWeightCheck5)
    void onWeightCheck5() {
        btnWeight.setEnabled(true);
    }

    @OnCheckedChanged(R.id.rdWeightCheck6)
    void onWeightCheck6() {
        btnWeight.setEnabled(true);
    }

    @OnClick(R.id.btnWeight)
    void onGroupWeight() {
      setFashionInforWeight();
        Log.d(TAG, "onEventDetail: "+fashionInfor.getAge());
        Log.d(TAG, "onEventDetail: "+fashionInfor.getHeight());
        Log.d(TAG, "onEventDetail: "+fashionInfor.getWeight());
        Log.d(TAG, "onEventDetail: "+fashionInfor.getSex());
        int bmi= (int) (fashionInfor.getWeight()/(fashionInfor.getHeight()*fashionInfor.getHeight()));
        Log.d(TAG, "onGroupWeight: "+bmi);
        fashionInfor.setBmi(bmi);
        mRealm.commitTransaction();
        EventBus.getDefault().post(fashionInfor);

        startActivityForResult(new Intent(this, FashionDetailActivity.class),3002);
        // viewHeight.setBackgroundColor(getResources().getColor(R.color.colorGrayWhite));
        // viewWeight.setBackgroundColor(getResources().getColor(R.color.colorWhite));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    @OnClick(R.id.btnWeightCancel)
    void onWeightCancel() {
        radioGroupHeight.setVisibility(View.VISIBLE);
        radioGroupWeight.setVisibility(View.GONE);
        fashionInfor.setWeight((float) 0);
    }

    //check fashion infor
    public void setFashionInforSex(){
       /* if (!rdEuroCheck.isChecked() || !rdAsia.isChecked()||!rdNouthAmerican.isChecked()||!rdAfrica.isChecked()
                ||!rdSouthAmerican.isChecked()||!rdOrher.isChecked()) {
            btnSex.setEnabled(false);
        }*/
        if(rdEuroCheck.isChecked()){
            setSex(0);
        }else if(rdAsia.isChecked()){
            setSex(1);
        }else if(rdAmerican.isChecked()){
            setSex(2);
        }else if(rdAfrica.isChecked()){
            setSex(3);
        }else if(rdOther.isChecked()){
            setSex(4);
        }
    }
    private void setSex(int n){
        fashionInfor.setSex(n);
    }
    public void setFashionInforAge(){
        if(rdAgeCheck1.isChecked()){
            setAge(13);
        }else if(rdAgeCheck2.isChecked()){
            setAge(17);
        }else if(rdAgeCheck3.isChecked()){
            setAge(22);
        }else if(rdAgeCheck4.isChecked()){
            setAge(25);
        }else if(rdAgeCheck5.isChecked()){
            setAge(35);
        }else if(rdAgeCheck6.isChecked()){
            setAge(50);
        }
    }
    private void setAge(int n){
        fashionInfor.setAge(n);
    }
    public void setFashionInforHeight(){
        if(rdHeightCheck1.isChecked()){
            setHeight((float) 1.5);
        }else if(rdHeightCheck2.isChecked()){
            setHeight((float) 1.6);
        }else if(rdHeightCheck3.isChecked()){
            setHeight((float) 1.75);
        }
    }
    private void setHeight(float n){
        fashionInfor.setHeight(n);
    }
    private void setFashionInforWeight(){
        if(rdWeightCheck1.isChecked()){
            setWeight(30);
        }else if(rdWeightCheck2.isChecked()){
            setWeight(40);
        }else if(rdWeightCheck3.isChecked()){
            setWeight(50);
        }else if(rdWeightCheck4.isChecked()){
            setWeight(60);
        }else if(rdWeightCheck5.isChecked()){
            setWeight(70);
        }else if(rdWeightCheck6.isChecked()){
            setWeight(80);
        }
    }
    private void setWeight(float n){
        fashionInfor.setWeight(n);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        if (mGameIsInProgress) {
            resumeGame(mTimerMilliseconds);
        }

    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            startGame();
        }
    }

    private void startGame() {
        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
        resumeGame(1);
    }

    private void resumeGame(long milliseconds) {
        // Create a new timer for the correct length and start it.
        mGameIsInProgress = true;
        mTimerMilliseconds = milliseconds;
        createTimer(milliseconds);
        mCountDownTimer.start();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
        mRealm.close();
    }

    private void createTimer(final long milliseconds) {
        // Create the game timer, which counts down to the end of the level
        // and shows the "retry" button.
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

        mCountDownTimer = new CountDownTimer(milliseconds, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                mTimerMilliseconds = millisUnitFinished;
            }

            @Override
            public void onFinish() {
                mGameIsInProgress = false;
                showInterstitial();
            }
        };
    }
}
