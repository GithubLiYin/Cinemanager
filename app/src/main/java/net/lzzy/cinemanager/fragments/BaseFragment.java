package net.lzzy.cinemanager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public abstract class BaseFragment extends Fragment {
     public BaseFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(),container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        populate();
    }

    /**
     *
     */
    protected abstract void populate();
    /**
     *
     * @return
     */
    protected abstract int getLayoutRes();

    <T extends View> T find(@IdRes int id){
        return getView().findViewById(id);
    }
    public abstract void search(String kw);
}
