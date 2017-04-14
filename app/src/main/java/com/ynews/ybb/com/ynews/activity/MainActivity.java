package com.ynews.ybb.com.ynews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.blankj.utilcode.utils.LogUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.ynews.ybb.com.ynews.R;
import com.ynews.ybb.com.ynews.fragment.AboutFragment;
import com.ynews.ybb.com.ynews.fragment.GIFFragment;
import com.ynews.ybb.com.ynews.fragment.JokeFragment;
import com.ynews.ybb.com.ynews.fragment.NewsFragment;
import com.ynews.ybb.com.ynews.fragment.RobotFragment;
import com.ynews.ybb.com.ynews.fragment.TodayFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/10 0010.
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.nv_left)
    NavigationView nvLeft;
    @BindView(R.id.dl_activity_main)
    DrawerLayout dlActivityMain;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private FragmentManager     manager;
    private FragmentTransaction transaction;

    private NewsFragment  newsFragment;      //新闻数据
    private JokeFragment jokeFragment;      //段子
    private RobotFragment robotFragment;    //机器人
    private AboutFragment aboutFragment;    //关于
    private TodayFragment todayFragment;    //历史上的今天
    private GIFFragment gifFragment;        //动态图

    private Fragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        /*************************** 底部bar 设置点击事件 ***************************/
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_news:
                        LogUtils.i("setOnTabSelectListener");
                        if (newsFragment == null) newsFragment = new NewsFragment();
                        switchFragment(newsFragment);
                        nvLeft.setCheckedItem(R.id.nav_news);
                        closeDrawerLayout();
                        break;
                    case R.id.tab_joke:
                        nvLeft.setCheckedItem(R.id.nav_duanzi);
                        if (jokeFragment == null) jokeFragment = new JokeFragment();
                        switchFragment(jokeFragment);
                        closeDrawerLayout();
                        break;
                    case R.id.tab_today:
                        nvLeft.setCheckedItem(R.id.nav_today_of_history);
                        if (todayFragment == null) todayFragment = new TodayFragment();
                        switchFragment(todayFragment);
                        closeDrawerLayout();
                        break;
                    case R.id.tab_robot:
                        nvLeft.setCheckedItem(R.id.nav_robot);
                        if (robotFragment == null) robotFragment = new RobotFragment();
                        switchFragment(robotFragment);
                        closeDrawerLayout();
                        break;
                    case R.id.tab_about:
                        nvLeft.setCheckedItem(R.id.nav_other);
                        if (aboutFragment == null) aboutFragment = new AboutFragment();
                        switchFragment(aboutFragment);
                        closeDrawerLayout();
                        break;
                }
            }
        });
//        //底部bar设置再次点击事件
//        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
//            @Override
//            public void onTabReSelected(@IdRes int tabId) {
//                switch (tabId) {
//                    case R.id.tab_news:
//                        break;
//                    case R.id.tab_joke:
//                        if (gifFragment == null) gifFragment = new GIFFragment();
//                        switchFragment(gifFragment);
//                        break;
//                    case R.id.tab_robot:
//                        break;
//                }
//            }
//        });

        nvLeft.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                nvLeft.setCheckedItem(item.getItemId());
                dlActivityMain.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_news:
                        bottomBar.selectTabAtPosition(0, true);
                        break;
                    case R.id.nav_duanzi:
                        bottomBar.selectTabAtPosition(1, true);
                        break;
                    case R.id.nav_today_of_history:
                        bottomBar.selectTabAtPosition(2, true);
                        break;
                    case R.id.nav_robot:
                        bottomBar.selectTabAtPosition(3, true);
                        break;
                    case R.id.nav_other:
                        bottomBar.selectTabAtPosition(4, true);
                        break;
                    case R.id.nav_location:
                        Intent location = new Intent(MainActivity.this,LocationActivity.class);
                        startActivity(location);
                        break;
                    case R.id.nav_more:
                        Intent setting = new Intent(MainActivity.this,SettingActivity.class);
                        startActivity(setting);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }


    /**
     * 切换Fragment的显示
     *
     * @param target 要切换的 Fragment
     */
    private void switchFragment(Fragment target) {

        // 如果当前的fragment 就是要替换的fragment 就直接return
        if (currentFragment == target) return;

        // 获得 Fragment 事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // 如果当前Fragment不为空，则隐藏当前的Fragment
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        // 如果要显示的Fragment 已经添加了，那么直接 show
        if (target.isAdded()) {
            transaction.show(target);
        } else {
            // 如果要显示的Fragment没有添加，就添加进去
            transaction.add(R.id.fl_content, target, target.getClass().getName());
        }

        // 事务进行提交
        transaction.commit();

        //并将要显示的Fragment 设为当前的 Fragment
        currentFragment = target;
    }

    /**
     * 关闭左侧 侧滑菜单
     */
    private void closeDrawerLayout() {
        if (dlActivityMain.isDrawerOpen(Gravity.LEFT)) {
            dlActivityMain.closeDrawers();
        }
    }
}


