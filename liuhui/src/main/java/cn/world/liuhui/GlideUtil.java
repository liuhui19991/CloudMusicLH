package cn.world.liuhui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SizeReadyCallback;

/**
 * Created by liuhui on 2016/7/19.
 */
public class GlideUtil {
    private static final String TAG = "GlideUtil";

    /**
     * 在activity中展示
     *
     * @param view
     * @param url
     */
    public static <T> void display(ImageView view, T url) {
        displayUrl(view, url, 0);
    }

    /**
     * @param view         要展示的控件
     * @param url          要展示的地址
     * @param defaultImage 默认显示的图片
     */
    private static <T> void displayUrl(final ImageView view, T url, @DrawableRes int defaultImage) {
        // 不能崩
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        // View还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(defaultImage)//在图片加载完成以前显示占位符
                .crossFade()//淡入淡出动画,默认是调用的,可以传毫秒值减慢/加快,动画事件
                .error(defaultImage)
                .dontAnimate()//不展示淡入淡出效果,直接显示图片
                .centerCrop()//用上这句就可以填充控件
                .into(view)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!view.isShown()) {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * 没有默认图片----有填充样式，低优先级加载
     *
     * @param context
     * @param iv
     * @param path
     * @param center  true 可能会完全填充，但图像可能不会完整显示使用较多 false 自适应填充
     */
    public static void loadLowPriority(Context context, ImageView iv, String path, boolean center) {

        if (center)
            Glide.with(context)
                    .load(path)
                    .priority(Priority.LOW)//设置图片加载的优先级
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存类型
                    .crossFade()//淡入淡出动画,默认是调用的,可以传毫秒值减慢/加快,动画事件
//                    .dontAnimate()//不展示淡入淡出效果,直接显示图片
                    .centerCrop()
                    .into(iv);
    }

    /**
     * 默认的gif图片
     *
     * @param mActivity 上下文
     * @param iv        要展示的控件
     * @param resource  要展示的资源
     */
    public static void displayGif(Activity mActivity, ImageView iv, int resource) {
        Glide.with(mActivity)
                .load(resource)
                .asGif()
                .error(resource)
                .into(iv);
    }

    public static <T> void displayRound(Context context, ImageView imageView, T url) {
        Glide.with(context)
                .load(url)
                .transform(new GlideRoundTransformUtil(context))
                .into(imageView);
    }

    public static void displayCircle(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .transform(new GlideCircleTransfromUtil(context))
                .into(imageView);
    }
}
