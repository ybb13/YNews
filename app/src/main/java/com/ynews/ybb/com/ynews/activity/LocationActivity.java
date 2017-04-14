package com.ynews.ybb.com.ynews.activity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.ynews.ybb.com.ynews.R;
import com.ynews.ybb.com.ynews.bean.Events;
import com.ynews.ybb.com.ynews.untils.LocationService;
import com.ynews.ybb.com.ynews.untils.MyLocationListener;
import com.ynews.ybb.com.ynews.untils.RxBus;


import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import rx.functions.Action1;


/**
 * Created by Administrator on 2017/4/12 0012.
 */

public class LocationActivity extends SwipeBackActivity{


    public TextView LocationResult;
    private LocationService mlocationService;
    private MyLocationListener mlistener;
    private ImageView miv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        LocationResult = (TextView)findViewById(R.id.act_locaiton);
        miv = (ImageView)findViewById(R.id.iv);
        LocationResult.setMovementMethod(ScrollingMovementMethod.getInstance());

        mlistener = new MyLocationListener();
        mlocationService = new LocationService(LocationActivity.this);
        mlocationService.registerListener(mlistener);
        mlocationService.start();



        RxBus.with().setEvent(Events.LOCATION).onNext(new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                String content = events.getContent();
                LocationResult.setText(content);
            }
        }).create();


    }

    @Override
    protected void onStop() {
        mlocationService.stop();
        mlocationService.unregisterListener(mlistener);
        super.onStop();
    }
}
