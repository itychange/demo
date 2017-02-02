package mrblack.myapplication.fashiondetail.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 1/11/2017.
 */
public class inforfashiondetail implements Parcelable{
    private Bitmap bmp;

    public inforfashiondetail(){

    }
    protected inforfashiondetail(Parcel in) {
        bmp = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<inforfashiondetail> CREATOR = new Creator<inforfashiondetail>() {
        @Override
        public inforfashiondetail createFromParcel(Parcel in) {
            return new inforfashiondetail(in);
        }

        @Override
        public inforfashiondetail[] newArray(int size) {
            return new inforfashiondetail[size];
        }
    };

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(bmp, i);
    }
}
