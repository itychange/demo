package mrblack.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by PC on 1/6/2017.
 */
public class FashionInfor extends RealmObject  {
    @PrimaryKey
    private  int sex;
    private  int bmi;
    private  int calo;
    private  float height;
    private  float weight;
    private  int age;
    public FashionInfor(){

    }

    protected FashionInfor(Parcel in) {
        height = in.readFloat();
        weight = in.readFloat();
        age = in.readInt();
        sex = in.readInt();
        bmi=in.readInt();
        calo=in.readInt();
    }


    public float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public int getBmi() {
        return bmi;
    }

    public void setBmi(int bmi) {
        this.bmi = bmi;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getCalo() {
        return calo;
    }

    public void setCalo(int calo) {
        this.calo = calo;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}

