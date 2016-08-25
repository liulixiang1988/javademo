package lx.job.base.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性工具
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class PropertyUtils {
    private Properties properties = null;

    private PropertyUtils(String propertiesFile) throws IOException {
        properties = new Properties();
        InputStream in = new BufferedInputStream(new FileInputStream(propertiesFile));
        properties.load(in);
        in.close();
    }

    public static PropertyUtils createInstance(String propertiesFile) throws IOException{
        return new PropertyUtils(propertiesFile);
    }

    public void destroyInstance(){
        properties = null;
    }

    public String getProperty(String key) {
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        return null;
    }

    public int getPropertyInt(String key) {
        String value = getProperty(key).trim();
        return Integer.valueOf(value);
    }

    public long getPropertyLong(String key) {
        String value = getProperty(key).trim();
        return Long.valueOf(value);
    }

    public boolean getPropertyBool(String key) {
        String value = getProperty(key).trim();
        return Boolean.valueOf(value);
    }


    public String getProperty(String key,String defaultValue) {
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        return defaultValue;
    }

    public int getPropertyInt(String key,int defaultValue) {
        try {
            String value = getProperty(key).trim();
            return Integer.valueOf(value);
        }catch (Exception ex){
            return defaultValue;
        }
    }

    public long getPropertyLong(String key,long defaultValue) {
        try {
            String value = getProperty(key).trim();
            return Long.valueOf(value);
        }catch(Exception ex){
            return defaultValue;
        }
    }

    public boolean getPropertyBool(String key,boolean defaultValue) {
        try {
            String value = getProperty(key).trim();
            return Boolean.valueOf(value);
        }catch (Exception ex){
            return defaultValue;
        }
    }
}
