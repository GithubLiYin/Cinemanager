package net.lzzy.cinemanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import net.lzzy.cinemanager.R;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layoutMenu;
    private TextView tvTitle;
    private SearchView searchView;
    private LinearLayout addOrderView;
    private String EXTRA_NEW_CINEMA = "new_cinema";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        sliTitle();
    }

    private void sliTitle() {
        layoutMenu = findViewById(R.id.bar_title_layout_menu);
        layoutMenu.setVisibility(View.GONE);
        findViewById(R.id.bar_title_layout_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visible = layoutMenu.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                layoutMenu.setVisibility(visible);
            }
        });
        tvTitle = findViewById(R.id.bar_title_tv);
        tvTitle.setText(R.string.bar_title_tv_my_order);
        searchView = findViewById(R.id.bar_title_sv);
        findViewById(R.id.bar_title_iv_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutMenu.setVisibility(View.VISIBLE);

            }
        });
        findViewById(R.id.bar_title_tv_my_order).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_add_order).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_view_cinema).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_add_cinema).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_title_tv_my_order:

                break;
            case R.id.bar_title_tv_add_order:
                addOrderView.setVisibility(View.VISIBLE);
                layoutMenu.setVisibility(View.GONE);
                break;
            case R.id.bar_title_tv_add_cinema:
                Intent intent = new Intent(this, CinemasActivity.class);
                intent.putExtra(EXTRA_NEW_CINEMA, true);
                startActivity(intent);
                finish();
                break;
            case R.id.bar_title_tv_view_cinema:
                startActivity(new Intent(this, CinemasActivity.class));
                finish();
                break;
            default:
                break;
        }
    }
}
