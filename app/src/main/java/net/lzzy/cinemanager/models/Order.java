package net.lzzy.cinemanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import net.lzzy.sqllib.Ignored;
import net.lzzy.sqllib.Sqlitable;
import net.lzzy.sqllib.Table;

import java.util.UUID;

/**
 * Created by lzzy_gxy on 2019/3/11.
 * Description:
 */
@Table(name = "Orders")
public class Order extends BaseEntity implements Sqlitable, Parcelable {
    @Ignored
    static final String COL_MOVIE = "movie";
    @Ignored
    static final String COL_MOVIE_TIME = "movieTime";
    @Ignored
    static final String COL_PRICE = "price";
    @Ignored
    static final String COL_CINEMA_ID = "cinemaId";
    private String movie;
    private String movieTime;
    private float price;
    private UUID cinemaId;
    public Order(){}

    protected Order(Parcel in) {
        movie = in.readString();
        movieTime = in.readString();
        price = in.readFloat();
        cinemaId=UUID.fromString(in.readString());

    }
    @Ignored
    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(String movieTime) {
        this.movieTime = movieTime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public UUID getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(UUID cinemaId) {
        this.cinemaId = cinemaId;
    }

    @Override
    public boolean needUpdate() {
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movie);
        dest.writeString(movieTime);
        dest.writeFloat(price);
        dest.writeString(cinemaId.toString());
    }
}
