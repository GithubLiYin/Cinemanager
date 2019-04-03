package net.lzzy.cinemanager.activities;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.fragments.CinemaOrderFragment;
import net.lzzy.cinemanager.fragments.CinemasFragment;

import static net.lzzy.cinemanager.activities.MainActivity.EXTRA_CINEMA_ID;

/**
 *
 * @author lzzy_gxy
 * @date 2019/4/2
 * Description:
 */
public class CinemaOrdersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cinema_orders);
        String cinemaId=getIntent().getStringExtra(EXTRA_CINEMA_ID);
        FragmentManager manager=getSupportFragmentManager();
        Fragment fragment= manager.findFragmentById(R.id.cinema_orders_container);
        if (fragment==null){
            fragment=CinemaOrderFragment.newinstence(cinemaId);
            manager.beginTransaction().add(R.id.cinema_orders_container,fragment).commit();

        }



    }
}
