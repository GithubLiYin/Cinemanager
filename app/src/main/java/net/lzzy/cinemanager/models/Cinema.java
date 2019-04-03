package net.lzzy.cinemanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import net.lzzy.sqllib.Ignored;
import net.lzzy.sqllib.Sqlitable;

/**
 * Created by lzzy_gxy on 2019/3/11.
 * Description:
 */
public class Cinema extends BaseEntity implements Sqlitable, Parcelable {
    @Ignored
    static final String COL_LOCATION = "location";
    private String name;
    private String location;
    private String province;
    private String city;
    private String area;
    public Cinema(){}

    protected Cinema(Parcel in) {
        name = in.readString();
        location = in.readString();
        province = in.readString();
        city = in.readString();
        area = in.readString();
    }
@Ignored
    public static final Creator<Cinema> CREATOR = new Creator<Cinema>() {
        @Override
        public Cinema createFromParcel(Parcel in) {
            return new Cinema(in);
        }

        @Override
        public Cinema[] newArray(int size) {
            return new Cinema[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public boolean needUpdate() {
        return false;
    }

    @Override
    public String toString() {
        return location+name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(obj instanceof Cinema){
            return this.toString().equals(obj.toString());
        }else {
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(location);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(area);
    }
}
