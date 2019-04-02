package net.lzzy.cinemanager.fragments;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityPicker;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;

/**
 * @author lzzy_gxy
 * @date 2019/3/27
 * Description:
 */
public class AddCinemaFragment extends BaseFragment {
    private String province="广西壮族自治区";
    private String city = "柳州";
    private String area ="鱼峰区";
    private TextView tvArea;
    private EditText edtName;
    private OnFragmentInteractionListener listener;
    private OnCinemaCreateListener cinemalistener;
    private String name;

    @Override
    protected void populate() {
        listener.hideSearch();
        tvArea =
                find(R.id.activity_cinema_dialog_area);
        edtName =
                find(R.id.activity_cinema_dialog_edt);
        find(R.id.activity_cinema_dialog_area).setOnClickListener(v -> {
            JDCityPicker cityPicker = new JDCityPicker();
            cityPicker.init(getActivity());
            cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                @Override
                public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                    AddCinemaFragment.this.province = province.getName();
                    AddCinemaFragment.this.city = city.getName();
                    AddCinemaFragment.this.area = district.getName();
                    String loc = province.getName()
                            + city.getName() + district.getName();
                    tvArea.setText(loc);
                }

                @Override
                public void onCancel() {

                }
            });
            cityPicker.showCityPicker();
        });
        find(R.id.activity_cinema_dialog_yes_btn).setOnClickListener(v -> {
            name = edtName.getText().toString();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getActivity(), "没有名称", Toast.LENGTH_LONG).show();
                return;
            }
            Cinema cinema = new Cinema();
            cinema.setArea(area);
            cinema.setCity(city);
            cinema.setName(name);
            cinema.setProvince(province);
            cinema.setLocation(tvArea.getText().toString());
            edtName.setText("");
            cinemalistener.saveCinema(cinema);

        });
        find(R.id.activity_cinema_dialog_no_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cinemalistener.cancelAddCinema();
            }
        });

    }

    @Override
    public int getLayoutRes() {
        return R.layout.frament_add_cinemas;
    }

    @Override
    public void search(String kw) {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            listener.hideSearch();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnFragmentInteractionListener) context;
            cinemalistener = (OnCinemaCreateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "必须实现onCinemaCreateListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        cinemalistener = null;
    }

    public interface OnCinemaCreateListener {
        /**
         * 取消按钮
         */
        void cancelAddCinema();

        /**
         * 点击保存数据持久化
         *
         * @param cinema
         */
        void saveCinema(Cinema cinema);
    }

}
