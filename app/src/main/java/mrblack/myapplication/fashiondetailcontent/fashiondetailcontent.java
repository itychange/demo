package mrblack.myapplication.fashiondetailcontent;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.florent37.materialimageloading.MaterialImageLoading;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mrblack.myapplication.R;
import mrblack.myapplication.baseactivity.BaseActivity;
import mrblack.myapplication.fashiondetail.model.inforfashion;
import mrblack.myapplication.fashiondetail.model.inforfashiondetail;
import mrblack.myapplication.fashiondetailcontent.model.fashiondetailmodel;


public class fashiondetailcontent extends BaseActivity {

    private static final int RC_EXTERNAL_STORAGE_PERM = 123;
    public static final String TAG_ARTWORK = "artwork";


    @Bind(R.id.artDetailImageView)
    ImageView artDetailImageView;

    @Bind(R.id.layout_bottom_sheet)
    View bottomSheetLayout;

/*    @Bind(R.id.artTitle)
    TextView artTitle;

    @Bind(R.id.artSubTitle)
    TextView artSubTitle;*/

    @Bind(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;

    @Bind(R.id.saveButton)
    FloatingActionButton mFavoriteButton;


    @Bind(R.id.llLayout)
    LinearLayout llLayout;



    private String TAG="TAG";
    private boolean isFavoriteChanged = false;

    private static final long GAME_LENGTH_MILLISECONDS = 30000;


    Firebase root = null;
    private String url = "";


    private List<fashiondetailmodel> arrayList=null ;
    Intent intent=null;


    private InterstitialAd mInterstitialAd;
    private CountDownTimer mCountDownTimer;
    private Button mRetryButton;
    private boolean mGameIsInProgress;
    private long mTimerMilliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arts_details);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        loadArtImage();
        setupSlidingUpPanelLayout();

        intent=this.getIntent();
        Log.d(TAG, "onCreate: "+intent.getStringExtra("url"));
        Glide.with(this).load(intent.getStringExtra("url")).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Log.d("bull","resource"+resource);
                artDetailImageView.setImageBitmap(resource);
                MaterialImageLoading.animate(artDetailImageView).setDuration(2000).start();
            }
        });


        detectUrl();

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

    private void setupSlidingUpPanelLayout() {
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
    }

    private boolean isPanelCollapsed() {
        return slidingUpPanelLayout.getPanelState()==SlidingUpPanelLayout.PanelState.COLLAPSED;
    }

    private void collapsePanel() {
        if(!isPanelCollapsed()) {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
    }

    private void hidePanel() {
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    private void showPanel() {
        if(isPanelCollapsed()){
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }
    }

    private void loadArtImage() {


    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void updateFiseBase(){
        arrayList=new ArrayList<>();
        root = new Firebase(url);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (final DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: "+child.getValue(inforfashion.class).getImage());
                   final View v = getLayoutInflater().inflate(R.layout.view_item_layput, null);
                    final ImageView listview = (ImageView) v.findViewById(R.id.ivPhoto);
                    Glide.with(fashiondetailcontent.this).load(child.getValue(inforfashion.class).getImage()).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            listview.setImageBitmap(resource);
                            MaterialImageLoading.animate(listview).setDuration(2000).start();
                            llLayout.addView(v);
                        }
                    });
                    listview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Glide.with(fashiondetailcontent.this).load(child.getValue(inforfashion.class).getImage()).asBitmap().into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Log.d("bull","resource"+resource);
                                    artDetailImageView.setImageBitmap(resource);
                                    MaterialImageLoading.animate(artDetailImageView).setDuration(2000).start();
                                }
                            });

                        }
                    });
/*
                    arrayList.add(new fashiondetailmodel(child.getValue(inforfashion.class).getImage()));
*/
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }



    private void detectUrl(){
        String murl=intent.getStringExtra("titleInfor");
        if(murl.equals("court shoe")){
            url="https://stylefashion-1e05b.firebaseio.com/giaycaogot/item";
            updateFiseBase();
        }else if(murl.equals("handbag big")){
            url="https://stylefashion-1e05b.firebaseio.com/tuilon/item";
            updateFiseBase();
        }else if(murl.equals("earring")){
            url="https://stylefashion-1e05b.firebaseio.com/earring/item";
            updateFiseBase();
        }else if(murl.equals("spectacles")){
            url="https://stylefashion-1e05b.firebaseio.com/kinhmat/item";
            updateFiseBase();
        }else if(murl.equals("fedora")){
            url="https://stylefashion-1e05b.firebaseio.com/fedora/item";
            updateFiseBase();
        }else if (murl.equals("belt")){
            url="https://stylefashion-1e05b.firebaseio.com/belt/item";
            updateFiseBase();
        }else if(murl.equals("dorothy bag")){
            url="https://stylefashion-1e05b.firebaseio.com/tuideocheo/item";
            updateFiseBase();
        }else if(murl.equals("hand held purse")){
            url="https://stylefashion-1e05b.firebaseio.com/vicamtay/item";
            updateFiseBase();
        }else if(murl.equals("watches")){
            url="https://stylefashion-1e05b.firebaseio.com/dongho/item";
            updateFiseBase();
        }else if(murl.equals("overcoat")){
            url="https://stylefashion-1e05b.firebaseio.com/aokhoac/item";
            updateFiseBase();
        }else if(murl.equals("vest")){
            url="https://stylefashion-1e05b.firebaseio.com/aovest/item";
            updateFiseBase();
        }else if(murl.equals("scarpin")){
            url="https://stylefashion-1e05b.firebaseio.com/giaycaogot/item";
            updateFiseBase();
        }else if(murl.equals("notebook")||murl.equals("slip-on")){
            return;
        }
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

}
