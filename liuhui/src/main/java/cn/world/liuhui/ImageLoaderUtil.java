package cn.world.liuhui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SizeReadyCallback;

/**
 * Created by liuhui on 2016/7/19.
 */
public class ImageLoaderUtil {

    public static <T> void display(ImageView view, T url) {
        displayUrl(view, url);
    }

    /**
     * @param view 要展示的控件
     * @param url  要展示的地址
     */
    private static <T> void displayUrl(final ImageView view, T url) {
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
//                .placeholder(defaultImage)//在图片加载完成以前显示占位符
                .crossFade()//淡入淡出动画,默认是调用的,可以传毫秒值减慢/加快,动画事件
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
     * @param center true 可能会完全填充，但图像可能不会完整显示使用较多 false 自适应填充
     */
    public static void loadLowPriority(ImageView imageView, String path, boolean center) {
        if (imageView == null) {
            return;
        }
        Context context = imageView.getContext();
        // View还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        Glide.with(context)
                .load(path)
                .priority(Priority.LOW)//设置图片加载的优先级
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存类型
                .crossFade()//淡入淡出动画,默认是调用的,可以传毫秒值减慢/加快,动画事件
//                    .dontAnimate()//不展示淡入淡出效果,直接显示图片
                .centerCrop()
                .into(imageView);
    }

    /**
     * 默认的gif图片
     *
     * @param imageView 要展示的控件
     * @param resource  要展示的资源
     */
    public static<T> void displayGif(ImageView imageView, T resource) {
        if (imageView == null) {
            return;
        }
        Context context = imageView.getContext();
        // View还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        Glide.with(context)
                .load(resource)
                .asGif()
                .into(imageView);
    }

    public static <T> void displayRound(ImageView imageView, T url) {
        if (imageView == null) {
            return;
        }
        Context context = imageView.getContext();
        // View还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        Glide.with(context)
                .load(url)
                .transform(new GlideRoundTransform(context))
                .into(imageView);
    }

    public static void displayCircle(ImageView imageView, String url) {
        if (imageView == null) {
            return;
        }
        Context context = imageView.getContext();
        // View还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        Glide.with(context)
                .load(url)
                .transform(new GlideCircleTransfrom(context))
                .into(imageView);
    }
}
