package com.myutils.core.okhttp.cookies;

import java.util.List;

import com.myutils.core.logger.L;

import android.content.Context;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动管理Cookies
 */
public class CookiesManager implements CookieJar {

    private PersistentCookieStore cookieStore;

    public CookiesManager(Context context) {
        cookieStore = new PersistentCookieStore(context);
    }


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        //L.i("saveFromResponse =============" + url + "  ");
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
               // L.i("saveFromResponse =============" + item + "  " + url);
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }


    public PersistentCookieStore getCookieStore() {
        return cookieStore;
    }


    public void setCookieStore(PersistentCookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }


}
