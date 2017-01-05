package mrblack.myapplication.fashiondetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mrblack.myapplication.R;
import mrblack.myapplication.fashiondetail.adapter.FashionAdapter;
import mrblack.myapplication.fashiondetail.model.inforfashion;


public class FashionDetailActivity extends AppCompatActivity {

    @Bind(R.id.rcvList)
    RecyclerView rcvFashion;
    FashionAdapter mFashionAdapter;
    ArrayList<inforfashion> listInforFashion=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashion_detail);
        ButterKnife.bind(this);
        mFashionAdapter = new FashionAdapter(this);
        listInforFashion=new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        for(int i=0;i<8;i++){
            listInforFashion.add(new inforfashion(i));
        }
        mFashionAdapter.setDataFashion(listInforFashion);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        rcvFashion.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
        rcvFashion.setNestedScrollingEnabled(false);
        rcvFashion.setAdapter(mFashionAdapter);
        rcvFashion.setHasFixedSize(true);
        rcvFashion.setFocusable(false);
    }
}
