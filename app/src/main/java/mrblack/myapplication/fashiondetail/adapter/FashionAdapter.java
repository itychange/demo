package mrblack.myapplication.fashiondetail.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mrblack.myapplication.R;
import mrblack.myapplication.fashiondetail.model.inforfashion;
import mrblack.myapplication.utils.MathUtil;

public class FashionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<inforfashion> mfashionList = new ArrayList<>();
    private Context mContext;
    private OnClickAlbumListener mListener;
    private int itemHeight;

    public FashionAdapter(Context context) {
        mContext = context;
        itemHeight = MathUtil.getScreenWith(context)/4;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fashiondetail, null);
        FashionViewHolder viewHolder = new FashionViewHolder(view);
        return viewHolder;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        inforfashion album = mfashionList.get(position);
        FashionViewHolder newsHolder = (FashionViewHolder) holder;


    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mfashionList.size();
    }

    public void setDataFashion(ArrayList<inforfashion> list) {
        mfashionList = list;
        notifyDataSetChanged();
    }

    public class FashionViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView ivPhoto;
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getLayoutPosition();
                inforfashion fashion = mfashionList.get(pos);
                mListener.onClickAlbum(fashion, pos);
            }
        };

        public FashionViewHolder(View itemView) {
            super(itemView);
           // this.ivPhoto = (AppCompatImageView) itemView.findViewById(R.id.ivPhoto);
            /*RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            layoutParams.height =75;
            itemView.setLayoutParams(layoutParams);*/
            itemView.setPadding(4,4,4,4);
            //itemView.setOnClickListener(clickListener);
        }
    }

    interface OnClickAlbumListener {
        void onClickAlbum(inforfashion model, int position);
    }


}
