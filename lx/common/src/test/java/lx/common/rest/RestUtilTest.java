package lx.common.rest;

import org.junit.Test;

import java.net.URI;

/**
 * Created by Liu Lixiang on 2017/9/15.
 */
public class RestUtilTest {
    @Test
    public void execute() throws Exception {
        URI uri = URI.create("https://www.baidu.com");
        System.out.println(uri.getScheme());
    }

}