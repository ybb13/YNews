package com.ynews.ybb.com.ynews.untils;


import android.util.Log;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.ynews.ybb.com.ynews.bean.Events;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.SerializedSubject;


/**
 * Created by Administrator on 2017/4/11 0011.
 */

/**
 * 使用RxJava代替EventBus的方案
 *
 * @author thanatos
 * @create 2017-04-011
 **/
public class RxBus {

    private static RxBus rxBus;
    private final Subject<Events<?>, Events<?>> _bus = new SerializedSubject<>(PublishSubject.<Events<?>>create());

    private RxBus() {
    }

    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    public void send(Events<?> o) {
        _bus.onNext(o);
    }

    public void send(@Events.EventCode int code, Object content) {
        Events<Object> event = new Events<>();
        event.code = code;
        event.content = content;
        send(event);
    }

    public Observable<Events<?>> toObservable() {
        return _bus;
    }

    public static SubscriberBuilder with(){
        return new SubscriberBuilder();
    }


    public static class SubscriberBuilder {

        private int event;
        private Action1<? super Events<?>> onNext;
        private Action1<Throwable> onError;


        public SubscriberBuilder setEvent(@Events.EventCode int event) {
            this.event = event;
            return this;
        }


        public SubscriberBuilder onNext(Action1<? super Events<?>> action) {
            this.onNext = action;
            return this;
        }

        public SubscriberBuilder onError(Action1<Throwable> action) {
            this.onError = action;
            return this;
        }


        public void create() {
            _create();
        }

        public Subscription _create() {

                return RxBus.getInstance().toObservable()
                        .filter(new Func1<Events<?>, Boolean>() {
                            @Override
                            public Boolean call(Events<?> events) {
                                return events.code == event;
                            }
                        })   //过滤 根据code判断返回事件
                        .subscribe(onNext, onError == null ? new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        } : onError);


        }
    }
}