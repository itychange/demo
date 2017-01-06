package mrblack.myapplication.fashiondetailcontent;

import android.os.Bundle;
import android.os.PersistableBundle;

import mrblack.myapplication.R;
import mrblack.myapplication.baseactivity.BaseActivity;

/**
 * Created by PC on 1/6/2017.
 */
public class fashiondetailcontent extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_fashion_detail);
    }
}
