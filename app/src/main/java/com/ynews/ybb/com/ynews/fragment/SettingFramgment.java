package com.ynews.ybb.com.ynews.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.suke.widget.SwitchButton;
import com.ynews.ybb.com.ynews.R;
import com.ynews.ybb.com.ynews.activity.LocationActivity;
import com.ynews.ybb.com.ynews.untils.LocationService;
import com.ynews.ybb.com.ynews.untils.MyLocationListener;
import com.ynews.ybb.com.ynews.untils.SharedPrefsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/13 0013.
 */

public class SettingFramgment extends Fragment {

    @BindView(R.id.ischeck_jpush)
    SwitchButton ischeck_jpush;
    @BindView(R.id.setting_wheather)
    TextView setting_wheather;
    private LocationService mlocationService;
    private MyLocationListener mlistener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting,null);
        ButterKnife.bind( this , view ) ;

        checkJupsh();

        setting_wheather.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mlistener = new MyLocationListener();
                mlocationService = new LocationService(getActivity());
                mlocationService.registerListener(mlistener);
                mlocationService.start();
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     *

     * @param

     * @author Administrator

     * @date   2017/4/13 0013 15:10

     **/

    public void checkJupsh(){

        boolean ischeck = SharedPrefsUtil.getValue(getActivity(),"ischeck_jpush",true);

        ischeck_jpush.setChecked(ischeck);

        ischeck_jpush.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ischeck_jpush.setChecked(isChecked);
                SharedPrefsUtil.putValue(getActivity(),"ischeck_jpush",isChecked);
            }
        });
    }
}
