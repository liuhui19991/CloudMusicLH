package com.carporange.cloudmusic.domain;

import java.util.List;

/**
 * Created by liuhui on 2016/8/1.
 */
public class ViewBanner {

    private List<BannersBean> banners;

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public static class BannersBean {
        private String url;
        private String banner;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }
    }
}
