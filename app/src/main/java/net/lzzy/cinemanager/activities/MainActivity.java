package net.lzzy.cinemanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.fragments.AddCinemaFragment;
import net.lzzy.cinemanager.fragments.AddOrderFragment;
import net.lzzy.cinemanager.fragments.BaseFragment;
import net.lzzy.cinemanager.fragments.CinemasFragment;
import net.lzzy.cinemanager.fragments.OnFragmentInteractionListener;
import net.lzzy.cinemanager.fragments.OrdersFragment;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.utils.ViewUtils;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity
        implements
        View.OnClickListener
        , OnFragmentInteractionListener, AddCinemaFragment.OnCinemaCreateListener
        ,AddOrderFragment.OnOrderCreateListener , CinemasFragment.OnCinemaSelectedListener {
    private FragmentManager manager = getSupportFragmentManager();
    private SparseArray<Fragment> fragmentArray = new SparseArray<>();
    private SparseArray<String> titleArray = new SparseArray<>();
    private LinearLayout layoutMenu;
    private TextView tvTitle;
    private SearchView searchView;
    public static String EXTRA_CINEMA_ID = "cinema_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        sliTitle();
        searchView.setOnQueryTextListener(new ViewUtils.AbstractQueryHandler() {
            @Override
            public boolean handleQuery(String kw) {
                Fragment fragment=manager.findFragmentById(R.id.fragment_container);
                if (fragment!=null){
                    if (fragment instanceof BaseFragment){
                        ((BaseFragment)fragment).search(kw);
                    }
                }
                return false;
            }
        });
    }

    private void sliTitle() {
        titleArray.put(R.id.bar_title_tv_my_order, "我的订单");
        titleArray.put(R.id.bar_title_tv_add_order, "添加订单");
        titleArray.put(R.id.bar_title_tv_view_cinema, "查看影院");
        titleArray.put(R.id.bar_title_tv_add_cinema, "添加影院");
        layoutMenu = findViewById(R.id.bar_title_layout_menu);
        layoutMenu.setVisibility(View.GONE);
        findViewById(R.id.bar_title_iv_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visible = layoutMenu.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                layoutMenu.setVisibility(visible);
            }
        });
        tvTitle = findViewById(R.id.bar_title_tv);
        tvTitle.setText("我的订单");
        searchView = findViewById(R.id.bar_title_sv);
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
        layoutMenu.setVisibility(View.GONE);
        tvTitle.setText(titleArray.get(v.getId()));
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = fragmentArray.get(v.getId());
        if (fragment == null) {
            fragment = createFragment(v.getId());
            fragmentArray.put(v.getId(), fragment);
            transaction.add(R.id.fragment_container, fragment);
        }
        for (Fragment f : manager.getFragments()) {
            transaction.hide(f);
        }
        transaction.show(fragment).commit();
    }

    private Fragment createFragment(int id) {
        switch (id) {
            case R.id.bar_title_tv_add_cinema:
                return new AddCinemaFragment();
            case R.id.bar_title_tv_view_cinema:
                return new CinemasFragment();
            case R.id.bar_title_tv_add_order:
                return new AddOrderFragment();
            case R.id.bar_title_tv_my_order:
                return new OrdersFragment();
            default:
                break;
        }
        return null;
    }


    @Override
    public void hideSearch() {
        searchView.setVisibility(View.INVISIBLE);
    }


    @Override
    public void cancelAddCinema() {
        Fragment addcin = fragmentArray.get(R.id.bar_title_tv_add_cinema);
            if (addcin==null){
                return ;
            }
        Fragment cinemasFragment = fragmentArray.get(R.id.bar_title_tv_view_cinema);
        FragmentTransaction transaction = manager.beginTransaction();
        if (cinemasFragment == null) {
            cinemasFragment = new CinemasFragment();
            fragmentArray.put(R.id.bar_title_tv_view_cinema, cinemasFragment);
            transaction.add(R.id.fragment_container, cinemasFragment);
        }
        transaction.hide(addcin).show(cinemasFragment).commit();
        tvTitle.setText(titleArray.get(R.id.bar_title_tv_view_cinema));
    }

    @Override
    public void saveCinema(Cinema cinema) {
        Fragment addcin = fragmentArray.get(R.id.bar_title_tv_add_cinema);
        if (addcin==null){
            return ;
        }
        Fragment cinemasFragment=fragmentArray.get(R.id.bar_title_tv_view_cinema);
        FragmentTransaction transaction=manager.beginTransaction();
        if (cinemasFragment==null){
             cinemasFragment=CinemasFragment.newInstance(cinema);
            fragmentArray.put(R.id.bar_title_tv_view_cinema,cinemasFragment);
            transaction.add(R.id.fragment_container,cinemasFragment);
        }else {
            ((CinemasFragment)cinemasFragment).save(cinema);
        }
        transaction.hide(addcin).show(cinemasFragment).commit();

        tvTitle.setText(titleArray.get(R.id.bar_title_tv_view_cinema));

    }
    @Override
    public void cancelAddOrder () {
        Fragment addOrders=fragmentArray.get(R.id.bar_title_tv_add_order);
        if (addOrders==null){
            return;
        }
        Fragment orders=fragmentArray.get(R.id.bar_title_tv_my_order);
        FragmentTransaction transaction=manager.beginTransaction();
        if (orders==null){
            orders=new OrdersFragment();
            fragmentArray.put(R.id.bar_title_tv_my_order,orders);
            transaction.add(R.id.fragment_container,orders);
        }
        transaction.hide(addOrders).show(orders).commit();
        tvTitle.setText(titleArray.get(R.id.bar_title_tv_my_order));
    }

    @Override
    public void saveOrder(Order order) {
        Fragment addOrders=fragmentArray.get(R.id.bar_title_tv_add_order);
        if (addOrders==null){
            return;
        }
        Fragment ordersFragment=fragmentArray.get(R.id.bar_title_tv_my_order);
        FragmentTransaction transaction=manager.beginTransaction();
        if (ordersFragment==null){
            ordersFragment=OrdersFragment.newInstance(order);
            fragmentArray.put(R.id.bar_title_tv_my_order,ordersFragment);
            transaction.add(R.id.fragment_container,ordersFragment);
        }else {
            ((OrdersFragment)ordersFragment).save(order);
        }
        transaction.hide(addOrders).show(ordersFragment).commit();
        tvTitle.setText(titleArray.get(R.id.bar_title_tv_my_order));
    }

    @Override
    public void OnCinemaSelected(String cinemaId) {
        Intent intent=new Intent(this, CinemaOrdersActivity.class);
        intent.putExtra(MainActivity.EXTRA_CINEMA_ID,cinemaId);
        startActivity(intent);
    }
}
