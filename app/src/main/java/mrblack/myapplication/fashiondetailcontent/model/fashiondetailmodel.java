package mrblack.myapplication.fashiondetailcontent.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 1/13/2017.
 */
public class fashiondetailmodel{
    private String image;


    public fashiondetailmodel(String image){
        this.image=image;

    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
