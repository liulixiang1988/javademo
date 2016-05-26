package liulixiang1988;


import okhttp3.OkHttpClient;

/**
 * Created by liulixiang on 16/5/26.
 */
public class Main {
    public static void main(String[] args) {
        OkHttpDemo demo = new OkHttpDemo();
        try {
            demo.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
