package net.lzzy.cinemanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * @author lzzy_gxy
 * @date 2019/3/26
 * Description:
 */
public class CinemasFragment extends BaseFragment {

    private static final String ARG_NEW_ORDER="argNewOrder";
    private Cinema cinema;
    private ListView listView;
    private List<Cinema> cinemas;
    private CinemaFactory factory = CinemaFactory.getInstance();
    private OnCinemaSelectedListener listener;
    private View empty;
    private GenericAdapter<Cinema> adapter;

    public static CinemasFragment newInstance(Cinema cinema){
        CinemasFragment fragment=new CinemasFragment();
        Bundle args=new Bundle();
        args.putParcelable(ARG_NEW_ORDER,cinema);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void populate() {
        listView = find(R.id.fragment_lv_cinemas);
        empty = find(R.id.activity_cinemas_tv_none);
        listView.setEmptyView(empty);
        cinemas = factory.get();
        adapter = new GenericAdapter<Cinema>(getActivity(), R.layout.cinema_item, cinemas) {
            @Override
            public void populate(ViewHolder viewHolder, Cinema cinema) {
                viewHolder.setTextView(R.id.cinema_item_tv_name, cinema.getName())
                        .setTextView(R.id.cinema_item_tv_location, cinema.getLocation());

            }


            @Override
            public boolean persistInsert(Cinema cinema) {
                return factory.addCinema(cinema);
            }

            @Override
            public boolean persistDelete(Cinema cinema) {
                return factory.deleteCinema(cinema);
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.OnCinemaSelected(adapter
                        .getItem(position).getId().toString());
            }
        });
        if (cinema != null) {
            save(cinema);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnCinemaSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "必须实现onCinemaCreateListener");
        }

    }

    public void save(Cinema cinema) {
        adapter.add(cinema);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_cinemas;
    }

    @Override
    public void search(String kw) {
        cinemas.clear();
        if (TextUtils.isEmpty(kw)) {
            cinemas.addAll(factory.get());
        } else {
            cinemas.addAll(factory.searchCinemas(kw));
        }
        adapter.notifyDataSetChanged();
    }

    public interface OnCinemaSelectedListener {
        void OnCinemaSelected(String cinemaId);
    }
}
