package com.ynews.ybb.com.ynews.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ynews.ybb.com.ynews.R;
import com.ynews.ybb.com.ynews.bean.Events;
import com.ynews.ybb.com.ynews.untils.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/4/13 0013.
 */

public class WeatherFragment extends Fragment {

    @BindView(R.id.fragment_im_w)
    ImageView fragment_im_w;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather,null);
        ButterKnife.bind( this , view ) ;

       // String string = getArguments().getString("code");
      //  Glide.with(WeatherFragment.this).load("http://files.heweather.com/cond_icon/"+string+".png").into(fragment_im_w);
        RxBus.with().setEvent(Events.WEATHERF_RAGMENT).onNext(new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                String string = events.getContent();
                Toast.makeText(getActivity(),"string="+ 45646546,Toast.LENGTH_LONG).show();
                //Glide.with(WeatherFragment.this).load("http://files.heweather.com/cond_icon/"+string+".png").into(fragment_im_w);
            }
        }).onError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(getActivity(),"string="+ 5555,Toast.LENGTH_LONG).show();
            }
        }).create();

        return view;
    }
}
