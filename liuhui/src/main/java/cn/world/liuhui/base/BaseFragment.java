package cn.world.liuhui.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by luhui on 2016/10/26.
 */

public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment {
    public P mPresenter;
    protected Activity mContext;
    protected View mContentView;
    private boolean isVisible;                  //是否可见状态
    private boolean isPrepared;                 //标志位，View已经初始化完成。
    private boolean isFirstLoad = true;         //是否第一次加载

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) mContentView = inflater.inflate(getLayoutId(), container, false);

        if (mContentView.getParent() != null) {//重要
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            parent.removeView(mContentView);
        }
//        ButterKnife.bind(this, mContentView);
        isFirstLoad = true;
        isPrepared = true;
        lazyLoad();

        return mContentView;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    public abstract P initPresenter();

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        mPresenter = initPresenter();
        initView();
        initListener();
    }

    protected void initListener() {

    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//setUserVisibleHint()方法先于onCreateView执行
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 当前fragment可见时候加载
     */
    protected void onVisible() {
        lazyLoad();
    }
}
