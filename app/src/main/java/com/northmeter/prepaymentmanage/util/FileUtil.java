package com.northmeter.prepaymentmanage.util;

import java.io.File;

/**
 * Created by Lht
 * on 2016/12/19.
 * des: 创建缓存目录
 */
public class FileUtil {
    public static File getcacheDir() {
        File file = new File(MyApplication.getContext().getExternalCacheDir(), "prepayCache");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
