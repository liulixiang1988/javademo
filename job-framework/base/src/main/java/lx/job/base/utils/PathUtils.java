package lx.job.base.utils;

import java.io.File;
import java.nio.file.Paths;

/**
 * 路径工具
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class PathUtils {
    /**
     * 基路径
     * @param valueType 指定的项目路径
     * @param <T>
     * @return
     */
    public static <T> String getProjectPath(Class<T> valueType) {
        java.net.URL url = valueType.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        try {
            filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (filePath.endsWith(".jar"))
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        java.io.File file = new java.io.File(filePath);
        filePath = file.getAbsolutePath();
        return filePath;
    }

    public static String getPath(String root, String... more) {
        return Paths.get(root, more).toString();
    }

    public static boolean existFile(String root, String... more) {
        String path = getPath(root, more);
        File f = new File(path);
        return f.exists() && !f.isDirectory();
    }

    public static String getFileName(String path) {
        return Paths.get(path).getFileName().toString();
    }
}
