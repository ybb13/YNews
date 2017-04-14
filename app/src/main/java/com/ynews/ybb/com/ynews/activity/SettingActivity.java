package com.ynews.ybb.com.ynews.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.blankj.utilcode.utils.SPUtils;
import com.bumptech.glide.Glide;
import com.suke.widget.SwitchButton;
import com.ynews.ybb.com.ynews.R;
import com.ynews.ybb.com.ynews.bean.Events;
import com.ynews.ybb.com.ynews.bean.WeatherBean;
import com.ynews.ybb.com.ynews.fragment.SettingFramgment;
import com.ynews.ybb.com.ynews.fragment.WeatherFragment;
import com.ynews.ybb.com.ynews.net.QClitent;
import com.ynews.ybb.com.ynews.net.QNewsService;
import com.ynews.ybb.com.ynews.untils.Constants;
import com.ynews.ybb.com.ynews.untils.RxBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import rx.Subscription;
import rx.functions.Action1;


/**
 * Created by Administrator on 2017/4/13 0013.
 */

public class SettingActivity extends SwipeBackActivity {
    private FragmentManager fm;
    private Subscription subscription;


//    @BindView(R.id.item_setting_tv)
//    TextView item_setting_tv;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        //
        fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.setting_content,new SettingFramgment()).commit();

        // Glide.with(SettingActivity.this).load("http://files.heweather.com/cond_icon/"+string+".png").into(miv);
        subscription = RxBus.with().setEvent(Events.WEATHER).onNext(new Action1<Events<?>>() {
            @Override
            public void call(final Events<?> events) {
                String city = events.getContent();
                QClitent.getInstance()
                        .create(QNewsService.class, Constants.BASE_WETHER_URL)
                        .getWether(city)
                        .map(new Function<WeatherBean, String>() {
                            @Override
                            public String apply(WeatherBean weatherBean) throws Exception {
                                return weatherBean.getHeWeather5().get(0).getNow().getCond().getCode();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .doOnNext(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                WeatherFragment weatherFragment = new WeatherFragment();
                                fm.beginTransaction().replace(R.id.setting_content,weatherFragment).commit();
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String string) throws Exception {
                               // Glide.with(SettingActivity.this).load("http://files.heweather.com/cond_icon/"+string+".png").into(miv);
                                //WeatherFragment weatherFragment = new WeatherFragment();
//                                Bundle bundle = new Bundle();
//                                bundle.putString("code",string);
//                                weatherFragment.setArguments(bundle);
                                RxBus.getInstance().send(Events.WEATHERF_RAGMENT,string);
                               // fm.beginTransaction().replace(R.id.setting_content,weatherFragment).commit();

                            }
                        });
            }
        })._create();

    }

    @Override
    protected void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }

//    protected void initData()
//    {
//        mDatas = new ArrayList<String>();
//        for (int i = 'A'; i < 'z'; i++)
//        {
//            mDatas.add("" + (char) i);
//        }
//    }
//
//    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
//
//        MyViewHolder.OnSingleItemClickListener mclickListener;
//
//        @Override
//        public void onBindViewHolder(MyViewHolder holder, final int position) {
//            holder.item_setting_tv.setText(mDatas.get(position));
//
//            holder.item_switch_button.setChecked(true);
//
//
//            if(mclickListener!=null){
//                holder.item_switch_button.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
//                        Log.d("com.ynews.ybb.com.ynews","点击按钮看来是性质");
//                        mclickListener.onItemClick(position,isChecked);
//                    }
//                });
//            }
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
//                    SettingActivity.this).inflate(R.layout.item_setting, parent,
//                    false));
//            return holder;
//
//        }
//
//
//        @Override
//        public int getItemCount() {
//            return mDatas.size();
//        }
//
//        public void setClickListener(MyViewHolder.OnSingleItemClickListener listener){
//            this.mclickListener = listener;
//        }
//
//    }
//
//    static class MyViewHolder extends RecyclerView.ViewHolder
//    {
//        TextView item_setting_tv;
//        SwitchButton item_switch_button;
//
//        public MyViewHolder(View view )
//        {
//            super(view);
//            item_setting_tv = (TextView)view.findViewById(R.id.item_setting_tv);
//            item_switch_button = (SwitchButton)view.findViewById(R.id.item_switch_button);
//        }
//
//       public interface OnSingleItemClickListener{
//             void onItemClick(int postion , boolean isChecked);
//    }

    }




