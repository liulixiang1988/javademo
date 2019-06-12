package io.liulx.reflect;

import java.io.*;

public class MyClassLoader extends ClassLoader {
    private String path;
    private String classLoaderName;

    public MyClassLoader(String path, String classLoaderName) {
        this.path = path;
        this.classLoaderName = classLoaderName;
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] b = loadClassData(name);
        return defineClass(name, b, 0, b.length);
    }

    //用于加载类文件
    private byte[] loadClassData(String name) {
        name = path + name + ".class";
        byte[] result = null;
        try(InputStream in = new FileInputStream(new File(name));
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int i = 0;
            while((i = in.read()) != -1) {
                out.write(i);
            }
            result = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
