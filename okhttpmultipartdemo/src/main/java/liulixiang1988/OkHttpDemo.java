package liulixiang1988;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by liulixiang on 16/5/26.
 */
public class OkHttpDemo {
    private String _multiPartFormContentType = "----IScanningFormBoundarySMFEtUYQG6r5B920";
    // 写入字符串的Key
    private String _stringKeyHeader = "\r\n--%s" +
            "\r\nContent-Disposition: form-data; name=\"%s\"" +
            "\r\n\r\n%s";
    private String _filePartHeader = "\r\n--%s" +
            "\r\nContent-Disposition: form-data; name=\"%s\"; filename=\"%s\"" +
            "\r\nContent-Type: application/octet-stream\r\n\r\n";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private MediaType OCT_TYPE = MediaType.parse("application/octet-stream");

    private final OkHttpClient client = new OkHttpClient();

    public void run() throws Exception {

        String path = getFilePath("static/1.jpg");
        System.out.println(path);
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBody.Builder()
                //.setType(MultipartBody.PARALLEL)
                .addFormDataPart("title", "Square Logo")
                .addFormDataPart("filename", "manage.py",
                        RequestBody.create(OCT_TYPE, getFileFromURL("static/manage.py")))
                .addFormDataPart("image", "logo-square.png",
                        RequestBody.create(MEDIA_TYPE_PNG, getFileFromURL("static/1.jpg")))
                .build();

        Request request = new Request.Builder()
                .addHeader("Content-Type", "multipart/form-data; boundary=" + _multiPartFormContentType)
                .url("http://httpbin.org")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());
    }

    private String addText2Http(String key, String value)
    {
        String s = String.format(_stringKeyHeader, _multiPartFormContentType, key, value);
        System.out.println(s);
        return s;
    }

    private File getFileFromURL(String filename) {
        URL url = this.getClass().getClassLoader().getResource(filename);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        } finally {
            return file;
        }
    }

    private String getFilePath(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource(filename).getPath();
    }

}
