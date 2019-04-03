package net.lzzy.cinemanager.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.models.OrderFactory;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * @author lzzy_gxy
 * @date 2019/4/2
 * Description:
 */
public class CinemaOrderFragment extends BaseFragment {

    private static final String AGR_CINEMA_ID = "argCinemaId";
    private String cinemaId;

    public static CinemaOrderFragment newinstence(String cinemaId) {
        CinemaOrderFragment fragment = new CinemaOrderFragment();
        Bundle args = new Bundle();
        args.putString(AGR_CINEMA_ID, cinemaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            cinemaId=getArguments().getString(AGR_CINEMA_ID);
        }
    }

    /**
     * 显示列表
     */
    @Override
    protected void populate() {
        ListView listView = find(R.id.cinema_order_fragment);
        View empty = find(R.id.cinema_order_fragment_none);
        listView.setEmptyView(empty);
        List<Order> orders = OrderFactory.getInstance().getOrdersByCinema(cinemaId);
        GenericAdapter<Order> adapter = new GenericAdapter<Order>(getActivity(), R.layout.cinema_item, orders) {
            @Override
            public void populate(ViewHolder viewHolder, Order order) {
                viewHolder.setTextView(R.id.cinema_item_tv_name, order.getMovie())
                        .setTextView(R.id.cinema_item_tv_location, order.getMovieTime());
            }

            @Override
            public boolean persistInsert(Order order) {
                return false;
            }

            @Override
            public boolean persistDelete(Order order) {
                return false;
            }

        };
        listView.setAdapter(adapter);
    }

    /**
     * @return
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.cinema_orders_frament;
    }

    @Override
    public void search(String kw) {

    }
}
