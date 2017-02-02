package mrblack.myapplication.fashiondetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.florent37.materialimageloading.MaterialImageLoading;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import butterknife.OnClick;
import io.realm.Realm;
import mrblack.myapplication.R;
import mrblack.myapplication.fashiondetail.adapter.FashionAdapter;
import mrblack.myapplication.fashiondetail.model.inforfashion;
import mrblack.myapplication.fashiondetail.model.inforfashiondetail;
import mrblack.myapplication.fashiondetailcontent.fashiondetailcontent;
import mrblack.myapplication.model.FashionInfor;


public class FashionDetailActivity extends AppCompatActivity implements FashionAdapter.ClickEventListener {
    @Bind(R.id.rcvList)
    RecyclerView rcvFashion;
    @Bind(R.id.ivContent)
    ImageView ivContent;
    @Bind(R.id.txtBMI)
    TextView txtBMI;
    @Bind(R.id.titleInfor)
    TextView titleInfor;
   /* @Bind(R.id.txtBuoi)
    TextView txtBuoi;*/


    @Bind(R.id.btnShoppingandParty)
    Button btnShoppingandParty;
    @Bind(R.id.btnTowork)
    Button btnToWork;
    @Bind(R.id.pLoading)
    ProgressBar pLoading;

    FashionAdapter mFashionAdapter;
    ArrayList<inforfashion> listInforFashion=null;
    private String TAG="TAG";
    private Realm mRealm;
    Firebase root = null;
    private String url = "https://stylefashion-1e05b.firebaseio.com/eu/map/shopingandparty";
    String body="";
    public String urldetail="";
    private int checkCountry=0;
    private InterstitialAd mInterstitialAd;
    private CountDownTimer mCountDownTimer;
    private Button mRetryButton;
    private boolean mGameIsInProgress;
    private long mTimerMilliseconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashion_detail);
        ButterKnife.bind(this);
        mRealm = Realm.getInstance(this);
        mFashionAdapter = new FashionAdapter(this, (FashionAdapter.ClickEventListener) FashionDetailActivity.this);
        listInforFashion=new ArrayList<>();
        Firebase.setAndroidContext(this);
        /*txtBuoi.setText(getTimeFromAndroid());*/
        FashionInfor fashionInfor=mRealm.where(FashionInfor.class).findFirst();
        body=detectBmi(fashionInfor.getBmi());
        checkCountry=fashionInfor.getSex();
        txtBMI.setText(body);
        detecterUrlPartyShoppingBMI(detectBmi(fashionInfor.getBmi()));

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



    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @OnClick(R.id.ivContent)
    public void setDetailInfor(){
        Intent intent=new Intent(this,fashiondetailcontent.class);
        intent.putExtra("url",urldetail);
        intent.putExtra("titleInfor",titleInfor.getText().toString());
        startActivity(intent);
    }
    @OnClick(R.id.btnShoppingandParty)
    public void btnShoppingandParty(){
        detecterUrlPartyShoppingBMI(body);
    }
    @OnClick(R.id.btnTowork)
    public void setBtnToWork(){
        detecterUrlPtoWorkBMI(body);
    }

    private String getTimeFromAndroid() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        if(DateFormat.is24HourFormat(this)) {
            if (hours >= 1 && hours <= 12) {
                return "Good Morning";
            } else if (hours >= 12 && hours <= 16) {
                return "Good Afternoon";
            } else if (hours >= 16 && hours <= 21) {
                return "Good Evening";
            } else if (hours >= 21 && hours <= 24) {
                return "Good Night";
            }
        }else{
            return hours+"h"+min;
        }
        return hours+"h"+min;
    }
    private String detectBmi(int bmi){
        if (bmi >= 30) {
            return "OBESE";
        } else if (bmi >= 25) {
            return "OVERWEIGHT";
        } else if (bmi >= 18.5) {
            return "IDEAL";
        } else {
            return "UNDERWEIGHT";
        }
    }
    private void updateFiseBase(){
        root = new Firebase(url);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                checkCountry(dataSnapshot);
                mFashionAdapter.setDataFashion(listInforFashion);
                rcvFashion.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
                rcvFashion.setNestedScrollingEnabled(false);
                rcvFashion.setAdapter(mFashionAdapter);
                rcvFashion.setHasFixedSize(true);
                rcvFashion.setFocusable(false);
                urldetail=listInforFashion.get(0).getImage_color();
                pLoading.setVisibility(View.VISIBLE);
                Glide.with(FashionDetailActivity.this).load(listInforFashion.get(0).getImage_color()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ivContent.setImageBitmap(resource);
                        MaterialImageLoading.animate(ivContent).setDuration(1000).start();
                        pLoading.setVisibility(View.GONE);

                    }
                });
                titleInfor.setText(listInforFashion.get(0).getTitle());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
    private void checkCountry(DataSnapshot dataSnapshot){
        if(checkCountry==1){
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                inforfashion mInforfashion=new inforfashion();
                mInforfashion.setId(child.getValue(inforfashion.class).getId());
                mInforfashion.setImage(child.getValue(inforfashion.class).getImage());
                mInforfashion.setImage_color(child.getValue(inforfashion.class).getImage_asia());
                mInforfashion.setName(child.getValue(inforfashion.class).getName());
                mInforfashion.setTitle(child.getValue(inforfashion.class).getTitle());
                mInforfashion.setInformation(child.getValue(inforfashion.class).getInformation());
                listInforFashion.add(mInforfashion);
            }
        }else{
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                inforfashion mInforfashion=new inforfashion();
                mInforfashion.setId(child.getValue(inforfashion.class).getId());
                mInforfashion.setImage(child.getValue(inforfashion.class).getImage());
                mInforfashion.setImage_color(child.getValue(inforfashion.class).getImage_color());
                mInforfashion.setName(child.getValue(inforfashion.class).getName());
                mInforfashion.setTitle(child.getValue(inforfashion.class).getTitle());
                mInforfashion.setInformation(child.getValue(inforfashion.class).getInformation());
                listInforFashion.add(mInforfashion);
            }
        }
    }

    @Override
    public void onDeleteEvent(View view, int position) {
        urldetail=listInforFashion.get(position).getImage_color();
      //  pLoading.setVisibility(View.VISIBLE);
        Glide.with(FashionDetailActivity.this).load(urldetail).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                ivContent.setImageBitmap(resource);
                MaterialImageLoading.animate(ivContent).setDuration(1000).start();
             //   pLoading.setVisibility(View.GONE);
            }
        });
        titleInfor.setText(listInforFashion.get(position).getTitle());
    }
    private void detecterUrlPartyShoppingBMI(String tvBMI) {
        if (tvBMI.equals("OBESE") || tvBMI.equals("OVERWEIGHT")) {
            url="https://stylefashion-1e05b.firebaseio.com/eu/map/shopingandparty";
        } else if (tvBMI.equals("IDEAL")) {
            url="https://stylefashion-1e05b.firebaseio.com/eu/chuan/shopingandparty";
        } else {
            url="https://stylefashion-1e05b.firebaseio.com/eu/gay/shopingandparty";
        }
        listInforFashion.clear();
        mFashionAdapter.notifyDataSetChanged();
        updateFiseBase();
    }

    private void detecterUrlPtoWorkBMI(String tvBMI) {
        if (tvBMI.equals("OBESE") || tvBMI.equals("OVERWEIGHT")) {
            url="https://stylefashion-1e05b.firebaseio.com/eu/map/towork";
        } else if (tvBMI.equals("IDEAL")) {
            url="https://stylefashion-1e05b.firebaseio.com/eu/chuan/towork";
        } else {
            url="https://stylefashion-1e05b.firebaseio.com/eu/gay/towork";
        }
        listInforFashion.clear();
        mFashionAdapter.notifyDataSetChanged();
        updateFiseBase();
    }


    @Override
    public void onPause() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startGame();
    }
}
