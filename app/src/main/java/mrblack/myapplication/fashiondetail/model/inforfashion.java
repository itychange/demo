package mrblack.myapplication.fashiondetail.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 1/2/2017.
 */
public class inforfashion implements Parcelable {
    private int imgfashion;
    private int id;
    private String image;
    private String image_color;
    private String name;
    private String title;
    private String information;
    private String image_asia;

    public inforfashion(){

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

    public String getImage_asia() {
        return image_asia;
    }

    public void setImage_asia(String image_asia) {
        this.image_asia = image_asia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_color() {
        return image_color;
    }

    public void setImage_color(String image_color) {
        this.image_color = image_color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public inforfashion(int imgfashion){
        this.imgfashion=imgfashion;
    }

    public inforfashion(Parcel in) {
        imgfashion = in.readInt();
    }



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
        parcel.writeInt(id);
        parcel.writeString(image);
        parcel.writeString(image_color);
        parcel.writeString(name);
        parcel.writeString(title);
        parcel.writeString(information);
        parcel.writeString(image_asia);

    }
}
