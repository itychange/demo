package mrblack.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mrblack.myapplication.baseactivity.BaseActivity;
import mrblack.myapplication.fashiondetail.FashionDetailActivity;


public class MainActivity extends BaseActivity {

    @Bind(R.id.radioGroupAge)
    LinearLayout radioGroupAge;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSex)
     void onGroupSex() {
        radioGroupSex.setVisibility(View.GONE);
        radioGroupAge.setVisibility(View.VISIBLE);
        txtTitleSex.setTextColor(getResources().getColor(R.color.colorGrayWhite));
        txtTitleAge.setTextColor(getResources().getColor(R.color.colorWhite));
        viewSex.setBackground(getResources().getDrawable(R.drawable.bg_circle_gray_white));
        viewAge.setBackground(getResources().getDrawable(R.drawable.bg_circle_white));
    }

    @OnClick(R.id.btnAge)
     void onGroupAge() {
        radioGroupAge.setVisibility(View.GONE);
        radioGroupHeight.setVisibility(View.VISIBLE);
        txtTitleAge.setTextColor(getResources().getColor(R.color.colorGrayWhite));
        txtTitleHeight.setTextColor(getResources().getColor(R.color.colorWhite));
        viewAge.setBackground(getResources().getDrawable(R.drawable.bg_circle_gray_white));
        viewHeight.setBackground(getResources().getDrawable(R.drawable.bg_circle_white));
    }

    @OnClick(R.id.btnHeight)
     void onGroupHeight() {
        radioGroupHeight.setVisibility(View.GONE);
        radioGroupWeight.setVisibility(View.VISIBLE);
        txtTitleHeight.setTextColor(getResources().getColor(R.color.colorGrayWhite));
        txtTitleWeight.setTextColor(getResources().getColor(R.color.colorWhite));
        viewHeight.setBackground(getResources().getDrawable(R.drawable.bg_circle_gray_white));
        viewWeight.setBackground(getResources().getDrawable(R.drawable.bg_circle_white));
    }

    @OnClick(R.id.btnWeight)
     void onGroupWeight() {
        startActivity(new Intent(this, FashionDetailActivity.class));
       // viewHeight.setBackgroundColor(getResources().getColor(R.color.colorGrayWhite));
       // viewWeight.setBackgroundColor(getResources().getColor(R.color.colorWhite));
    }
}
