package mrblack.myapplication.fashiondetailcontent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.firebase.client.ValueEventListener;
import com.github.florent37.materialimageloading.MaterialImageLoading;
import java.util.ArrayList;
import java.util.List;

import mrblack.myapplication.R;
import mrblack.myapplication.fashiondetail.model.inforfashion;
import mrblack.myapplication.fashiondetailcontent.model.fashiondetailmodel;
import mrblack.myapplication.utils.MathUtil;

public class detailadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<fashiondetailmodel> mfashionList = new ArrayList<>();
    private Context mContext;
    private int itemHeight;
    private ClickEventListener mClickEventListener;

    public detailadapter(Context context,ClickEventListener clickEventListener) {
        mContext = context;
        itemHeight = MathUtil.getScreenWith(context)/4;
        this.mClickEventListener=clickEventListener;

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
        fashiondetailmodel album = mfashionList.get(position);
        final FashionViewHolder newsHolder = (FashionViewHolder) holder;
        newsHolder.pLoading.setVisibility(View.VISIBLE);
        Glide.with(mContext).load(album.getImage()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                newsHolder.ivPhoto.setImageBitmap(resource);
                MaterialImageLoading.animate(newsHolder.ivPhoto).setDuration(2000).start();
                newsHolder.pLoading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mfashionList.size();
    }

    public void setDataFashion(ArrayList<fashiondetailmodel> list) {
        mfashionList = list;
        notifyDataSetChanged();
    }

    public class FashionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatImageView ivPhoto;
        ProgressBar pLoading;


        public FashionViewHolder(View itemView) {
            super(itemView);
            this.ivPhoto = (AppCompatImageView) itemView.findViewById(R.id.ivPhoto);
            this.pLoading= (ProgressBar) itemView.findViewById(R.id.pLoading);
            this.ivPhoto.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mClickEventListener.onDeleteEvent(view,getAdapterPosition());
        }
    }
    public interface ClickEventListener {
        void onDeleteEvent(View view, int position);
    }

}
