package lx.common.rest;

import lx.common.util.JsonUtil;
import lx.common.util.PathUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

/**
 * Created by Liu Lixiang on 2017/9/15.
 */
public class RestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestUtil.class);

    private static final int TIMEOUT = 60;

    /**
     * TLSv1.2
     */
    public static final String TLSV12 = "TLSv1.2";

    /**
     * TLSv1.1
     */
    public static final String TLSV11 = "TLSv1.1";

    private static final X509TrustManager X509_ALL_TM = new X509TrustManager()
    {
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers()
        {
            return new java.security.cert.X509Certificate[] {};
        }

        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException
        {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException
        {
        }

    };

    /**
     * 执行服务
     *
     * @param bodyMap 请求体
     * @param method 请求方法
     * @param headMap 请求头
     * @param url 请求地址
     * @param protocol 协议
     * @return String
     */
    public String execute(Map<String, Object> bodyMap, String method, Map<String, String> headMap, String url,
                          String protocol)
    {
        RestRequestMessage msg = new RestRequestMessage();
        msg.setHeaders(headMap);
        msg.setHttpMethod(method);
        msg.setPayload(bodyMap);

        return internalExecute(msg, url, protocol);
    }

    /**
     * 执行服务
     *
     * @param message 请求消息
     * @param resourcePath 请求地址
     * @return String
     */
    public String execute(RestRequestMessage message, String resourcePath)
    {
        return internalExecute(message, resourcePath, SSLConnectionSocketFactory.SSL);
    }

    /**
     * 调用GET方法
     *
     * @param url 调用服务地址
     * @param protocol 协议
     * @return T
     */
    public String get(String url, String protocol)
    {
        String json = execute(Collections.<String, Object> emptyMap(),
                HttpGet.METHOD_NAME,
                Collections.<String, String> emptyMap(),
                url,
                protocol);

        return StringUtils.defaultString(json);
    }

    /**
     * 调用GET方法
     *
     * @param url 调用服务地址
     * @param protocol 协议
     * @param clazz 将返回的字符通过JSON转成Bean
     * @return T
     */
    public <T> T get(String url, String protocol, Class<T> clazz)
    {
        T result = JsonUtil.toObject(get(url, protocol), clazz);
        return result;
    }

    private String internalExecute(RestRequestMessage message, String resourcePath, String protocol)
    {
        CloseableHttpClient httpClient = null;
        try
        {
            HttpRequestBase request = buildRequestMessage(message, resourcePath);
            httpClient = createHttpClient(resourcePath, protocol);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        }
        catch (UnsupportedEncodingException e)
        {
            LOGGER.error(e + "");
        }
        catch (URISyntaxException e)
        {
            LOGGER.error(e + "");
        }
        catch (KeyManagementException e)
        {
            LOGGER.error(e + "");
        }
        catch (NoSuchAlgorithmException e)
        {
            LOGGER.error(e + "");
        }
        catch (ClientProtocolException e)
        {
            LOGGER.error(e + "");
        }
        catch (IOException e)
        {
            LOGGER.error(e + "");
        }
        finally
        {
            if (null != httpClient)
            {
                try
                {
                    httpClient.close();
                }
                catch (IOException e)
                {
                    LOGGER.error("Close failed " + e);
                }
            }
        }
        return null;
    }

    /**
     * 执行服务
     *
     * @param message 请求消息
     * @param resourcePath 请求地址
     * @return String
     */
    public String executeTLS(RestRequestMessage message, String resourcePath)
    {
        return internalExecute(message, resourcePath, SSLConnectionSocketFactory.TLS);
    }

    private CloseableHttpClient createHttpClient(String resourcePath, String protocol)
            throws KeyManagementException, NoSuchAlgorithmException
    {
        CloseableHttpClient client = null;
        if (resourcePath.startsWith("https"))
        {
            if (SSLConnectionSocketFactory.SSL.equals(protocol))
            {
                client = createSSLHttpClient();
            }
            else if (SSLConnectionSocketFactory.TLS.equals(protocol) || TLSV12.equals(protocol))
            {

                client = createTLSHttpClient(TLSV12);
            }
        }
        if (null == client)
        {
            client = HttpClients.createDefault();
        }

        return client;
    }

    private CloseableHttpClient createSSLHttpClient() throws KeyManagementException, NoSuchAlgorithmException
    {
        SSLContext sslContext = SSLContexts.custom().useProtocol(SSLConnectionSocketFactory.SSL).build();

        sslContext.init(null, new TrustManager[] {X509_ALL_TM}, new SecureRandom());

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    private CloseableHttpClient createTLSHttpClient(String version)
            throws KeyManagementException, NoSuchAlgorithmException
    {
        SSLContext sslContext = SSLContexts.custom().useProtocol(SSLConnectionSocketFactory.TLS).build();

        sslContext.init(null, new TrustManager[] {X509_ALL_TM}, new SecureRandom());

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] {version}, null,
                NoopHostnameVerifier.INSTANCE);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    private HttpRequestBase buildRequestMessage(RestRequestMessage message, String resourcePath)
            throws URISyntaxException, UnsupportedEncodingException
    {
        HttpRequestBase request = null;

        if (HttpGet.METHOD_NAME.equalsIgnoreCase(message.getHttpMethod()))
        {
            HttpGet httpGet = new HttpGet(resourcePath);
            setParameters(httpGet, message.getParams());
            request = httpGet;
        }
        else if (HttpPost.METHOD_NAME.equalsIgnoreCase(message.getHttpMethod()))
        {
            HttpPost httpPost = new HttpPost(resourcePath);

            if (StringUtils.isEmpty(message.getHeaders().get(HttpHeaders.CONTENT_TYPE)))
            {
                httpPost.setEntity(new StringEntity(URLDecoder.decode(getGsonPayLoad(message), Consts.UTF_8.name()),
                        ContentType.APPLICATION_JSON));
            }
            else if (message.getHeaders()
                    .get(HttpHeaders.CONTENT_TYPE)
                    .equalsIgnoreCase("application/x-www-form-urlencoded"))
            {
                httpPost.setEntity(new StringEntity(URLDecoder.decode(getGsonPayLoad(message), Consts.UTF_8.name()),
                        ContentType.APPLICATION_FORM_URLENCODED));
            }
            else
            {
                httpPost.setEntity(new StringEntity(URLDecoder.decode(getGsonPayLoad(message), Consts.UTF_8.name()),
                        ContentType.APPLICATION_JSON));
            }

            request = httpPost;
        }
        else if (HttpPut.METHOD_NAME.equalsIgnoreCase(message.getHttpMethod()))
        {
            HttpPut httpPut = new HttpPut(resourcePath);
            httpPut.setEntity(new StringEntity(URLDecoder.decode(getGsonPayLoad(message), Consts.UTF_8.name()),
                    ContentType.APPLICATION_JSON));
            request = httpPut;
        }
        else if (HttpDelete.METHOD_NAME.equalsIgnoreCase(message.getHttpMethod()))
        {
            HttpDelete httpDelete = new HttpDelete(resourcePath);
            setParameters(httpDelete, message.getParams());
            request = httpDelete;
        }
        else
        {
            String msg = message.getHttpMethod() + " is not a valid HTTP method";
            throw new IllegalArgumentException(msg);
        }

        // 设置头部
        setHeaders(request, message.getHeaders());

        return request;
    }

    private String getGsonPayLoad(RestRequestMessage message) throws UnsupportedEncodingException
    {
        return PathUtils.jsonPathFormat(URLEncoder.encode(message.getPayLoad(), Consts.UTF_8.name()));
    }

    private void setParameters(HttpRequestBase httpRequest, Map<String, String> parameters) throws URISyntaxException
    {
        if (!parameters.isEmpty())
        {
            URIBuilder uriBuilder = new URIBuilder(httpRequest.getURI());

            for (Map.Entry<String, String> entry : parameters.entrySet())
            {
                uriBuilder.addParameter(entry.getKey(), entry.getValue());
            }

            httpRequest.setURI(uriBuilder.build());
        }
    }

    private void setHeaders(HttpRequestBase httpRequest, Map<String, String> headers)
    {
        if (!headers.isEmpty())
        {
            for (Map.Entry<String, String> entry : headers.entrySet())
            {
                httpRequest.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }
}
