package net.lzzy.cinemanager.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.models.OrderFactory;
import net.lzzy.cinemanager.utils.AppUtils;
import net.lzzy.cinemanager.utils.ViewUtils;
import net.lzzy.simpledatepicker.CustomDatePicker;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 *
 * @author lzzy_gxy
 * @date 2019/3/27
 * Description:
 */
public class AddOrderFragment extends BaseFragment {
    private OnFragmentInteractionListener listener;
    private OnOrderCreateListener orderlistener;
    private ListView listView;
    private OrderFactory factory;
    private LinearLayout addOrderView;
    private Spinner spinner;
    private CustomDatePicker picker;
    private TextView tvDate;
    private EditText edtMovieName;
    private EditText edtPrice;
    private ImageView imgQRCode;
    private List<Cinema> cinemas;
    private boolean isdelete=false;
    private Spinner spCinema;



    @Override
    protected void populate() {
        listener.hideSearch();
        listView = find(R.id.fragment_container);
        addOrderView = find(R.id.fragment_add_order);
        factory = OrderFactory.getInstance();
        tvDate = find(R.id.add_order_select_time);
        edtMovieName = find(R.id.add_order_edt_movie_name);
        edtPrice = find(R.id.add_order_edt_movie_price);
        imgQRCode = find(R.id.add_order_rq_code);
        spCinema =find(R.id.add_order_spinner);
        initDatePicker();
        showDialog();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.frament_add_order;
    }

    @Override
    public void search(String kw) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnFragmentInteractionListener) context;
            orderlistener = (OnOrderCreateListener) context;
        }catch (ClassCastException e){
            throw new  ClassCastException(context.toString()+"必须实现OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
        orderlistener=null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            listener.hideSearch();
        }
    }
    private void initDatePicker() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
        String now = simpleDateFormat.format(new Date());
        tvDate.setText(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        String end = simpleDateFormat.format(calendar.getTime());
        picker = new CustomDatePicker(getActivity(), s -> tvDate.setText(s), now, end);

    }

    private void showDialog() {
        cinemas = CinemaFactory.getInstance().get();
         spCinema.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, cinemas));

        find(R.id.add_order_btn_cancel).setOnClickListener(v -> {
            orderlistener.cancelAddOrder();
        });
        find(R.id.add_order_btn_save).setOnClickListener(v -> {
            Order order = new Order();
            String movie = edtMovieName.getText().toString();
            String time = tvDate.getText().toString();
            if (TextUtils.isEmpty(movie) || TextUtils.isEmpty(time)) {
                Toast.makeText(getActivity(), "信息不全", Toast.LENGTH_SHORT).show();
                return;
            }
            Float price;
            try {
                price = Float.parseFloat(edtPrice.getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "数字格式不正确", Toast.LENGTH_SHORT).show();
                return;
            }
            if (cinemas.size()!=0){
                Cinema cinema = cinemas.get(spCinema.getSelectedItemPosition());
                order.setMovie(movie);
                order.setMovieTime(time);
                order.setPrice(price);
                order.setCinemaId(cinema.getId());
                orderlistener.saveOrder(order);
                edtMovieName.setText("");
                edtPrice.setText("");
            }else {
                Toast.makeText(getActivity(), "请添加影院", Toast.LENGTH_SHORT).show();
            }
        });
        find(R.id.add_order_btn_create).setOnClickListener(v -> {
            String name = edtMovieName.getText().toString();
            String price = edtPrice.getText().toString();
            String location = spCinema.getSelectedItem().toString();
            String time = tvDate.getText().toString();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price) || TextUtils.isEmpty(location) || TextUtils.isEmpty(time)) {
                Toast.makeText(getActivity(), "信息不全", Toast.LENGTH_SHORT).show();
                return;
            }
            String content = "[" + name + "]" + time + "\n" + location + "票价" + price + "元";
            imgQRCode.setImageBitmap(AppUtils.createQRCodeBitmap(content, 200, 200));
        });
        imgQRCode.setOnLongClickListener(v -> {
            Bitmap bitmap = ((BitmapDrawable) imgQRCode.getDrawable()).getBitmap();
            Toast.makeText(getActivity(), AppUtils.readQRCode(bitmap), Toast.LENGTH_SHORT).show();
            return true;
        });

    }


    public interface OnOrderCreateListener{
        void cancelAddOrder();

        /**
         * 点击保存数据持久化
         *
         * @param order
         */
        void saveOrder(Order order);
    }
}
