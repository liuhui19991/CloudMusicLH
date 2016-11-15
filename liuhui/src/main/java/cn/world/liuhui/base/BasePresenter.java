package cn.world.liuhui.base;

import android.app.Activity;

/**
 * Created y liuhui on 2016/10/24.
 */

public abstract class BasePresenter<V> {
    public V mView;//这里view必须使用public才能在presenter中调用他的方法使得在activity中使用
    public Activity mContext;
    public void setView(V view) {
        mView = view;
        requestMessage();//加载数据
    }

    protected abstract void requestMessage();


    public void dettach() {

    }
}
