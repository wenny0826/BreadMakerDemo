package com.wenny.breadmakerdemo;

import android.content.Context;

import java.io.File;

/**
 * Created by miqi02 on 2016/12/27.
 */

@SuppressWarnings("ALL")
public class FileUtil {

    /**
     * 重新获得文件在外部存储中的路径
     *
     * @param path
     * @return
     */
    public static String getThumbDir(Context context,String path) {
        if (path != null) {
            File file = new File(path);
            //noinspection ConstantConditions
            return context.getExternalCacheDir().getParent() + "/" + file.getName();
        }
        return null;
    }
}
