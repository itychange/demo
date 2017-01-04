package mrblack.myapplication.utils;

import android.text.TextUtils;

/**
 * Url util
 *
 * @author TuanNT
 */
public class UrlUtil {
    private UrlUtil() {
        // no instance¬
    }

    public static String formatPhotoUrl(String url) {
        return TextUtils.isEmpty(url) ? null : url;
    }
}
