package com.clientBase.observable;

import java.util.Observable;

public class CarObservable extends Observable {

    //单例
    private static CarObservable instance = null;

    public static CarObservable getInstance() {

        if (null == instance) {
            instance = new CarObservable();
        }
        return instance;
    }

    //通知观察者更新数据
    public void notifyStepChange(String msg) {
        setChanged();//设置changeFlag
        notifyObservers(msg);//通知观察者
    }

}