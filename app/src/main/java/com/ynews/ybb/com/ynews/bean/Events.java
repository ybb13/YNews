package com.ynews.ybb.com.ynews.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class Events<T> {

    //所有事件的CODE
    public static final int NEWS = 1; //新闻
    public static final int WEATHER = 2; //天气
    public static final int LOCATION = 3; //定位
    public static final int WEATHERF_RAGMENT = 4; //定位
    public static final int OTHER = 21; //其它事件

    //枚举
    @IntDef({NEWS, WEATHER ,OTHER ,WEATHERF_RAGMENT,LOCATION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventCode {}


    public @Events.EventCode int code;
    public T content;

    public static <O> Events<O> setContent(O t) {
        Events<O> events = new Events<>();
        events.content = t;
        return events;
    }

    public <T> T getContent() {
        return (T) content;
    }

}
