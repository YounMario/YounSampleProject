package com.younchen.younsampleproject.commons.utils;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/4/14.
 */

public class ImageUtils {

    public static Bitmap copy(Bitmap source) {
        return source.copy(source.getConfig(), true);
    }
}
