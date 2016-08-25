package lx.job.app.common;

import lx.job.base.utils.PathUtils;
import java.io.File;
import java.nio.file.Paths;

/**
 * 配置文件路径
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class AppPath {
    private static final String APP_PROP_NAME = "webapp.properties";
    private static final String C3P0_PROP_NAME="c3p0-config.xml";
    private static final String STATIC_NAME = "view";
    private static final String TEMP_NAME = "temp";
    private static final String DEFAULT_JOB_NAME="default-jobs.json";
    private static final String TEMPLATE_FOLDER = "templates";

    private static String appRoot=null;
    private static String c3p0PropFilePath = null;
    private static String log4jPropFilePath=null;
    private static String appPropFilePath = null;
    private static String staticFilePath = null;
    private static String tempPath = null;
    private static String defaultjobFilePath=null;
    private static String templateFolder = null;


    static{
        appRoot = PathUtils.getProjectPath(AppPath.class);
        appPropFilePath = Paths.get(appRoot,APP_PROP_NAME).toString();
        c3p0PropFilePath = Paths.get(appRoot,C3P0_PROP_NAME).toString();
        defaultjobFilePath = Paths.get(appRoot,DEFAULT_JOB_NAME).toString();
        staticFilePath = Paths.get(appRoot,STATIC_NAME).toString();
        tempPath = Paths.get(appRoot,TEMP_NAME).toString();
        templateFolder = Paths.get(appRoot, TEMPLATE_FOLDER).toString();

        //创建一些虚拟目录
        File tempDir = new File(tempPath);
        if(!tempDir.exists()){
            try {
                tempDir.mkdir();
            }catch (Exception e){
            }
        }

        File templateDir = new File(templateFolder);
        if (!templateDir.exists()) {
            templateDir.mkdir();
        }
    }


    public static String getAppPropFilePath() {
        return appPropFilePath;
    }

    public static String getLog4jPropFilePath() {
        return log4jPropFilePath;
    }

    public static String getAppRoot() {
        return appRoot;
    }

    public static String getStaticFilePath() {
        return staticFilePath;
    }

    public static String getTempPath() {
        return tempPath;
    }

    public static String getTemplateFolder() {
        return templateFolder;
    }

    public static String getC3p0PropFilePath() {
        return c3p0PropFilePath;
    }

    public static String getDefaultjobFilePath() {
        return defaultjobFilePath;
    }

    public static String getTempFilePath(String format, Object... args) {
        return AppPath.getTempPath().concat(String.format(format, args));
    }

    public static String getTempFilePath(String fileName) {
        return AppPath.getTempFilePath("%s", fileName);
    }
}
