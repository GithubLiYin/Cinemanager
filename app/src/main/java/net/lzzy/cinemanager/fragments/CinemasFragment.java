package net.lzzy.cinemanager.fragments;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.utils.AppUtils;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * @author lzzy_gxy
 * @date 2019/3/26
 * Description:
 */
public class CinemasFragment extends BaseFragment {

    private Cinema cinema;
    private ListView listView;
    private List<Cinema> cinemas;
    private CinemaFactory factory = CinemaFactory.getInstance();
    private View empty;
    private GenericAdapter<Cinema> adapter;
    private double MIX_DISTANCE=100;
    private boolean isDelete;
    private ImageView img;

    public CinemasFragment() {
    }

    public CinemasFragment(Cinema cinema) {
        this.cinema = cinema;
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

                Button btn = viewHolder.getView(R.id.cinema_item_btn);
                btn.setOnClickListener(v -> new AlertDialog.Builder(getActivity())
                        .setTitle("删除确认")
                        .setMessage("要删除订单吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", (dialog, which) ->
                                adapter.remove(cinema)).show());
                viewHolder.getConvertView().setOnTouchListener(new View.OnTouchListener() {

                    private float touchX2;
                    private float touchX1;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        slideToDelete(event, cinema, btn);
                        return true;
                    }

                    private void slideToDelete(MotionEvent event, Cinema cinema, Button btn) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                touchX1 = event.getX();
                                break;
                            case MotionEvent.ACTION_UP:
                                touchX2 = event.getX();
                                if (touchX1 - touchX2 > MIX_DISTANCE) {
                                    if (!isDelete) {
                                        btn.setVisibility(View.VISIBLE);
                                        isDelete = true;
                                    }
                                } else {
                                    if (btn.isShown()) {
                                        btn.setVisibility(View.GONE);
                                        isDelete = false;
                                    } else {
                                        clickOrder(cinema);
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
            }

            private void clickOrder(Cinema cinema) {
                cinema=CinemaFactory.getInstance().getById(cinema.getId().toString());
                String content = "[" + cinema.getName() + "]"+cinema.getLocation();
                View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_qrcode,null);
                ImageView img=view.findViewById(R.id.dialog_qrcode_img);
                img.setImageBitmap(AppUtils.createQRCodeBitmap(content,300,300));
                new AlertDialog.Builder(getActivity())
                        .setView(view).show();
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
        if (cinema != null) {
            save(cinema);
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
}
