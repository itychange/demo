package mrblack.myapplication.fashiondetail.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 1/2/2017.
 */
public class inforfashion implements Parcelable {
    private int imgfashion;
    public inforfashion(int imgfashion){
        this.imgfashion=imgfashion;
    }

    public inforfashion(Parcel in) {
        imgfashion = in.readInt();
    }

    public static final Creator<inforfashion> CREATOR = new Creator<inforfashion>() {
        @Override
        public inforfashion createFromParcel(Parcel in) {
            return new inforfashion(in);
        }

        @Override
        public inforfashion[] newArray(int size) {
            return new inforfashion[size];
        }
    };

    public int getImgfashion() {
        return imgfashion;
    }

    public void setImgfashion(int imgfashion) {
        this.imgfashion = imgfashion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(imgfashion);
    }
}
